package Form;
import Koperasi.CRUD;
import Main.Menu;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FormAnggota extends javax.swing.JFrame {
    CRUD Data = new CRUD();
    String status = "";
    public FormAnggota() {
        initComponents();
        Data.Koneksi();
        TampilData();
    }
    public void TampilData(){
        String namaTable = "anggota";
        String daftarField[]  = {
            "no_anggota",
            "nama_anggota",
            "alamat",
            "kota",
            "no_telp",            
            "pekerjaan"
        };
        String judulKolom[] = {
            "No Anggota",            
            "Nama Anggota",
            "Alamat",
            "Kota",
            "Nomor Telephone",
            "Pekerjaan"
        };
        tableAnggota.setModel(Data.TampilData(namaTable, daftarField, judulKolom, null, null, null, null));
    }
    public void EventTableClick(){
        DefaultTableModel tabelModel = (DefaultTableModel) tableAnggota.getModel();
        int indexTerpilih =  tableAnggota.getSelectedRow();
        txtNo.setText(tabelModel.getValueAt(indexTerpilih, 0).toString());
        txtNama.setText(tabelModel.getValueAt(indexTerpilih, 1).toString());
        txtAlamat.setText(tabelModel.getValueAt(indexTerpilih, 2).toString());
        txtKota.setText(tabelModel.getValueAt(indexTerpilih, 3).toString());
        txtTelephone.setText(tabelModel.getValueAt(indexTerpilih, 4).toString());        
        txtPekerjaan.setText(tabelModel.getValueAt(indexTerpilih, 5).toString());
    }
    public void TambahData() {
        status = Data.TambahData("anggota", "no_anggota,nama_anggota,alamat,kota,no_telp,pekerjaan", 
            "'" + txtNo.getText() + "',"+
            "'" + txtNama.getText() + "',"+
            "'" + txtAlamat.getText() + "',"+
            "'" + txtKota.getText() + "'," +              
            "'" + txtTelephone.getText() + "'," + 
            "'" + txtPekerjaan.getText() + "'"
        );
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void EditData(){
        status = Data.EditData("anggota", 
                "no_anggota = '" + txtNo.getText() + "'," + 
                "nama_anggota = '" + txtNama.getText() + "'," + 
                "alamat = '" + txtAlamat.getText() + "'," + 
                "kota = '" + txtKota.getText() + "'," +                                
                "no_telp = '" + txtTelephone.getText() + "'," +                
                "pekerjaan = '" + txtPekerjaan.getText() + "'"      
        , "no_anggota=" + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void HapusData(){
        status = Data.HapusData("anggota", " no_anggota= " + txtNo.getText());
        JOptionPane.showMessageDialog(null, status);
        TampilData();
        Kosongkan();
    }
    public void Kosongkan(){
        txtNo.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtKota.setText("");        
        txtTelephone.setText("");        
        txtPekerjaan.setText("");
    }
    public void CariNama(){
        String namaTable = "anggota";
        String daftarField[]  = {
            "no_anggota",
            "nama_anggota",
            "alamat",
            "kota",
            "no_telp",            
            "pekerjaan"
        };
        String judulKolom[] = {
            "No Anggota",            
            "Nama Anggota",
            "Alamat",
            "Kota",
            "Nomor Telephone",
            "Pekerjaan"
        };
        String kondisiByNamaAnggota = "nama_anggota LIKE '%"+ txtCariNama.getText() +"%'";
        DefaultTableModel hasilPencarianByNamaAnggota = Data.CariData("anggota", daftarField, kondisiByNamaAnggota, judulKolom);
        tableAnggota.setModel(hasilPencarianByNamaAnggota);
    }
    public void CariNomor(){
        String namaTable = "anggota";
        String daftarField[]  = {
            "no_anggota",
            "nama_anggota",
            "alamat",
            "kota",
            "no_telp",            
            "pekerjaan"
        };
        String judulKolom[] = {
            "No Anggota",            
            "Nama Anggota",
            "Alamat",
            "Kota",
            "Nomor Telephone",
            "Pekerjaan"
        };
        String kondisiByNomorAnggota = "no_anggota LIKE '%"+ txtCariNomor.getText() +"%'";
        DefaultTableModel hasilPencarianByNomorAnggota = Data.CariData("anggota", daftarField, kondisiByNomorAnggota, judulKolom);
        tableAnggota.setModel(hasilPencarianByNomorAnggota);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        labelNo = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        labelNama = new javax.swing.JLabel();
        labelAlamat = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtKota = new javax.swing.JTextField();
        labelKota = new javax.swing.JLabel();
        txtTelephone = new javax.swing.JTextField();
        labelTelephone = new javax.swing.JLabel();
        txtPekerjaan = new javax.swing.JTextField();
        labelPekerjaan = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKosongkan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAnggota = new javax.swing.JTable();
        btnKembali = new javax.swing.JButton();
        txtCariNomor = new javax.swing.JTextField();
        labelCariNomor = new javax.swing.JLabel();
        labelCariNama = new javax.swing.JLabel();
        txtCariNama = new javax.swing.JTextField();
        btnCariNomor = new javax.swing.JButton();
        btnCariNama = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 700));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Form Anggota");

        labelNo.setText("No Anggota");

        labelNama.setText("Nama Anggota");

        labelAlamat.setText("Alamat");

        labelKota.setText("Kota");

        labelTelephone.setText("Telephone");

        labelPekerjaan.setText("Pekerjaan");

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

        tableAnggota.setModel(new javax.swing.table.DefaultTableModel(
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
        tableAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAnggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAnggota);

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        labelCariNomor.setText("Nomor Anggota");

        labelCariNama.setText("Nama Anggota");

        btnCariNomor.setText("Cari");
        btnCariNomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariNomorActionPerformed(evt);
            }
        });

        btnCariNama.setText("Cari");
        btnCariNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariNamaActionPerformed(evt);
            }
        });

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNo)
                            .addComponent(labelKota)
                            .addComponent(labelTelephone)
                            .addComponent(labelPekerjaan)
                            .addComponent(labelAlamat)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelNama)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNo)
                                        .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKosongkan))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCariNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCariNomor))
                            .addComponent(labelCariNomor)
                            .addComponent(labelCariNama)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCariNama, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCariNama)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
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
                            .addComponent(labelNama)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAlamat)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelKota)
                            .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelephone)
                            .addComponent(txtTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPekerjaan)
                            .addComponent(txtPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnEdit)
                            .addComponent(btnHapus)
                            .addComponent(btnKosongkan))
                        .addGap(68, 68, 68)
                        .addComponent(labelCariNomor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCariNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariNomor))
                        .addGap(17, 17, 17)
                        .addComponent(labelCariNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCariNama, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariNama)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );

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

    private void tableAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAnggotaMouseClicked
        EventTableClick();
    }//GEN-LAST:event_tableAnggotaMouseClicked

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        this.dispose();
        Menu Menu = new Menu();
        Menu.setVisible(true);
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnCariNomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNomorActionPerformed
        CariNomor();
    }//GEN-LAST:event_btnCariNomorActionPerformed

    private void btnCariNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNamaActionPerformed
        CariNama();
    }//GEN-LAST:event_btnCariNamaActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAnggota().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariNama;
    private javax.swing.JButton btnCariNomor;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnKosongkan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlamat;
    private javax.swing.JLabel labelCariNama;
    private javax.swing.JLabel labelCariNomor;
    private javax.swing.JLabel labelKota;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNo;
    private javax.swing.JLabel labelPekerjaan;
    private javax.swing.JLabel labelTelephone;
    private javax.swing.JTable tableAnggota;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCariNama;
    private javax.swing.JTextField txtCariNomor;
    private javax.swing.JTextField txtKota;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtPekerjaan;
    private javax.swing.JTextField txtTelephone;
    // End of variables declaration//GEN-END:variables
}
