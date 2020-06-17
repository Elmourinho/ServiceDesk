package com.elmar.servicedeskbackend.mappers;

import com.elmar.servicedeskbackend.model.Ticket;
import com.elmar.servicedeskbackend.model.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket fromDtoToEntity(TicketDto ticketDto){
        return Ticket.builder()
                .title(ticketDto.getTitle())
                .email(ticketDto.getEmail())
                .description(ticketDto.getDescription())
                .status(ticketDto.getStatus())
                .priority(ticketDto.getPriority())
                .build();
    }

    public TicketDto fromEntityToDto(Ticket ticket){
        return TicketDto.builder()
                .date(ticket.getDate())
                .ticketNumber(ticket.getTicketNumber())
                .title(ticket.getTitle())
                .email(ticket.getEmail())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .build();
    }
}
