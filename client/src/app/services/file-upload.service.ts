import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  url = `${environment.apiUrl}/orders/upload`;

  constructor(private http: HttpClient) { }

  uploadWithProgress(formData: FormData): Observable<any> {
    return this.http.post(this.url, formData, {observe: 'events', reportProgress: true})
      .pipe(
        catchError(err => this.handleError(err))
      )
  }

  private handleError(err: any) {
    return throwError(err);
  }
}
