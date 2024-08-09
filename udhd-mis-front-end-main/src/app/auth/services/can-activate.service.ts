import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from "@angular/router";
import {  Observable } from "rxjs";
import { map } from "rxjs/operators";
import { LoginUserResponseDTO } from "../model/login-response.model";
import { Principal } from "./principal.service";

@Injectable()
export class CanActivateGuard implements CanActivate{
  emptyUser: LoginUserResponseDTO = new LoginUserResponseDTO();
  constructor(private principal:Principal){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
  : boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.principal.getAuthenticationInfo()
    .pipe(map(user=>!!user.jwt && !!user.authority && !!route.data && route.data.authorities.indexOf(user.authority)>=0))


  }

}
