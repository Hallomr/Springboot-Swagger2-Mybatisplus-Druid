package com.pingan.traffic.accident.document.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.pingan.traffic.accident.document.domain.SignatureInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * 替换表单签名
 */
public class SignatureUtil {
    //将证书文件放入指定路径，并读取keystore ，获得私钥和证书链
    //这里我是自己签的一个证书，密码为123456
    /**
     *
     * 正式环境可以用如下命令签署一个p12证书 （前提是确保当前环境已安装jdk，并配置了环境变量）
     * keytool -genkeypair -alias serverkey -keyalg RSA -keysize 2048 -validity 3650 -keystore D:\tmp\p12test.keystore
     *
     * keytool -exportcert -keystore  D:\tmp\p12test.keystore -file D:\tmp\p12test.cer -alias serverkey
     *
     * keytool -importkeystore -srckeystore E:\temp\p12test.keystore -destkeystore E:\temp\p12test.p12 -srcalias serverkey -destalias serverkey -srcstoretype jks -deststoretype pkcs12 -noprompt
     *
     *
     */
    public static final char[] PASSWORD = "123456".toCharArray();//keystory密码

    public static SignatureInfo signatureInfo(String fieldName,byte[] bytes){
        try {
            String pkPath = "/signature/signature.p12";
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(pkPath), PASSWORD);
            String alias = ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
            Certificate[] chain = ks.getCertificateChain(alias);
            //封装签章信息
            SignatureInfo info = new SignatureInfo();
            info.setReason("事故处理");
            info.setLocation("签名");
            info.setPk(pk);
            info.setChain(chain);
            info.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            info.setDigestAlgorithm(DigestAlgorithms.SHA1);
            info.setFieldName(fieldName);//fieldName 签名域名称
            info.setImage(Image.getInstance(bytes));
            info.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 单多次签章通用
     * @param src
     * @param target
     * @param signatureInfos
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws DocumentException
     */
    public static String sign(String src,  SignatureInfo... signatureInfos){
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(src);
            for (SignatureInfo signatureInfo : signatureInfos) {
                ByteArrayOutputStream tempArrayOutputStream = new ByteArrayOutputStream();
                PdfReader reader = new PdfReader(inputStream);
                //创建签章工具PdfStamper ，最后一个boolean参数是否允许被追加签名
                PdfStamper stamper = PdfStamper.createSignature(reader, tempArrayOutputStream, '\0', null, true);
                // 获取数字签章属性对象
                PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
                appearance.setReason(signatureInfo.getReason());
                appearance.setLocation(signatureInfo.getLocation());
                //设置签名的签名域名称，多次追加签名的时候，签名预名称不能一样，图片大小受表单域大小影响（过小导致压缩）
                appearance.setVisibleSignature(signatureInfo.getFieldName());
                //读取图章图片
                appearance.setSignatureGraphic(signatureInfo.getImage());
                appearance.setCertificationLevel(signatureInfo.getCertificationLevel());
                //设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
                appearance.setRenderingMode(signatureInfo.getRenderingMode());
                // 摘要算法
                ExternalDigest digest = new BouncyCastleDigest();
                // 签名算法
                ExternalSignature signature = new PrivateKeySignature(signatureInfo.getPk(), signatureInfo.getDigestAlgorithm(), null);
                // 调用itext签名方法完成pdf签章
                MakeSignature.signDetached(appearance, digest, signature, signatureInfo.getChain(), null, null, null, 0, signatureInfo.getSubfilter());
                //定义输入流为生成的输出流内容，以完成多次签章的过程
                inputStream = new ByteArrayInputStream(tempArrayOutputStream.toByteArray());
                result = tempArrayOutputStream;
            }
            //创建临时文件
            String desPath = src.replace(".pdf", "_temp.pdf");
            outputStream = new FileOutputStream(new File(desPath));
            outputStream.write(result.toByteArray());
            outputStream.flush();
            return desPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=outputStream){
                    outputStream.close();
                }
                if(null!=inputStream){
                    inputStream.close();
                }
                if(null!=result){
                    result.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
