package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.UsaCoupon;

public interface UsaCouponDAO {
    public UsaCoupon create(
            Long idUtente,
            Long idCoupon
    ) throws DuplicatedObjectException;

    public void update(UsaCoupon usaCoupon);

    public void delete(UsaCoupon usaCoupon);
}
