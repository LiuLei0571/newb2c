package com.example.b2c.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.EventLog;

import com.example.b2c.widget.util.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by YCKJ-0001 on 2016/6/16.
 */
public class BitmapUtil {


    private static File ff;
    private DisplayImageOptions options;

    public static File  saveBitmap2file(Bitmap bmp,String filename){
        File file=new File(filename);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
public File yasuo(Context context,String imagePath){
    Bitmap bitMap=BitmapFactory.decodeFile(imagePath);
    //图片允许最大空间   单位：KB
    double maxSize =400.00;
//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] b = baos.toByteArray();
//将字节换成KB
    double mid = b.length/1024;
//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
    if (mid > maxSize) {
//获取bitmap大小 是允许最大大小的多少倍
        double i = mid / maxSize;
//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
        bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                bitMap.getHeight() / Math.sqrt(i));
    }
    File file = saveBitmap2file( bitMap, FileUtils.getCachePath(context) + FileUtils.getImgName(".jpg"));
    return file;
}
    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
// 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
// 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
// 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
// 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 将图片进行质量压缩
     * @return
     */
    public File compressImage(Context context,String imagePath) {
        Bitmap image=BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 40;
        int i = baos.toByteArray().length / 1024;
        while ( i>500) {  //循环判断如果压缩后图片是否大于800kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        BitmapFactory.Options option=new BitmapFactory.Options();
        option.inJustDecodeBounds = false;
        option.inSampleSize = 3; //width，hight设为原来的十分一

        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, option);//把ByteArrayInputStream数据生成图片
        File file = saveBitmap2file( bitmap, FileUtils.getCachePath(context) + FileUtils.getImgName(".jpg"));
        return file;
    }
    /**加载本地图片，pictureSize越大越清晰一般设置1080*1920
     **/
    public Bitmap revitionImageSize(String filePath, int pictureSize) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
//2.为位图设置100K的缓存
        opts.inTempStorage = new byte[100 * 1024];
//3.设置位图颜色显示优化方式
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
//4.设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
        opts.inPurgeable = true;
//6.设置解码位图的尺寸信息
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = true;
        bitmap=BitmapFactory.decodeFile(filePath, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, pictureSize);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
// TODO: handle exception
        }
        return bitmap;
    }
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
// return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    /**加载网络图片，pictureSize越大越清晰一般设置1080*1920
            **/

    public Bitmap downloadImgByUrl(String imgUrl, int pictureSize) {
        BufferedInputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(imgUrl);
            conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
//2.为位图设置100K的缓存
        opts.inTempStorage = new byte[100 * 1024];
//3.设置位图颜色显示优化方式
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
//4.设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
        opts.inPurgeable = true;
//6.设置解码位图的尺寸信息
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = true;
//        bitmap=BitmapFactory.decodeStream(is, null, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, pictureSize);
        opts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opts);
            is.reset();
            conn.disconnect();
        } catch (Exception e) {
// TODO: handle exception
        }
        return bitmap;
    }

}
