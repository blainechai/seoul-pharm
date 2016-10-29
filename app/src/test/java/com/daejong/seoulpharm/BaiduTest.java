package com.daejong.seoulpharm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BaiduTest {

    @Test
    public void jsoupTest() throws Exception {
        Document document = Jsoup.connect("http://baike.baidu.com/item/布洛芬").get();

//        System.out.println(document);
//        System.out.println(document.select(".para-title"));
//        ArrayList<Document> arrayList = new ArrayList<>();
//        Elements result;
//        result = document.select("h2.title-text");
//        System.out.println("result: " + result);
//        System.out.println();
//        Element tmpElement = null;
//        for (Element element : result) {
////            System.out.println(element.ownText());
//            if (element.ownText().equals("用法与用量")) {
//                System.out.println(element);
//                tmpElement = element;
//            }
//        }
//        tmpElement = tmpElement.parent();
//        if (tmpElement != null) {
//            while ((tmpElement = tmpElement.nextElementSibling()).hasClass("para")) {
//                System.out.println(tmpElement.ownText());
//            }
//        }
//
//
//        for (Element element : result) {
////            System.out.println(element.ownText());
//            if (element.ownText().equals("适应症")) {
//                System.out.println(element);
//                tmpElement = element;
//            }
//        }
//        tmpElement = tmpElement.parent();
//        if (tmpElement != null) {
//            while ((tmpElement = tmpElement.nextElementSibling()).hasClass("para")) {
//                System.out.println(tmpElement.ownText());
//            }
//        }
        printInfo("分子结构", document);
        printInfo("适应症", document);
        printInfo("用法与用量", document);
        printInfo("成份", document);
        printInfo("性状", document);
        printInfo("作用类别", document);

        document = Jsoup.connect("https://translate.google.com/#auto/en/%EC%9D%B4%EB%B6%80%ED%94%84%EB%A1%9C%ED%8E%9C").get();
        System.out.println(document);
    }

    @Test
    public void hi(){
        System.out.println("hi");
    }

    public void printInfo(String searchToken, Document document) {
        Elements result;
        result = document.select("h2.title-text");
        Element tmpElement = null;
        for (Element element : result) {
//            System.out.println(element.ownText());
            if (element.ownText().equals(searchToken)) {
//                System.out.println(element);
                tmpElement = element;
            }
        }
        tmpElement = tmpElement.parent();
        if (tmpElement != null) {
            while ((tmpElement = tmpElement.nextElementSibling()).hasClass("para")) {
                System.out.println(tmpElement.ownText());
            }
        }
        System.out.println();
    }
}