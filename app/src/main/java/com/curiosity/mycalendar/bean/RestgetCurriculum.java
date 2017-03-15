package com.curiosity.mycalendar.bean;

import org.jsoup.nodes.Document;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class RestgetCurriculum {
    private String viewState;
    private Document document;
    private String year;
    private String semester;

    public RestgetCurriculum(String viewState, Document document) {
        this.viewState = viewState;
        this.document = document;
    }

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
