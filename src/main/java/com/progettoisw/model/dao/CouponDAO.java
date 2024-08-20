package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Coupon;
import com.progettoisw.model.mo.Utente;

import java.util.Date;
import java.util.List;

public interface CouponDAO {
    public Coupon create(
            String codice,
            Integer sconto,
            String genere,
            Date data_inizio,
            Date data_fine
            ) throws DuplicatedObjectException;

    public void update(Coupon coupon);

    public void delete(Coupon coupon);

    public Coupon findByCouponId(Long couponId);

    public List<Coupon> findAllCoupons();

    public List<Coupon> findAllCouponsWithUsers();
}
