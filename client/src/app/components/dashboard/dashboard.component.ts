import { BehaviorSubject } from 'rxjs';
import { JeansService } from './../../services/jeans.service';
import { Jean } from './../../models/jean.model';
import { Component, OnInit } from '@angular/core';
import {Order} from "../../models/order";
import { FileUploadService } from 'src/app/services/file-upload.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public jeansObject: Object;
  percentCompleted = 0;
  isUploaded = false;
  fileName = '';
  fileType = '';
  public description: string;
  public size: string;

  constructor(private jeanService: JeansService, private fuService: FileUploadService, private router: Router, private route: ActivatedRoute) {
      const jeanSubject = jeanService.getAllJeans();
      jeanSubject.subscribe(data => {
        this.jeansObject = data;
      })
   }

  ngOnInit(): void {
  }

  public upload(files: File[]) {
    const file = files[0];
    console.log(file.name);
    this.isUploaded = false;
    this.fileName = '';
    this.fileType = '';

    const formData = new FormData();
    formData.append('file', file);
    this.fuService.uploadWithProgress(formData)
      .subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          console.log(event);
          this.percentCompleted = Math.round(100* event.loaded / event.total);
        }
        else if (event instanceof HttpResponse) {
          this.isUploaded = true;
          this.fileName = event.body.fileName;
          this.fileType = event.body.fileType;
          this.jeanService.reloadAllData();
        }
      })
  }

  public goToOrderAndAddToOrder(jeans: string) {
    const total = this.calculateTotalStock(jeans);
    const forecast = this.calculateStockForecast(jeans);
    const quantity = forecast - total;
    this.router.navigate(["/orders"], {
      queryParams: {
        'jean': jeans,
        'quantity': quantity
      },

    })
  }

  public getKeys(): string[] {
    return Object.keys(this.jeansObject);
  }

  public calculateTotalStock(key: string): number {
    return this.jeansObject[key].reduce((last, current) => (typeof last == "number") ? last + current.currentStock : (typeof last == "undefined") ? 0 : last.currentStock + current.currentStock);
  }

  public calculateStockForecast(key: string): number {
    return this.jeansObject[key].reduce((last, current) => (typeof last == "number") ? last + current.lastThreeMonths : (typeof last == "undefined") ? 0 : last.lastThreeMonths + current.lastThreeMonths)
  }

}
