export class FinYearDateDTO {
  id?:number;
  fileId?: string;
  file?: File;
  startDate!: string;
  endDate!: string;
  inputData!: string;
  quarter!: string;
}
