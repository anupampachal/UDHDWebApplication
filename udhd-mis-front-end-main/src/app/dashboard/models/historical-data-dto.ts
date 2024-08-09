export class HistoricalDataDTO {
  id?:number;
  fileId?: string;
  file?: File;
  startDate!: string;
  endDate!: string;
  ulbId!: string;
}
