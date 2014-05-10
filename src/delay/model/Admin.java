/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.model;

/**
 *
 * @author M3 New
 */
public class Admin {
    private String idUser;
    private String nama;
    private String username;
    private String password;
    private String idBandara;
    private int roles = 1;

    public Admin(){
    
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    public Admin(String idUser, String nama, String username, String password, String idBandara) {
        this.idUser = idUser;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.idBandara = idBandara;
    }
    
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    public String getIdBandara() {
        return idBandara;
    }

    public void setIdBandara(String idBandara) {
        this.idBandara = idBandara;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
