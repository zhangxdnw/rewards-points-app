package cn.zxd.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;


public class MD5Utils {
    public MD5Utils() {
    }

    public static void main(String[] args) throws Exception {
        try {
            System.out.println(MD5Utils.getHexMD5Str("abcd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHexMD5Str(String strIn) throws Exception {
        return getHexMD5Str(strIn.getBytes());
    }

    public static String getHexMD5Str(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            return getHexMD5Str(Base64Utils.encode(buffer));
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getHexMD5Str(byte arrIn[]) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte arrB[] = md.digest(arrIn);
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < arrB.length; i++) {
            int intTmp;
            for (intTmp = arrB[i]; intTmp < 0; intTmp += 256) ;
            if (intTmp < 16)
                sb.append('0');
            sb.append(Integer.toString(intTmp, 16));
        }

        return sb.toString().toUpperCase();
    }
}
