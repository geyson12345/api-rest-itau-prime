package br.com.itau.seguros.restfull.controller;

import br.com.itau.seguros.restfull.dto.OrderCreatedEventDto;
import br.com.itau.seguros.restfull.dto.UserDTO;
import br.com.itau.seguros.restfull.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String ID = "/{id}";



    @GetMapping(value =  ID)
    public ResponseEntity<UserDTO> findById(@PathVariable  Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){

       return ResponseEntity.ok().body(service.findAll()
               .stream().map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList()));

    }

    @PostMapping
    public ResponseEntity<UserDTO>create(@RequestBody UserDTO obj){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(
                service.create(obj).getId()).toUri();

//                Configurando  Servidor Exchange - RabbitMQ
//                OrderCreatedEventDto dadosCliente = new OrderCreatedEventDto(obj.getCpf() ,obj.getNome());
//                rabbitTemplate.convertAndSend("orders.v1.order-created", "", dadosCliente);

                return  ResponseEntity.created(uri).build();

    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> deleteById(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO obj){
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(obj), UserDTO.class));
    }

}
