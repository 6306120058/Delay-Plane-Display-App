/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;

import java.util.ArrayList;

/**
 *
 * @author M3 New
 */
public class Maskapai {
    
    private String idMaskapai;
    private String namaMaskapai;
    private String phone;
    private String website;
    ArrayList<Pesawat> listPesawat;
    
    public Maskapai(){
        listPesawat = new ArrayList<>();
    }

    public Maskapai(String idMaskapai, String namaMaskapai) {
        this.idMaskapai = idMaskapai;
        this.namaMaskapai = namaMaskapai;
        listPesawat = new ArrayList<>();
    }
    
    public String getIdMaskapai() {
        return idMaskapai;
    }

    public void setIdMaskapai(String idMaskapai) {
        this.idMaskapai = idMaskapai;
    }

    public String getNamaMaskapai() {
        return namaMaskapai;
    }

    public void setNamaMaskapai(String namaMaskapai) {
        this.namaMaskapai = namaMaskapai;
    }

    public ArrayList<Pesawat> getListPesawat() {
        return listPesawat;
    }

    public void setListPesawat(ArrayList<Pesawat> listPesawat) {
        this.listPesawat = listPesawat;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    

}
