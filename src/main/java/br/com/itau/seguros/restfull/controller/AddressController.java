package br.com.itau.seguros.restfull.controller;

import br.com.itau.seguros.restfull.dto.AddressRequestDTO;
import br.com.itau.seguros.restfull.services.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/address")
public class AddressController {


    public static final String ID = "/{id}";

    @Autowired
    private AddressServiceImpl serviceImpl;


    @PostMapping("/event/{event_id}")
    public ResponseEntity<AddressRequestDTO> addAddressToEvent(@PathVariable Integer event_id,
                                                               @RequestBody AddressRequestDTO obj) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(
                serviceImpl.addAddressToEvent(event_id,obj).getId()).toUri();

        return  ResponseEntity.created(uri).build();


    }


}
