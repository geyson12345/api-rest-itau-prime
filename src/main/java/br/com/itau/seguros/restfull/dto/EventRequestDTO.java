package br.com.itau.seguros.restfull.dto;

public record EventRequestDTO(

        String title,
        String description,
        Long date_inicio,
        Long date_fim,
        String imgtUrl,
        String eventUrl,
        Boolean remote,
        String city) {}
