import { TicketService } from './../ticket.service';
import { Ticket } from './../models/ticket';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.scss']
})
export class TicketDetailComponent implements OnInit {

  ticketPriorities: Array<string>;
  form: FormGroup;

  constructor(private dialogRef: MatDialogRef<TicketDetailComponent>,
              @Inject(MAT_DIALOG_DATA) public ticket: Ticket,
              private formGroup: FormBuilder,
              private ticketService: TicketService) { }

  ngOnInit(): void {

    this.ticketPriorities = this.ticketService.getPriorityList();

    this.form = this.formGroup.group({
      ticketNumber: this.ticket?.ticketNumber,
      status: this.ticket?.status,
      title: [this.ticket?.title, Validators.required],
      email: [this.ticket?.email, [Validators.required, Validators.email]],
      description: [this.ticket?.description, Validators.required],
      priority: [this.ticket?.priority, Validators.required]
    });
  }

  resolve(ticket: Ticket) {
    this.ticketService.update({ ...ticket, ...{ status: 'CLOSED' } }).pipe(
      tap(() => this.dialogRef.close(true)),
      catchError(err => of(null))
    ).subscribe();
  }

  save(ticket: Ticket) {
    (ticket.ticketNumber ? this.ticketService.update(ticket) : this.ticketService.create(ticket)).pipe(
      tap(() => this.dialogRef.close(true)),
      catchError(err => of(null))
    ).subscribe();
  }

}
