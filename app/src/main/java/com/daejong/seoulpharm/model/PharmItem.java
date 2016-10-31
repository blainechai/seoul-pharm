package com.daejong.seoulpharm.model;


import java.io.Serializable;

/**
 * Created by Hyunwoo on 2016. 10. 1..
 */
public class PharmItem implements Serializable {

    private String mainKey;

    private String nameKor;
    private String nameEng;
    private String nameChi;

    private String addressKor;
    private String addressEng;

    private String hKorCity;
    private String hKorGu;
    private String hKorDong;

    private String tel;

    private String availLanKor;
    private String availLanEng;
    private String availLanChi;

    // NOT API
    private String latitude;
    private String longtitude;

    // constructor
    public PharmItem() {

    }

    public PharmItem(String mainKey, String nameKor, String nameEng, String nameChi, String addressKor, String addressEng, String hKorCity, String hKorGu, String hKorDong, String tel, String availLanKor, String availLanEng, String availLanChi, String latitude, String longtitude) {
        this.mainKey = mainKey;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.nameChi = nameChi;
        this.addressKor = addressKor;
        this.addressEng = addressEng;
        this.hKorCity = hKorCity;
        this.hKorGu = hKorGu;
        this.hKorDong = hKorDong;
        this.tel = tel;
        this.availLanKor = availLanKor;
        this.availLanEng = availLanEng;
        this.availLanChi = availLanChi;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    // getter & setter
    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getNameKor() {
        return nameKor;
    }

    public void setNameKor(String nameKor) {
        this.nameKor = nameKor;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameChi() {
        return nameChi;
    }

    public void setNameChi(String nameChi) {
        this.nameChi = nameChi;
    }

    public String getAddressKor() {
        return addressKor;
    }

    public void setAddressKor(String addressKor) {
        this.addressKor = addressKor;
    }

    public String getAddressEng() {
        return addressEng;
    }

    public void setAddressEng(String addressEng) {
        this.addressEng = addressEng;
    }

    public String gethKorCity() {
        return hKorCity;
    }

    public void sethKorCity(String hKorCity) {
        this.hKorCity = hKorCity;
    }

    public String gethKorGu() {
        return hKorGu;
    }

    public void sethKorGu(String hKorGu) {
        this.hKorGu = hKorGu;
    }

    public String gethKorDong() {
        return hKorDong;
    }

    public void sethKorDong(String hKorDong) {
        this.hKorDong = hKorDong;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getAvailLanKor() {
        return availLanKor;
    }

    public void setAvailLanKor(String availLanKor) {
        this.availLanKor = availLanKor;
    }

    public String getAvailLanEng() {
        return availLanEng;
    }

    public void setAvailLanEng(String availLanEng) {
        this.availLanEng = availLanEng;
    }

    public String getAvailLanChi() {
        return availLanChi;
    }

    public void setAvailLanChi(String availLanChi) {
        this.availLanChi = availLanChi;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return this.longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

}
