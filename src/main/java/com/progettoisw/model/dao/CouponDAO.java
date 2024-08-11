package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Coupon;
import com.progettoisw.model.mo.Utente;

import java.util.Date;

public interface CouponDAO {
    public Coupon create(
            Long idCoupon,
            Utente[] utenti,
            Integer sconto,
            String genere,
            Date data_inizio,
            Date data_fine
            );

    public void update(Coupon coupon);

    public void delete(Coupon coupon);

    public Coupon findByCouponId(Long couponId);
}
