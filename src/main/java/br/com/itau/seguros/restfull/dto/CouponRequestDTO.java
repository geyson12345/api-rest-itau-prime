package br.com.itau.seguros.restfull.dto;

public record CouponRequestDTO(

        String code,
        Integer discount,
        Long valid

) {
}
