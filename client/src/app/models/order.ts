
export class Order {
  orderNumber: number;
  name: string;
  quantity: number;
  productCode: string;
  description: string;
  fabric: string;
  size: string;
  orderFix: number;

  constructor(orderNumber?: number, name?: string, quantity?: number,
              productCode?: string, description?: string, fabric?: string,
              size?: string) {
    this.orderNumber = orderNumber;
    this.name = name;
    this.quantity = quantity;
    this.productCode = productCode;
    this.description = description;
    this.fabric = fabric;
    this.size = size;
  }

  static trueCopy(order: Order): Order {
    // @ts-ignore
    return (order == null ? null : Object.assign(new Order(), order));
  }
}
