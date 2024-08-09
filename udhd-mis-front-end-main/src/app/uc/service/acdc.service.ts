import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { ULBDTO } from "src/app/shared/model/ulb.model";
import { ACDCULBBasedDTO } from "../ac_dc/model/ac-dc.model";
import { UCServerRouteConstants } from "../server-routes/uc-server-routes.const";
import { GeoDivisionDTO } from "src/app/shared/model/geo-division.model";
import { GeoDistrictDTO } from "src/app/shared/model/geo-district.model";

@Injectable()
export class ACDCService {

    getGEOGRAPHYROUTES='/api/settings/user-mgt/division'
    constructor(private http: HttpClient) { }

    findACDCById(id: string): Observable<ACDCULBBasedDTO> {
        return this.http.get<ACDCULBBasedDTO>(UCServerRouteConstants.AC_DC_FIND_BY_ID_URL + "/" + id);
    }

    getACDCByPage(data: PaginationModel): Observable<GenericResponseObject<ACDCULBBasedDTO>> {
        return this.http.post<GenericResponseObject<ACDCULBBasedDTO>>(UCServerRouteConstants.AC_DC_FIND_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    createACDC(requestData: ACDCULBBasedDTO): Observable<ACDCULBBasedDTO> {
        return this.http.post<ACDCULBBasedDTO>(UCServerRouteConstants.AC_DC_CREATE_UPDATE_URL, requestData);
    }

    updateACDC(requestData: ACDCULBBasedDTO): Observable<ACDCULBBasedDTO> {
        return this.http.put<ACDCULBBasedDTO>(UCServerRouteConstants.AC_DC_CREATE_UPDATE_URL, requestData);
    }
    deleteACDCById(id?: number) {
        if (!!id) {
            return this.http.delete(UCServerRouteConstants.ACDC_BASE_URL + "/" + id);
        }
        return of(null);
    }
    getULBList(){
        return this.http.get<ULBDTO[]>(UCServerRouteConstants.AC_DC_ULB_LIST_URL );
    }

    getDivisionList(): Observable<GeoDivisionDTO> {
        return this.http.get<GeoDivisionDTO>(UCServerRouteConstants.AC_DC_GET_GEOGRAPHY_DIVISION);
    }
    getDistrictListByDivisionId(id: number): Observable<GeoDistrictDTO> {
        return this.http.get<GeoDistrictDTO>(UCServerRouteConstants.AC_DC_GET_GEOGRAPHY_DISTRICT + "/" + id);
    }
    getULBListByDistrictId(id: number, ulbType: string): Observable<ULBDTO> {
        return this.http.get<ULBDTO>(UCServerRouteConstants.AC_DC_GET_GEOGRAPHY_ULB + "/" + id + "/type/" + ulbType);
    }


}

