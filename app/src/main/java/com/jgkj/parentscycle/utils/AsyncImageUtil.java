package com.jgkj.parentscycle.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Administrator on 2016/1/20.
 */
public class AsyncImageUtil {
    private static final String TAG = "AsyncImageUtil";
    public static void asyncLoadImage(ImageView imageView,String url,int defImageResId,boolean isCycle,boolean isFade) {
        DisplayImageOptions options = null;
        if (isCycle) {
            if (isFade) {
                options = new DisplayImageOptions.Builder()
                        .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                        .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                        .imageScaleType(ImageScaleType.NONE)
//                    .displayer(new RoundedBitmapDisplayer(300))  // 设置成圆角图片
                        .displayer(new FadeInBitmapDisplayer(500))
                        .delayBeforeLoading(200)
                        .build();
            } else {
                options = new DisplayImageOptions.Builder()
                        .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                        .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                        .imageScaleType(ImageScaleType.NONE)
//                    .displayer(new RoundedBitmapDisplayer(300))  // 设置成圆角图片
//                    .displayer(new FadeInBitmapDisplayer(500))
                        .delayBeforeLoading(200)
                        .build();
            }

            ImageLoader.getInstance().displayImage(url, imageView,options,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    Bitmap tempBitmap = null;
                    if (loadedImage != null) {
                        try {
                            tempBitmap = CircularImage.getCroppedRoundBitmap(loadedImage, 50);
                            ImageView imageViewCallback = ((ImageView) view);
//                            Object bgObj = imageViewCallback.getBackground();
//                            if (bgObj != null) {
//                                Bitmap bitmap1 = ((BitmapDrawable)bgObj ).getBitmap();
//                                if (bitmap1 != null) {
//                                    if (!bitmap1.isRecycled()) {
//                                        bitmap1.recycle();;
//                                    }
//                                    System.gc();
//                                    bitmap1 = null;
//                                }
//                            }
                            imageViewCallback.setImageBitmap(tempBitmap);
                            tempBitmap = null;
                        } catch (Error e) {
                            if (tempBitmap != null) {
                                if (!tempBitmap.isRecycled()) {
                                    tempBitmap.recycle();
                                }
                                tempBitmap = null;
                            }

                            if (loadedImage != null) {
                                if (!loadedImage.isRecycled()) {
                                    loadedImage.recycle();
                                }
                                loadedImage = null;
                            }


                            System.gc();
                            e.printStackTrace();
                        }
                        LogUtil.d(TAG,"the bitmap size with:" + loadedImage.getWidth() + " height:" + loadedImage.getHeight());
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    super.onLoadingCancelled(imageUri, view);
                }
            });

        } else {
            if (isFade) {
                options = new DisplayImageOptions.Builder()
                        .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                        .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                        .imageScaleType(ImageScaleType.NONE)
                        .displayer(new FadeInBitmapDisplayer(500))
                        .delayBeforeLoading(200)
                        .build();
            } else {
                options = new DisplayImageOptions.Builder()
                        .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                        .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                        .imageScaleType(ImageScaleType.NONE)
                        .build();
            }

            ImageLoader.getInstance().displayImage(url, imageView,options);
        }
    }

    public interface AsyncImageInterface {
        public void asyncLoadImageComplete(Bitmap bmp, ImageView imageView, int flag);
    }
    public static void asyncLoadImageForCallback(final AsyncImageInterface asyncImageListener, final ImageView imageView,String url,int defImageResId,boolean isCycle,boolean isFade,final int flag) {
        DisplayImageOptions options;
        if (isFade) {
            options = new DisplayImageOptions.Builder()
                    .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    .imageScaleType(ImageScaleType.NONE)
//                    .displayer(new RoundedBitmapDisplayer(300))  // 设置成圆角图片
                    .displayer(new FadeInBitmapDisplayer(500))
                    .delayBeforeLoading(200)
                    .build();
        } else {
            options = new DisplayImageOptions.Builder()
                    .showStubImage(defImageResId)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(defImageResId)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(defImageResId)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    .imageScaleType(ImageScaleType.NONE)
//                    .displayer(new RoundedBitmapDisplayer(300))  // 设置成圆角图片
//                    .displayer(new FadeInBitmapDisplayer(500))
                    .delayBeforeLoading(200)
                    .build();
        }

        ImageLoader.getInstance().displayImage(url, imageView,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                Bitmap tempBitmap = null;
                if (loadedImage != null) {
                    try {
                        tempBitmap = CircularImage.getCroppedRoundBitmap(loadedImage, 50);
                        if (asyncImageListener != null) {
                            asyncImageListener.asyncLoadImageComplete(tempBitmap,imageView,flag);
                        }

                        ImageView imageViewCallback = ((ImageView) view);
//                        Object bgObj = imageViewCallback.getBackground();
//                        if (bgObj != null) {
//                            Bitmap bitmap1 = ((BitmapDrawable)bgObj ).getBitmap();
//                            if (bitmap1 != null) {
//                                if (!bitmap1.isRecycled()) {
//                                    bitmap1.recycle();;
//                                }
//                                System.gc();
//                                bitmap1 = null;
//                            }
//                        }

                        imageViewCallback.setImageBitmap(tempBitmap);
                        tempBitmap = null;
                    } catch (Error e) {

                        if (tempBitmap != null) {
                            if (!tempBitmap.isRecycled()) {
                                tempBitmap.recycle();
                            }
                            tempBitmap = null;
                        }

                        if (loadedImage != null) {
                            if (!loadedImage.isRecycled()) {
                                loadedImage.recycle();
                            }
                            loadedImage = null;
                        }

                        System.gc();
                        e.printStackTrace();
                    }
                    LogUtil.d(TAG, "the bitmap size with:" + loadedImage.getWidth() + " height:" + loadedImage.getHeight());
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }
        });
    }
}
