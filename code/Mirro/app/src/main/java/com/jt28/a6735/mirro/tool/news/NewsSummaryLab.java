package com.jt28.a6735.mirro.tool.news;

import java.util.ArrayList;

public class NewsSummaryLab {
    private static final NewsSummaryLab instance = new NewsSummaryLab();
    private ArrayList<NewsSummary> mNewsSummaryArrayList;

    private NewsSummaryLab() {
        mNewsSummaryArrayList = new ArrayList<>();
    }

    public static NewsSummaryLab getInstance() {
        return instance;
    }

    public ArrayList<NewsSummary> getNewsSummaryArrayList() {
        return mNewsSummaryArrayList;
    }

    public void addNewsSummary(NewsSummary newsSummary) {
        mNewsSummaryArrayList.add(newsSummary);
    }

    public void removeNewsSummary(int position) {
        mNewsSummaryArrayList.remove(position);
    }

    public NewsSummary getNewsSummary(int position) {
        return mNewsSummaryArrayList.get(position);
    }
}
