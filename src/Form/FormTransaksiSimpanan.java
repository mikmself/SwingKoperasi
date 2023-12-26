package Form;
import Koperasi.CRUD;
import Main.Menu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FormTransaksiSimpanan extends javax.swing.JFrame {
    CRUD Data = new CRUD();
    String status = "";
    public FormTransaksiSimpanan() {
        initComponents();
        Data.Koneksi();
        TampilData();
    }
    public void TampilData(){
        String namaTable = "trans_simpanan";
        String daftarField[]  = {
            "trans_simpanan.nosimpan",
            "jenis_simpanan.jenis_simp",
            "anggota.nama_anggota",  
            "trans_simpanan.tglsimpanan",
            "trans_simpanan.jenis",
            "trans_simpanan.saldo"
        };
        String judulKolom[] = {
            "No",            
            "Simpanan",
            "Anggota",
            "Tanggal",
            "Jenis",
            "Saldo"
        };
        String joinClause1 = "INNER JOIN anggota";
        String onClause1 = "trans_simpanan.no_anggota = anggota.no_anggota";
        String joinClause2 = "INNER JOIN jenis_simpanan";
        String onClause2 = "trans_simpanan.kode_simp = jenis_simpanan.kode_simp";
        tableTransaksiSimpanan.setModel(Data.TampilData(namaTable, daftarField, judulKolom, joinClause1, onClause1, joinClause2, onClause2));
        
        String namaTable2 = "detail_simpanan";
        String daftarField2[]  = {
            "detail_simpanan.nosimpanan",
            "anggota.nama_anggota",
            "detail_simpanan.debit",
            "detail_simpanan.kredit",
            "detail_simpanan.saldo"
        };
        String judulKolom2[] = {
            "Simpanan",            
            "Anggota",
            "Debit",
            "Kredit",
            "Saldo"
        };
        String joinClause1B = "INNER JOIN anggota";
        String onClause1B = "detail_simpanan.no_anggota = anggota.no_anggota";
        tableDetailSimpanan.setModel(Data.TampilData(namaTable2, daftarField2, judulKolom2, joinClause1B, onClause1B, null, null));
    }
    public void EventTableClick(){
        DefaultTableModel tabelModel = (DefaultTableModel) tableTransaksiSimpanan.getModel();
        int indexTerpilih =  tableTransaksiSimpanan.getSelectedRow();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(tabelModel.getValueAt(indexTerpilih, 3).toString());
            txtTanggal.setDate(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace(); 
        }
        txtNo.setText(tabelModel.getValueAt(indexTerpilih, 0).toString());
        txtJenis.setText(tabelModel.getValueAt(indexTerpilih, 4).toString());
        txtSaldo.setText(tabelModel.getValueAt(indexTerpilih, 5).toString());
        
        
        displayDetailSimpananData(Integer.parseInt(txtNo.getText()));
    }
    private void displayDetailSimpananData(int nosimpanan) {
        DefaultTableModel detailTableModel = (DefaultTableModel) tableDetailSimpanan.getModel();
        detailTableModel.setRowCount(0);

        try (Statement statement = Data.conn.createStatement()) {
            String query = "SELECT * FROM detail_simpanan WHERE nosimpanan = " + nosimpanan;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                detailTableModel.addRow(new Object[]{
                        resultSet.getInt("nosimpanan"),
                        resultSet.getInt("no_anggota"),
                        resultSet.getInt("debit"),
                        resultSet.getInt("kredit"),
                        resultSet.getInt("saldo")
                });
                txtDebit.setText(String.valueOf(resultSet.getInt("debit")));
                txtKredit.setText(String.valueOf(resultSet.getInt("kredit")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void TambahData() {
        String kodeSimpanan = "";
        String noAnggota = "";
        Date selectedDate = txtTanggal.getDate();
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
//      Jenis Simpanan
        try {
            String column = txtSimpanan.getSelectedItem().toString();
            JenisSimpanan jenisSimpanan = JenisSimpanan.findByCoulmn(column);
            if (jenisSimpanan != null) {
                kodeSimpanan = jenisSimpanan.getKodeSimp().toString();
            } else {
                kodeSimpanan = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }        
        status = Data.TambahData("trans_simpanan", "nosimpan,kode_simp,no_anggota,tglsimpanan,jenis,saldo", 
            "'" + txtNo.getText() + "',"+
            "'" + kodeSimpanan + "',"+
            "'" + noAnggota + "',"+
            "'" + Data.FormatTanggal(selectedDate) + "'," +              
            "'" + txtJenis.getText() + "'," +            
            "'" + txtSaldo.getText() + "'"
        );
        Data.TambahData("detail_simpanan", "nosimpanan,no_anggota,debit,kredit,saldo", 
            "'" + txtNo.getText() + "',"+
            "'" + noAnggota + "',"+
            "'" + txtDebit.getText() + "'," +              
            "'" + txtKredit.getText() + "'," +            
            "'" + txtSaldo.getText() + "'"
        );
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void EditData(){
        String kodeSimpanan = "";
        String noAnggota = "";
        Date selectedDate = txtTanggal.getDate();
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
//      Jenis Simpanan
        try {
            String column = txtSimpanan.getSelectedItem().toString();
            JenisSimpanan jenisSimpanan = JenisSimpanan.findByCoulmn(column);
            if (jenisSimpanan != null) {
                kodeSimpanan = jenisSimpanan.getKodeSimp().toString();
            } else {
                kodeSimpanan = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return;
        }
        status = Data.EditData("trans_simpanan", 
                "nosimpan = '" + txtNo.getText() + "'," + 
                "kode_simp = '" + kodeSimpanan + "'," + 
                "no_anggota = '" + noAnggota + "'," + 
                "tglsimpanan = '" + Data.FormatTanggal(selectedDate) + "'," +                                
                "jenis = '" + txtJenis.getText() + "',"+
                "saldo = '" + txtSaldo.getText() + "'"
        , "nosimpan=" + txtNo.getText());
        Data.EditData("detail_simpanan", 
                "nosimpanan = '" + txtNo.getText() + "'," + 
                "no_anggota = '" + noAnggota + "'," + 
                "debit = '" + txtDebit.getText() + "'," +                                
                "kredit = '" + txtKredit.getText() + "',"+
                "saldo = '" + txtSaldo.getText() + "'"
        , "nosimpanan=" + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void HapusData(){
        status = Data.HapusData("trans_simpanan", " nosimpan= " + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void Kosongkan(){
        txtNo.setText("");
        txtJenis.setText("");
        txtTanggal.setDate(new java.util.Date());
        txtSaldo.setText("");                  
        txtKredit.setText("");        
        txtDebit.setText("");        
      
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("koperasi?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jenisSimpananQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JenisSimpanan j");
        jenisSimpananList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jenisSimpananQuery.getResultList();
        anggotaQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT a FROM Anggota a");
        anggotaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : anggotaQuery.getResultList();
        jLabel1 = new javax.swing.JLabel();
        labelAlamat = new javax.swing.JLabel();
        labelKota = new javax.swing.JLabel();
        txtJenis = new javax.swing.JTextField();
        labelTelephone = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKosongkan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksiSimpanan = new javax.swing.JTable();
        btnKembali = new javax.swing.JButton();
        labelTelephone1 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        labelTelephone2 = new javax.swing.JLabel();
        labelTelephone3 = new javax.swing.JLabel();
        txtSimpanan = new javax.swing.JComboBox<>();
        txtAnggota = new javax.swing.JComboBox<>();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        txtDebit = new javax.swing.JTextField();
        txtKredit = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDetailSimpanan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 700));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Form Transaksi Simpanan");

        labelAlamat.setText("Anggota");

        labelKota.setText("Tanggal");

        labelTelephone.setText("Jenis");

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

        tableTransaksiSimpanan.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTransaksiSimpanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiSimpananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTransaksiSimpanan);

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        labelTelephone1.setText("Saldo");

        labelTelephone2.setText("Debit");

        labelTelephone3.setText("Kredit");

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jenisSimpananList, txtSimpanan);
        bindingGroup.addBinding(jComboBoxBinding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, anggotaList, txtAnggota);
        bindingGroup.addBinding(jComboBoxBinding);

        tableDetailSimpanan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableDetailSimpanan);

        jLabel2.setText("No");

        jLabel3.setText("Simpanan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(653, 653, 653)
                        .addComponent(btnKembali)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelKota)
                                            .addComponent(labelTelephone)
                                            .addComponent(labelAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelTelephone1)
                                            .addComponent(labelTelephone3)
                                            .addComponent(labelTelephone2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtDebit)
                                            .addComponent(txtAnggota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                            .addComponent(txtJenis)
                                            .addComponent(txtSaldo)
                                            .addComponent(txtKredit)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnTambah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEdit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHapus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnKosongkan))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSimpanan, javax.swing.GroupLayout.Alignment.TRAILING, 0, 305, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
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
                            .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAlamat)
                            .addComponent(txtAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelKota)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone)
                            .addComponent(txtJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone1)
                            .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTelephone2)
                                .addGap(36, 36, 36)
                                .addComponent(labelTelephone3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDebit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtKredit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnEdit)
                            .addComponent(btnHapus)
                            .addComponent(btnKosongkan)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
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

    private void tableTransaksiSimpananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiSimpananMouseClicked
        EventTableClick();
    }//GEN-LAST:event_tableTransaksiSimpananMouseClicked

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        this.dispose();
        Menu Menu = new Menu();
        Menu.setVisible(true);
    }//GEN-LAST:event_btnKembaliActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksiSimpanan().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<Form.JenisSimpanan> jenisSimpananList;
    private javax.persistence.Query jenisSimpananQuery;
    private javax.swing.JLabel labelAlamat;
    private javax.swing.JLabel labelKota;
    private javax.swing.JLabel labelTelephone;
    private javax.swing.JLabel labelTelephone1;
    private javax.swing.JLabel labelTelephone2;
    private javax.swing.JLabel labelTelephone3;
    private javax.swing.JTable tableDetailSimpanan;
    private javax.swing.JTable tableTransaksiSimpanan;
    private javax.swing.JComboBox<String> txtAnggota;
    private javax.swing.JTextField txtDebit;
    private javax.swing.JTextField txtJenis;
    private javax.swing.JTextField txtKredit;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JComboBox<String> txtSimpanan;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
