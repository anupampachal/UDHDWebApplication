import { EventEmitter, Injectable } from "@angular/core";
import { filter, map, switchMap, tap } from "rxjs/operators";
import { LoginUserResponseDTO } from "../model/login-response.model";
import * as moment from "moment";

import { environment } from "src/environments/environment";
import { BehaviorSubject, Observable, of, } from "rxjs";


const emptyUser: LoginUserResponseDTO = new LoginUserResponseDTO();

@Injectable()
export class Principal {
  private authenticationState = new BehaviorSubject<LoginUserResponseDTO>(emptyUser);
  private userIdentity$: Observable<LoginUserResponseDTO> = this.authenticationState.asObservable();
  constructor() {
    this.initialize();
  }

  initialize(){
    if(!this.isLoggedIn()){
      this.authenticationState.next(emptyUser)
    }
    else{
      const userInfo: LoginUserResponseDTO= new LoginUserResponseDTO();
      userInfo.authority=localStorage.getItem('authority')||'';
      userInfo.jwt= localStorage.getItem("id_token")||'';
      userInfo.username= localStorage.getItem('username')||'';
      userInfo.name= localStorage.getItem('name')||'';

      this.authenticationState.next(userInfo);
    }

  }
  private setSession(userInfo: LoginUserResponseDTO) {
    //this.authenticate(userInfo);
    const expiresAt = moment().add(environment.expiresInSeconds, 'seconds');
    localStorage.setItem('id_token', userInfo.jwt?userInfo.jwt:'');
    localStorage.setItem('username', userInfo.username?userInfo.username:'');
    localStorage.setItem('authority', userInfo.authority?userInfo.authority:'');
    localStorage.setItem('name', userInfo.name?userInfo.name:'');
   // localStorage.setItem('expires_at', expiresAt.toString());
   localStorage.setItem('expires_at', JSON.stringify(expiresAt));
  }
  //to set new authentication value
  authenticate(userIdentity: LoginUserResponseDTO) {
   // this.authenticationState.next(userIdentity);
    this.setSession(userIdentity);
    this.initialize();
  }

  //check if the current user has any of the authorities mentioned in the array
  hasAnyAuthority(authorities: string[]): Promise<boolean> {
   return this.getAuthenticationInfo().toPromise().then(user=> authorities.indexOf(user.authority)>-1);
  }

  //check if the current user has the single authority in test
  hasAuthority(authority: string): Promise<boolean> {
    return this.userIdentity$.toPromise().then(user => user.authority === authority);
  }

  //find out if the user is authenticated or not
  isAuthenticated(): Observable<boolean> {
    //return this.userIdentity$.pipe(map(user => !!user.jwt && user.jwt.trim()!=''));
    return this.authenticationState.asObservable().pipe(map(res=>!!res.jwt));
  }

  //gets the currently authenticated user
  getAuthenticationInfo(): Observable<LoginUserResponseDTO> {
    
      if(this.isLoggedIn())
        return this.authenticationState.asObservable();
      else
        return of(emptyUser)
  }


  isLoggedIn(): boolean {
    const expiration = localStorage.getItem("expires_at");
    if (expiration) {
      const expiresAt = JSON.parse(expiration);
      return moment().isBefore(moment(expiresAt));
    } else {
      this.logout();
    }
    return false;
  }
  logout() {
    this.authenticationState.next(emptyUser);
    localStorage.removeItem("id_token");
    localStorage.removeItem("expires_at");
    localStorage.removeItem('authority')!;
    localStorage.removeItem('username')!;
    localStorage.removeItem('name')!;

  }
}
