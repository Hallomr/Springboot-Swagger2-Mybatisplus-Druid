package com.example.mybatisplus.util;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SignatureSetUtil {

    /**
     * 生成签名域pdf
     * 定位签名 float [ ] : float[0]:pageNum float[1]:x float[2]:y
     * signatureNum 签名数
     * documentType 文书类型，用于定位签名相对坐标方向：1：相对坐标向上签名，2:相对坐标向下签名 3：相对坐标向左签名 4:相对坐标向下签名
     * */
    public static String signatureSet( float[] coord,int signatureNum,int documentType,String temp){
        try {
            //初始签名域名称
            String signatureName = "signature";
            //签名域宽高
            int width = 64, height = 16;
            //初始化PDF阅读器
            PdfReader pdfReader = new PdfReader(new FileInputStream(temp));
            //pdf临时文件路径
            String desPath = temp.replace(".pdf", "_temp.pdf");
            //创建PdfStamper对象
            FileOutputStream fileOutputStream = new FileOutputStream(desPath);
            PdfStamper ps = new PdfStamper(pdfReader,fileOutputStream);

            for (int i=1;i<=signatureNum;i++) {
                // 创建数组签名域
                Rectangle areaSignatureRect = getRectangle(width, height, coord[1], coord[2],documentType,i);
                int pageNo = (int) coord[0]; // PDF 文件的页码从 1 开始，而不是 0
                PdfFormField pdfFormField = PdfFormField.createSignature(ps.getWriter());
                pdfFormField.setFieldName(signatureName+i); // 签名域标识
                pdfFormField.setPage(pageNo);
                pdfFormField.setWidget(areaSignatureRect, null);
                ps.addAnnotation(pdfFormField, pageNo);
            }

            //必须关闭，否则创建的PDF为0KB
            ps.close();
            fileOutputStream.close();
            pdfReader.close();
            return desPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //设置签名坐标
    private static Rectangle getRectangle(int width, int height, float x, float y,int documentType,int i) {
        if(documentType == 1){
            return new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
                    x, // 点1 x坐标
                    y + height*i, // 点1 y坐标
                    x + width, // 点2 x坐标
                    y + height*(i+1) // 点2 y坐标
            );
        }else if(documentType == 2){
            return new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
                    x, // 点1 x坐标
                    y - height*i, // 点1 y坐标
                    x + width, // 点2 x坐标
                    y - height*(i+1) // 点2 y坐标
            );
        }else if(documentType == 3){
            return new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
                    x-width*i, // 点1 x坐标
                    y, // 点1 y坐标
                    x + width*(1-i), // 点2 x坐标
                    y + height // 点2 y坐标
            );
        }else if(documentType == 4){
            return new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
                    x+width*i, // 点1 x坐标
                    y, // 点1 y坐标
                    x + width*(1+i), // 点2 x坐标
                    y + height // 点2 y坐标
            );
        }
        return null;
    }
}
