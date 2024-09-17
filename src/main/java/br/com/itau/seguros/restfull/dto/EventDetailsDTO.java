package br.com.itau.seguros.restfull.dto;

import java.util.Date;
import java.util.List;

public record EventDetailsDTO(
        Long id,
        String title,
        String description,
        Date date_inicio,
        Date date_fim,
        String imgUrl,
        String eventUrl,
        Boolean remote,
        List<CouponDTO> coupons) {

    public record CouponDTO(
            String code,
            Integer discount,
            Date valid) {
    }




}
