package com.yichan.gaotezhipei.common.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by simon on 2018/2/10 0010.
 */

public class FileUtil {

    private static String ROOT_CACHE;
    public static String ROOT_DIR="yichan";
    private static FileUtil instance = null;

    public static FileUtil getInstance(Context context) {
        if (instance == null) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                ROOT_CACHE = (Environment.getExternalStorageDirectory() + "/"
                        + ROOT_DIR + "/");
            } else {
                ROOT_CACHE = (context.getFilesDir().getAbsolutePath() + "/"+ROOT_DIR+"/");
            }
            File dir = new File(ROOT_CACHE);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            instance = new FileUtil();
        }
        return instance;
    }

    public File makeDir(String dir) {
        File fileDir = new File(ROOT_CACHE + dir);
        if (fileDir.exists()) {
            return fileDir;
        } else {
            fileDir.mkdirs();
            return fileDir;
        }
    }

    /**
     * 判断SD卡是否存在
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
