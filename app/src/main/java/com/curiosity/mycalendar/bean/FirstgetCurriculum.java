package com.curiosity.mycalendar.bean;

import java.util.List;

/**
 * Created by Curiosity on 2017-1-1.
 */

public class FirstgetCurriculum {
    private String viewState;
    private List<String> years;

    public FirstgetCurriculum(String viewState, List<String> years) {
        this.viewState = viewState;
        this.years = years;
    }

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "FirstgetCurriculum{" +
                "viewState='" + viewState + '\'' +
                ", years=" + years +
                '}';
    }
}
