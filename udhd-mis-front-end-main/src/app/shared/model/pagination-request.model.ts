export class PaginationModel {
  pageNo?: number;
  pageSize?: number;
  total?: number;
  queryString?: string;
  sortedBy?: string;
  directionOfSort?: string;
  code?: number;
  id?: number;
  dateFrom?: Date;
  dateTo?: Date;
  dateType?: string;
  status?: string;
  criteria?: string;
}
