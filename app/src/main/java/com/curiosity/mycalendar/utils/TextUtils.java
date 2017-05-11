package com.curiosity.mycalendar.utils;


import android.util.Log;
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

    public static boolean equals(String a, String b) {
        return a == null && b == null || !(a == null || b == null) && a.equals(b);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static String[] spiltString(String s, int range) {
        Log.d("myW", "spiltString: " + s);
        int pieceNum = (int)Math.ceil(s.length()*1.0/range);
        Log.d("myW", "spiltString: " + pieceNum);
        if(pieceNum == 0) {
            return new String[]{s};
        } else {
            int lastIndex = 0;
            String [] res = new String[pieceNum];
            for(int i = 0; i < pieceNum; ++i) {
                int tail = Math.min(s.length(), lastIndex+range);
                res[i] = s.substring(lastIndex, tail);
                lastIndex+=range;
            }
            return res;
        }
    }

    public static String formatNumString(String[] num, String spec) {
        StringBuilder sb = new StringBuilder();
        if(num.length == 0) {
            return "";
        }
        sb.append(Integer.valueOf(num[0]));
        for(int i = 1; i < num.length; ++i) {
            Log.d("myW", "formatNumString: " + num[i]);
            sb.append(spec);
            sb.append(Integer.valueOf(num[i]));
        }
        return sb.toString();
    }

    public static String formatDate(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.substring(0, 4));
        sb.append("年");
        sb.append(date.substring(5,7));
        sb.append("月");
        sb.append(date.substring(8, 10));
        sb.append("日");
        return sb.toString();
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
