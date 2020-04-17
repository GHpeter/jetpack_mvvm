package com.fuxing.libcommon.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-17
 * Description:截取视频文件的封面图
 **/
public class FileUtils {
    public static LiveData<String> generateVideoCover(final String filePath) {
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {

                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(filePath);
                //获取第一个关键帧
                Bitmap frame = retriever.getFrameAtTime();
                FileOutputStream fos = null;
                if (frame != null) {
                    byte[] bytes = compressBitmap(frame, 200);
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            , System.currentTimeMillis() + "jpeg");
                    try {
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                        fos.write(bytes);
                        liveData.postValue(file.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {

                            try {
                                fos.flush();
                                fos.close();
                                fos = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                } else {
                    liveData.postValue(null);
                }

            }
        });
        return liveData;
    }

    //循环压缩
    private static byte[] compressBitmap(Bitmap bitmap, int limit) {
        if (bitmap != null && limit > 0) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
            while (bos.toByteArray().length > limit * 1024) {
                bos.reset();
                options = -5;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
            }
            byte[] bytes = bos.toByteArray();
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bos = null;

            }
            return bytes;
        }
        return null;
    }
}
