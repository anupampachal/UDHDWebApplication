import { MatTableDataSource } from "@angular/material/table";

export class MatTableWithPagination<Type>{
  cols!: string[] ;
  colsWithAction!: string[];
  pageSizeOptions!:number [];
  data!:MatTableDataSource<Type>;
  dataTotLength!:number;
  pageSize!:number;
  currentPage!:number;
}
