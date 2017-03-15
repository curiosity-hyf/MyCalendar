package com.curiosity.mycalendar.bean;

import android.graphics.Bitmap;

/**
 * Created by Curiosity on 2016-12-31.
 */

public class VerifyCode {
    private Bitmap bitmap;
    private String cookie;
    private String viewState;

    public VerifyCode(Bitmap bitmap, String cookie, String viewState) {
        this.bitmap = bitmap;
        this.cookie = cookie;
        this.viewState = viewState;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }
}
