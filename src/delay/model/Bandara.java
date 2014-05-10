/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;

/**
 *
 * @author GHUFRAN
 */
public class Bandara {
    private String idBandara;
    private String namaBandara;
    private String lokasiBandara;

    public Bandara(String idBandara, String namaBandara, String lokasiBandara) {
        this.idBandara = idBandara;
        this.namaBandara = namaBandara;
        this.lokasiBandara = lokasiBandara;
    }
    
    public Bandara(){
        
    }

    public String getIdBandara() {
        return idBandara;
    }

    public void setIdBandara(String idBandara) {
        this.idBandara = idBandara;
    }

    public String getNamaBandara() {
        return namaBandara;
    }

    public void setNamaBandara(String namaBandara) {
        this.namaBandara = namaBandara;
    }

    public String getLokasiBandara() {
        return lokasiBandara;
    }

    public void setLokasiBandara(String lokasiBandara) {
        this.lokasiBandara = lokasiBandara;
    }
    
    
}
