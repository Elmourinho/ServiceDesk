package com.elmar.servicedeskbackend.services;

import com.elmar.servicedeskbackend.enums.Priority;
import com.elmar.servicedeskbackend.enums.TicketStatus;
import com.elmar.servicedeskbackend.mappers.TicketMapper;
import com.elmar.servicedeskbackend.model.Ticket;
import com.elmar.servicedeskbackend.model.TicketDto;
import com.elmar.servicedeskbackend.repositories.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Test
    @DisplayName("findOpenTickets should find all open tickets without sorting")
    void findOpenTickets_ShouldFindAllOpenTicketsWithoutSort() {

        Ticket ticket1 = buildDummyTicket(Priority.MAJOR);
        Ticket ticket2 = buildDummyTicket(Priority.CRITICAL);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        TicketDto ticketDto1 = buildDummyTicketDto(Priority.MAJOR);
        TicketDto ticketDto2 = buildDummyTicketDto(Priority.CRITICAL);

        when(ticketRepository.findByStatus(TicketStatus.OPEN, null)).thenReturn(tickets);
        when(ticketMapper.fromEntityToDto(ticket1)).thenReturn(ticketDto1);
        when(ticketMapper.fromEntityToDto(ticket2)).thenReturn(ticketDto2);

        List<TicketDto> ticketDtos = ticketService.findOpenTickets(Optional.empty(), Optional.empty());
        assertThat(ticketDtos, hasSize(2));

    }

    @Test
    @DisplayName("findOpenTickets should find all open tickets with proper sorting")
    void findOpenTickets_ShouldFindAllOpenTicketsWithProperSort() {

        Ticket ticket1 = buildDummyTicket(Priority.MAJOR);
        Ticket ticket2 = buildDummyTicket(Priority.CRITICAL);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        TicketDto ticketDto1 = buildDummyTicketDto(Priority.MAJOR);
        TicketDto ticketDto2 = buildDummyTicketDto(Priority.CRITICAL);

        Sort sort = Sort.by(Sort.Direction.ASC, "priority");

        when(ticketRepository.findByStatus(TicketStatus.OPEN, sort)).thenReturn(tickets);
        when(ticketMapper.fromEntityToDto(eq(ticket1))).thenReturn(ticketDto1);
        when(ticketMapper.fromEntityToDto(eq(ticket2))).thenReturn(ticketDto2);

        List<TicketDto> ticketDtos = ticketService.findOpenTickets(Optional.of("priority"), Optional.of("ASC"));
        assertThat(ticketDtos, hasSize(2));
        assertThat(ticketDtos.get(0).getPriority(), is(Priority.MAJOR));
        assertThat(ticketDtos.get(1).getPriority(), is(Priority.CRITICAL));
    }

    @Test
    @DisplayName("findOpenTickets should throw exception when sort order is wrong")
    void findOpenTickets_ShouldThrowExceptionWhenSortOrderIsWrong() {

        assertThrows(IllegalArgumentException.class,
                () -> ticketService.findOpenTickets(Optional.of("priority"), Optional.of("ASCC"))
        );
    }

    @Test
    @DisplayName("findOpenTickets should throw exception when sort field is wrong")
    void findOpenTickets_ShouldThrowExceptionWhenSortFieldIsWrong() {

        assertThrows(IllegalArgumentException.class,
                () -> ticketService.findOpenTickets(Optional.of("priority1"), Optional.of("ASC"))
        );
    }

    private Ticket buildDummyTicket(Priority priority) {
        return Ticket.builder()
                .status(TicketStatus.OPEN)
                .priority(priority)
                .build();
    }

    private TicketDto buildDummyTicketDto(Priority priority) {
        return TicketDto.builder()
                .status(TicketStatus.OPEN)
                .priority(priority)
                .build();
    }
}
