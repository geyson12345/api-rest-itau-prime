package br.com.itau.seguros.restfull.services.impl;

import br.com.itau.seguros.restfull.domain.coupon.Coupon;
import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.EventDetailsDTO;
import br.com.itau.seguros.restfull.dto.EventRequestDTO;
import br.com.itau.seguros.restfull.dto.EventResponseDTO;
import br.com.itau.seguros.restfull.exceptions.ObjectNotFoundException;
import br.com.itau.seguros.restfull.repositories.CouponRepository;
import br.com.itau.seguros.restfull.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CouponRepository couponRepository;

    public Event createEvent(EventRequestDTO obj) throws ParseException {

        Event event = new Event();

        event.setTitle(obj.title());
        event.setDescription(obj.description());
        event.setDate_inicio( new Date(obj.date_inicio()));
        event.setDate_fim(new Date(obj.date_fim()));
//        event.setEventUrl(obj.imgtUrl());
//        event.setEventUrl(obj.eventUrl());
        event.setRemote(obj.remote());
        return eventRepository.save(event);

    }
    // Tragar todos os eventos paginados
    public List<EventResponseDTO> getObterListaEvent(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> pageEvents = eventRepository.findAll(pageable);
        return pageEvents.map(event -> {

                return new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate_inicio(),
                        event.getDate_fim(),
                        event.getImgUrl(),
                        event.getEventUrl(),
                        event.getRemote()

                );

        }).stream().toList();
    }

    // Tragar todos os eventos que ainda irão acontecer
    public List<EventResponseDTO> getObterListaEventQueAindaVaoAcontecer(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> pageEvents = eventRepository.findListaEventoFuturos(new Date(),pageable);
        return pageEvents.map(event -> {

            return new EventResponseDTO(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getDate_inicio(),
                    event.getDate_fim(),
                    event.getImgUrl(),
                    event.getEventUrl(),
                    event.getRemote()


            );

        }).stream().toList();
    }

    // Lista Eventos por periodo de datas
    public List<EventResponseDTO> getObterListaEventPorData(int page, int size, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> pageEvents = eventRepository.findListaEventoPorData(startDate, endDate, pageable);
        return pageEvents.map(event -> {

            return new EventResponseDTO(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getDate_inicio(),
                    event.getDate_fim(),
                    event.getImgUrl(),
                    event.getEventUrl(),
                    event.getRemote()
            );

        }).stream().toList();

    }



    // Lista Eventos relacionados ao seus coupon
    public EventDetailsDTO getEventDetails(Integer eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Evento Nao encontrado"));

        List<Coupon> coupons = couponRepository.findByEventIdAndValidAfter(eventId);

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate_inicio(),
                event.getDate_fim(),
                event.getImgUrl(),
                event.getEventUrl(),
                event.getRemote(),
                couponDTOs);

    }



}
