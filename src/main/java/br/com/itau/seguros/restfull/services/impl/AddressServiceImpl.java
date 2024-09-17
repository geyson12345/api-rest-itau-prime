package br.com.itau.seguros.restfull.services.impl;

import br.com.itau.seguros.restfull.domain.address.Address;
import br.com.itau.seguros.restfull.domain.event.Event;
import br.com.itau.seguros.restfull.dto.AddressRequestDTO;
import br.com.itau.seguros.restfull.exceptions.ObjectNotFoundException;
import br.com.itau.seguros.restfull.repositories.AddressRepository;
import br.com.itau.seguros.restfull.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EventRepository eventRepository;


    public Address addAddressToEvent (Integer id, AddressRequestDTO data){
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Evento não Localizado"));

        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);
        return addressRepository.save(address);


    }





}
