import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Order} from '../models/order';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  public orders: Order[] = [];
  public selectedOrder1 = Order;

  constructor(private httpClient: HttpClient) {

  }

  findAll(): Order[] {
    return this.orders;
  }

  findById(orderNumber: number): Order {
    // tslint:disable-next-line:triple-equals
    return this.orders.find((order) => order.orderNumber == orderNumber);
  }

  // @ts-ignore
  save(order: Order): void {
    this.orders[this.orders.findIndex((foundOrder) => foundOrder.orderNumber === order.orderNumber)] = order;
    this.putReplaceOrder(order).subscribe(data => console.log(data));
  }

  deleteById(orderNumber: number): Order {
    this.deleteOrder(orderNumber);
    const idx = this.orders.findIndex((foundOrder) => foundOrder.orderNumber === orderNumber);
    if (idx === -1) {
      return null;
    } else {
      return this.orders.splice(idx, 1)[0];
    }
  }

  getAllOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${environment.apiUrl}/orders`);
  }

  postNewSavedOrder(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(`${environment.apiUrl}/orders`, order);
  }

  private putReplaceOrder(order: Order): Observable<Order> {
    return this.httpClient.put<Order>(`${environment.apiUrl}/orders/` + order.orderNumber, order);
  }

  private deleteOrder(orderNumber: number): void {
    this.httpClient.delete<Order>(`${environment.apiUrl}/orders/` + orderNumber).subscribe(data => console.log(data));
  }
}
