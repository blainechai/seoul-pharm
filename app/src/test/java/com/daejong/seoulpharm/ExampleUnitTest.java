package com.daejong.seoulpharm;

import com.daejong.seoulpharm.model.MedicineInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Document document = Jsoup.connect("http://drug.mfds.go.kr/html/bxsSearchDrugProduct.jsp?item_Seq=199603002").get();
        MedicineInfo medicineInfo = new MedicineInfo();
        medicineInfo.setEffect(document.select("#A_EE_DOC").get(0).parent().select(">div").text());
        medicineInfo.setUsage(document.select("#A_UD_DOC").get(0).parent().select(">div").text());
        medicineInfo.setCaution(document.select("#A_NB_DOC").get(0).parent().select(">div").text());
        String imgSrc;
        if (document.select("#td_image>img").size() > 0) {
            imgSrc = document.select("#td_image>img").first().absUrl("src");
        } else {
            imgSrc = document.select(".txc-image").first().absUrl("src");
        }
        medicineInfo.setImageSrc(imgSrc);
        System.out.println(medicineInfo.getImageSrc());


    }
}