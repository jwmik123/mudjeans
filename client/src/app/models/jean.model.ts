export class Jean {
  public id: number;
  public productCode: string;
  public description: string;
  public size: string;
  public total: number;
  public currentStock: number;
  public lastThreeMonths: number;

  public static trueCopy(jean: Jean): Jean {
    return (jean == null ? null : Object.assign(new Jean(), jean));
  }
}
