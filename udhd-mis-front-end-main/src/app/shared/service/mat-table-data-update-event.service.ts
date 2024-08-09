import { EventEmitter, Injectable } from "@angular/core";

@Injectable()
export class MatTableDataUpdateService{
   tableDataChangeEvent:EventEmitter<any>= new EventEmitter();

}
