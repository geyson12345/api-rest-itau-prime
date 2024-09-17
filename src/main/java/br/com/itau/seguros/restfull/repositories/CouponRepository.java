package br.com.itau.seguros.restfull.repositories;

import br.com.itau.seguros.restfull.domain.coupon.Coupon;
import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.EventDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CouponRepository  extends JpaRepository<Coupon, Integer> {


    @Query(value = " SELECT * from coupon  c where c.event_id= :eventId" , nativeQuery = true)
    List<Coupon>findByEventIdAndValidAfter(@Param("eventId") Integer eventId);

}



