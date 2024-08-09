import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ULBDTO } from "../model/ulb.model";

@Injectable()
export class ULBSelectorService {
    constructor(private http:HttpClient){}

    /*getULBListForOthers():Observable<ULBDTO[]>{
        return null;
    }*/
}