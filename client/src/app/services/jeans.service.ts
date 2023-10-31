import { Jean } from './../models/jean.model';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JeansService {

  public jeans: BehaviorSubject<any>;

  constructor(private http: HttpClient) {
      this.jeans = new BehaviorSubject<any>({});
      this.reloadAllData();
   }

   public reloadAllData(): void {
     const obs = this.restGetJeans();
     obs.subscribe((data: Jean[]) => {
        const obj = {};

        const keys = Object.keys(data);

        for (let i = 0; i < keys.length; i++) {
          obj[keys[i]] = [];
          const keydata = data[keys[i]];
          for (let j = 0; j < keydata.length; j++) {
            obj[keys[i]].push(Jean.trueCopy(Object.assign(new Jean(), data[keys[i]][j])))
          }
        }
        console.log(obj)
        this.jeans.next(obj);
     })
   }

   private restGetJeans(): Observable<any> {
     const url = `${environment.apiUrl}/jean`;
     return this.http.get<any>(url);
   }

   public getAllJeans(): BehaviorSubject<any> {
     return this.jeans;
   }
}
