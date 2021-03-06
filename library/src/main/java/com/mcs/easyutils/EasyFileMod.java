package com.mcs.easyutils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.mcs.easyutils.EasyLogMod.error;
import static com.mcs.easyutils.EasyParseMod.getFileName;

@SuppressWarnings("unused")
public class EasyFileMod
{
    private static String TAG = "EasyFileMod";
    private final Context context;

    public EasyFileMod(final Context context) {
        this.context = context;
    }

    public static void  moveFile(File inputPath, File outputPath) {
        InputStream in = null;
        OutputStream out = null;
        String fileName = getFileName(inputPath);

        try {
            CreateDirs(outputPath);

            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + "/" + fileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;

            deleteFiles(inputPath.getAbsolutePath());
        }

        catch (FileNotFoundException fnfe1) {
            error(TAG, fnfe1.getMessage());
        }
        catch (Exception e) {
            error(TAG, e.getMessage());
        }
    }

    public static void deleteFiles(String path) {
        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e){
                error(TAG, e.getMessage());
            }
        }

    }
    public static boolean CreateDirs(File dir) {
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        else {
            return true;
        }
    }

}