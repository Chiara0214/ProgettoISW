package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.UsaCouponDAO;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.UsaCoupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsaCouponDAOMySQLJDBCImpl implements UsaCouponDAO {

  private final String COUNTER_ID = "couponId";
  Connection conn;

  public UsaCouponDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public UsaCoupon create(Long idUtente, Long idCoupon) throws DuplicatedObjectException {
    PreparedStatement ps;
    UsaCoupon usaCoupon = new UsaCoupon();
    usaCoupon.setIdUtente(idUtente);
    usaCoupon.setIdCoupon(idCoupon);

    try {

      String sql
              = " SELECT * "
              + " FROM USA_COUPON "
              + " WHERE "
              + " usacoupon_deleted = 0 AND "
              + " id_utente = ? AND"
              + " id_coupon = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setLong(i++, usaCoupon.getIdUtente());
      ps.setLong(i++, usaCoupon.getIdCoupon());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("UsaCouponDAOJDBCImpl.create: Tentativo di creazione di un UsaCoupon già esistente.");
      }

      sql
              = " INSERT INTO USA_COUPON "
              + "   ( id_utente,"
              + "     id_coupon,"
              + "     usacoupon_deleted "
              + "   ) "
              + " VALUES (?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setLong(i++, usaCoupon.getIdUtente());
      ps.setLong(i++, usaCoupon.getIdCoupon());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return usaCoupon;
  }

  @Override
  public void update(UsaCoupon usaCoupon) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(UsaCoupon usaCoupon) {
    PreparedStatement ps;

    try {

      String sql
              = " UPDATE USA_COUPON "
              + " SET usacoupon_deleted = 1 "
              + " WHERE "
              + " id_utente = ? AND "
              + " id_coupon = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, usaCoupon.getIdUtente());
      ps.setLong(2, usaCoupon.getIdCoupon());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
