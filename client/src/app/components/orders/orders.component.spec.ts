import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {JeansService} from '../../services/jeans.service';
import {OrdersService} from '../../services/orders.service';
import { OrdersComponent } from './orders.component';
import { Order } from 'src/app/models/order';
import { Jean } from 'src/app/models/jean.model';

describe('OrdersComponent', () => {
  let component: OrdersComponent;
  let fixture: ComponentFixture<OrdersComponent>;
  let componentHTML: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [{provide: ActivatedRoute, useValue:{snapshot: {queryParamMap:{keys:['jean', 'quantity'], get(value: string): string | number {return value == 'jean' ? "Some name of a jean" : 123}}}}}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersComponent);
    component = fixture.componentInstance;
    componentHTML = fixture.debugElement.nativeElement;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Placeholder should be "Fill In Amount"', () => {
    const title = 'initialize';

    component.placeholder = title;
    fixture.detectChanges();

    expect(component.placeholder).toContain(title);
  });

  it('selectedOrder should be null on start', () => {
    expect(component.selectedOrder).toBeNull();
  });


  it('addOrderScreen should be true on start', () => {
    expect(component.addOrderScreen).toBeTrue();
  });

  it('addOrderScreen should be false after selecting order', () => {
    component.selectOrder(null);
    fixture.detectChanges();
    expect(component.addOrderScreen).toBeFalse();
  });

  it('selectedOrder should be filled after selected', () => {
    let testOrder = new Order(12,'testOrder',23, 'long jeans', 'test desc');
    component.selectOrder(testOrder);
    fixture.detectChanges();
    console.log(component.selectedOrder);
    expect(component.selectedOrder).toBeInstanceOf(Order);
  });

  it('shoud save the data to the local storage', () => {
    component.orderData = [new Order(123, "order1", 12, "abc123", "description order1", "demin", "L"), new Order(456, "order2", 21, "321cba", "description order2", "denim", "S")];
    component.jeansObject = {"description order1": [new Jean()], "description order2": [new Jean()]};
    component.saveFormDataToLocalStorage();
    expect(JSON.stringify(component.orderData)).toBe(localStorage.getItem("orderFormData"));
  });

  it('should add a formfield to the form', () => {
    const length = component.orderData.length;
    expect(component.orderData.length).toBe(length);
    component.addOrderFormField();
    expect(component.orderData.length).toBe(length + 1);
  });

  //Semih
  it('should post and delete test order',  () => {
    const testOrder = new Order(643, 'test', 2, '#325', 'testDesciption', 'testFabric', 'small');
    let deletedOrder = null;
    component.ordersService.postNewSavedOrder(testOrder);
    const orders = component.ordersService.orders;
    for (let i = 0; i < component.ordersService.orders.length; i++) {
      if (orders[i].orderNumber === 643) {
        deletedOrder = orders[i];
        break;
      }
    }
    expect(deletedOrder).toBeNull();
  });


// Michael Test 3
  it('Placeholder should be "Fill In Amount"', () => {
    // initialize a placeholder const
    const placeholder = 'Fill In Amount';
    // check if the placeholder from the component is the same with the one should be there
    expect(component.placeholder).toContain(placeholder);
  });

  // Michael Test 4
  it('no order should be selected at start',  () => {
    // check if the selectedOrder from the component is null
    expect(component.selectedOrder).toBeNull();
  });

  // Michael Test 5
  it('when clicked on add order, new form should apear',  () => {
    const nativeElement = fixture.nativeElement;
    // finding the button with the tag
    const button = nativeElement.querySelector('button');
    // check if there is a button found
    expect(button).toBeDefined();
    button.click();
    // check if the order screen is visible when clicked on the button, if yes = true
    expect(component.ViewAddOrderScreen).toBeTruthy();
  });

  // Michael Test 6
  it('should delete new order',  () => {
    // creating a neworder
    const newOrder = new Order();
    newOrder.orderNumber = 222;
    newOrder.name = 'test';
    newOrder.quantity = 2;
    newOrder.productCode = '#345';
    newOrder.description = 'test2';
    newOrder.fabric = 'YWK';
    newOrder.size = 'M';

    // post the new made order
    component.ordersService.postNewSavedOrder(newOrder);
    // initialising deletOrder let
    let deletedOrder = null;
    // create a loop which loops for the length of the amount of orders
    for (let i = 0; i < component.ordersService.orders.length; i++) {
      // checks for the looped order if the ordernumber is 222
      if (component.ordersService.orders[i].orderNumber === 222) {
        // if this is the case, delete order
        deletedOrder = component.ordersService.deleteById(222);
        break;
      }
    }
    expect(deletedOrder).toBeNull();
  });


});
