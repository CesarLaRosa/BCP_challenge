import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  constructor(private http: HttpClient) { }

  converter(currency: { amount: string, sourceCurrency: string, destinationCurrency: string }): Observable<boolean> {
    return this.http.post<any>(`${config.apiUrl}/money/change`, currency)
      .pipe(
        catchError(error => {
          console.error(error);
          return of(false);
        }));
  }
}
