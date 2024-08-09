import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";

export class AuthUnAuthorisedInterceptor implements HttpInterceptor {

    constructor(private router: Router) { }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(tap(
            (_event: HttpEvent<any>) => { }
            , (err: any) => {
                if (err instanceof HttpErrorResponse && (err.status === 403)) {
                    this.router.navigate(['/home']);
                }
            }));
    }

}
