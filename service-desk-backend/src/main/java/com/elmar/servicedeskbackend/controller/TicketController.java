package com.elmar.servicedeskbackend.controller;

import com.elmar.servicedeskbackend.model.TicketDto;
import com.elmar.servicedeskbackend.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = "/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public List<TicketDto> findOpenTickets(@RequestParam Optional<String> sortBy,
                                           @RequestParam Optional<String> sortOrder) {
        return ticketService.findOpenTickets(sortBy, sortOrder);
    }

    @PostMapping
    public TicketDto save(@RequestBody TicketDto ticketDto) {
        return ticketService.save(ticketDto);
    }

    @PutMapping(value = "/{ticketNumber}")
    public TicketDto update(@PathVariable String ticketNumber, @RequestBody TicketDto ticketDto) {
        return ticketService.update(ticketNumber, ticketDto);
    }

}
