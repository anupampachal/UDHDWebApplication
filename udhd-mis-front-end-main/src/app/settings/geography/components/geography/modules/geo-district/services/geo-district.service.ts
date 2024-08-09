import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { SettingsServerRouteConstants } from "src/app/settings/settings-server-route-constant";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { GeoDistrictDTO } from "src/app/shared/model/geo-district.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class GeoDistrictService {

    constructor(private http: HttpClient) { }

    findDistrictById(id: string): Observable<GeoDistrictDTO> {
        return this.http.get<GeoDistrictDTO>(SettingsServerRouteConstants.STNG_GEODISTRICT_FIND_DISTRICT_BY_ID_URL + "/" + id);
    }

    getGeoDistrictByPage(data: PaginationModel): Observable<GenericResponseObject<GeoDistrictDTO>> {
        return this.http.post<GenericResponseObject<GeoDistrictDTO>>(SettingsServerRouteConstants.STNG_GEODISTRICT_GET_DISTRICT_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    createUpdateGeoDistrict(requestData: GeoDistrictDTO): Observable<GeoDistrictDTO> {
        return this.http.post<GeoDistrictDTO>(SettingsServerRouteConstants.STNG_GEODISTRICT_CREATE_UPDATE_GEO_DISTRICT_URL, requestData);
    }

    
    deleteGeoDistrictById(id?: number) {
        if (!!id) {
            return this.http.delete(SettingsServerRouteConstants.STNG_GEODISTRICT_CREATE_UPDATE_GEO_DISTRICT_URL + "/" + id);
        }
        return of(null);
    }

    getGeoDistrictByPageForDivision(data: PaginationModel): Observable<GenericResponseObject<GeoDistrictDTO>> {
        return this.http.post<GenericResponseObject<GeoDistrictDTO>>(SettingsServerRouteConstants.STNG_GEODISTRICT_GET_DISTRICT_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

}

