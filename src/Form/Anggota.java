package Form;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "anggota", catalog = "koperasi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Anggota.findAll", query = "SELECT a FROM Anggota a")
    , @NamedQuery(name = "Anggota.findByNoAnggota", query = "SELECT a FROM Anggota a WHERE a.noAnggota = :noAnggota")
    , @NamedQuery(name = "Anggota.findByNamaAnggota", query = "SELECT a FROM Anggota a WHERE a.namaAnggota = :namaAnggota")
    , @NamedQuery(name = "Anggota.findByAlamat", query = "SELECT a FROM Anggota a WHERE a.alamat = :alamat")
    , @NamedQuery(name = "Anggota.findByKota", query = "SELECT a FROM Anggota a WHERE a.kota = :kota")
    , @NamedQuery(name = "Anggota.findByNoTelp", query = "SELECT a FROM Anggota a WHERE a.noTelp = :noTelp")
    , @NamedQuery(name = "Anggota.findByPekerjaan", query = "SELECT a FROM Anggota a WHERE a.pekerjaan = :pekerjaan")})
public class Anggota implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "no_anggota")
    private Short noAnggota;
    @Basic(optional = false)
    @Column(name = "nama_anggota")
    private String namaAnggota;
    @Basic(optional = false)
    @Column(name = "alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "kota")
    private String kota;
    @Basic(optional = false)
    @Column(name = "no_telp")
    private long noTelp;
    @Basic(optional = false)
    @Column(name = "pekerjaan")
    private String pekerjaan;

    public Anggota() {
    }

    public Anggota(Short noAnggota) {
        this.noAnggota = noAnggota;
    }

    public Anggota(Short noAnggota, String namaAnggota, String alamat, String kota, long noTelp, String pekerjaan) {
        this.noAnggota = noAnggota;
        this.namaAnggota = namaAnggota;
        this.alamat = alamat;
        this.kota = kota;
        this.noTelp = noTelp;
        this.pekerjaan = pekerjaan;
    }

    public Short getNoAnggota() {
        return noAnggota;
    }

    public void setNoAnggota(Short noAnggota) {
        Short oldNoAnggota = this.noAnggota;
        this.noAnggota = noAnggota;
        changeSupport.firePropertyChange("noAnggota", oldNoAnggota, noAnggota);
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        String oldNamaAnggota = this.namaAnggota;
        this.namaAnggota = namaAnggota;
        changeSupport.firePropertyChange("namaAnggota", oldNamaAnggota, namaAnggota);
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        String oldAlamat = this.alamat;
        this.alamat = alamat;
        changeSupport.firePropertyChange("alamat", oldAlamat, alamat);
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        String oldKota = this.kota;
        this.kota = kota;
        changeSupport.firePropertyChange("kota", oldKota, kota);
    }

    public long getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(long noTelp) {
        long oldNoTelp = this.noTelp;
        this.noTelp = noTelp;
        changeSupport.firePropertyChange("noTelp", oldNoTelp, noTelp);
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        String oldPekerjaan = this.pekerjaan;
        this.pekerjaan = pekerjaan;
        changeSupport.firePropertyChange("pekerjaan", oldPekerjaan, pekerjaan);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noAnggota != null ? noAnggota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anggota)) {
            return false;
        }
        Anggota other = (Anggota) object;
        if ((this.noAnggota == null && other.noAnggota != null) || (this.noAnggota != null && !this.noAnggota.equals(other.noAnggota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return namaAnggota;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    public static Anggota findByCoulmn(String column) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Anggota jenisPinjaman = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/koperasi", "root", "");
            pst = conn.prepareStatement("SELECT * FROM anggota WHERE nama_anggota = ?");
            pst.setString(1, column);
            rs = pst.executeQuery();
            if (rs.next()) {
                jenisPinjaman = new Anggota();
                jenisPinjaman.setNoAnggota(rs.getShort("no_anggota"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return jenisPinjaman;
    }
}
