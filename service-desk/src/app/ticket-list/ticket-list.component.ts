import { TicketDetailComponent } from './../ticket-detail/ticket-detail.component';
import { Observable, of, merge, BehaviorSubject } from 'rxjs';
import { TicketService } from './../ticket.service';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { Ticket } from '../models/ticket';
import { MatDialog } from '@angular/material/dialog';
import { tap, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit, AfterViewInit {
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ['ticketNumber', 'title', 'email', 'description', 'priority', 'date'];

  tickets$: Observable<Ticket[]> = new Observable<Ticket[]>();
  loadTickets$ = new BehaviorSubject(null);

  constructor(private ticketService: TicketService,
              private dialog: MatDialog) { }

  ngOnInit() {

    this.tickets$ = this.loadTickets$.pipe(
      switchMap((sort?: Sort) => this.ticketService.get({
        ...(sort ? { sortBy: sort.active } : {}),
        ...(sort ? { sortOrder: sort.direction } : {})
      }))
    );
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(this.loadTickets$);
  }

  openModal(ticket?: Ticket) {
    const dref = this.dialog.open(TicketDetailComponent, {
      width: '500px',
      data: ticket
    });

    dref.afterClosed().subscribe(shouldRefresh => shouldRefresh ? this.loadTickets$.next(null) : null);
  }

}
