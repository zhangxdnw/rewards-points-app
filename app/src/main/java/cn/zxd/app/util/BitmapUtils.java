package cn.zxd.app.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtils {

    /*
     * byte[] data保存的是纯RGB的数据，而非完整的图片文件数据
     */
    static public Bitmap createMyBitmap(byte[] data, int width, int height){
        int []colors = convertByteToColor(data);
        if (colors == null){
            return null;
        }

        Bitmap bmp = null;

        try {
            bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                    Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            // TODO: handle exception

            return null;
        }

        return bmp;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64Utils.encode(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 将RGB数组转化为像素数组
     */
    public static int[] convertByteToColor(byte[] data){
        int size = data.length;
        if (size == 0){
            return null;
        }


        // 理论上data的长度应该是3的倍数，这里做个兼容
        int arg = 0;
        if (size % 3 != 0){
            arg = 1;
        }

        int []color = new int[size / 3 + arg];
        int red, green, blue;


        if (arg == 0){									//  正好是3的倍数
            for(int i = 0; i < color.length; ++i){

                color[i] = (data[i * 3] << 16 & 0x00FF0000) |
                        (data[i * 3 + 1] << 8 & 0x0000FF00 ) |
                        (data[i * 3 + 2] & 0x000000FF ) |
                        0xFF000000;
            }
        }else{										// 不是3的倍数
            for(int i = 0; i < color.length - 1; ++i){
                color[i] = (data[i * 3] << 16 & 0x00FF0000) |
                        (data[i * 3 + 1] << 8 & 0x0000FF00 ) |
                        (data[i * 3 + 2] & 0x000000FF ) |
                        0xFF000000;
            }

            color[color.length - 1] = 0xFF000000;					// 最后一个像素用黑色填充
        }

        return color;
    }
}
