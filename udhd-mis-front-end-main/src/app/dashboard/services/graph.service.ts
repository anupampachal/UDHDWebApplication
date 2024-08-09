import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { DashboardDTO } from "../models/dashboard-chart-dto";

@Injectable({
    providedIn: 'root'
})
export class GraphService {
    public static BASE_URL = environment.serverUrl;
    public static DASHBOARD_URL = GraphService.BASE_URL + '/dashboard'
    constructor(private http: HttpClient) { }

    getDashboardData(): Observable<DashboardDTO> {
        return this.http.get<DashboardDTO>(GraphService.DASHBOARD_URL)
    }

}