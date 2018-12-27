package com.beiyongjin.byg.network;

import android.Manifest;
import android.content.Context;

import com.erongdu.wireless.tools.log.Logger;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.beiyongjin.byg.common.BaseParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/7/6 09:53
 * <p/>
 * Description:
 */
public class FileDownloadUtil {
    private static final String TAG = "DownloadFile";

    /**
     * 保存文件
     *
     * @param body
     *         文件流
     * @param filename
     *         文件名,含后缀
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String filename) {
        Logger.i(TAG, "下载完成，准备写入文件.");
        Context context = ContextHolder.getContext();
        // 如果没有读写SD卡的权限，则不写入文件
        if (null != context && !PermissionCheck.getInstance().checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.i(TAG, "请求读写SD卡权限");
            return false;
        }
        // 目录不存在  则创建
        File dir = new File(BaseParams.ROOT_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            File         futureStudioIconFile = new File(BaseParams.ROOT_PATH + File.separator + filename);
            InputStream  inputStream          = null;
            OutputStream outputStream         = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize           = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Logger.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                ToastUtil.toast("下载成功");
                //Cutscenes.dismiss(true);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件是否存在
     */
    public static boolean isExists(String filename) {
        try {
            File file = new File(BaseParams.ROOT_PATH + File.separator + filename);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
