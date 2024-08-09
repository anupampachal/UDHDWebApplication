import { Pageable } from "./pageable.model";
import { Sort } from "./sort.model";

export interface Page<Type> {
  content: Type[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  sort: Sort;
  number: number;
  first: boolean;
  size: number;
  numberOfElements: number;
  empty: boolean;
}
