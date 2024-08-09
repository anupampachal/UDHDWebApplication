import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { GeoDistrictDTO } from "src/app/shared/model/geo-district.model";
import { GeoDivisionDTO } from "src/app/shared/model/geo-division.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { ULBDTO } from "src/app/shared/model/ulb.model";
import { OtherTypeUserInfoDTO } from "../../model/other-user-info.model";
import { SLPMUUserInfoDTO } from "../../model/slpmu-user-info.model";
import { UDHDUserInfoDTO } from "../../model/udhd-user-info.model";
import { ULBsInfoForOtherTypesOfUsers } from "../../model/ulb-info-for-other-user.model";
import { ULBInfoForULBUsersDTO } from "../../model/ulb-info-for-ulb-user.model";
import { ULBUserInfoDTO } from "../../model/ulb-user-info.model";
import { UserInfoDTO } from "../../model/user-mgt.model";
import { SettingsServerRouteConstants } from "../../settings-server-route-constant";
import { AuthEntity } from "../auth.model";

@Injectable()
export class UserMgtService {
    constructor(private http: HttpClient) { }

    findUserById(id: number): Observable<UserInfoDTO> {
        return this.http.get<UserInfoDTO>(SettingsServerRouteConstants.USER_MGT_BASE_URL + "/" + id);
    }

    getUserListByPage(data: PaginationModel): Observable<GenericResponseObject<UserInfoDTO>> {
        return this.http.post<GenericResponseObject<UserInfoDTO>>(SettingsServerRouteConstants.STNG_USER_MGT_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    createUpdateULBUser(requestData: ULBUserInfoDTO): Observable<ULBUserInfoDTO> {
        return this.http.post<ULBUserInfoDTO>(SettingsServerRouteConstants.STNG_USER_MGT_CREATE_UPDATE_ULB_USER, requestData);
    }

    createUpdateUDHDUser(requestData: UDHDUserInfoDTO): Observable<UDHDUserInfoDTO> {
        return this.http.post<UDHDUserInfoDTO>(SettingsServerRouteConstants.STNG_USER_MGT_CREATE_UPDATE_UDHD_USER, requestData);
    }

    createUpdateSLMPUUser(requestData: SLPMUUserInfoDTO): Observable<SLPMUUserInfoDTO> {
        return this.http.post<SLPMUUserInfoDTO>(SettingsServerRouteConstants.STNG_USER_MGT_CREATE_UPDATE_SLPMU_USER, requestData);
    }

    createUpdateOtherUser(requestData: OtherTypeUserInfoDTO): Observable<OtherTypeUserInfoDTO> {
        return this.http.post<OtherTypeUserInfoDTO>(SettingsServerRouteConstants.STNG_USER_MGT_CREATE_UPDATE_OTHER_USER, requestData);
    }

    getULBListForOtherUser(username: string): Observable<ULBsInfoForOtherTypesOfUsers> {
        return this.http.get<ULBsInfoForOtherTypesOfUsers>(SettingsServerRouteConstants.STNG_USER_MGT_GET_ULB_LIST_FOR_OTHER_USER + "/" + username);
    }

    getULBForULBUser(username: string): Observable<ULBInfoForULBUsersDTO> {
        return this.http.get<ULBInfoForULBUsersDTO>(SettingsServerRouteConstants.STNG_USER_MGT_GET_ULB_FOR_ULB_USER + "/" + username);
    }

    getULBList(): Observable<ULBDTO[]> {
        return this.http.get<ULBDTO[]>(SettingsServerRouteConstants.STNG_USER_MGT_GET_ULB_LIST);
    }

    getDivisionList(): Observable<GeoDivisionDTO> {
        return this.http.get<GeoDivisionDTO>(SettingsServerRouteConstants.STNG_USER_MGT_GET_GEO_DIVISION_LIST);
    }
    getDistrictListByDivisionId(id: number): Observable<GeoDistrictDTO> {
        return this.http.get<GeoDistrictDTO>(SettingsServerRouteConstants.STNG_USER_MGT_GET_GEO_DISTRICT_LIST_FOR_DIVISION + "/" + id);
    }
    getULBListByDistrictId(id: number, ulbType: string): Observable<ULBDTO[]> {
        return this.http.get<ULBDTO[]>(SettingsServerRouteConstants.STNG_USER_MGT_GET_GEO_ULB_LIST_FOR_DISTRICT + "/" + id + "/type/" + ulbType);
    }

    getAuthorityNameByAuthorityType(authorityType: string) {
      return this.http.get<AuthEntity[]>(SettingsServerRouteConstants.USER_MGT_AUTHORITY_URL + "/" + authorityType)
    }
   

}
