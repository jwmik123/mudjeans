import { ActivatedRoute, Router } from '@angular/router';
import { JeansService } from './../../services/jeans.service';
import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs';
//import {OrdersService} from '../../services/orders.service';
import {Order} from "../../models/order";
import {OrdersService} from "../../services/orders.service";
import { FormBuilder, FormGroup } from '@angular/forms';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

public orders: Order[];
public selectedOrder: Order;
public addOrderScreen: boolean;
public jeans: Order;
public jeansObject: object;
public placeholder: string;


  public orderData: Order[];


  constructor(public ordersService: OrdersService, private jeanService: JeansService, private route: ActivatedRoute, private router: Router) {
    this.orderData = [];
    this.addOrderScreen = false;

    jeanService.getAllJeans().subscribe((data: object) => {
      this.jeansObject = data;
      this.loadOrderData();
      console.log('all data loaded');
    })

    if (localStorage.getItem('orderFormData')) {
      this.orderData = JSON.parse(localStorage.getItem('orderFormData'));
      this.addOrderScreen = true;
    }
    if (route.snapshot.queryParamMap.keys.length) {
      this.addOrderScreen = true;
      const order = new Order();
      order.description = route.snapshot.queryParamMap.get('jean');
      order.quantity = Number.parseInt(route.snapshot.queryParamMap.get('quantity'));
      this.orderData.push(order);
      console.log(route.snapshot.queryParamMap);
      this.saveFormDataToLocalStorage();
      router.navigate(['/orders']);
    }
    this.ordersService.getAllOrders().subscribe(
      (data) => {
        this.orders = data;

        let fakeOrders = [];

        data.forEach(element => {

          let orderFound = false;
          let orderIndex = null;

          fakeOrders.forEach((order, index) => {
            if (order.orderNumber == element.name) {
              orderFound = true;
              orderIndex = index;
            }
          });

          if (orderFound == false) {
            fakeOrders.push({orderNumber: element.name, quantity: 1, jeans: [{name: element.description, quantity: element.quantity, products: [element]}]})
          } else {

            let jeanFound = false;
            let jeanIndex = null;

            fakeOrders[orderIndex].jeans.forEach((jean, index) => {
              if (jean.name == element.description) {
                jeanFound = true;
                jeanIndex = index;
              }
            });

            if (jeanFound == false) {
              fakeOrders[orderIndex].jeans.push({name: element.description, quantity: element.quantity, products: [element]})
              fakeOrders[orderIndex].quantity++;
            } else {
              fakeOrders[orderIndex].jeans[jeanIndex].products.push(element);
              fakeOrders[orderIndex].jeans[jeanIndex].quantity = fakeOrders[orderIndex].jeans[jeanIndex].quantity + element.quantity;
            }
          }
        });

        this.orders = fakeOrders;

        //console.log(fakeOrders);
      },
      (error) => {
        console.log('Error: Status ' + error.status + ' - ' + error.error);
      });
   }

   saveFormDataToLocalStorage(): void {
     this.loadOrderData();
     localStorage.setItem('orderFormData', JSON.stringify(this.orderData));
   }

  //  constructor() {
  //   this.addOrderScreen = false;
  //  }

  ngOnInit(): void {
    // this.orders = [];
    // this.orders.push({name: "Slim Lassen - Strong Blue", quantity: "1", orderNumber: "#7262", products: [{productCode: "MB0004S001D001-28-32", description: "Slim Lassen - Strong Blue - W28 L32", fabric: "RCY DAVE", size: "W28 L32", orderFix: "100"},{productCode: "MB0004S001D001-30-32", description: "Slim Lassen - Strong Blue - W30 L32", fabric: "RCY DAVE", size: "W30 L32", orderFix: "60"},{productCode: "MB0004S001D001-28-34", description: "	Slim Lassen - Strong Blue - W28 L34", fabric: "RCY DAVE", size: "W28 L34", orderFix: "50"},{productCode: "MB0004S001D001-28-32", description: "Slim Lassen - Strong Blue - W28 L32", fabric: "RCY DAVE", size: "W28 L32", orderFix: "100"},{productCode: "MB0004S001D001-30-32", description: "Slim Lassen - Strong Blue - W30 L32", fabric: "RCY DAVE", size: "W30 L32", orderFix: "60"},{productCode: "MB0004S001D001-28-34", description: "	Slim Lassen - Strong Blue - W28 L34", fabric: "RCY DAVE", size: "W28 L34", orderFix: "50"}]});
    // this.orders.push({name: "Beverly short- Dip Black", quantity: "3", orderNumber: "#3274", products: [{productCode: "WB0016R002D013-S", description: "Beverly short- Dip Black - S", fabric: "RCY LANX", size: "S", orderFix: "30"},{productCode: "WB0016R002D013-M", description: "Beverly short- Dip Black - M", fabric: "RCY LANX", size: "M", orderFix: "20"},{productCode: "WB0016R002D013-L", description: "Beverly short- Dip Black - L", fabric: "RCY LANX", size: "L", orderFix: "60"}]});
    // this.orders.push({name: "Beverly short- Dip Black", quantity: "2", orderNumber: "#3264", products: [{productCode: "WB0016R002D013-S", description: "Beverly short- Dip Black - S", fabric: "RCY LANX", size: "S", orderFix: "30"},{productCode: "WB0016R002D013-M", description: "Beverly short- Dip Black - M", fabric: "RCY LANX", size: "M", orderFix: "20"},{productCode: "WB0016R002D013-L", description: "Beverly short- Dip Black - L", fabric: "RCY LANX", size: "L", orderFix: "60"}]});
    // this.orders.push({name: "Skinny Hazen - Strong Blue", quantity: "1", orderNumber: "#3345", products: [{productCode: "WB0004S001D001-31-30", description: "Skinny Hazen - Strong Blue - W31 L30", fabric: "RCY DAVE", size: " W31 L30", orderFix: "55"},{productCode: "WB0004S001D001-31-31", description: "Skinny Hazen - Strong Blue - W31 L31", fabric: "RCY DAVE", size: " W31 L31", orderFix: "45"},{productCode: "WB0004S001D001-32-34", description: "Skinny Hazen - Strong Blue - W32 L34", fabric: "RCY DAVE", size: " W32 L34", orderFix: "25"}]});
    // this.orders.push({name: "Slim Lassen - Strong Blue", quantity: "1", orderNumber: "#7262", products: [{productCode: "MB0004S001D001-28-32", description: "Slim Lassen - Strong Blue - W28 L32", fabric: "RCY DAVE", size: "W28 L32", orderFix: "100"},{productCode: "MB0004S001D001-30-32", description: "Slim Lassen - Strong Blue - W30 L32", fabric: "RCY DAVE", size: "W30 L32", orderFix: "60"},{productCode: "MB0004S001D001-28-34", description: "	Slim Lassen - Strong Blue - W28 L34", fabric: "RCY DAVE", size: "W28 L34", orderFix: "50"},{productCode: "MB0004S001D001-28-32", description: "Slim Lassen - Strong Blue - W28 L32", fabric: "RCY DAVE", size: "W28 L32", orderFix: "100"},{productCode: "MB0004S001D001-30-32", description: "Slim Lassen - Strong Blue - W30 L32", fabric: "RCY DAVE", size: "W30 L32", orderFix: "60"},{productCode: "MB0004S001D001-28-34", description: "	Slim Lassen - Strong Blue - W28 L34", fabric: "RCY DAVE", size: "W28 L34", orderFix: "50"}]});
    // this.orders.push({name: "Skinny Hazen - Strong Blue", quantity: "1", orderNumber: "#3345", products: [{productCode: "WB0004S001D001-31-30", description: "Skinny Hazen - Strong Blue - W31 L30", fabric: "RCY DAVE", size: " W31 L30", orderFix: "55"},{productCode: "WB0004S001D001-31-31", description: "Skinny Hazen - Strong Blue - W31 L31", fabric: "RCY DAVE", size: " W31 L31", orderFix: "45"},{productCode: "WB0004S001D001-32-34", description: "Skinny Hazen - Strong Blue - W32 L34", fabric: "RCY DAVE", size: " W32 L34", orderFix: "25"}]});
    this.selectedOrder = null;
    this.placeholder = 'Fill In Amount';
  }

  loadOrderData() {
    for (let order of this.orderData) {
      if (!this.jeansObject[order.description]) break;
      const data = this.jeansObject[order.description][0];
      // order.fabric = data.fabric;
      order.productCode = data.productCode;
      order.name = data.description;
      order.size = data.size;
    }
  }

  selectOrder(order) {
    this.addOrderScreen = false;
    if (order != null) {
      this.selectedOrder = order;
    }
    // this.ordersService.selectedOrder1 = order;
    // this.addOrderScreen = false;
  }

  ViewAddOrderScreen() {
    this.addOrderFormField();
    this.addOrderScreen = true;
  }

  addOrderFormField(): void {
    this.orderData.push(new Order());
    console.log(this.orderData);
  }

  addOrder() {
    console.log("try add");

    let ordersService = this.ordersService;

    let orderData = this.orderData;
    let jeansObject = this.jeansObject;

      let ordernum = (Math.floor(Math.random() * (9999 - 1111 + 1)) + 1111).toString();
        ordernum = "#" + ordernum;
        console.log(ordernum);

    // $('.jean').each(function(i, obj) {
    //   let val = <number>$(this).find(`[name='jean${i}']`).val();
    //   let quantity = <number>$(this).find(`[name='ordernumber${i}']`).val();
    //   if(val != null && quantity > 10) {

    //     val = Math.round(val);
    //     quantity = Math.round(quantity);

        for (let index = 0; index < orderData.length; index++) {
          // console.log(jeansObject[val])
          let d = orderData[index];
          if (!d.description || !d.quantity) continue;
          console.log(d);
          ordersService.postNewSavedOrder({
            "name": ordernum,
            "orderNumber": Number.parseInt(ordernum.replace("#", "")),
            "quantity": d.quantity / 5,
            "productCode": d.productCode,
            "description": d.description,
            "fabric": "RCY LANX",
            "size": d.size,
            "orderFix": d.quantity / 5
            }).subscribe(
              (data) => {
                console.log(data, " did add");
              },
              (error) => {
                console.log('Error: Status ' + error.status + ' - ' + error.error);
              })
        }
        this.addOrderScreen = false;
        localStorage.removeItem('orderFormData');
        ordersService.getAllOrders()
          .pipe(take(1))
          .subscribe(data => {
            this.orders = data;
          })
      }



  public getJeanKeys(): string[] {
    return Object.keys(this.jeansObject);
  }

}
