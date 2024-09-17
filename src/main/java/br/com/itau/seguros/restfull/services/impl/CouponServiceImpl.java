package br.com.itau.seguros.restfull.services.impl;

import br.com.itau.seguros.restfull.domain.coupon.Coupon;
import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.CouponRequestDTO;
import br.com.itau.seguros.restfull.exceptions.ObjectNotFoundException;
import br.com.itau.seguros.restfull.repositories.CouponRepository;
import br.com.itau.seguros.restfull.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl {


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;


    public Coupon addCouponToEvent(Integer id, CouponRequestDTO obj) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Evento Nao encontrado"));

        Coupon coupon = new Coupon();
        coupon.setCode(obj.code());
        coupon.setDiscount(obj.discount());
        coupon.setValid( new Date(obj.valid()));
        coupon.setEvent(event);
        return couponRepository.save(coupon);

    }


    public List<Coupon> consultCoupons(Integer eventId) {
        return couponRepository.findByEventIdAndValidAfter(eventId);
    }

}
