package com.jt28.a6735.mirro.tool.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NetEaseNewsRankSpider {
    private static final String rankUrl = "http://news.163.com/rank/";
    private static final HashMap<String, String> headersMap = new HashMap<>();

    static {
        headersMap.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3192.0 Safari/537.36");
    }

    /**
     * 从 url 中提取新闻 tr 元素，并且将其装入 ArrayList 返回
     *
     * @return ArrayList
     * @throws IOException 网络错误
     */
    private static ArrayList<Element> getNewsTrElementListFromRankUrl() throws IOException {
        Document document = Jsoup.connect(rankUrl)
                .headers(headersMap)
                .get();
        Elements tbodyElements = document.select("tbody");
        Elements tbodyTrElements = tbodyElements.select("tr");
        ArrayList<Element> trElementList = new ArrayList<>();
        for (Element tbodyTrElement : tbodyTrElements) {
            Elements th = tbodyTrElement.select("th");
            if (th.size() != 0) {
                continue;
            }
            trElementList.add(tbodyTrElement);
        }
        return trElementList;
    }

    private static ArrayList<Element> getNewsTrElementListFromRankUrl2() throws IOException {
        Document document = Jsoup.connect(rankUrl)
                .headers(headersMap)
                .get();
        Elements tbodyElements = document.select("tbody");
        Elements tbodyTrElements = tbodyElements.select("src");
        ArrayList<Element> trElementList = new ArrayList<>();
        for (Element tbodyTrElement : tbodyTrElements) {
            Elements th = tbodyTrElement.select("th");
            if (th.size() != 0) {
                continue;
            }
            trElementList.add(tbodyTrElement);
        }
        return trElementList;
    }

    /**
     * 从 tr 元素中提取数据，并且封装成 NewsSummary 对象返回。如果解析失败则返回 null
     *
     * @param trElement 被解析的 tr 元素对象
     * @return NewsSummary or null
     */
    private static NewsSummary getNewsSummaryFromTrElement(Element trElement) {
        Elements tdElements = trElement.select("td");
        if (tdElements.size() != 2) {
            return null;
        }
        String title = tdElements.get(0).text();
        String href = tdElements.get(0).select("a").get(0).attr("href");
        String clickCount = tdElements.get(1).text();
        return new NewsSummary(title, href, clickCount);
    }



    /**
     * 从新闻 href 中提取数据，并且封装成 News 对象返回
     *
     * @param href 被解析的新闻链接
     * @return News 对象
     * @throws IOException 网络错误
     */
    private static BigNews getNewsFromHref(String href) throws IOException {
        Document document = Jsoup.connect(href)
                .headers(headersMap)
                .get();
        String title = document.select("#epContentLeft > h1").get(0).text();
        Elements pElements = document.select("#endText");
        BigNews news = new BigNews(title);
        for (Element pElement : pElements) {
            news.addText(pElement.text());
        }

        Elements elements = document.getElementsByTag("img");
        int url_i =0 ;
        for(Element element : elements){
            //获取每个img标签的src属性的内容，即图片地址，加"abs:"表示绝对路径
            String imgSrc = element.attr("abs:src");
//            Log.d("lhb", "" + imgSrc);
            if(url_i == 4) {
                news.setUrl(imgSrc);
            }
            url_i++;
        }

        return news;
    }

    private static String getNewsFromHrefImg(String href) throws IOException {
        Document document = Jsoup.connect(href)
                .headers(headersMap)
                .get();

        Elements elements = document.getElementsByTag("img");
        for(Element element : elements){
            //获取每个img标签的src属性的内容，即图片地址，加"abs:"表示绝对路径
            String imgSrc = element.attr("abs:src");
//            Log.d("lhb", "" + imgSrc);
        }
        return "";
    }
    /**
     * 获得新闻概要 List
     *
     * @return NewsSummary List
     * @throws IOException 网络错误
     */
    public static ArrayList<NewsSummary> getNewsSummaryList() throws IOException {
        ArrayList<Element> trElementList = NetEaseNewsRankSpider.getNewsTrElementListFromRankUrl();
        ArrayList<NewsSummary> newsSummaryArrayList = new ArrayList<>();
        for (Element element : trElementList) {
            NewsSummary newsSummary = NetEaseNewsRankSpider.getNewsSummaryFromTrElement(element);
            if (newsSummary == null) {
                continue;
            }
            newsSummaryArrayList.add(newsSummary);
        }
        return newsSummaryArrayList;
    }

    /**
     * 从新闻概要对象中获得新闻详情对象
     *
     * @param newsSummary 被解析的新闻概要对象
     * @return News Object
     * @throws IOException 网络错误
     */
    public static BigNews getNewsFromNewsSummary(NewsSummary newsSummary) throws IOException {
        getNewsFromHrefImg(newsSummary.getHref());
        return getNewsFromHref(newsSummary.getHref());
    }
}
