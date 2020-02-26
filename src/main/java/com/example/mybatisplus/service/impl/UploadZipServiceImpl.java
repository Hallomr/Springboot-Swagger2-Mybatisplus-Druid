package com.example.mybatisplus.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.mybatisplus.common.listener.UploadDataListener;
import com.example.mybatisplus.config.FileConfig;
import com.example.mybatisplus.entity.UserEntity;
import com.example.mybatisplus.service.UploadZipService;
import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
@Service
@Slf4j
public class UploadZipServiceImpl implements UploadZipService {
    @Autowired
    private UserService userService;
    /**
     *返回的是批次号
     *同时我另外开了线程处理zip文件里面的图片和excel，
     */
    @Override
    public String uploadZipFilesAndParse(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String fileType = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        String uuid = UUID.randomUUID().toString();
        //判断文件是不是zip类型
        if(fileType.equals("zip")){

            //FileConfig.localtion是配置文件和config类生产的，我会在评论区附上这些代码，测试demo的时候大家可以直接把FileConfig.localtion替换成D:/test
            //String desPath = FileConfig.localtion + File.separator + uuid.replaceAll("-", "");
            String desPath = "D:/zip" + File.separator + uuid.replaceAll("-", "");

            //下面这三行的代码就是把上传文件copy到服务器，一定不要遗漏了。
            //遗漏了这个代码，在本地测试环境不会出问题，在服务器上一定会报没有找到文件的错误
            String savePath = FileConfig.localtion + File.separator;
            File savefile = new File(savePath+filename);
            if (!savefile.getParentFile().exists()) {
                savefile.getParentFile().mkdirs();
            }
            savefile.delete();
            //file.transferTo(savefile);

            FileUtil fileUtil = new FileUtil();
            //解压zip文件，我是写在公共类里面，FileUtil类代码评论区见
            FileUtil.unZip(file, desPath,savePath);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<File> fileList = new ArrayList<>();
                    fileList = fileUtil.getSubFiles(desPath,fileList);
                    for (File oneFile : fileList){
                        if (oneFile.getName().toLowerCase().endsWith(".xls") || oneFile.getName().toLowerCase().endsWith(".xlsx") ) {
                            try {
                                //解析处理excel文件
                                parseExcelFile(oneFile,uuid);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }else if(oneFile.getName().toLowerCase().endsWith(".jpg")) {
                            try {
                                //解析处理图片文件
                                parseImageFile(oneFile,uuid);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }

                    //最后要删除文件，删除文件的方法见评论区FileUtil类
                    FileUtil.clearFiles(desPath);

                }
            }).start();

        }
        return uuid;
    }

    private void parseExcelFile(File file,String uuid) throws Exception {
        EasyExcel.read(new FileInputStream(file), UserEntity.class, new UploadDataListener(userService)).sheet().doRead();
        /*log.info("file name:"+file.getName());
        FileInputStream is = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        int firstRowIndex = sheet.getFirstRowNum() + 1;
        int lastRowIndex = sheet.getLastRowNum();

        List<VapBatchPersonInfo> batchPersonList = new ArrayList<>();
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {

            VapBatchPersonInfo vapBatchPersonInfo  = new VapBatchPersonInfo();
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(KEY_CREATED_ID, createdId);

                Cell resultCell = row.createCell(lastCellIndex);
                Cell msgCell = row.createCell(lastCellIndex + 1);
                Boolean flag = false;
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    String titleName = sheet.getRow(0).getCell(cIndex).toString();
                    String checkTitleName = checkTitleName(cIndex, titleName);
                    if (!"SUCCESS".equals(checkTitleName)) {
                        msgCell.setCellValue(checkTitleName);
                        resultCell.setCellValue("Failed");
                        flag = true;
                        break;
                    }
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        jsonObject.put(titleName, cell.toString());
                    }

                }
                if (flag) {
                    rIndex = 0;
                    lastRowIndex = 0;
                } else {
                    vapBatchPersonInfo.setBatchNo(uuid);
                    vapBatchPersonInfo.setName(jsonObject.getString("fullName"));
                    vapBatchPersonInfo.setImageName(jsonObject.getString("imageName"));
                    vapBatchPersonInfo.setConfidenceThreshold(jsonObject.getString("confidenceThreshold"));
                    vapBatchPersonInfo.setCreatedId(jsonObject.getString("createdId"));
                    vapBatchPersonInfo.setIdentityNo(jsonObject.getString("identityNo"));
                    vapBatchPersonInfo.setCreatedDate(new Date());
                    vapBatchPersonInfo.setLastUpdatedId(jsonObject.getString("createdId"));
                    vapBatchPersonInfo.setLastUpdatedDate(new Date());
                    vapBatchPersonInfo.setStatus(TaskStatus.RUNNING);
                    batchPersonList.add(vapBatchPersonInfo);
                }
            }
        }
        batchPersonInfoRepository.saveAll(batchPersonList);*/

    }

    private void parseImageFile(File file, String uuid) throws Exception {

        /*String imgStr ="";
        //        FileInputStream fis = new FileInputStream(file);
        //        byte[] buffer = new byte[(int) file.length()];
        //        int offset = 0;
        //        int numRead = 0;
        //        while (offset < buffer.length && (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
        //            offset += numRead;
        //        }
        //        if (offset != buffer.length) {
        //            throw new IOException("Could not completely read file " + file.getName());
        //        }
        //        fis.close();
        //        Base64 encoder = new Base64();
        //        imgStr = Base64.encodeBase64String(buffer);
        //        imgStr.length();
        //        log.info("file name:"+file.getName());
        //        //        LogUtils.info("file imgStr:"+imgStr);
        //        //        LogUtils.info("file imgStr.length:"+imgStr.length());*/

    }
}
