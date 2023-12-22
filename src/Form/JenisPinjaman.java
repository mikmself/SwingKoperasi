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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "jenis_pinjaman", catalog = "koperasi", schema = "")
@NamedQueries({
    @NamedQuery(name = "JenisPinjaman.findAll", query = "SELECT j FROM JenisPinjaman j")
    , @NamedQuery(name = "JenisPinjaman.findByKodePinj", query = "SELECT j FROM JenisPinjaman j WHERE j.kodePinj = :kodePinj")
    , @NamedQuery(name = "JenisPinjaman.findByJenisPinj", query = "SELECT j FROM JenisPinjaman j WHERE j.jenisPinj = :jenisPinj")
    , @NamedQuery(name = "JenisPinjaman.findByMaxPinj", query = "SELECT j FROM JenisPinjaman j WHERE j.maxPinj = :maxPinj")
    , @NamedQuery(name = "JenisPinjaman.findByMaxAngsuran", query = "SELECT j FROM JenisPinjaman j WHERE j.maxAngsuran = :maxAngsuran")
    , @NamedQuery(name = "JenisPinjaman.findByBunga", query = "SELECT j FROM JenisPinjaman j WHERE j.bunga = :bunga")})
public class JenisPinjaman implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_pinj")
    private Short kodePinj;
    @Basic(optional = false)
    @Column(name = "jenis_pinj")
    private String jenisPinj;
    @Basic(optional = false)
    @Column(name = "max_pinj")
    private int maxPinj;
    @Basic(optional = false)
    @Column(name = "max_angsuran")
    private short maxAngsuran;
    @Basic(optional = false)
    @Column(name = "bunga")
    private short bunga;

    public JenisPinjaman() {
    }

    public JenisPinjaman(Short kodePinj) {
        this.kodePinj = kodePinj;
    }

    public JenisPinjaman(Short kodePinj, String jenisPinj, int maxPinj, short maxAngsuran, short bunga) {
        this.kodePinj = kodePinj;
        this.jenisPinj = jenisPinj;
        this.maxPinj = maxPinj;
        this.maxAngsuran = maxAngsuran;
        this.bunga = bunga;
    }

    public Short getKodePinj() {
        return kodePinj;
    }

    public void setKodePinj(Short kodePinj) {
        Short oldKodePinj = this.kodePinj;
        this.kodePinj = kodePinj;
        changeSupport.firePropertyChange("kodePinj", oldKodePinj, kodePinj);
    }

    public String getJenisPinj() {
        return jenisPinj;
    }

    public void setJenisPinj(String jenisPinj) {
        String oldJenisPinj = this.jenisPinj;
        this.jenisPinj = jenisPinj;
        changeSupport.firePropertyChange("jenisPinj", oldJenisPinj, jenisPinj);
    }

    public int getMaxPinj() {
        return maxPinj;
    }

    public void setMaxPinj(int maxPinj) {
        int oldMaxPinj = this.maxPinj;
        this.maxPinj = maxPinj;
        changeSupport.firePropertyChange("maxPinj", oldMaxPinj, maxPinj);
    }

    public short getMaxAngsuran() {
        return maxAngsuran;
    }

    public void setMaxAngsuran(short maxAngsuran) {
        short oldMaxAngsuran = this.maxAngsuran;
        this.maxAngsuran = maxAngsuran;
        changeSupport.firePropertyChange("maxAngsuran", oldMaxAngsuran, maxAngsuran);
    }

    public short getBunga() {
        return bunga;
    }

    public void setBunga(short bunga) {
        short oldBunga = this.bunga;
        this.bunga = bunga;
        changeSupport.firePropertyChange("bunga", oldBunga, bunga);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodePinj != null ? kodePinj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JenisPinjaman)) {
            return false;
        }
        JenisPinjaman other = (JenisPinjaman) object;
        if ((this.kodePinj == null && other.kodePinj != null) || (this.kodePinj != null && !this.kodePinj.equals(other.kodePinj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jenisPinj;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    public static JenisPinjaman findByCoulmn(String column) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JenisPinjaman jenisPinjaman = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/koperasi", "root", "");
            pst = conn.prepareStatement("SELECT * FROM jenis_pinjaman WHERE jenis_pinj = ?");
            pst.setString(1, column);
            rs = pst.executeQuery();
            if (rs.next()) {
                jenisPinjaman = new JenisPinjaman();
                jenisPinjaman.setKodePinj(rs.getShort("kode_pinj"));
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
