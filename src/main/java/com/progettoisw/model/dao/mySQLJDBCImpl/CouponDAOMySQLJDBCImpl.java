package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.CouponDAO;
import com.progettoisw.model.mo.Coupon;
import com.progettoisw.model.mo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class CouponDAOMySQLJDBCImpl implements CouponDAO {

  private final String COUNTER_ID = "idCoupon";
  Connection conn;

  public CouponDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Coupon create(Long idCoupon, Utente[] utenti, Integer sconto, String genere, Date data_inizio, Date data_fine) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void update(Coupon coupon) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Coupon coupon) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Coupon findByCouponId(Long idCoupon) {
    PreparedStatement ps;
    Coupon coupon = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM COUPON "
              + " WHERE "
              + "   id_coupon = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, idCoupon);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        coupon = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return coupon;
  }

  static Coupon read(ResultSet rs) {

    Coupon coupon = new Coupon();
    try {
      coupon.setIdCoupon(rs.getLong("id_coupon"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setSconto(rs.getInt("sconto"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setGenere(rs.getString("genere"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setDataInizio(rs.getDate("data_inizio"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setDataFine(rs.getDate("data_fine"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setDeleted(rs.getBoolean("deleted"));
    } catch (SQLException sqle) {
    }

    return coupon;
  }

}
