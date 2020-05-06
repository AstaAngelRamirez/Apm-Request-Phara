package com.apm.request.utils;

import android.content.Context;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/06/17.
 * <p>
 * Handle file actions and functions
 */

public class FileUtils {

    /**
     * Gets MimeType of file
     *
     * @param file source file
     * @return MimeType in string
     */
    public static String GetMimeTypeFromFile(File file) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
        if (extension != null && !extension.equals("")) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        } else if (extension != null) {
            extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * Read content of raw file
     * @param context   Application context
     * @param resId     Resource id of raw file
     * @return          String with raw file content
     */
    public static String ReadRawTextFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
