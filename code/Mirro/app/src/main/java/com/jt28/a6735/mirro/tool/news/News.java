package com.jt28.a6735.mirro.tool.news;

import java.util.ArrayList;
import java.util.List;

public class News {
    private String title;
    private List<String> textList;

    public News(String title) {
        this.title = title;
        this.textList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addText(String text) {
        this.textList.add(text);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("标题：" + this.title + "\n\n\n");
        for (String s : this.textList) {
            stringBuilder.append(s).append("\n\n");
        }
        return stringBuilder.toString();
    }
}
