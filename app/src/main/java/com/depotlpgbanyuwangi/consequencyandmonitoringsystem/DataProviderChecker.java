package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

public class DataProviderChecker {

    private String checkername;
    private String waktucek;
    private String nopolcek;
    private String milikcek;
    private String IDcek;
    private String file;

    public DataProviderChecker(String checkername,String IDcek, String waktucek,String nopolcek,String milikcek, String file){
        this.setCheckername(checkername);
        this.setIDcek(IDcek);
        this.setWaktucek(waktucek);
        this.setNopolcek(nopolcek);
        this.setMilikcek(milikcek);
        this.setFile(file);

    }

    public String getNotification(){
        String notification = "Pengecekan Skidtank SPPBE <b>"+getMilikcek()+"</b> Nomor Polisi <b>"+getNopolcek()+"</b>  telah diverifikasi <b>"+getCheckername()+"</b> dan dapat didownload.";
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


}
