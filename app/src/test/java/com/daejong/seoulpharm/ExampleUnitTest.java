package com.daejong.seoulpharm;

import com.daejong.seoulpharm.model.MedicineInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.w3c.dom.NodeList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Pattern barcodePattern = Pattern.compile("(880([6][2456789]|[0][56789])[0-9]{7}[c|C|0-9])");
//        Pattern pattern = Pattern.compile("(?:^(GET|POST) ([^?]+)[?]?(.*) (HTTP/(0.9|1.0|1.1))$)");
//
        Matcher matcher = barcodePattern.matcher("Contents=0108806500006014Format=DATA_MATRIX");
        System.out.println("Contents=0108806500006014Format=DATA_MATRIX".matches("\\w.*"));
        System.out.println(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group());

//        Pattern pattern = Pattern.compile("(?:^(GET|POST) ([^?]+)[?]?(.*) (HTTP/(0.9|1.0|1.1))$)");
//        String line = "POST /tomcat-docs/images/tomcat.gif?userid=admin HTTP/0.9";
//        Matcher matches = pattern.matcher(line);

//        if (matches.find()) {
//            System.out.println("Match find");
//            System.out.println("Method: " + matches.replaceAll("$1"));
//            System.out.println("URL: " + matches.replaceAll("$2"));
//            System.out.println("Param: " + matches.replaceAll("$3"));
//            System.out.println("Version: " + matches.replaceAll("$4"));
//        }
    }
}