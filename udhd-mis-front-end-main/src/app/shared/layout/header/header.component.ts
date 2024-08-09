import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { LoginUserResponseDTO } from "src/app/auth/model/login-response.model";
import { LoginService } from "src/app/auth/services/login.service";
import { Principal } from "src/app/auth/services/principal.service";
import { SideNavToggleService } from "../../service/side-nav-toggle.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  userInfo$!: Observable<LoginUserResponseDTO>;
  constructor(private loginService: LoginService, private principal: Principal,
    private sidenavToggleService: SideNavToggleService,
    private router: Router) { }

  ngOnInit(): void {
    this.userInfo$ = this.principal.getAuthenticationInfo();
  }
  logout() {
    this.loginService.logout();
    //window.location.reload();
  }
  onBtnPress() {
    this.sidenavToggleService.setMinifiedSubjectState();
  }
}
