import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Ticket } from './models/ticket';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  endPoint = 'http://localhost:8080/tickets';

  constructor(private http: HttpClient) { }

  get(params): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.endPoint, { params }).pipe(
      catchError(err => of([]))
    );
  }

  update(ticket: Ticket) {
    return this.http.put(`${this.endPoint}/${ticket.ticketNumber}`, ticket);
  }

  create(ticket: Ticket) {
    return this.http.post(this.endPoint, ticket);
  }

  getPriorityList() {
    return ['BLOCKER', 'CRITICAL', 'MAJOR', 'MINOR', 'TRIVIAL'];
  }
}
