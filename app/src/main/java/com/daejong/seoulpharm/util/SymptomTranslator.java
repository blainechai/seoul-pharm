package com.daejong.seoulpharm.util;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class SymptomTranslator {

    private static SymptomTranslator instance;
    public static SymptomTranslator getInstance() {
        if (instance == null) {
            instance = new SymptomTranslator();
        }
        return instance;
    }
    private SymptomTranslator() {
    }

    

}
