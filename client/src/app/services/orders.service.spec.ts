import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { OrdersService } from './orders.service';
import {Order} from "../models/order";

describe('OrdersService', () => {
  let service: OrdersService;
  let testOrder: Order;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OrdersService]
    });
    service = TestBed.inject(OrdersService);
    testOrder = new Order(12,'testOrder',23, 'long jeans', 'test desc');
    service.orders.push(testOrder);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should save', () => {
    //expect(service.save(testOrder)).toBeTruthy();
  });

  it('should create order in array', () => {
    expect(service.orders.length).toBeGreaterThanOrEqual(1);
  })

  it('should delete order in array', () => {
    service.deleteById(12);
    expect(service.orders.length).toBeLessThan(1);
  })
});
