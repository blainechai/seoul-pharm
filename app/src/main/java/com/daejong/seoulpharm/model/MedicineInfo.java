package com.daejong.seoulpharm.model;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by blainechai on 2016. 10. 25..
 */

public class MedicineInfo implements Serializable{
    private String name;
    private String company;
    private ArrayList<String> components;
    private String effect;
    private String usage;
    private String caution;
    private String itemSeq;
    private String imageSrc;
    private String barcode;


    public MedicineInfo() {
    }

    public MedicineInfo(String name, String company, ArrayList<String> components, String effect, String usage, String caution, String itemSeq, String imageSrc, String barcode) {
        this.name = name;
        this.company = company;
        this.components = components;
        this.effect = effect;
        this.usage = usage;
        this.caution = caution;
        this.itemSeq = itemSeq;
        this.imageSrc = imageSrc;
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ArrayList<String> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<String> components) {
        this.components = components;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        String rtn ="";
        rtn+=this.name;
        rtn+=this.company;
        rtn+=this.components;
        rtn+=this.effect;
        rtn+=this.usage;
        rtn+=this.caution;
        rtn+=this.itemSeq;
        return rtn;
    }


    public Drawable loadImageFromWebOperations() {
        try {
            InputStream is = (InputStream) new URL(this.imageSrc).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
