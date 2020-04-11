package com.example.mybatisplus.controller;


import com.example.mybatisplus.service.impl.MinioTemplate;
import com.example.mybatisplus.util.PdfKeywordCoordUtil;
import com.example.mybatisplus.util.SignatureSetUtil;
import com.pingan.traffic.accident.document.domain.SignatureInfo;
import com.pingan.traffic.accident.document.utils.SignatureUtil;
import io.minio.ObjectStat;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/v1.0")
public class DocumentController {
    @Resource
    private MinioTemplate minioTemplate;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, String document) {
        String fileName = file.getOriginalFilename();
        try {
            minioTemplate.putObject(document, fileName, file.getInputStream());
        } catch (Exception e) {
            return "上传失败";
        }
        return "上传成功";
    }

    @GetMapping("/select")
    public String documentUrl(String document, String fileName) {
        return minioTemplate.getUrl(document, fileName);
    }

    @PostMapping("/uploadDocToPdf")
    public void docToPdf(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            //上传文件名
            String originalFilename = file.getOriginalFilename();
            String realPath = request.getSession().getServletContext().getRealPath("/");
            XWPFDocument document;
            InputStream doc = file.getInputStream();
            document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            //转pdf文件名
            String pdfName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + ".pdf";
            //临时文件
            String tempPath = realPath + pdfName;
            OutputStream outputStream = new FileOutputStream(tempPath);
            PdfConverter.getInstance().convert(document, outputStream, options);
            //上传minio文件服务
            File file1 = new File(tempPath);
            FileInputStream fileInputStream = new FileInputStream(file1);
            minioTemplate.putObject("document", pdfName, fileInputStream);
            doc.close();
            outputStream.close();
            fileInputStream.close();
            //删除临时文件
            if (file1.exists()) {
                file1.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf签名设置 功能：根据文书类型设置签名域 将可签名的pdf更新到minio
     *
     * @param bucketName    bucket名称
     * @param fileName      文件名称
     * @param directionType 签名域设置定位，用于定位签名相对坐标方向：1：相对坐标向上签名，2:相对坐标向下签名 3：相对坐标向左签名 4:相对坐标向右签名
     * @param signatureNum  设置签名数量
     * @param documentType  文书类型 用于判定搜索坐标关键字
     * @return minio文件服务访问url
     */
    @GetMapping("/signatureSet")
    public String signatureSet(String bucketName, String fileName, int directionType, int signatureNum, int documentType, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        //minio获取流
        InputStream inputStream = minioTemplate.getObject(bucketName, fileName);
        FileInputStream d = null;
        FileInputStream fileInputStream = null;
        String temp = null;
        String desPath = null;
        try {
            ObjectStat objectInfo = minioTemplate.getObjectInfo(bucketName, fileName);
            //创建临时文件
            temp = realPath + objectInfo.name();
            createTempFile(inputStream, temp);
            byte[] pdfData = new byte[(int) objectInfo.length()];
            //获取临时文件流
            fileInputStream = new FileInputStream(temp);
            fileInputStream.read(pdfData);
            //todo 需要维护签名文书 签名关键字
            List<float[]> coord = PdfKeywordCoordUtil.findKeywordPostions(pdfData, "当事人或代理人");
            desPath = SignatureSetUtil.signatureSet(coord.get(0), signatureNum, directionType, temp);
            //更新可签名的pdf到 minio文件服务
            d = new FileInputStream(new File(desPath));
            minioTemplate.putObject(bucketName, fileName, d);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                d.close();
                fileInputStream.close();
                inputStream.close();
                File tem = new File(temp);
                if (tem.exists()) {
                    tem.delete();
                }
                File des = new File(desPath);
                if (des.exists()) {
                    tem.delete();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    @PostMapping("signature")
    public void signature(@RequestParam("file") MultipartFile file, String bucketName,String fileName,HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/");
        InputStream inputStream = minioTemplate.getObject(bucketName, fileName);
        String temp = null;
        //minio获取流
        try {
            ObjectStat objectInfo = minioTemplate.getObjectInfo(bucketName, fileName);
            //创建临时文件
            temp = realPath + objectInfo.name();
            createTempFile(inputStream, temp);
            byte[] bytes = file.getBytes();
            SignatureInfo signature1 = SignatureUtil.signatureInfo("signature1", bytes);
            String desPath = SignatureUtil.sign(temp, signature1);
            FileInputStream fileInputStream = new FileInputStream(new File(desPath));
            minioTemplate.putObject(bucketName, fileName, fileInputStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTempFile(InputStream is, String fileName) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            int len = -1;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
