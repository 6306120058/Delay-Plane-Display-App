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
public class Keberangkatan {
    private Bandara Destination;
    private Date timekeberangkatan;
    private Date timekeberangkatanaktual;
    private String statuskeberangkatan;

    public Keberangkatan() {
    }

    public Keberangkatan(Bandara Destination, Date timekeberangkatan, Date timekeberangkatanaktual, String statuskeberangkatan) {
        this.Destination = Destination;
        this.timekeberangkatan = timekeberangkatan;
        this.timekeberangkatanaktual = timekeberangkatanaktual;
        this.statuskeberangkatan = statuskeberangkatan;
    }
    
    
    public Bandara getDestination() {
        return Destination;
    }

    public void setDestination(Bandara Destination) {
        this.Destination = Destination;
    }

    public Date getTimekeberangkatan() {
        return timekeberangkatan;
    }

    public void setTimekeberangkatan(Date timekeberangkatan) {
        this.timekeberangkatan = timekeberangkatan;
    }

    public Date getTimekeberangkatanaktual() {
        return timekeberangkatanaktual;
    }

    public void setTimekeberangkatanaktual(Date timekeberangkatanaktual) {
        this.timekeberangkatanaktual = timekeberangkatanaktual;
    }

    public String getStatuskeberangkatan() {
        return statuskeberangkatan;
    }

    public void setStatuskeberangkatan(String statuskeberangkatan) {
        this.statuskeberangkatan = statuskeberangkatan;
    }

    
    
}
