export class HolidayDTO {
  id?: number;
  occasion!: string;//min 3 max100
  startDate!: string; //min 6 max=50
  endDate!: string;//min 2max 50
  status!: boolean;
}
