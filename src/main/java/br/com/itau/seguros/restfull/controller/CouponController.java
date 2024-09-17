package br.com.itau.seguros.restfull.controller;


import br.com.itau.seguros.restfull.domain.coupon.Coupon;
import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.CouponRequestDTO;
import br.com.itau.seguros.restfull.services.impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {



    @Autowired
    private CouponServiceImpl serviceImpl;

    public static final String ID = "/{id}";


    @PostMapping("/event/{event_id}")
    public ResponseEntity<CouponRequestDTO> addCouponToEvent (@PathVariable Integer event_id, @RequestBody CouponRequestDTO obj) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(
                serviceImpl.addCouponToEvent(event_id,obj).getId()).toUri();

        return  ResponseEntity.created(uri).build();

    }





}
