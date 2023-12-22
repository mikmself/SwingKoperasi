/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "jenis_simpanan", catalog = "koperasi", schema = "")
@NamedQueries({
    @NamedQuery(name = "JenisSimpanan.findAll", query = "SELECT j FROM JenisSimpanan j")
    , @NamedQuery(name = "JenisSimpanan.findByKodeSimp", query = "SELECT j FROM JenisSimpanan j WHERE j.kodeSimp = :kodeSimp")
    , @NamedQuery(name = "JenisSimpanan.findByJenisSimp", query = "SELECT j FROM JenisSimpanan j WHERE j.jenisSimp = :jenisSimp")})
public class JenisSimpanan implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_simp")
    private Short kodeSimp;
    @Basic(optional = false)
    @Column(name = "jenis_simp")
    private String jenisSimp;

    public JenisSimpanan() {
    }

    public JenisSimpanan(Short kodeSimp) {
        this.kodeSimp = kodeSimp;
    }

    public JenisSimpanan(Short kodeSimp, String jenisSimp) {
        this.kodeSimp = kodeSimp;
        this.jenisSimp = jenisSimp;
    }

    public Short getKodeSimp() {
        return kodeSimp;
    }

    public void setKodeSimp(Short kodeSimp) {
        Short oldKodeSimp = this.kodeSimp;
        this.kodeSimp = kodeSimp;
        changeSupport.firePropertyChange("kodeSimp", oldKodeSimp, kodeSimp);
    }

    public String getJenisSimp() {
        return jenisSimp;
    }

    public void setJenisSimp(String jenisSimp) {
        String oldJenisSimp = this.jenisSimp;
        this.jenisSimp = jenisSimp;
        changeSupport.firePropertyChange("jenisSimp", oldJenisSimp, jenisSimp);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeSimp != null ? kodeSimp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JenisSimpanan)) {
            return false;
        }
        JenisSimpanan other = (JenisSimpanan) object;
        if ((this.kodeSimp == null && other.kodeSimp != null) || (this.kodeSimp != null && !this.kodeSimp.equals(other.kodeSimp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jenisSimp;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    public static JenisSimpanan findByCoulmn(String column) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JenisSimpanan jenisSimpanan = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/koperasi", "root", "");
            pst = conn.prepareStatement("SELECT * FROM jenis_simpanan WHERE jenis_simp = ?");
            pst.setString(1, column);
            rs = pst.executeQuery();
            if (rs.next()) {
                jenisSimpanan = new JenisSimpanan();
                jenisSimpanan.setKodeSimp(rs.getShort("kode_simp"));
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
        return jenisSimpanan;
    }
}
