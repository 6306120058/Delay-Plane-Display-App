/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;



/**
 *
 * @author GHUFRAN
 */
public class Penerbangan {
    private String idPenerbangan;
    private Maskapai maskapai;
    private Pesawat pesawat;
    private Bandara Source;
    private Bandara Destination;
    private String timekeberangkatan;
    private String timekeberangkatanaktual;
    private String statuskeberangkatan;
    private String timekedatangan;
    private String timekedatanganaktual;
    private String statuskedatangan;

    public Penerbangan() {
        
    }

    public Penerbangan(String idPenerbangan, Maskapai maskapai, Pesawat pesawat, Bandara Source, Bandara Destination, String timekeberangkatan, String timekeberangkatanaktual, String statuskeberangkatan, String timekedatangan, String timekedatanganaktual, String statuskedatangan) {
        this.idPenerbangan = idPenerbangan;
        this.maskapai = maskapai;
        this.pesawat = pesawat;
        this.Source = Source;
        this.Destination = Destination;
        this.timekeberangkatan = timekeberangkatan;
        this.timekeberangkatanaktual = timekeberangkatanaktual;
        this.statuskeberangkatan = statuskeberangkatan;
        this.timekedatangan = timekedatangan;
        this.timekedatanganaktual = timekedatanganaktual;
        this.statuskedatangan = statuskedatangan;
    }
   
    public String getIdPenerbangan() {
        return idPenerbangan;
    }

    public void setIdPenerbangan(String idPenerbangan) {
        this.idPenerbangan = idPenerbangan;
    }

    public Maskapai getMaskapai() {
        return maskapai;
    }

    public void setMaskapai(Maskapai maskapai) {
        this.maskapai = maskapai;
    }

    public Pesawat getPesawat() {
        return pesawat;
    }

    public void setPesawat(Pesawat pesawat) {
        this.pesawat = pesawat;
    }

    public Bandara getSource() {
        return Source;
    }

    public void setSource(Bandara Source) {
        this.Source = Source;
    }

    public Bandara getDestination() {
        return Destination;
    }

    public void setDestination(Bandara Destination) {
        this.Destination = Destination;
    }

    public String getTimekeberangkatan() {
        return timekeberangkatan;
    }

    public void setTimekeberangkatan(String timekeberangkatan) {
        this.timekeberangkatan = timekeberangkatan;
    }

    public String getTimekeberangkatanaktual() {
        return timekeberangkatanaktual;
    }

    public void setTimekeberangkatanaktual(String timekeberangkatanaktual) {
        this.timekeberangkatanaktual = timekeberangkatanaktual;
    }

    public String getStatuskeberangkatan() {
        return statuskeberangkatan;
    }

    public void setStatuskeberangkatan(String statuskeberangkatan) {
        this.statuskeberangkatan = statuskeberangkatan;
    }

    public String getTimekedatangan() {
        return timekedatangan;
    }

    public void setTimekedatangan(String timekedatangan) {
        this.timekedatangan = timekedatangan;
    }

    public String getTimekedatanganaktual() {
        return timekedatanganaktual;
    }

    public void setTimekedatanganaktual(String timekedatanganaktual) {
        this.timekedatanganaktual = timekedatanganaktual;
    }

    public String getStatuskedatangan() {
        return statuskedatangan;
    }

    public void setStatuskedatangan(String statuskedatangan) {
        this.statuskedatangan = statuskedatangan;
    }

}
