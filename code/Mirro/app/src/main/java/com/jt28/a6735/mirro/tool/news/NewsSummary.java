package com.jt28.a6735.mirro.tool.news;

public class NewsSummary {
    private String title;
    private String href;
    private String clickCount;

    public NewsSummary(String title, String href, String clickCount) {
        this.title = title;
        this.href = href;
        this.clickCount = clickCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "标题：" + this.title + " 链接：" + this.href + " 点击数：" + this.clickCount;
    }
}
