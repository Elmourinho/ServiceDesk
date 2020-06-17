package com.elmar.servicedeskbackend.services;

import com.elmar.servicedeskbackend.enums.TicketStatus;
import com.elmar.servicedeskbackend.mappers.TicketMapper;
import com.elmar.servicedeskbackend.model.Ticket;
import com.elmar.servicedeskbackend.model.TicketDto;
import com.elmar.servicedeskbackend.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public List<TicketDto> findOpenTickets(Optional<String> sortBy, Optional<String> sortOrder) {

        Sort sort = buildSort(sortBy, sortOrder);

        return ticketRepository.findByStatus(TicketStatus.OPEN, sort)
                .stream()
                .filter(ticket -> ticket.getStatus().equals(TicketStatus.OPEN))
                .map(ticketMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public TicketDto save(TicketDto ticketDto) {
        Ticket ticket = ticketRepository.save(ticketMapper.fromDtoToEntity(ticketDto));

        ticket.setTicketNumber(String.format("Ticket-%s", ticket.getId()));
        ticket.setDate(Instant.now());
        ticket.setStatus(TicketStatus.OPEN);
        Ticket resultTicket = ticketRepository.save(ticket);
        return ticketMapper.fromEntityToDto(resultTicket);
    }

    public TicketDto update(String ticketNumber, TicketDto ticketDto) {
        Optional<Ticket> ticketOptional = ticketRepository.findByTicketNumber(ticketNumber);
        TicketDto dto;
        if(ticketOptional.isPresent()){
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(ticketDto.getStatus());
            ticket.setDescription(ticketDto.getDescription());
            ticket.setEmail(ticketDto.getEmail());
            ticket.setTitle(ticketDto.getTitle());
            ticket.setPriority(ticketDto.getPriority());
            Ticket resultTicket = ticketRepository.save(ticket);
            return ticketMapper.fromEntityToDto(resultTicket);
        } else {
            throw new ResourceNotFoundException();
        }


    }

    private Sort buildSort(Optional<String> sortBy, Optional<String> sortOrder) {
        if (!sortBy.isPresent() || sortBy.get().isEmpty() || !sortOrder.isPresent() || sortOrder.get().isEmpty()) {
            return null;
        }

        String sortByString = sortBy.get();
        String sortOrderString = sortOrder.get();

        if (Stream.of("ASC", "DESC").noneMatch(sortOrderString::equalsIgnoreCase)) {
            throw new IllegalArgumentException("Sort order is wrong");
        }
        if (Stream.of("PRIORITY", "DATE").noneMatch(sortByString::equalsIgnoreCase)) {
            throw new IllegalArgumentException("Sort field is wrong");
        }

        return Sort.by(Sort.Direction.fromString(sortOrder.get()), sortBy.get());
    }


}
