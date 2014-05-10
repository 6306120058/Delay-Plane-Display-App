/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;

/**
 *
 * @author M3 New
 */
public class Pesawat {
    private String namaPesawat;
    private String idPesawat;
    private String idMaskapai;
    
    public Pesawat(){
        
    }

    public Pesawat(String namaPesawat, String idPesawat, String idMaskapai) {
        this.namaPesawat = namaPesawat;
        this.idPesawat = idPesawat;
        this.idMaskapai = idMaskapai;
    }
    
    public String getNamaPesawat() {
        return namaPesawat;
    }

    public String getIdMaskapai() {
        return idMaskapai;
    }

    public void setIdMaskapai(String idMaskapai) {
        this.idMaskapai = idMaskapai;
    }

    
    public void setNamaPesawat(String namaPesawat) {
        this.namaPesawat = namaPesawat;
    }

    
    public String getIdPesawat() {
        return idPesawat;
    }

    
    public void setIdPesawat(String idPesawat) {
        this.idPesawat = idPesawat;
    }
}
