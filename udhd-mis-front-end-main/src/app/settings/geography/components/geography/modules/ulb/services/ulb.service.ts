import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { SettingsServerRouteConstants } from "src/app/settings/settings-server-route-constant";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { ULBDTO } from "src/app/shared/model/ulb.model";

@Injectable()
export class ULBService {

    constructor(private http: HttpClient) { }

    findULBById(id: string): Observable<ULBDTO> {
        return this.http.get<ULBDTO>(SettingsServerRouteConstants.STNG_ULB_FIND_ULB_BY_ID_URL + "/" + id);
    }

    getULBDataByPage(data: PaginationModel): Observable<GenericResponseObject<ULBDTO>> {
        return this.http.post<GenericResponseObject<ULBDTO>>(SettingsServerRouteConstants.STNG_ULB_GET_ULB_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    createULB(requestData: ULBDTO): Observable<ULBDTO> {
        return this.http.post<ULBDTO>(SettingsServerRouteConstants.STNG_ULB_CREATE_UPDATE_ULB_URL, requestData);
    }

    updateULB(requestData: ULBDTO): Observable<ULBDTO> {
        return this.http.put<ULBDTO>(SettingsServerRouteConstants.STNG_ULB_CREATE_UPDATE_ULB_URL, requestData);
    }
    deleteULB(id?: number) {
        if (!!id) {
            return this.http.delete(SettingsServerRouteConstants.STNG_ULB_CREATE_UPDATE_ULB_URL + "/" + id);
        }
        return of(null);
    }

    getULBByPageForDistrict(data: PaginationModel): Observable<GenericResponseObject<ULBDTO>> {
        return this.http.post<GenericResponseObject<ULBDTO>>(SettingsServerRouteConstants.STNG_ULB_GET_ULB_BY_PAGE_FOR_DISTRICT, data).pipe(shareReplay(2));
    }

}

