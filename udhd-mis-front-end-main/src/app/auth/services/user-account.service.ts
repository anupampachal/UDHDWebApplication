import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Observable } from "rxjs";
import { LoginUserResponseDTO } from "../model/login-response.model";
import { environment } from "src/environments/environment";

@Injectable()
export class UserAccountService{
    private apiUrl= environment.serverUrl;
    constructor(private http:HttpClient){}


    getUserAccountInfo():Observable<LoginUserResponseDTO>{
        return this.http.get<LoginUserResponseDTO>(this.apiUrl+'/account-info');
    }
}