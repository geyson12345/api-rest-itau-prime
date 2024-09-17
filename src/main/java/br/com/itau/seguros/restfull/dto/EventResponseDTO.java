package br.com.itau.seguros.restfull.dto;

import java.util.Date;

public record EventResponseDTO(

        Long id,
        String title,
        String description,
        Date date_inicio,
        Date date_fim,
        String imgtUrl,
        String eventUrl,
        Boolean remote


        ) {}


