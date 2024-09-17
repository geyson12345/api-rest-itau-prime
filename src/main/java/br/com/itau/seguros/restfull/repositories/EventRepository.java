package br.com.itau.seguros.restfull.repositories;

import br.com.itau.seguros.restfull.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e where e.date_inicio >= :currentDate  ")
    public Page<Event> findListaEventoFuturos(@Param("currentDate") Date currentDate, Pageable pageable);



    @Query("SELECT e FROM Event e where e.date_inicio >= :dataInicio and  e.date_fim <= :dataFim " )
    public Page<Event> findListaEventoPorData(@Param("dataInicio") Date dataInicio,
                                              @Param("dataFim")    Date dataFim,
                                              Pageable pageable);





}







