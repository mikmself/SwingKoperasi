package Koperasi;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class CRUD {
    public Connection conn;
    public String Koneksi(){
        String urlDB = "jdbc:mysql://localhost:3306/koperasi"; 
        try {
            conn = DriverManager.getConnection(urlDB, "root", "");
            System.out.println("Sukses terhubung ke databse");
            return "Sukses terhubung ke databse";
        } catch (Exception e) {
            return e.toString();
        }
    }
    public DefaultTableModel TampilData(String namaTable, String daftarField[], String JudulKolom[], String joinClause1, String onClause1, String joinClause2, String onClause2) {
        try {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Koneksi belum dibuka.");
            }

            DefaultTableModel tabelModel = new DefaultTableModel(JudulKolom, 0);
            Statement st = conn.createStatement();

            String query = "SELECT * FROM " + namaTable;

            if (joinClause1 != null && onClause1 != null) {
                query += " " + joinClause1 + " ON " + onClause1;
            }

            if (joinClause2 != null && onClause2 != null) {
                query += " " + joinClause2 + " ON " + onClause2;
            }

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Vector data = new Vector();
                for (int i = 0; i < daftarField.length; i++) {
                    data.addElement(rs.getString(daftarField[i]));
                }
                tabelModel.addRow(data);
            }

            return tabelModel;
        } catch (SQLException e) {
            e.printStackTrace();  
            return null;
        }
    }

    public String TambahData(String namaTable, String daftarField, String valueField){
        try {
            if(conn.isClosed()){
                throw new SQLException("Koneksi belum dibuka");
            }else{
                Statement st = conn.createStatement();
                st.executeUpdate("INSERT INTO " + namaTable + " (" + daftarField + ") values(" + valueField +")");
                return "Berhasil memasukan data baru";
            }
        } catch (SQLException e) {
            return "Gagal memasukan data : " + e.getMessage();
        }
    }
    public String EditData(String namaTable, String data, String kondisi){
        try {
            if(conn.isClosed()){
                throw new SQLException("Koneksi belum dibuka");
            }else{
                Statement st = conn.createStatement();
                st.executeUpdate("UPDATE " + namaTable + " SET " + data + " WHERE " + kondisi);
                return "Berhasil mengupdate data";
            }
        } catch (SQLException e) {
            return "Gagal memasukan data : " + e.getMessage();
        }
    }
    public String HapusData(String namaTable, String kondisi){
        try {
            if(conn.isClosed()){
                throw new SQLException("Koneksi belum dibuka");
            }else{
                Statement st = conn.createStatement();
                st.executeUpdate("DELETE FROM " + namaTable + " WHERE " + kondisi);
                return "Berhasil menghapus data";
            }
        } catch (SQLException e) {
            return "Gagal memasukan data : " + e.getMessage();
        }
    }
    public static String FormatTanggal(Date date) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public DefaultTableModel CariData(String namaTable, String[] daftarField, String kondisi, String[] judulKolom) {
        try {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Koneksi belum dibuka.");
            }
            DefaultTableModel tabelModel = new DefaultTableModel(judulKolom, 0);
            Statement st = conn.createStatement();
            String query = "SELECT * FROM " + namaTable;
            if (kondisi != null && !kondisi.isEmpty()) {
                query += " WHERE " + kondisi;
            }
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Vector data = new Vector();
                for (int i = 0; i < daftarField.length; i++) {
                    data.addElement(rs.getString(daftarField[i]));
                }
                tabelModel.addRow(data);
            }
            return tabelModel;
        } catch (SQLException e) {
            e.printStackTrace();  
            return null;
        }
    }
}
