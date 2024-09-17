package br.com.itau.seguros.restfull.domain.event;

import br.com.itau.seguros.restfull.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Table(name = "event")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String imgUrl;
    private String eventUrl;
    private Date date_inicio;
    private Date date_fim;
    private Boolean remote;


}



