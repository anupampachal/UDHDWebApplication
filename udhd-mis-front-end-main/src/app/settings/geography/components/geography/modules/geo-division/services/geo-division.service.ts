import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { SettingsServerRouteConstants } from "src/app/settings/settings-server-route-constant";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { GeoDivisionDTO } from "src/app/shared/model/geo-division.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class GeoDivisionService {

    constructor(private http: HttpClient) { }

    findDivisionById(id: string): Observable<GeoDivisionDTO> {
        return this.http.get<GeoDivisionDTO>(SettingsServerRouteConstants.STNG_GEODIVISION_FIND_DIVISION_BY_ID_URL + "/" + id);
    }

    getGeoDivisionByPage(data: PaginationModel): Observable<GenericResponseObject<GeoDivisionDTO>> {
        return this.http.post<GenericResponseObject<GeoDivisionDTO>>(SettingsServerRouteConstants.STNG_GEODIVISION_GET_DIVISION_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    createGeoDivision(requestData: GeoDivisionDTO): Observable<GeoDivisionDTO> {
        return this.http.post<GeoDivisionDTO>(SettingsServerRouteConstants.STNG_GEODIVISION_CREATE_UPDATE_GEO_DIVISION_URL, requestData);
    }

    updatePhysicalStorageData(requestData: GeoDivisionDTO): Observable<GeoDivisionDTO> {
        return this.http.put<GeoDivisionDTO>(SettingsServerRouteConstants.STNG_GEODIVISION_CREATE_UPDATE_GEO_DIVISION_URL, requestData);
    }
    deletePhysicalStorageById(id?: number) {
        if (!!id) {
            return this.http.delete(SettingsServerRouteConstants.STNG_GEODIVISION_CREATE_UPDATE_GEO_DIVISION_URL + "/" + id);
        }
        return of(null);
    }

}

