package com.alex.witAg.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-02.
 */

public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }


    // 从资源加载
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 设置inJustDecodeBounds = true ,表示先不加载图片
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 调用方法计算合适的 inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // inJustDecodeBounds 置为 false 真正开始加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        // 设置inJustDecodeBounds = true ,表示先不加载图片
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        // 调用方法计算合适的 inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // inJustDecodeBounds 置为 false 真正开始加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static Bitmap decodeSampledBitmapFromArray(byte[] imgByte, int reqWidth, int reqHeight) {
        // 设置inJustDecodeBounds = true ,表示先不加载图片
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);

        // 调用方法计算合适的 inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // inJustDecodeBounds 置为 false 真正开始加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
    }

    // 计算 BitmapFactpry 的 inSimpleSize的值的方法
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // 获取图片原生的宽和高
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w= " + width + " h=" + height);
        int inSampleSize = 1;

        // 如果原生的宽高大于请求的宽高,那么将原生的宽和高都置为原来的一半
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 主要计算逻辑
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }


}
