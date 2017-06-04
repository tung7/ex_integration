package com.tung7.ex.repository.base.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 根据文件 获取文件类型
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/27.
 * @update
 */

public class FileTypeHelper {
    public static final HashMap<String, String> fileTypes = new HashMap<String, String>() {{
        put("FFD8FF", "jpg");
        put("89504E47", "png");
        put("47494638", "gif");
        put("49492A00", "tif");
        put("424D", "bmp");

        put("41433130", "dwg"); // CAD
        put("38425053", "psd");
        put("7B5C727466", "rtf"); // 日记本
        put("3C3F786D6C", "xml");
        put("68746D6C3E", "html");
        put("44656C69766572792D646174653A", "eml"); // 邮件
        put("D0CF11E0", "doc");
        put("5374616E64617264204A", "mdb");
        put("252150532D41646F6265", "ps");
        put("255044462D312E", "pdf");
        put("504B0304", "docx");
        put("52617221", "rar");
        put("57415645", "wav");
        put("41564920", "avi");
        put("2E524D46", "rm");
        put("000001BA", "mpg");
        put("000001B3", "mpg");
        put("6D6F6F76", "mov");
        put("3026B2758E66CF11", "asf");
        put("4D546864", "mid");
        put("1F8B08", "gz");
        put("", "");
    }};

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        String key = getFileHeader(filePath);
        System.out.println(key);
        return fileTypes.get(key);
    }

    /**
     * 根据字节数组
     */
    public static String getFileType(byte[] data) {
        return fileTypes.get(getFileHeader(data));
    }

    /**
     * 根据字节流
     */
    public static String getFileType(InputStream inputStream) {
        return fileTypes.get(getFileHeader(inputStream));
    }

    /**
     * 根据文件对象
     * @param f
     * @return
     */
    public static String getFileType(File f) {
        return fileTypes.get(getFileHeader(f));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    private static String getFileHeader(String filePath) {
        return getFileHeader(new File(filePath));
    }

    private static String getFileHeader(byte[] data) {
        byte[] head = Arrays.copyOfRange(data, 0 ,4);
        return  bytesToHexString(head);
    }

    private static String getFileHeader(InputStream is) {
        byte[] b = new byte[4];
        try {
            is.read(b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 根据文对象获取文件头信息
     *
     * @param file
     *            文件
     * @return 文件头信息
     */
    private static String getFileHeader(File file) {
        String value = null;
        try {
            value = getFileHeader(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src
     *            要读取文件头信息的文件的byte数组
     * @return 文件头信息
     *下面这段代码就是用来对文件类型作验证的方法，
    第一个参数是文件的字节数组，第二个就是定义的可通过类型。代码很简单，         主要是注意中间的一处，将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了。
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        final String fileType = getFileType("f:/2003");
        System.out.println(fileType);
    }
}
