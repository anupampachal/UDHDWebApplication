import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { MyProfileRequestDTO } from "../../model/myprofile-request.model";
import { MyProfileResponseDTO } from "../../model/myprofile-response.model";
import { SettingsServerRouteConstants } from "../../settings-server-route-constant";

@Injectable()
export class MyProfileMgtService {
    constructor(private http: HttpClient) { }
    /**Gets current user profile information */
    getMyProfileInformation(): Observable<MyProfileResponseDTO> {
        return this.http.get<MyProfileResponseDTO>(SettingsServerRouteConstants.STNG_MY_PROFILE_BASE_URL);
    }
    /**Update my profile information */
    updateMyProfileInformation(requestInfo: MyProfileRequestDTO): Observable<MyProfileResponseDTO> {
        return this.http.put<MyProfileResponseDTO>(SettingsServerRouteConstants.STNG_MY_PROFILE_BASE_URL, requestInfo);
    }
    /**Upload user image */
    uploadImageFile(file: File): Observable<MyProfileResponseDTO> {
        const formdata: FormData = new FormData()
        formdata.append('file', file);
        return this.http.patch<MyProfileResponseDTO>(SettingsServerRouteConstants.STNG_MY_PROFILE_BASE_URL,
            formdata, { reportProgress: true }
        )
    }
    /**User image download */
    getMyProfileImage() {
        return this.http.get(SettingsServerRouteConstants.STNG_MY_PROFILE_GET_PROFILE_LOGO, { responseType: 'blob' });
    }

}
