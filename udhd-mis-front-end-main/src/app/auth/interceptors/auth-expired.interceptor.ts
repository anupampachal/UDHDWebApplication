import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injector } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { LoginService } from "../services/login.service";

export class AuthExpiredInterceptor implements HttpInterceptor{

  constructor(private injector:Injector){}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   return next.handle(req).pipe(tap(
     (_event:HttpEvent<any>)=>{}
     ,(err:any)=>{
       if(err instanceof HttpErrorResponse && (err.status===401 )){
         const loginService:LoginService=this.injector.get(LoginService);
         loginService.logout();
       }
     }));
  }

}
