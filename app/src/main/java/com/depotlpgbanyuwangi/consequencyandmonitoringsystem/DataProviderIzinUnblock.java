package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

public class DataProviderIzinUnblock {

    private String nomor_polisi;
    private String nama_sppbe;
    private String status_izin;
    private String deadline;
    private String unblock_requester;


    public DataProviderIzinUnblock(String nomor_polisi,String nama_sppbe, String status_izin,String deadline,String unblock_requester){

        this.setNomor_polisi(nomor_polisi);
        this.setNama_sppbe(nama_sppbe);
        this.setStatus_izin(status_izin);
        this.setDeadline(deadline);
        this.setUnblock_requester(unblock_requester);

    }

    public String getNotification(){
        String notification = "Checker <b>"+getUnblock_requester()+"</b> meminta izin unblock untuk skidtank Nomor Polisi <b>"+getNomor_polisi()+"</b> milik <b>"+getNama_sppbe()+"</b>.";
        return notification;
    }

    public String getNomor_polisi() {
        return nomor_polisi;
    }

    public void setNomor_polisi(String nomor_polisi) {
        this.nomor_polisi = nomor_polisi;
    }

    public String getNama_sppbe() {
        return nama_sppbe;
    }

    public void setNama_sppbe(String nama_sppbe) {
        this.nama_sppbe = nama_sppbe;
    }

    public String getStatus_izin() {
        return status_izin;
    }

    public void setStatus_izin(String status_izin) {
        this.status_izin = status_izin;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getUnblock_requester() {
        return unblock_requester;
    }

    public void setUnblock_requester(String unblock_requester) {
        this.unblock_requester = unblock_requester;
    }

}
