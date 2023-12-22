package Form;
import Koperasi.CRUD;
import Main.Menu;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormTransaksiPinjaman extends javax.swing.JFrame {
    CRUD Data = new CRUD();
    String status = "";
    public FormTransaksiPinjaman() {
        initComponents();
        Data.Koneksi();
        TampilData();
    }
    public void TampilData(){
        String namaTable = "trans_pinjaman";
        String daftarField[]  = {
            "trans_pinjaman.nopinjam",
            "jenis_pinjaman.jenis_pinj",
            "trans_pinjaman.tglpinjam",
            "anggota.nama_anggota",  
            "trans_pinjaman.keterangan",
            "trans_pinjaman.pinjpokok",
            "trans_pinjaman.lama",
            "trans_pinjaman.bunga",
            "trans_pinjaman.admin",
            "trans_pinjaman.jml",
            "trans_pinjaman.total",
            "trans_pinjaman.angsuran"
        };
        String judulKolom[] = {
            "Nomor Pinjaman",            
            "Jenis Pinjaman",
            "Tanggal",
            "Anggota",
            "Keterangan",            
            "Pinjaman Pokok",
            "Lama",
            "Bunga",
            "Admin",
            "Jumlah",
            "Total",
            "Angsuran"
        };
        String joinClause1 = "INNER JOIN jenis_pinjaman";
        String onClause1 = "trans_pinjaman.kode_pinj = jenis_pinjaman.kode_pinj";
        String joinClause2 = "INNER JOIN anggota";
        String onClause2 = "trans_pinjaman.no_anggota = anggota.no_anggota";
        tableTransaksiPinjaman.setModel(Data.TampilData(namaTable, daftarField, judulKolom, joinClause1, onClause1, joinClause2, onClause2));
    }
    public void EventTableClick(){
        try {
            DefaultTableModel tabelModel = (DefaultTableModel) tableTransaksiPinjaman.getModel();
            int indexTerpilih =  tableTransaksiPinjaman.getSelectedRow();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(tabelModel.getValueAt(indexTerpilih, 2).toString());
            
            txtNo.setText(tabelModel.getValueAt(indexTerpilih, 0).toString());
            txtTanggal.setDate(parsedDate);
            txtKeterangan.setText(tabelModel.getValueAt(indexTerpilih, 4).toString());           
            txtPinjamanPokok.setText(tabelModel.getValueAt(indexTerpilih, 5).toString());
            txtLama.setText(tabelModel.getValueAt(indexTerpilih, 6).toString());
            txtBunga.setText(tabelModel.getValueAt(indexTerpilih, 7).toString());
            txtAdmin.setText(tabelModel.getValueAt(indexTerpilih, 8).toString());
            txtJumlah.setText(tabelModel.getValueAt(indexTerpilih, 9).toString());
            txtTotal.setText(tabelModel.getValueAt(indexTerpilih, 10).toString());
            txtAngsuran.setText(tabelModel.getValueAt(indexTerpilih, 11).toString());
        } catch (ParseException ex) {
            Logger.getLogger(FormTransaksiPinjaman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void TambahData() {
        String kodePinjaman = "";
        String noAnggota = "";
//      Jenis Pinjaman
        try {
            String column = txtPinjaman.getSelectedItem().toString();
            JenisPinjaman jenisPinjaman = JenisPinjaman.findByCoulmn(column);
            if (jenisPinjaman != null) {
                kodePinjaman = jenisPinjaman.getKodePinj().toString();
            } else {
                kodePinjaman = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }
//      Anggota
        try {
            String column = txtAnggota.getSelectedItem().toString();
            Anggota anggota = Anggota.findByCoulmn(column);
            if (anggota != null) {
                noAnggota = anggota.getNoAnggota().toString();
            } else {
                noAnggota = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }
        Date selectedDate = txtTanggal.getDate();
        status = Data.TambahData("trans_pinjaman", "nopinjam ,kode_pinj ,tglpinjam ,no_anggota ,keterangan ,pinjpokok ,lama ,bunga ,admin ,jml ,total ,angsuran", 
            "'" + txtNo.getText() + "',"+            
            "'" + kodePinjaman + "',"+
            "'" + Data.FormatTanggal(selectedDate) + "',"+
            "'" + noAnggota + "',"+
            "'" + txtKeterangan.getText() + "',"+
            "'" + txtPinjamanPokok.getText() + "',"+
            "'" + txtLama.getText() + "',"+
            "'" + txtBunga.getText() + "',"+
            "'" + txtAdmin.getText() + "',"+
            "'" + txtJumlah.getText() + "',"+
            "'" + txtTotal.getText() + "',"+
            "'" + txtAngsuran.getText() + "'"
        );
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void EditData(){
        String kodePinjaman = "";
        String noAnggota = "";
//      Jenis Pinjaman
        try {
            String column = txtPinjaman.getSelectedItem().toString();
            JenisPinjaman jenisPinjaman = JenisPinjaman.findByCoulmn(column);
            if (jenisPinjaman != null) {
                kodePinjaman = jenisPinjaman.getKodePinj().toString();
            } else {
                kodePinjaman = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }
//      Anggota
        try {
            String column = txtAnggota.getSelectedItem().toString();
            Anggota anggota = Anggota.findByCoulmn(column);
            if (anggota != null) {
                noAnggota = anggota.getNoAnggota().toString();
            } else {
                noAnggota = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }
        Date selectedDate = txtTanggal.getDate();
        status = Data.EditData("trans_pinjaman", 
                "nopinjam = '" + txtNo.getText() + "'," + 
                "kode_pinj = '" + kodePinjaman + "'," +                                             
                "tglpinjam = '" + Data.FormatTanggal(selectedDate) + "'," +                                
                "no_anggota = '" + noAnggota + "'," +                                
                "keterangan = '" + txtKeterangan.getText() + "'," +                                           
                "pinjpokok = '" + txtPinjamanPokok.getText() + "'," +                                
                "lama = '" + txtLama.getText() + "'," +                                
                "bunga = '" + txtBunga.getText() + "'," +                                     
                "admin = '" + txtAdmin.getText() + "'," +                                
                "jml = '" + txtJumlah.getText() + "'," +                                
                "total = '" + txtTotal.getText() + "'," +                                       
                "angsuran = '" + txtAngsuran.getText() + "'"     
        , "nopinjam=" + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void HapusData(){
        status = Data.HapusData("trans_pinjaman", " nopinjam= " + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void Kosongkan(){
        txtNo.setText("");            
        txtTanggal.setDate(new java.util.Date());
        txtKeterangan.setText("");
        txtPinjamanPokok.setText("");
        txtLama.setText("");
        txtBunga.setText("");
        txtAdmin.setText("");
        txtJumlah.setText("");
        txtTotal.setText("");
        txtAngsuran.setText("");        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("koperasi?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jenisPinjamanQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JenisPinjaman j");
        jenisPinjamanList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jenisPinjamanQuery.getResultList();
        anggotaQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT a FROM Anggota a");
        anggotaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : anggotaQuery.getResultList();
        jLabel1 = new javax.swing.JLabel();
        labelNo = new javax.swing.JLabel();
        labelAlamat = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        labelKota = new javax.swing.JLabel();
        txtPinjamanPokok = new javax.swing.JTextField();
        labelTelephone = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKosongkan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksiPinjaman = new javax.swing.JTable();
        btnKembali = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtLama = new javax.swing.JTextField();
        labelTelephone1 = new javax.swing.JLabel();
        labelTelephone2 = new javax.swing.JLabel();
        txtBunga = new javax.swing.JTextField();
        txtAdmin = new javax.swing.JTextField();
        labelTelephone3 = new javax.swing.JLabel();
        labelTelephone4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        labelTelephone5 = new javax.swing.JLabel();
        txtAngsuran = new javax.swing.JTextField();
        labelTelephone6 = new javax.swing.JLabel();
        txtAnggota = new javax.swing.JComboBox<>();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtPinjaman = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 700));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Form Transaksi Pinjaman");

        labelNo.setText("No");

        labelAlamat.setText("Anggota");

        labelKota.setText("Keterangan");

        labelTelephone.setText("Pinjaman Pokok");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKosongkan.setText("Kosongkan");
        btnKosongkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKosongkanActionPerformed(evt);
            }
        });

        tableTransaksiPinjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableTransaksiPinjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiPinjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTransaksiPinjaman);

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        jLabel2.setText("Jenis");

        labelTelephone1.setText("Lama");

        labelTelephone2.setText("Bunga");

        labelTelephone3.setText("Admin");

        labelTelephone4.setText("Jumlah");

        labelTelephone5.setText("Total");

        labelTelephone6.setText("Angsuran");

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, anggotaList, txtAnggota);
        bindingGroup.addBinding(jComboBoxBinding);

        jLabel3.setText("Tanggal");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jenisPinjamanList, txtPinjaman);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKembali))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelNo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKosongkan))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNo, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPinjaman, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelKota)
                                    .addComponent(labelTelephone)
                                    .addComponent(labelAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelTelephone1)
                                    .addComponent(labelTelephone2)
                                    .addComponent(labelTelephone3)
                                    .addComponent(labelTelephone4)
                                    .addComponent(labelTelephone5)
                                    .addComponent(labelTelephone6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPinjamanPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBunga, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnKembali))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNo)
                            .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtPinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAlamat)
                            .addComponent(txtAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelKota)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone)
                            .addComponent(txtPinjamanPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone1)
                            .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone2)
                            .addComponent(txtBunga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone3)
                            .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone4)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone5)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone6)
                            .addComponent(txtAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnEdit)
                            .addComponent(btnHapus)
                            .addComponent(btnKosongkan)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        TambahData();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        EditData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        HapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKosongkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKosongkanActionPerformed
        Kosongkan();
    }//GEN-LAST:event_btnKosongkanActionPerformed

    private void tableTransaksiPinjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiPinjamanMouseClicked
        EventTableClick();
    }//GEN-LAST:event_tableTransaksiPinjamanMouseClicked

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        this.dispose();
        Menu Menu = new Menu();
        Menu.setVisible(true);
    }//GEN-LAST:event_btnKembaliActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksiPinjaman().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.util.List<Form.Anggota> anggotaList;
    private javax.persistence.Query anggotaQuery;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnKosongkan;
    private javax.swing.JButton btnTambah;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Form.JenisPinjaman> jenisPinjamanList;
    private javax.persistence.Query jenisPinjamanQuery;
    private javax.swing.JLabel labelAlamat;
    private javax.swing.JLabel labelKota;
    private javax.swing.JLabel labelNo;
    private javax.swing.JLabel labelTelephone;
    private javax.swing.JLabel labelTelephone1;
    private javax.swing.JLabel labelTelephone2;
    private javax.swing.JLabel labelTelephone3;
    private javax.swing.JLabel labelTelephone4;
    private javax.swing.JLabel labelTelephone5;
    private javax.swing.JLabel labelTelephone6;
    private javax.swing.JTable tableTransaksiPinjaman;
    private javax.swing.JTextField txtAdmin;
    private javax.swing.JComboBox<String> txtAnggota;
    private javax.swing.JTextField txtAngsuran;
    private javax.swing.JTextField txtBunga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtNo;
    private javax.swing.JComboBox<String> txtPinjaman;
    private javax.swing.JTextField txtPinjamanPokok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTotal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
