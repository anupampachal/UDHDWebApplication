import { HttpClient } from "@angular/common/http";
import { Injectable, EventEmitter } from "@angular/core";
import { Observable, of } from "rxjs";
import * as moment from "moment";
import { tap } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { LoginEmailRequestDTO } from "../model/login-request-email.model";
import { LoginUserResponseDTO } from "../model/login-response.model";
import { Router } from "@angular/router";
import { Principal } from "./principal.service";


@Injectable()
export class LoginService {
    private apiUrl = environment.serverUrl;
    constructor(private http: HttpClient, private router: Router, private principal: Principal) { }

    login(loginEmailReqDTO: LoginEmailRequestDTO): Observable<LoginUserResponseDTO> {
        return this.http.post<LoginUserResponseDTO>(this.apiUrl + "/auth", loginEmailReqDTO)
            .pipe(tap(userLogin => this.principal.authenticate(userLogin)));
    }

    logout() {
       
        this.principal.logout();
        this.router.navigate(['/auth/auth/login']);
        
    }


}
