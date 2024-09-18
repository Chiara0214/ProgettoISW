package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.CouponDAO;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CouponDAOMySQLJDBCImpl implements CouponDAO {

  private final String COUNTER_ID = "couponId";
  Connection conn;

  public CouponDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Coupon create(String codice, Integer sconto, String genere, Date data_inizio, Date data_fine) throws DuplicatedObjectException  {
    PreparedStatement ps;
    Coupon coupon = new Coupon();
    coupon.setCodice(codice);
    coupon.setSconto(sconto);
    coupon.setGenere(genere);
    coupon.setDataInizio(data_inizio);
    coupon.setDataFine(data_fine);

    try {

      String sql
              = " SELECT id_coupon "
              + " FROM COUPON "
              + " WHERE "
              + " coupon_deleted = 0 AND "
              + " codice = ? AND"
              + " sconto = ? AND"
              + " coupon_genere = ? AND"
              + " data_inizio = ? AND"
              + " data_fine = ? ";

      ps = conn.prepareStatement(sql);

      int i = 1;
      java.sql.Date startDate = new java.sql.Date(coupon.getDataInizio().getTime());
      java.sql.Date endDate = new java.sql.Date(coupon.getDataFine().getTime());

      ps.setString(i++, coupon.getCodice());
      ps.setInt(i++, coupon.getSconto());
      ps.setString(i++, coupon.getGenere());
      ps.setDate(i++, startDate);
      ps.setDate(i++, endDate);

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("CouponDAOJDBCImpl.create: Tentativo di creazione di un coupon già esistente.");
      }

      sql = "update counter set counterValue=counterValue+1 where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      resultSet = ps.executeQuery();
      resultSet.next();

      coupon.setIdCoupon(resultSet.getLong("counterValue"));

      resultSet.close();

      sql
              = " INSERT INTO COUPON "
              + "   ( id_coupon,"
              + "     codice,"
              + "     sconto,"
              + "     coupon_genere,"
              + "     data_inizio,"
              + "     data_fine,"
              + "     coupon_deleted "
              + "   ) "
              + " VALUES (?,?,?,?,?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setLong(i++, coupon.getIdCoupon());
      ps.setString(i++, coupon.getCodice());
      ps.setInt(i++, coupon.getSconto());
      ps.setString(i++, coupon.getGenere());
      ps.setDate(i++, startDate);
      ps.setDate(i++, endDate);

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return coupon;
  }

  @Override
  public void update(Coupon coupon) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Coupon coupon) {
    PreparedStatement ps;

    try {

      String sql
              = " UPDATE COUPON "
              + " SET coupon_deleted = 1 "
              + " WHERE "
              + " id_coupon = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, coupon.getIdCoupon());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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

  @Override
  public List<Coupon> findAllCoupons() {
    PreparedStatement ps;
    Coupon coupon = null;
    List<Coupon> coupons = new ArrayList<Coupon>();

    try {

      String sql
              = " SELECT * "
              + "   FROM COUPON "
              + " WHERE coupon_deleted = 0 ";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        coupon = read(resultSet);
        coupons.add(coupon);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return coupons;
  }

  @Override
  public List<Coupon> findAllCouponsWithUsers() {
    PreparedStatement ps;
    Coupon coupon = null;
    Utente utente = null;
    List<Coupon> coupons = new ArrayList<Coupon>();

    try {

      String sql
              = " SELECT * "
              + "   FROM COUPON LEFT OUTER JOIN USA_COUPON NATURAL JOIN UTENTE ON COUPON.id_coupon = USA_COUPON.id_coupon; ";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        coupon = read(resultSet);
        utente = UtenteDAOMySQLJDBCImpl.read(resultSet);
        /* Verifico se il coupon è già nella lista coupons */
        Long couponId = coupon.getIdCoupon();
        Coupon inList = coupons.stream().filter(c -> {return c.getIdCoupon().equals(couponId);}).findAny().orElse(null);

        if(inList != null) {
          /* Se è già presente aggiungo l'utente alla lista utenti del coupon */
          inList.setUtenti(utente);
        } else {
          /* se non è già presente creo una nuova lista utenti da inserire nel coupon */
          List<Utente> utenti = new ArrayList<Utente>();
          utenti.add(utente);
          coupon.setUtenti(utenti);
          coupons.add(coupon);
        }
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return coupons;
  }

  static Coupon read(ResultSet rs) {

    Coupon coupon = new Coupon();
    try {
      coupon.setIdCoupon(rs.getLong("id_coupon"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setCodice(rs.getString("codice"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setSconto(rs.getInt("sconto"));
    } catch (SQLException sqle) {
    }
    try {
      coupon.setGenere(rs.getString("coupon_genere"));
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
      coupon.setDeleted(rs.getBoolean("coupon_deleted"));
    } catch (SQLException sqle) {
    }

    return coupon;
  }

}
