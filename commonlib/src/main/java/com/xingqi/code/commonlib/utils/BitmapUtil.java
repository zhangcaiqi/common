package com.xingqi.code.commonlib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {


    /**
     * 可能会有点耗时，可以在子线程调用
     *
     * @param srcFile 要压缩的图片文件
     * @param path    压缩后的图片文件路径
     * @return File 压缩成功后的图片文件
     */

    public static File bitmapCompress(File srcFile, String path, int tagWidth, int tagHeight) {
        if (srcFile == null || !srcFile.exists()) {
            throw new RuntimeException("图片文件不存在");
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        } else {
            if (path.contains(".")) {
                path = path.substring(0, path.lastIndexOf("."));
                path = path + ".jpg";//jpg格式
            } else {
                path = path + ".jpg";//jpg格式
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置为true，不会申请内存，可以得到原生的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        int outWidth = options.outWidth;//原生的宽
        int outHeight = options.outHeight;//原生的高

        /**
         * 图片大小（分辨率）压缩
         * options.inSampleSize  这是压缩比率，实际压缩比率根据自己需求通过算法计算

         */
        options.inSampleSize = getSampleSize(outWidth, outHeight, tagWidth, tagHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap2 = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /**
         * 这里是图片质量压缩，第二个参数表示压缩率，100表示不压缩，0表示最大压缩

         */
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        bitmap2.recycle();
        FileOutputStream outputStream = null;
        File tagFile = new File(path);
        try {
            if (!tagFile.exists()) {
                tagFile.createNewFile();
            }
            outputStream = new FileOutputStream(tagFile);
            outputStream.write(stream.toByteArray());
            outputStream.flush();
            srcFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tagFile;
    }


    /**
     * 压缩比率 每次减少0.5倍
     *
     * @param srcWidth  原生的宽
     * @param srcHeight 原生的高
     * @param dstWidth  目标宽
     * @param dstHeight 目标高
     * @return
     */

    public static int getSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
        int widthSize = 0;
        int heightSize = 0;
        while (srcWidth > dstWidth) {
            widthSize += 2;
            srcWidth = srcWidth / 2;
        }
        while (srcHeight > dstHeight) {
            heightSize += 2;
            srcHeight = (srcHeight / 2);
        }
        if (widthSize > heightSize) {
            return widthSize;
        } else {
            return heightSize;
        }
    }

    public static Bitmap cropToSquare(File imageFile,float ratio){
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
        int width = bitmap.getWidth(); // 得到图片的宽，高
        int height = (int) (bitmap.getHeight()* ratio);
        //取最短的边长
        int squareSize = width > height ? height : width;
        int startX = width > height ? (width - height) / 2 : 0;
        int startY = width < height ? (height - width) / 2 : 0;
        Bitmap rlt = Bitmap.createBitmap(bitmap,startX,startY,squareSize,squareSize);
        bitmap.recycle();
        return rlt;
    }

    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
//        if (src != dst) { // 如果没有缩放，那么不回收
//            src.recycle(); // 释放Bitmap的native像素数组
//        }
        return dst;
    }
    public static boolean saveBitmapAsFile(String directory,String fileName, Bitmap bitmap) {
        File saveFile = new File(directory, fileName);

        boolean saved = false;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            saved = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saved;
    }

}
