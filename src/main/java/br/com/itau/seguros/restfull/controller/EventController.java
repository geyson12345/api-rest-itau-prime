package br.com.itau.seguros.restfull.controller;

import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.EventDetailsDTO;
import br.com.itau.seguros.restfull.dto.EventRequestDTO;
import br.com.itau.seguros.restfull.dto.EventResponseDTO;
import br.com.itau.seguros.restfull.dto.UserDTO;
import br.com.itau.seguros.restfull.repositories.EventRepository;
import br.com.itau.seguros.restfull.services.impl.EventServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/event")
public class EventController {


    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private ModelMapper mapper;

    public static final String ID = "/{id}";



    @PostMapping
    public ResponseEntity<EventRequestDTO>create(@RequestBody EventRequestDTO obj) throws ParseException {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(
                eventService.createEvent(obj).getId()).toUri();

        return  ResponseEntity.created(uri).build();
    }
    // Uma lista completa
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>>getListaEvent(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size){


        List<EventResponseDTO> allEvents = this.eventService.getObterListaEvent(page,size);
        return  ResponseEntity.ok(allEvents);

    }
    // Uma lista de futuros eventos
    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>>getListaEventFuturos(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){


        List<EventResponseDTO> allEvents = this.eventService.getObterListaEventQueAindaVaoAcontecer(page,size);
        return  ResponseEntity.ok(allEvents);

    }

    // Liata todos os eventos por periodo de datas

    @GetMapping("/dataEvento")
    public ResponseEntity<List<EventResponseDTO>>getListaEventPorData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){


        List<EventResponseDTO> allEvents = this.eventService.getObterListaEventPorData(page,size,startDate,endDate);
        return  ResponseEntity.ok(allEvents);

    }


    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable Integer eventId){
        EventDetailsDTO eventDetails = eventService.getEventDetails(eventId);
        return  ResponseEntity.ok(eventDetails);


    }

}
