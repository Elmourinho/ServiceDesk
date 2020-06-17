package com.elmar.servicedeskbackend;

import com.elmar.servicedeskbackend.enums.Priority;
import com.elmar.servicedeskbackend.enums.TicketStatus;
import com.elmar.servicedeskbackend.model.Ticket;
import com.elmar.servicedeskbackend.repositories.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Arrays;

@SpringBootApplication
public class ServiceDeskBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDeskBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(TicketRepository ticketRepository) {
        return args -> ticketRepository.saveAll(Arrays.asList(
                Ticket.builder().ticketNumber("Ticket1")
                        .date(Instant.now())
                        .description("Create new project")
                        .email("test@test.com")
                        .status(TicketStatus.OPEN)
                        .priority(Priority.MAJOR)
                        .title("New Project")
                        .build(),
                Ticket.builder().ticketNumber("Ticket2")
                        .date(Instant.now())
                        .description("Write tests for project")
                        .email("test@test.com")
                        .status(TicketStatus.OPEN)
                        .priority(Priority.CRITICAL)
                        .title("Test Project")
                        .build(),
                Ticket.builder().ticketNumber("Ticket3")
                        .date(Instant.now())
                        .description("Deploy project")
                        .email("test@test.com")
                        .status(TicketStatus.OPEN)
                        .priority(Priority.MINOR)
                        .title("Deploy")
                        .build(),
                Ticket.builder().ticketNumber("Ticket4")
                        .date(Instant.now())
                        .description("Write documentation")
                        .email("test@test.com")
                        .status(TicketStatus.OPEN)
                        .priority(Priority.TRIVIAL)
                        .title("Document Project")
                        .build()
        ));

    }
}
