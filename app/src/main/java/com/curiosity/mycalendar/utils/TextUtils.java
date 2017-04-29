package com.curiosity.mycalendar.utils;


import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Description : 该工具类用于文本的处理
 * Author : curiosity-hyf
 * Date : 2016-9-13
 * E-mail : curiooosity.h@gmail.com
 */

public class TextUtils {

    public static String getText(EditText editText) {
        return editText.getText().toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static String getYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static byte[] convertStreamToBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        byte[] cstb = null;
        int rc = 0;
        try {
            while ((rc = is.read(buff, 0, 1024)) != -1) {
                baos.write(buff, 0, rc);
            }
            cstb = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            baos = null;
        }
        return cstb;
    }

    public static String convertStreamToString(InputStream is, String encode) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = null;
        reader = new BufferedReader(new InputStreamReader(is, encode));
        sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        is.close();
        return sb == null ? "" : sb.toString();
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
