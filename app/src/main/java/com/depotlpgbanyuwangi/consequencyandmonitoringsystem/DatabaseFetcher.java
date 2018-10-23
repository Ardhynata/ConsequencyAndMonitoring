package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

public class DatabaseFetcher {

    private String nama_sppbe;
    private String nomor_polisi;
    private String nama_sopir;
    private String status_izin;
    private String kim_status;
    private String apar_co2_status;
    private String apar_dcp_status;
    private String head_truck_status;
    private String vessel_status;
    private String electrical_part_status;
    private String lamp_status;
    private String ban_roda_status;
    private String safety_helmet_status;
    private String uniform_status;
    private String safety_shoes_status;
    private String barang_terlarang_status;
    private String tanggal_jam_pengecekan;
    private String username;
    private String deadline;

    public DatabaseFetcher(
            String nama_sppbe,           String nomor_polisi ,
            String nama_sopir ,          String status_izin ,
            String kim_status ,          String apar_co2_status ,
            String apar_dcp_status ,     String head_truck_status,
            String vessel_status,        String electrical_part_status,
            String lamp_status,          String ban_roda_status,
            String safety_helmet_status, String uniform_status,
            String safety_shoes_status,  String barang_terlarang_status,
            String tanggal_jam_pengecekan, String username, String deadline)
    {
            this.setNama_sppbe(nama_sppbe);
            this.setNomor_polisi(nomor_polisi);
            this.setNama_sopir(nama_sopir);
            this.setStatus_izin(status_izin);
            this.setKim_status(kim_status);
            this.setApar_co2_status(apar_co2_status);
            this.setApar_dcp_status(apar_dcp_status);
            this.setHead_truck_status(head_truck_status);
            this.setVessel_status(vessel_status);
            this.setElectrical_part_status(electrical_part_status);
            this.setLamp_status(lamp_status);
            this.setBan_roda_status(ban_roda_status);
            this.setSafety_helmet_status(safety_helmet_status);
            this.setUniform_status(uniform_status);
            this.setSafety_shoes_status(safety_shoes_status);
            this.setBarang_terlarang_status(barang_terlarang_status);
            this.setTanggal_jam_pengecekan(tanggal_jam_pengecekan);
            this.setUsername(username);
            this.setDeadline(deadline);
    }

    public String statusstring(String status){

        String statuscek = "Data tidak ditemukan.";

        if(status.equals("0")){
            statuscek = ": Tidak Sesuai";
        }else if(status.equals("1")){
            statuscek = ": Sesuai";
        }

        return statuscek;
    }

    public String getNama_sppbe() {
        return nama_sppbe;
    }

    public void setNama_sppbe(String nama_sppbe) {
        this.nama_sppbe = nama_sppbe;
    }

    public String getNomor_polisi() {
        return nomor_polisi;
    }

    public void setNomor_polisi(String nomor_polisi) {
        this.nomor_polisi = nomor_polisi;
    }

    public String getNama_sopir() {
        return nama_sopir;
    }

    public void setNama_sopir(String nama_sopir) {
        this.nama_sopir = nama_sopir;
    }

    public String getStatus_izin() {
        return status_izin;
    }

    public void setStatus_izin(String status_izin) {
        this.status_izin = status_izin;
    }

    public String getKim_status() {
        return kim_status;
    }

    public void setKim_status(String kim_status) {
        this.kim_status = kim_status;
    }

    public String getApar_co2_status() {
        return apar_co2_status;
    }

    public void setApar_co2_status(String apar_co2_status) {
        this.apar_co2_status = apar_co2_status;
    }

    public String getApar_dcp_status() {
        return apar_dcp_status;
    }

    public void setApar_dcp_status(String apar_dcp_status) {
        this.apar_dcp_status = apar_dcp_status;
    }

    public String getHead_truck_status() {
        return head_truck_status;
    }

    public void setHead_truck_status(String head_truck_status) {
        this.head_truck_status = head_truck_status;
    }

    public String getVessel_status() {
        return vessel_status;
    }

    public void setVessel_status(String vessel_status) {
        this.vessel_status = vessel_status;
    }

    public String getElectrical_part_status() {
        return electrical_part_status;
    }

    public void setElectrical_part_status(String electrical_part_status) {
        this.electrical_part_status = electrical_part_status;
    }

    public String getLamp_status() {
        return lamp_status;
    }

    public void setLamp_status(String lamp_status) {
        this.lamp_status = lamp_status;
    }

    public String getBan_roda_status() {
        return ban_roda_status;
    }

    public void setBan_roda_status(String ban_roda_status) {
        this.ban_roda_status = ban_roda_status;
    }

    public String getSafety_helmet_status() {
        return safety_helmet_status;
    }

    public void setSafety_helmet_status(String safety_helmet_status) {
        this.safety_helmet_status = safety_helmet_status;
    }

    public String getUniform_status() {
        return uniform_status;
    }

    public void setUniform_status(String uniform_status) {
        this.uniform_status = uniform_status;
    }

    public String getSafety_shoes_status() {
        return safety_shoes_status;
    }

    public void setSafety_shoes_status(String safety_shoes_status) {
        this.safety_shoes_status = safety_shoes_status;
    }

    public String getBarang_terlarang_status() {
        return barang_terlarang_status;
    }

    public void setBarang_terlarang_status(String barang_terlarang_status) {
        this.barang_terlarang_status = barang_terlarang_status;
    }

    public String getTanggal_jam_pengecekan() {
        return tanggal_jam_pengecekan;
    }

    public void setTanggal_jam_pengecekan(String tanggal_jam_pengecekan) {
        this.tanggal_jam_pengecekan = tanggal_jam_pengecekan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
