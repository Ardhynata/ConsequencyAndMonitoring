package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

public class DataProvider {

    private String checkername;
    private String waktucek;
    private String nopolcek;
    private String milikcek;
    private String IDcek;

    public DataProvider(String checkername,String IDcek, String waktucek,String nopolcek,String milikcek){
        this.setCheckername(checkername);
        this.setIDcek(IDcek);
        this.setWaktucek(waktucek);
        this.setNopolcek(nopolcek);
        this.setMilikcek(milikcek);

    }

    public String getNotification(){
        String notification = "Checker <b>"+getCheckername()+"</b> telah selesai melakukan pemerikasaan SkidTank nomor polisi <b>"+getNopolcek()+"</b> milik <b>"+getMilikcek()+"</b>.";
        return notification;
    }

    public String getCheckername() {
        return checkername;
    }

    public void setCheckername(String checkername) {
        this.checkername = checkername;
    }

    public String getWaktucek() {
        return waktucek;
    }

    public void setWaktucek(String waktucek) {
        this.waktucek = waktucek;
    }

    public String getNopolcek() {
        return nopolcek;
    }

    public void setNopolcek(String nopolcek) {
        this.nopolcek = nopolcek;
    }

    public String getMilikcek() {
        return milikcek;
    }

    public void setMilikcek(String milikcek) {
        this.milikcek = milikcek;
    }

    public String getIDcek() {
        return IDcek;
    }

    public void setIDcek(String IDcek) {
        this.IDcek = IDcek;
    }




}
