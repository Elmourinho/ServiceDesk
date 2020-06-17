package com.elmar.servicedeskbackend.model;

import com.elmar.servicedeskbackend.enums.Priority;
import com.elmar.servicedeskbackend.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    @NotNull
    private String ticketNumber;

    @NotNull
    private Instant date;

    @NotNull
    private String title;

    @Email
    private String email;

    @NotNull
    private String description;

    @NotNull
    private TicketStatus status;

    @NotNull
    private Priority priority;
}
