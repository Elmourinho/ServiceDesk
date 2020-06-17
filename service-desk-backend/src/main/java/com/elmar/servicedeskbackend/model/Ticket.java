package com.elmar.servicedeskbackend.model;

import com.elmar.servicedeskbackend.enums.Priority;
import com.elmar.servicedeskbackend.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "title")
    private String title;

    @Column(name = "number")
    private String ticketNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TicketStatus status;

    @Column(name = "priority")
    private Priority priority;

}
