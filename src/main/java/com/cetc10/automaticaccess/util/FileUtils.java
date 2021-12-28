package com.cetc10.automaticaccess.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

public class FileUtils {
    /**
     * 验证License 若不验证则文档会有水印产生
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * word转pdf
     * @param wordPath
     * @param pdfPath
     * @return
     */
    public static boolean wordToPdf(String wordPath, String pdfPath) {
        try {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            getLicense();
        }catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(wordPath);  // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 读取word文本
     * @param in
     * @return
     */
    public static String readWordToString(InputStream in) {
        try {
            // 验证License，不验证会有水印产生
            getLicense();
        }catch (Exception e) {
            e.printStackTrace();
        }
        String text = null;
        try {
            Document document = new Document(in);
            text = document.getText();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    /**
     * 读取txt文本
     * @param inputStream
     * @return
     */
    public static String readTxtToString(InputStream inputStream) {
        String content = "";
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null){
                if (StringUtils.isBlank(content)) {
                    content = lineStr;
                } else {
                    content = content + "\n" + lineStr;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}
