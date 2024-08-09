import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable, Injector } from "@angular/core";
import * as moment from "moment";
import { Observable } from "rxjs";
import { filter,  map, mergeMap } from "rxjs/operators";
import { Principal } from "../services/principal.service";
const HEADER_NAME = "Authorization";
@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    var requestMethod: string = req.method.toLowerCase();
    const principal: Principal = this.injector.get(Principal);
    if (this.getIfTokenIsExpired()) {
      return principal.getAuthenticationInfo().pipe(
        filter(() => this.getIfTokenIsExpired()),
        map(auth => auth.jwt),
        map(jwt => this.getClonedRequest(req, jwt)),
        mergeMap(req => next.handle(req))
      );
    }

   return next.handle(req);
  }

  getClonedRequest(req: HttpRequest<any>, jwt?: string) {
    if (!!jwt)
      return req.clone({ headers: req.headers.set(HEADER_NAME,  "Bearer "+jwt) });

    return req;
  }

  getIfTokenIsExpired(): boolean {
    const expiration = localStorage.getItem("expires_at");
    if (expiration) {
      const expiresAt = JSON.parse(expiration);
      return moment().isBefore(moment(expiresAt));
    }
    return false;
  }

}


