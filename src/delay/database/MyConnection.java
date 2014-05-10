/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.database;


import delay.model.Admin;
import delay.model.Bandara;
import delay.model.Keberangkatan;
import delay.model.Kedatangan;
import delay.model.Maskapai;
import delay.model.Penerbangan;
import delay.model.Pesawat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author GHUFRAN
 */
public class MyConnection {

    Connection conn = null;
    
    public MyConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_penerbangan", "root", "12345");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.1:3306/db_penerbangan", "root", "12345");
        }
        catch(ClassNotFoundException | SQLException ex){
            //JOptionPane.showMessageDialog(null, "Kesalahan pada Koneksi db");
        }
    }
      
   /* =========================================================================
    * All method User 
    * =========================================================================
    */
    
    public void registrasi(Admin a){
        if(a != null){
            try {
                PreparedStatement p = conn.prepareStatement("insert into user "
                        + "(nama, username, password, id_bandara, roles) values (?, ?,?,?,?)");
                p.setString(1, a.getNama());
                p.setString(2, a.getUsername());
                p.setString(3, a.getPassword());
                p.setString(4, a.getIdBandara());
                p.setInt(5, a.getRoles());
                p.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public Admin cekLogin(Admin u){
        Admin admin = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from user where username='"+u.getUsername()+"' and password ='"+u.getPassword()+"'");
            if(rs.next()){
                admin = new Admin();
                admin.setIdBandara(rs.getString(1));
                admin.setNama(rs.getString(2));
                admin.setUsername(rs.getString(3));
                admin.setPassword(rs.getString(4));
                admin.setIdBandara(rs.getString(5));
                admin.setRoles(rs.getInt(6));
                return admin;
            }
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(null, "gagal koneksi cek login");
        }
        return admin;
    }
    
    
   /* =========================================================================
    * All method Maskapai 
    * =========================================================================
    */
    
    public void insertMaskapai(Maskapai m){
        if(m != null){
            try {
                 PreparedStatement p = conn.prepareStatement("insert into maskapai "
                        + "(nama_maskapai, website, phone) values (?, ?, ?)");
                //p.setString(1, m.getIdMaskapai());
                p.setString(1, m.getNamaMaskapai());
                p.setString(2, m.getWebsite());
                p.setString(3, m.getPhone());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void updateMaskapai(Maskapai m){
        if(m != null){
            try {
                 PreparedStatement p = conn.prepareStatement("update maskapai set nama_maskapai = ?,"
                        + "website = ?, phone = ? where id_maskapai = ? ");
                p.setString(1, m.getNamaMaskapai());
                p.setString(2, m.getWebsite());
                p.setString(3, m.getPhone());
                p.setString(4, m.getIdMaskapai());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void deleteMaskapai(Maskapai m){
        boolean cekPenerbangan = false;
        if(m != null){
            try {
                if(!cekPenerbangan){
                    PreparedStatement p = conn.prepareStatement("delete from maskapai "
                            + "where id_maskapai = ? ");
                    p.setString(1, m.getIdMaskapai());
                    p.executeUpdate();
                }else{
                    
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public ArrayList<Maskapai> allMaskapai(){
        ArrayList<Maskapai> listMaskapai = new ArrayList<Maskapai>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from maskapai");
            while(rs.next()){
                Maskapai m = new Maskapai();
                m.setIdMaskapai(rs.getString(1));
                m.setNamaMaskapai(rs.getString(2));
                m.setWebsite(rs.getString(3));
                m.setPhone(rs.getString(4));
                m.setListPesawat(allPesawatByID(m.getIdMaskapai()));
                listMaskapai.add(m);
            }
        } catch (SQLException ex) { 
            
        }
        return listMaskapai;
    }
    
    public Maskapai findMaskapaiByID(String id){
        Maskapai m=null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from maskapai where id_maskapai ='"+id+"' ");
            if(rs.next()){
                m = new Maskapai();
                m.setIdMaskapai(rs.getString(1));
                m.setNamaMaskapai(rs.getString(2));
                m.setWebsite(rs.getString(3));
                m.setPhone(rs.getString(4));
                m.setListPesawat(allPesawatByID(m.getIdMaskapai()));
                return m;
            }
        } catch (SQLException ex) { 
             ex.printStackTrace();
        }
        return m;
    }
    
    public Maskapai findMaskapaiByName(String nama){
        Maskapai m=null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from maskapai where nama_maskapai ='"+nama+"' ");
            if(rs.next()){
                m = new Maskapai();
                m.setIdMaskapai(rs.getString(1));
                m.setNamaMaskapai(rs.getString(2));
                m.setWebsite(rs.getString(3));
                m.setPhone(rs.getString(4));
                m.setListPesawat(allPesawatByID(m.getIdMaskapai()));
                return m;
            }
        } catch (SQLException ex) { 
             ex.printStackTrace();
        }
        return m;
    }
    
    
   /* =========================================================================
    * All method Pesawat 
    * =========================================================================
    */
    public void insertPesawat(Pesawat pe){
        if(pe != null){
            try {
                  PreparedStatement p = conn.prepareStatement("insert into pesawat "
                        + "(type_pesawat, id_maskapai) values (?, ?)");
                //p.setString(1, pe.getIdPesawat());
                p.setString(1, pe.getNamaPesawat());
                p.setString(2, pe.getIdMaskapai());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    public void updatePesawat(Pesawat pe){
        if(pe != null){
            try {
                 PreparedStatement p = conn.prepareStatement("update pesawat set nama_pesawat = ? "
                        + " where id_pesawat = ? ");
                p.setString(1, pe.getNamaPesawat());
                p.setString(2, pe.getIdPesawat());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void deletePesawat(Pesawat pe){
        boolean cekPenerbangan = false;
        if(pe != null){
            try {
                if(!cekPenerbangan){
                    PreparedStatement p = conn.prepareStatement("delete from pesawat "
                            + "where id_pesawat = ? ");
                    p.setString(1, pe.getIdPesawat());
                    p.executeUpdate();
                }else{
                    JOptionPane.showMessageDialog(null, "Tidak dapat dihapus, karena sedang dalam penerbangan");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public ArrayList<Pesawat> allPesawatByID(String id){
        ArrayList<Pesawat> listPesawat = new ArrayList<Pesawat>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from pesawat where id_maskapai='"+id+"' ");
            while(rs.next()){
                Pesawat p = new Pesawat();
                p.setIdPesawat(rs.getString(1));
                p.setNamaPesawat(rs.getString(2));
                listPesawat.add(p);
            }
        } catch (SQLException ex) { 
            
        }
        return listPesawat;
    }
    
    public Pesawat findPesawatByID(String id){
        Pesawat p = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from pesawat where id_pesawat='"+id+"' ");
            while(rs.next()){
                p = new Pesawat();
                p.setIdPesawat(rs.getString(1));
                p.setNamaPesawat(rs.getString(2));
                return p;
            }
        } catch (SQLException ex) { 
            
        }
        return p;
    }
    
    public Pesawat findPesawatByName(String nama){
        Pesawat p = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from pesawat where type_pesawat='"+nama+"' ");
            while(rs.next()){
                p = new Pesawat();
                p.setIdPesawat(rs.getString(1));
                p.setNamaPesawat(rs.getString(2));
                return p;
            }
        } catch (SQLException ex) { 
            
        }
        return p;
    }
    
    public ArrayList<Pesawat> allPesawat(){
        ArrayList<Pesawat> listPesawat = new ArrayList<Pesawat>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from pesawat");
            while(rs.next()){
                Pesawat p = new Pesawat();
                p.setIdPesawat(rs.getString(1));
                p.setNamaPesawat(rs.getString(2));
                listPesawat.add(p);
            }
        } catch (SQLException ex) { 
              
        }
        return listPesawat;
    }
    
   /* =========================================================================
    * All method Penerbangan
    * =========================================================================
    */
    
    public void insertPenerbangan(Penerbangan pe){
        if(pe != null){
            try {
                  PreparedStatement p = conn.prepareStatement("insert into penerbangan "
                        + "(id_maskapai,id_pesawat,id_bandara_src,id_bandara_dest,time_src_schd,time_src_actl,status_src,time_dest_schd,time_dest_actl, status_dest) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                //p.setString(1, null);
                p.setString(1, pe.getMaskapai().getIdMaskapai());
                p.setString(2, pe.getPesawat().getIdPesawat());
                p.setString(3, pe.getSource().getIdBandara());
                p.setString(4, pe.getDestination().getIdBandara());
                p.setString(5, pe.getTimekedatangan());
                p.setString(6, pe.getTimekedatanganaktual());
                p.setString(7, pe.getStatuskedatangan());
                p.setString(8, pe.getTimekeberangkatan());
                p.setString(9, pe.getTimekeberangkatanaktual());
                p.setString(10, pe.getStatuskedatangan());
                p.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage()+"tes");
            }
        }
    }
    
    public void updatePenerbangan(Penerbangan pe){
         if(pe != null){
            try {
                  PreparedStatement p = conn.prepareStatement("update penerbangan set " +
                        "id_maskapai = ?, " +
                        "id_pesawat = ?, " +
                        "id_bandara_src = ?, " +
                        "id_bandara_dest = ?, " +
                        "time_src_schd = ?, " +
                        "time_src_actl = ?, " +
                        "status_src = ?, " +
                        "time_dest_schd = ?, " +
                        "time_dest_actl  = ?, " +
                        "status_dest  = ? where id_penerbangan  = ? " );
                
                p.setString(1, pe.getMaskapai().getIdMaskapai());
                p.setString(2, pe.getPesawat().getIdPesawat());
                p.setString(3, pe.getSource().getIdBandara());
                p.setString(4, pe.getDestination().getIdBandara());
                p.setString(5, pe.getTimekedatangan());
                p.setString(6, pe.getTimekedatanganaktual());
                p.setString(7, pe.getStatuskedatangan());
                p.setString(8, pe.getTimekeberangkatan());
                p.setString(9, pe.getTimekeberangkatanaktual());
                p.setString(10, pe.getStatuskeberangkatan());
                p.setString(11, pe.getIdPenerbangan());
                p.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
        
    public void deletePenerbangan(String id){
        try {
                    PreparedStatement p = conn.prepareStatement("delete from penerbangan "
                            + "where id_penerbangan = ? ");
                    p.setString(1, id);
                    p.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    
    
    public ArrayList<Penerbangan> allPenerbangan(){
        ArrayList<Penerbangan> listPenerbangan = new ArrayList<Penerbangan>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from penerbangan");
            while(rs.next()){
                Penerbangan p = new Penerbangan();
                p.setIdPenerbangan(rs.getString(1));
                p.setMaskapai(findMaskapaiByID(rs.getString(2)));
                p.setPesawat(findPesawatByID(rs.getString(3)));
                p.setSource(findBandaraByID(rs.getString(4)));
                p.setDestination(findBandaraByID(rs.getString(5)));
                p.setTimekedatangan(rs.getString(6));
                p.setTimekedatanganaktual(rs.getString(7));
                p.setStatuskedatangan(rs.getString(8));
                p.setTimekeberangkatan(rs.getString(9));
                p.setTimekeberangkatanaktual(rs.getString(10));
                p.setStatuskeberangkatan(rs.getString(11));
                listPenerbangan.add(p);
            }
        } catch (SQLException ex) { 
            
        }
        return listPenerbangan;
    }
    
    
    /* =========================================================================
    * All method Bandara
    * =========================================================================
    */
    
    public void insertBandara(Bandara b){
        if(b != null){
            try {
                  PreparedStatement p = conn.prepareStatement("insert into bandara "
                        + "(nama_bandara, lokasi) values (?, ?)");
                //p.setString(1, b.getIdBandara());
                p.setString(1, b.getNamaBandara());
                p.setString(2, b.getLokasiBandara());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    public void updateBandara(Bandara b){
        if(b != null){
            try {
                 PreparedStatement p = conn.prepareStatement("update bandara set nama_bandara = ? and lokasi = ? "
                        + " where id_bandara = ? ");
                p.setString(1, b.getNamaBandara());
                p.setString(2, b.getLokasiBandara());
                p.setString(3, b.getIdBandara());
                p.executeUpdate();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void deleteBandara(Bandara b){
        boolean cekPenerbangan = false;
        if(b != null){
            try {
                if(!cekPenerbangan){
                    PreparedStatement p = conn.prepareStatement("delete from bandara "
                            + "where id_bandara = ? ");
                    p.setString(1, b.getIdBandara());
                    p.executeUpdate();
                }else{
                    JOptionPane.showMessageDialog(null, "Tidak dapat dihapus, karena sedang dalam penerbangan");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public ArrayList<Bandara> allBandara(){
        ArrayList<Bandara> listBandara= new ArrayList<Bandara>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from bandara");
            while(rs.next()){
                Bandara b = new Bandara();
                b.setIdBandara(rs.getString(1));
                b.setNamaBandara(rs.getString(2));
                b.setLokasiBandara(rs.getString(3));
                listBandara.add(b);
            }
        } catch (SQLException ex) { 
            
        }
        return listBandara;
    }
    
    public Bandara findBandaraByID(String id){
        Bandara b = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from bandara where id_bandara='"+id+"'");
            if(rs.next()){
                b = new Bandara();
                b.setIdBandara(rs.getString(1));
                b.setNamaBandara(rs.getString(2));
                b.setLokasiBandara(rs.getString(3)); 
            }
            return b;
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
        return b;
    }
    
    public Bandara findBandaraByName(String nama){
        Bandara b = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from bandara where nama_bandara='"+nama+"'");
            if(rs.next()){
                b = new Bandara();
                b.setIdBandara(rs.getString(1));
                b.setNamaBandara(rs.getString(2));
                b.setLokasiBandara(rs.getString(3)); 
            }
            return b;
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
        return b;
    }


}






//bulk


//    public void updatePenerbanganByKeberangkatan(String id, Keberangkatan k){
//         if(id != null && k !=null){
//            try {
//                  PreparedStatement p = conn.prepareStatement("insert into penerbangan "
//                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//                p.setString(1, pe.getIdPenerbangan());
//                p.setString(2, pe.getMaskapai().getIdMaskapai());
//                p.setString(3, pe.getPesawat().getIdPesawat());
//                p.setString(4, pe.getSource().getSource().getIdBandara());
//                p.setString(5, pe.getDestination().getDestination().getIdBandara());
//                p.setDate(6, (Date) pe.getSource().getTimekedatangan());
//                p.setDate(7, (Date) pe.getSource().getTimekedatanganaktual());
//                p.setString(8, pe.getSource().getStatuskedatangan());
//                p.setDate(9, (Date) pe.getDestination().getTimekeberangkatan());
//                p.setDate(10, (Date) pe.getDestination().getTimekeberangkatanaktual());
//                p.setString(11, pe.getSource().getStatuskedatangan());
//                p.executeUpdate();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage());
//            }
//        }
//    }
//    
//        public void updatePenerbanganByKedatangan(String id, Kedatangan k){
//         if(id != null && k !=null){
//            try {
//                  PreparedStatement p = conn.prepareStatement("insert into penerbangan "
//                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//                p.setString(1, pe.getIdPenerbangan());
//                p.setString(2, pe.getMaskapai().getIdMaskapai());
//                p.setString(3, pe.getPesawat().getIdPesawat());
//                p.setString(4, pe.getSource().getSource().getIdBandara());
//                p.setString(5, pe.getDestination().getDestination().getIdBandara());
//                p.setDate(6, (Date) pe.getSource().getTimekedatangan());
//                p.setDate(7, (Date) pe.getSource().getTimekedatanganaktual());
//                p.setString(8, pe.getSource().getStatuskedatangan());
//                p.setDate(9, (Date) pe.getDestination().getTimekeberangkatan());
//                p.setDate(10, (Date) pe.getDestination().getTimekeberangkatanaktual());
//                p.setString(11, pe.getSource().getStatuskedatangan());
//                p.executeUpdate();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage());
//            }
//        }
//    }