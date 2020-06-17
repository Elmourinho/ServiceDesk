package com.elmar.servicedeskbackend.repositories;

import com.elmar.servicedeskbackend.enums.TicketStatus;
import com.elmar.servicedeskbackend.model.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status, Sort sort);
    Optional<Ticket> findByTicketNumber(String ticketNumber);
}
