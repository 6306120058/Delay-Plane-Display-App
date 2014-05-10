/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;

import java.util.Date;

/**
 *
 * @author GHUFRAN
 */
public class Kedatangan {
    private Bandara Source;
    private Date timekedatangan;
    private Date timekedatanganaktual;
    private String statuskedatangan;

    public Kedatangan() {
    }

    public Kedatangan(Bandara Source, Date timekedatangan, Date timekedatanganaktual, String statuskedatangan) {
        this.Source = Source;
        this.timekedatangan = timekedatangan;
        this.timekedatanganaktual = timekedatanganaktual;
        this.statuskedatangan = statuskedatangan;
    }

    public Bandara getSource() {
        return Source;
    }

    public void setSource(Bandara Source) {
        this.Source = Source;
    }

    public Date getTimekedatangan() {
        return timekedatangan;
    }

    public void setTimekedatangan(Date timekedatangan) {
        this.timekedatangan = timekedatangan;
    }

    public Date getTimekedatanganaktual() {
        return timekedatanganaktual;
    }

    public void setTimekedatanganaktual(Date timekedatanganaktual) {
        this.timekedatanganaktual = timekedatanganaktual;
    }

    public String getStatuskedatangan() {
        return statuskedatangan;
    }

    public void setStatuskedatangan(String statuskedatangan) {
        this.statuskedatangan = statuskedatangan;
    }

    
    
    
    
}
