import { Page } from "./page.model";

export interface GenericResponseObject<Type>{
  totalRecords: number;
  currentRecords:Page<Type>;
  currentPage: number;
  itemsPerPage: number;
}
