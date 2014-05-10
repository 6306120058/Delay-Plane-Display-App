/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package delay.view;

import delay.database.MyConnection;
import delay.model.Admin;
import delay.model.Bandara;
import delay.model.Keberangkatan;
import delay.model.Kedatangan;
import delay.model.Maskapai;
import delay.model.Penerbangan;
import delay.model.Pesawat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author M3 New
 */
public class frmPenerbangan extends javax.swing.JFrame implements Runnable{
    private MyConnection db = new MyConnection();
    private Admin a = null;
    private frmKeberangkatan frmK;
    private frmKedatangan frmKe;
    private DefaultListModel listModelKedatangan = new DefaultListModel();
    private DefaultTableModel tbModelKedatangan = null;
    private DefaultListModel listModelKeberangkatan = new DefaultListModel();
    private DefaultTableModel tbModelKeberangkatan = null;
    private ArrayList<Maskapai> daftarMaskapai = new ArrayList<Maskapai>();
    private ArrayList<Pesawat> daftarPesawat = new ArrayList<Pesawat>();
    private ArrayList<Bandara> daftarBandara = new ArrayList<Bandara>();
    private ArrayList<Penerbangan> daftarPenerbangan = new ArrayList<Penerbangan>();
    private Bandara bandara = null;
    private Maskapai maskapai=null;
    private Pesawat pesawat=null;
    private Penerbangan penerbangan = null;
    private Kedatangan kedatangan = null;
    private Keberangkatan keberangkatan = null;
    
    /**
     * Creates new form frmPenerbangan
     */
    public frmPenerbangan(Admin a) {
        
        initComponents();
        this.a = a;
        frmK = new frmKeberangkatan(this.a);
        frmK.setVisible(true);
        frmKe = new frmKedatangan(this.a);
        frmKe.setVisible(true);
        bandara = db.findBandaraByID(a.getIdBandara());
        lblBandara.setText("Bandara : "+bandara.getNamaBandara());
        Thread t = new Thread(this);
        t.start();
        init();
    }
    
    public void init(){
        refreshCmbMaskapai();
        refreshCmbPesawat(daftarMaskapai.get(0).getIdMaskapai());
        
//        refreshCmbBandara();
        refreshCmbTujuan(a.getIdBandara());

        tbModelKedatangan = new DefaultTableModel();
        tbModelKedatangan.addColumn("Asal");
        tbModelKedatangan.addColumn("Pesawat");
        tbModelKedatangan.addColumn("Maskapai");
        tbModelKedatangan.addColumn("Jadwal Datang");
        tbModelKedatangan.addColumn("Datang");
        tbModelKedatangan.addColumn("Status");
        tblKedatangan.setModel(tbModelKedatangan);
        
        tbModelKeberangkatan = new DefaultTableModel();
        tbModelKeberangkatan.addColumn("Tujuan");
        tbModelKeberangkatan.addColumn("Pesawat");
        tbModelKeberangkatan.addColumn("Maskapai");
        tbModelKeberangkatan.addColumn("Jadwal Berangkat");
        tbModelKeberangkatan.addColumn("Berangkat");
        tbModelKeberangkatan.addColumn("Status");
        tblKeberangkatan.setModel(tbModelKeberangkatan);
        
        refreshPenerbangan();
        refreshTabelPenerbangan();
        
        //System.out.println(cmbBandara.getSelectedItem().toString());
    }
    
    public void refreshPenerbangan(){
        daftarPenerbangan = db.allPenerbangan();
        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
            Penerbangan penerbangan = it.next();
            //Kedatangan ke = new Kedatangan();
            //Keberangkatan k = new Keberangkatan();
            
            //daftarKeberangkatan.add(k);
            if(a.getIdBandara().equals(penerbangan.getSource().getIdBandara())){
                listModelKeberangkatan.addElement(penerbangan.getIdPenerbangan());
            }//daftarKedatangan.add(ke);
            if(a.getIdBandara().equals(penerbangan.getDestination().getIdBandara())){
                listModelKedatangan.addElement(penerbangan.getIdPenerbangan());
            }
        }
    }
    
    public void refreshTabelPenerbangan(){
        if(tbModelKeberangkatan.getRowCount()>0){
            for(int i = 0; i<= tbModelKeberangkatan.getRowCount();i++){
                tbModelKeberangkatan.removeRow(i);
            }
        }
        if(tbModelKedatangan.getRowCount()>0){
            for(int i = 0; i<= tbModelKedatangan.getRowCount();i++){
                tbModelKedatangan.removeRow(i);
            }
        }
        
        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
                Penerbangan penerbangan = it.next();
                if(a.getIdBandara().equals(penerbangan.getSource().getIdBandara())){
                    tbModelKeberangkatan.addRow(new String[] {
                        penerbangan.getDestination().getNamaBandara(),
                        penerbangan.getMaskapai().getNamaMaskapai(),
                        penerbangan.getPesawat().getNamaPesawat(),
                        penerbangan.getTimekeberangkatan(),
                        penerbangan.getTimekeberangkatanaktual(),
                        penerbangan.getStatuskeberangkatan(),
                    });
                }
                
                if(a.getIdBandara().equals(penerbangan.getDestination().getIdBandara())){
                    tbModelKedatangan.addRow(new String[] {
                        penerbangan.getSource().getNamaBandara(),
                        penerbangan.getMaskapai().getNamaMaskapai(),
                        penerbangan.getPesawat().getNamaPesawat(),
                        penerbangan.getTimekedatangan(),
                        penerbangan.getTimekedatanganaktual(),
                        penerbangan.getStatuskedatangan(),
                    });
                }
        }
    

    
    }
    
    public void refreshCmbMaskapai(){
        cmbMaskapai.removeAllItems();
        daftarMaskapai = db.allMaskapai();
        for (Iterator<Maskapai> it = daftarMaskapai.iterator(); it.hasNext();) {
            Maskapai maskapai = it.next();
            cmbMaskapai.addItem(maskapai.getNamaMaskapai());
        }
    }
    
    public boolean searchPesawatFlight(String id){
        boolean tes = false;
        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
                        Penerbangan p = it.next();
                        if(p.getPesawat().getIdPesawat().equals(id)){
                            tes = true;
                            return tes;
                        }
        }
        return tes;
    }
    
    public Penerbangan searchDataKeberangkatan(int i){
        Penerbangan pe = null;
        int cek = 0;
        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
                        Penerbangan p = it.next();
                if(a.getIdBandara().equals(p.getSource().getIdBandara())){
                    if(i==cek){
                        return p;
                    }
                    cek++;
                }
        }
        return null;
    }
    
    public Penerbangan searchDataKedatangan(int i){
        Penerbangan pe = null;
        int cek = 0;
        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
                        Penerbangan p = it.next();
                if(a.getIdBandara().equals(p.getDestination().getIdBandara())){
                    if(i==cek){
                        return p;
                    }
                    cek++;
                }
        }
        return null;
    }
    
    public void refreshCmbPesawat(String id){
        cmbPesawat.removeAllItems();
        if(id!=null){
            daftarPesawat = db.allPesawatByID(id);
            for (Iterator<Pesawat> it = daftarPesawat.iterator(); it.hasNext();) {
                Pesawat pesawat = it.next();
                //if(!searchPesawatFlight(pesawat.getIdPesawat())){
                    cmbPesawat.addItem(pesawat.getNamaPesawat());
                //}
            }
        }else{
            
        }
    }
    
        public void refreshCmbTujuan(String id){
        cmbTujuan.removeAllItems();
        daftarBandara = db.allBandara();
        for (Iterator<Bandara> it = daftarBandara.iterator(); it.hasNext();) {
            Bandara bandara = it.next();
            if(!bandara.getIdBandara().equals(id))
            {
                cmbTujuan.addItem(bandara.getNamaBandara());
            }
        }
    }
    
    public void selectedKeberangkatan(){
    
    }
    
    public void selectedKedatangan(){
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbPesawat = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtBerangkatMenit = new javax.swing.JTextField();
        txtBerangkatJam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDatangJam = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDatangMenit = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbMaskapai = new javax.swing.JComboBox();
        btnTakeOff = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cmbTujuan = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKeberangkatan = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBerangkatMenitKe = new javax.swing.JTextField();
        txtBerangkatJamKe = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDatangJamKe = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDatangMenitKe = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnLanded = new javax.swing.JButton();
        txtMaskapaiKe = new javax.swing.JTextField();
        txtPesawaiKe = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKedatangan = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        lblBandara = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Delay Plane Display");
        setResizable(false);

        jLabel5.setFont(new java.awt.Font("GeoSlab703 Md BT", 1, 24)); // NOI18N
        jLabel5.setText("Form Kelola Penerbangan");

        lblTime.setFont(new java.awt.Font("GeoSlab703 Md BT", 1, 24)); // NOI18N
        lblTime.setText("20:20:20");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(102, 204, 255));

        jPanel1.setBackground(new java.awt.Color(51, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Keberangkatan"));

        jLabel1.setText("Nama Maskapai");

        jLabel3.setText("Nama Pesawat");

        cmbPesawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPesawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPesawatActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel4.setText("Jadwal");

        jLabel2.setText(":");

        jLabel8.setText(":");

        jLabel6.setText("Berangkat");

        jLabel9.setText("Datang");

        cmbMaskapai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMaskapai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMaskapaiActionPerformed(evt);
            }
        });

        btnTakeOff.setText("Take off");
        btnTakeOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTakeOffActionPerformed(evt);
            }
        });

        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        jLabel7.setText("Tujuan");

        cmbTujuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTakeOff))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtDatangJam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtBerangkatJam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtBerangkatMenit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDatangMenit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cmbPesawat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbMaskapai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCreate))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(cmbTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbMaskapai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbPesawat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBerangkatJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBerangkatMenit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDatangJam)
                    .addComponent(txtDatangMenit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnTakeOff)
                    .addComponent(btnCreate))
                .addGap(4, 4, 4))
        );

        tblKeberangkatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Pesawat", "Id Pesawat", "Jam Keberangkatan", "Status"
            }
        ));
        tblKeberangkatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKeberangkatanMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblKeberangkatan);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Keberangkatan", jPanel5);

        jPanel6.setBackground(new java.awt.Color(51, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(51, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Kedatangan"));

        jLabel10.setText("Nama Maskapai");

        jLabel11.setText("Nama Pesawat");

        jLabel12.setText("Jadwal");

        jLabel13.setText(":");

        jLabel14.setText(":");

        jLabel15.setText("Berangkat");

        jLabel16.setText("Datang");

        btnLanded.setText("Landed");
        btnLanded.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLandedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLanded)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtDatangJamKe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtBerangkatJamKe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBerangkatMenitKe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDatangMenitKe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtMaskapaiKe)
                            .addComponent(txtPesawaiKe))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtMaskapaiKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesawaiKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBerangkatJamKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBerangkatMenitKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtDatangJamKe)
                    .addComponent(txtDatangMenitKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLanded)
                .addContainerGap())
        );

        tblKedatangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Pesawat", "Id Pesawat", "Jam Kedatangan", "Status"
            }
        ));
        tblKedatangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKedatanganMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKedatangan);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        jTabbedPane1.addTab("Kedatangan", jPanel6);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delay/image/PLANE.png"))); // NOI18N

        lblBandara.setFont(new java.awt.Font("GeoSlab703 Md BT", 1, 14)); // NOI18N
        lblBandara.setText("Bandara : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(lblTime))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBandara)
                                    .addComponent(jLabel5))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(3, 3, 3)
                        .addComponent(lblBandara)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime)
                        .addGap(14, 14, 14)))
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPesawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPesawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPesawatActionPerformed

    private void cmbMaskapaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMaskapaiActionPerformed
        // TODO add your handling code here:
        if(cmbMaskapai.getSelectedIndex() > -1){
            String id = daftarMaskapai.get(cmbMaskapai.getSelectedIndex()).getIdMaskapai();
            refreshCmbPesawat(id);
        }
    }//GEN-LAST:event_cmbMaskapaiActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        Penerbangan p = new Penerbangan();
        //p.setIdPenerbangan("022dd");
        p.setMaskapai(db.findMaskapaiByName(cmbMaskapai.getSelectedItem().toString()));
        p.setPesawat(db.findPesawatByName(cmbPesawat.getSelectedItem().toString()));
        p.setSource(db.findBandaraByID(a.getIdBandara()));
        p.setDestination(db.findBandaraByName(cmbTujuan.getSelectedItem().toString()));
        p.setTimekeberangkatan(txtBerangkatJam.getText()+":"+txtBerangkatMenit.getText()+":00");
        p.setTimekedatangan(txtDatangJam.getText()+":"+txtDatangMenit.getText()+":00");
        db.insertPenerbangan(p);
        frmK.refreshPenerbangan();
        frmK.refreshTabelPenerbangan();
        frmKe.refreshPenerbangan();
        frmKe.refreshTabelPenerbangan();
        refreshPenerbangan();
        refreshTabelPenerbangan();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblKeberangkatanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeberangkatanMousePressed
        // TODO add your handling code here:
        if(tblKeberangkatan.getSelectedRow() > -1 ){
            penerbangan = searchDataKeberangkatan(tblKeberangkatan.getSelectedRow());
        }
        cmbMaskapai.setSelectedItem(penerbangan.getMaskapai().getNamaMaskapai());
        cmbPesawat.setSelectedItem(penerbangan.getPesawat().getNamaPesawat());
        String[] berangkat = penerbangan.getTimekeberangkatan().split(":");
        String[] datang  = penerbangan.getTimekedatangan().split(":");
        txtBerangkatJam.setText(berangkat[0]);
        txtBerangkatMenit.setText(berangkat[1]);
        txtDatangJam.setText(datang[0]);
        txtDatangMenit.setText(datang[1]);
    }//GEN-LAST:event_tblKeberangkatanMousePressed

    private void tblKedatanganMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKedatanganMousePressed
        // TODO add your handling code here:
        if(tblKedatangan.getSelectedRow() > -1 ){
            penerbangan = searchDataKedatangan(tblKedatangan.getSelectedRow());
        }
        txtMaskapaiKe.setText(penerbangan.getMaskapai().getNamaMaskapai());
        txtPesawaiKe.setText(penerbangan.getPesawat().getNamaPesawat());
        String[] berangkat = penerbangan.getTimekeberangkatan().split(":");
        String[] datang  = penerbangan.getTimekedatangan().split(":");
        txtBerangkatJamKe.setText(berangkat[0]);
        txtBerangkatMenitKe.setText(berangkat[1]);
        txtDatangJamKe.setText(datang[0]);
        txtDatangMenitKe.setText(datang[1]);
    }//GEN-LAST:event_tblKedatanganMousePressed

    private void btnTakeOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTakeOffActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date d = new Date();
            System.out.println("waktu : "+formatter.format(d));
            penerbangan.setTimekeberangkatanaktual(formatter.format(d));
            String[] times = penerbangan.getTimekeberangkatan().split(":");
            String[] times2 = penerbangan.getTimekeberangkatanaktual().split(":");
                    //selisih
                    int[] waktu = new int[3];
                    int[] waktu2 = new int[3];
                    waktu[0] = Integer.parseInt(times[0])*3600;
                    waktu[1] = Integer.parseInt(times[1])*60;
                    waktu[2] = Integer.parseInt(times[2]);
                    int hasil = waktu[0]+waktu[1]+waktu[2];
                   
                    waktu2[0] = Integer.parseInt(times2[0])*3600;
                    waktu2[1] = Integer.parseInt(times2[1])*60;
                    waktu2[2] = Integer.parseInt(times2[2]);
                    int hasil2= waktu2[0]+waktu2[1]+waktu2[2];
                    int perbedaan = hasil2 - hasil;
                    System.out.println("perbedaan : "+perbedaan);
                    if(perbedaan > 30){
                        penerbangan.setStatuskeberangkatan("Delay");
                        System.out.println("delay");
                    }else{
                        penerbangan.setStatuskeberangkatan("On-Time");
                    }
            db.updatePenerbangan(penerbangan);
            frmK.refreshPenerbangan();
            frmK.refreshTabelPenerbangan();
            frmKe.refreshPenerbangan();
            frmKe.refreshTabelPenerbangan();
            refreshPenerbangan();
        refreshTabelPenerbangan();
    }//GEN-LAST:event_btnTakeOffActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        refreshCmbMaskapai();
        refreshCmbPesawat(daftarMaskapai.get(0).getIdMaskapai());
        refreshCmbTujuan(a.getIdBandara());
        txtBerangkatJam.setText("");
        txtBerangkatMenit.setText("");
        btnSave.setEnabled(true);
        btnTakeOff.setEnabled(false);
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnLandedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLandedActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date d = new Date();
            penerbangan.setTimekedatanganaktual(formatter.format(d));
            String[] times = penerbangan.getTimekedatangan().split(":");
            String[] times2 = penerbangan.getTimekedatanganaktual().split(":");
                    //selisih
                    int[] waktu = new int[3];
                    int[] waktu2 = new int[3];
                    waktu[0] = Integer.parseInt(times[0])*3600;
                    waktu[1] = Integer.parseInt(times[1])*60;
                    waktu[2] = Integer.parseInt(times[2]);
                    int hasil = waktu[0]+waktu[1]+waktu[2];
                   
                    waktu2[0] = Integer.parseInt(times2[0])*3600;
                    waktu2[1] = Integer.parseInt(times2[1])*60;
                    waktu2[2] = Integer.parseInt(times2[2]);
                    int hasil2= waktu2[0]+waktu2[1]+waktu2[2];
                    int perbedaan = hasil2 - hasil;
                    System.out.println("hasil : "+penerbangan.getTimekedatanganaktual());
                    if(perbedaan > 30){
                        penerbangan.setStatuskedatangan("Delay");
                    }else{
                        penerbangan.setStatuskedatangan("On - Time");
                    }
            
            db.updatePenerbangan(penerbangan);
            frmK.refreshPenerbangan();
            frmK.refreshTabelPenerbangan();
            frmKe.refreshPenerbangan();
            frmKe.refreshTabelPenerbangan();
            refreshPenerbangan();
            refreshTabelPenerbangan();
    }//GEN-LAST:event_btnLandedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPenerbangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPenerbangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPenerbangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPenerbangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPenerbangan(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnLanded;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTakeOff;
    private javax.swing.JComboBox cmbMaskapai;
    private javax.swing.JComboBox cmbPesawat;
    private javax.swing.JComboBox cmbTujuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBandara;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tblKeberangkatan;
    private javax.swing.JTable tblKedatangan;
    private javax.swing.JTextField txtBerangkatJam;
    private javax.swing.JTextField txtBerangkatJamKe;
    private javax.swing.JTextField txtBerangkatMenit;
    private javax.swing.JTextField txtBerangkatMenitKe;
    private javax.swing.JTextField txtDatangJam;
    private javax.swing.JTextField txtDatangJamKe;
    private javax.swing.JTextField txtDatangMenit;
    private javax.swing.JTextField txtDatangMenitKe;
    private javax.swing.JTextField txtMaskapaiKe;
    private javax.swing.JTextField txtPesawaiKe;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        boolean time=true;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            while(time){
            Date d = new Date();
            lblTime.setText(formatter.format(d));
            frmK.getLblTime().setText(lblTime.getText());
            frmKe.getLblTime().setText(lblTime.getText());
            Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(frmPenerbangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//for runn
//for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
//                    Penerbangan penerbangan = it.next();
//                   
//                    String[] times = penerbangan.getTimekeberangkatan().split(":");
//                    String[] times1 = penerbangan.getTimekedatangan().split(":");
//                    String[] times2 = lblTime.getText().split(":");
//                    //selisih
//                    int[] waktu = new int[3];
//                    int[] waktu1 = new int[3];
//                    int[] waktu2 = new int[3];
//                    waktu[0] = Integer.parseInt(times[0])*3600;
//                    waktu[0] = Integer.parseInt(times[1])*60;
//                    waktu[0] = Integer.parseInt(times[2]);
//                    int hasil = waktu[0]+waktu[1]+waktu[2];
//                   
//                    waktu1[0] = Integer.parseInt(times1[0])*3600;
//                    waktu1[0] = Integer.parseInt(times1[1])*60;
//                    waktu1[0] = Integer.parseInt(times1[2]);
//                    int hasil1 = waktu1[0]+waktu1[1]+waktu1[2];
//                    
//                    waktu2[0] = Integer.parseInt(times2[0])*3600;
//                    waktu2[0] = Integer.parseInt(times2[1])*60;
//                    waktu2[0] = Integer.parseInt(times2[2]);
//                    int hasil2= waktu2[0]+waktu2[1]+waktu2[2];
//                    int perbedaan = hasil2 - hasil;
//                    int perbedaan2 = hasil2 - hasil1; 
//                    System.out.println("selisih : "+perbedaan);
//                    
//                    if(perbedaan > 30 && penerbangan.getTimekeberangkatanaktual()==null){
//                        penerbangan.setStatuskeberangkatan("Delay");
//                        db.updatePenerbangan(penerbangan);
//                    }
//                    
//                    if(perbedaan2 > 30 && penerbangan.getTimekedatangan()==null){
//                        penerbangan.setStatuskedatangan("Delay");
//                        db.updatePenerbangan(penerbangan);
//                    }
//                    
//                    
//                }

    
//    public void refreshKeberangkatan(){
//        listModelKeberangkatan.removeAllElements();
//        daftarPenerbangan = db.allPenerbangan();
//        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
//            Penerbangan penerbangan = it.next();
//            Keberangkatan k = new Keberangkatan();
//            k = penerbangan.getDestination();
//            daftarKeberangkatan.add(k);
//            listModelKeberangkatan.addElement(penerbangan.getIdPenerbangan());
//        }
//    }
//    
//    public void refreshKedatangan(){
//        listModelKedatangan.removeAllElements();
//        daftarPenerbangan = db.allPenerbangan();
//        for (Iterator<Penerbangan> it = daftarPenerbangan.iterator(); it.hasNext();) {
//            Penerbangan penerbangan = it.next();
//            Kedatangan ke = new Kedatangan();
//            ke = penerbangan.getSource();
//            daftarKedatangan.add(ke);
//            listModelKedatangan.addElement(penerbangan.getIdPenerbangan());
//        }
//    }
//    
//    public void refreshTabelKeberangkatan(){
//        if(tbModelKeberangkatan.getRowCount()>0){
//            for(int i = 0; i<= tbModelKeberangkatan.getRowCount();i++){
//                tbModelKeberangkatan.removeRow(i);
//            }
//        }
//        
//        for (Iterator<Keberangkatan> it = daftarKeberangkatan.iterator(); it.hasNext();) {
//                Keberangkatan keberangkatan = it.next();
//                
//                tbModelKeberangkatan.addRow(new String[] {
//                    keberangkatan.getDestination().getNamaBandara(),
//                    keberangkatan.get().getNamaMaskapai(),
//                    keberangkatan.get.getNamaPesawat(),
//                });
//            }
//    }

//    public void refreshCmbBandara(){
//        cmbBandara.removeAllItems();
//        daftarBandara = db.allBandara();
//        //System.out.println(daftarBandara);
//        for (Iterator<Bandara> it = daftarBandara.iterator(); it.hasNext();) {
//            Bandara bandara = it.next();
//            cmbBandara.addItem(bandara.getNamaBandara());
//        }
//    }
//    
//    public void refreshCmbTujuan(String id){
//        cmbTujuan.removeAllItems();
//        for (Iterator<Bandara> it = daftarBandara.iterator(); it.hasNext();) {
//            Bandara bandara = it.next();
//            if(!bandara.getIdBandara().equals(id))
//            {
//                cmbTujuan.addItem(bandara.getNamaBandara());
//            }
//        }
//    }