import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import {applySourceSpanToExpressionIfNeeded} from "@angular/compiler/src/output/output_ast";
import { ActivatedRoute, Router } from '@angular/router';
import { JeansService } from 'src/app/services/jeans.service';
import { FileUploadService } from 'src/app/services/file-upload.service';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let componentHTML: HTMLCollection;



  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    componentHTML = fixture.debugElement.nativeElement;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

//Semih
  it('should show the Product Name in the table', () => {
    const title = 'Product Name';
    fixture.detectChanges();

    component.description = title;
    expect(component.description).toContain(title);
  });

//Semih
  it('should show the size in the table', () => {
    const title = 'Size';
    fixture.detectChanges();

    component.size = title;

    expect(component.size).toContain(title);
  });



  // Vgm werkt deze nog niet.....
  // it('the calculated stock', () => {
  //   expect(component.getKeys).toBeGreaterThan(0);
  // });

  it('get the keys of the jean object', () => {
    expect(component.getKeys()).toEqual([]);
    component.jeansObject = {"jeancode1":['jean1', 'jean2', 'jean3'], "jeancode2": ['jean1', 'jean2', 'jean3']};
    expect(component.getKeys().length).toBe(2);
    expect(component.getKeys()).toEqual(['jeancode1', 'jeancode2']);
  });

  it('calculate the total stock', () => {
    component.jeansObject = {'jeancode1': [{currentStock:12}, {currentStock:6}, {currentStock:8}, {currentStock:14}], 'jeancode2': [{currentStock:9}, {currentStock:21}, {currentStock:5}, {currentStock:15}]};
    expect(component.jeansObject).toEqual({'jeancode1': [{currentStock:12}, {currentStock:6}, {currentStock:8}, {currentStock:14}], 'jeancode2': [{currentStock:9}, {currentStock:21}, {currentStock:5}, {currentStock:15}]});
    expect(component.calculateTotalStock('jeancode1')).toBe(40);
    expect(component.calculateTotalStock('jeancode2')).toBe(50);
  });

  it('calculate the total stock forecast', () => {
    component.jeansObject = {'jeancode1': [{lastThreeMonths:12}, {lastThreeMonths:6}, {lastThreeMonths:8}, {lastThreeMonths:14}], 'jeancode2': [{lastThreeMonths:9}, {lastThreeMonths:21}, {lastThreeMonths:5}, {lastThreeMonths:15}]};
    expect(component.jeansObject).toEqual({'jeancode1': [{lastThreeMonths:12}, {lastThreeMonths:6}, {lastThreeMonths:8}, {lastThreeMonths:14}], 'jeancode2': [{lastThreeMonths:9}, {lastThreeMonths:21}, {lastThreeMonths:5}, {lastThreeMonths:15}]});
    expect(component.calculateStockForecast('jeancode1')).toBe(40);
    expect(component.calculateStockForecast('jeancode2')).toBe(50);
  });
});



