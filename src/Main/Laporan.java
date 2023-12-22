package Main;
import Koperasi.CRUD;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
public class Laporan extends javax.swing.JFrame {
    CRUD Data = new CRUD();
    String status = "";
    public Laporan() {
        initComponents();
        Data.Koneksi();
        TampilDataAnggota();
        TampilDataTransaksiPinjaman();
        TampilDataTransaksiSimpanan();
    }
    public void TampilDataAnggota(){
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
    public void TampilDataTransaksiPinjaman(){
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
    public void TampilDataTransaksiSimpanan(){
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
            "Kode Simpanan",
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
    }
    private void updateLaporan() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/koperasi", "root", "");
            PreparedStatement anggotaStmt = conn.prepareStatement("SELECT no_anggota,nama_anggota,alamat,kota,no_telp,pekerjaan FROM anggota");
            ResultSet anggotaResult = anggotaStmt.executeQuery();
            PreparedStatement transPinjamanStmt = conn.prepareStatement("SELECT trans_pinjaman.nopinjam,anggota.nama_anggota, jenis_pinjaman.jenis_pinj, trans_pinjaman.tglpinjam,trans_pinjaman.keterangan,trans_pinjaman.pinjpokok,trans_pinjaman.lama,trans_pinjaman.bunga,trans_pinjaman.admin,trans_pinjaman.jml,trans_pinjaman.total,trans_pinjaman.angsuran FROM `trans_pinjaman` INNER JOIN jenis_pinjaman ON trans_pinjaman.kode_pinj = jenis_pinjaman.kode_pinj INNER JOIN anggota ON trans_pinjaman.no_anggota = anggota.no_anggota");
            ResultSet transPinjamanResult = transPinjamanStmt.executeQuery();
            PreparedStatement transSimpananStmt = conn.prepareStatement("SELECT trans_simpanan.nosimpan, anggota.nama_anggota, jenis_simpanan.jenis_simp,trans_simpanan.tglsimpanan,trans_simpanan.jenis,trans_simpanan.saldo FROM trans_simpanan INNER JOIN jenis_simpanan ON trans_simpanan.kode_simp = jenis_simpanan.kode_simp INNER JOIN anggota ON trans_simpanan.no_anggota = anggota.no_anggota");
            ResultSet transSimpananResult = transSimpananStmt.executeQuery();
            PdfPTable tableTransPinjaman = new PdfPTable(12);         
            PdfPTable tableAnggota = new PdfPTable(6);
            PdfPTable tableTransSimpanan = new PdfPTable(6);

            tableTransPinjaman.addCell("No Pinjaman");
            tableTransPinjaman.addCell("Nama Anggota");
            tableTransPinjaman.addCell("Jenis Pinjaman");
            tableTransPinjaman.addCell("Tgl Pinjam");
            tableTransPinjaman.addCell("Keterangan");
            tableTransPinjaman.addCell("Pokok");
            tableTransPinjaman.addCell("Lama (Bulan)");
            tableTransPinjaman.addCell("Bunga");
            tableTransPinjaman.addCell("Admin");
            tableTransPinjaman.addCell("Jumlah");
            tableTransPinjaman.addCell("Total");
            tableTransPinjaman.addCell("Angsuran");
            while (transPinjamanResult.next()) {
                String noPinjaman = transPinjamanResult.getString("nopinjam");
                String namaAnggota = transPinjamanResult.getString("nama_anggota");
                String jenisPinjaman = transPinjamanResult.getString("jenis_pinj");
                String tglPinjam = transPinjamanResult.getString("tglpinjam");
                String keterangan = transPinjamanResult.getString("keterangan");
                String pokok = transPinjamanResult.getString("pinjpokok");
                String lama = transPinjamanResult.getString("lama");
                String bunga = transPinjamanResult.getString("bunga");
                String admin = transPinjamanResult.getString("admin");
                String jumlah = transPinjamanResult.getString("jml");
                String total = transPinjamanResult.getString("total");
                String angsuran = transPinjamanResult.getString("angsuran");
                tableTransPinjaman.addCell(noPinjaman);
                tableTransPinjaman.addCell(namaAnggota);
                tableTransPinjaman.addCell(jenisPinjaman);
                tableTransPinjaman.addCell(tglPinjam);
                tableTransPinjaman.addCell(keterangan);
                tableTransPinjaman.addCell(pokok);
                tableTransPinjaman.addCell(lama);
                tableTransPinjaman.addCell(bunga);
                tableTransPinjaman.addCell(admin);
                tableTransPinjaman.addCell(jumlah);
                tableTransPinjaman.addCell(total);
                tableTransPinjaman.addCell(angsuran);
            }
            
            tableAnggota.addCell("No Anggota");            
            tableAnggota.addCell("Nama Anggota");
            tableAnggota.addCell("Alamat");
            tableAnggota.addCell("Kota");
            tableAnggota.addCell("Nomor Telephone");
            tableAnggota.addCell("Pekerjaan");
            while (anggotaResult.next()) {
                String noAnggota = anggotaResult.getString("no_anggota");
                String namaAnggota = anggotaResult.getString("nama_anggota");                
                String alamat = anggotaResult.getString("alamat");
                String kota = anggotaResult.getString("kota");
                String noTelp = anggotaResult.getString("no_telp");                
                String pekerjaan = anggotaResult.getString("pekerjaan");
                tableAnggota.addCell(noAnggota);
                tableAnggota.addCell(namaAnggota);                
                tableAnggota.addCell(alamat);
                tableAnggota.addCell(kota);
                tableAnggota.addCell(noTelp);
                tableAnggota.addCell(pekerjaan);

                for (int i = 2; i < 12; i++) {
                    tableAnggota.addCell("");
                }
            }
            
            tableTransSimpanan.addCell("No Simpanan");          
            tableTransSimpanan.addCell("Nama Anggota");
            tableTransSimpanan.addCell("Jenis Simpanan");
            tableTransSimpanan.addCell("Tanggal Simpanan");
            tableTransSimpanan.addCell("Jenis");
            tableTransSimpanan.addCell("Salo");
            while (transSimpananResult.next()) {
                String noSimpan = transSimpananResult.getString("nosimpan");
                String namaAnggota = transSimpananResult.getString("nama_anggota");
                String jenisSimpanan = transSimpananResult.getString("jenis_simp");
                String tglSimpanan = transSimpananResult.getString("tglsimpanan");
                String jenis = transSimpananResult.getString("jenis");
                String saldo = transSimpananResult.getString("saldo");
                tableTransSimpanan.addCell(noSimpan);
                tableTransSimpanan.addCell(namaAnggota);
                tableTransSimpanan.addCell(jenisSimpanan);
                tableTransSimpanan.addCell(tglSimpanan);
                tableTransSimpanan.addCell(jenis);
                tableTransSimpanan.addCell(saldo);
                for (int i = 6; i < 12; i++) {
                    tableTransSimpanan.addCell("");
                }
            }
            conn.close();
            createPDF(tableAnggota, tableTransPinjaman, tableTransSimpanan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createPDF(PdfPTable tableAnggota, PdfPTable tableTransPinjaman, PdfPTable tableTransSimpanan) {
        try {
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream("laporan.pdf"));
            doc.open();

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
            Paragraph title = new Paragraph(new Chunk("Laporan Koperasi Simpan Pinjam", titleFont));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            doc.add(title);

            doc.add(new Paragraph("\n")); 
            
            Paragraph judulAnggota = new Paragraph("1. Laporan data anggota");
            judulAnggota.setSpacingAfter(10);
            doc.add(judulAnggota);
            tableAnggota.setWidthPercentage(100);
            tableAnggota.setSpacingAfter(20);
            doc.add(tableAnggota);

            Paragraph judulTransPinjaman = new Paragraph("2. Laporan transaksi peminjaman");
            judulTransPinjaman.setSpacingAfter(10);
            doc.add(judulTransPinjaman);
            tableTransPinjaman.setWidthPercentage(100);
            tableTransPinjaman.setSpacingAfter(20);
            doc.add(tableTransPinjaman);

            Paragraph judulTransSimpanan = new Paragraph("3. Laporan transaksi simpanan");
            judulTransSimpanan.setSpacingAfter(10);
            doc.add(judulTransSimpanan);
            tableTransSimpanan.setWidthPercentage(100);
            doc.add(tableTransSimpanan);

            doc.close();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPrint = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnKembali = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAnggota = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTransaksiPinjaman = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTransaksiSimpanan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnPrint.setText("Print To PDF");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Laporan Koperasi Simpan Pinjam");

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel2.setText("1. Laporan data anggota");

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
        jScrollPane1.setViewportView(tableAnggota);

        jLabel3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel3.setText("2. Laporan transaksi peminjaman");

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
        jScrollPane2.setViewportView(tableTransaksiPinjaman);

        jLabel4.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel4.setText("2. Laporan transaksi simpanan");

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
        jScrollPane3.setViewportView(tableTransaksiSimpanan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 312, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(275, 275, 275)
                        .addComponent(btnKembali))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrint)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnKembali))
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnPrint)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        this.dispose();
        Menu Menu = new Menu();
        Menu.setVisible(true);
    }//GEN-LAST:event_btnKembaliActionPerformed
    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        updateLaporan();
    }//GEN-LAST:event_btnPrintActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableAnggota;
    private javax.swing.JTable tableTransaksiPinjaman;
    private javax.swing.JTable tableTransaksiSimpanan;
    // End of variables declaration//GEN-END:variables
}
