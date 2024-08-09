import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginUserResponseDTO } from './auth/model/login-response.model';
import { LoginService } from './auth/services/login.service';
import { Principal } from './auth/services/principal.service';
import { NavItem } from './shared/model/nav-item.model';
import { NavService } from './shared/service/nav.service';
import { SideNavToggleService } from './shared/service/side-nav-toggle.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private principal:Principal,
    private loginService:LoginService,
    private sideNavToggle:SideNavToggleService,
    private router:Router, private navService: NavService) { }
  currentUser$!:Observable<LoginUserResponseDTO>;
  isSidenavToggled$!:Observable<boolean>;
  emptyUser: LoginUserResponseDTO = new LoginUserResponseDTO();
  isLoggedIn$!:Observable<boolean>;
  navItems: NavItem[]=[] ;
  isOpen=true;
  caroiselProducts=[
    {
      "img_src":"../../assets/images/demo1.jpeg",
      "product_name":"Demo1",
      "productsortdisc": "this is caroisel discription"
    },
    {
      "img_src":"../../assets/images/demo2.jpg",
      "product_name":"Demo2",
      "productsortdisc": "this is caroisel discription"
    },
    {
      "img_src":"../../assets/images/demo3.jpg",
      "product_name":"Demo3",
      "productsortdisc": "this is caroisel discription"
    }]
  ngOnInit(): void {
    this.isSidenavToggled$= this.sideNavToggle.getMinifiedState();
    this.navItems=this.navService.getNavItemForCustomer();
    this.currentUser$= this.principal.getAuthenticationInfo();
    this.isLoggedIn$= this.principal.isAuthenticated();
  }
  logout(){
    this.loginService.logout();
    //this.router.navigate(['auth','auth','login']);
    this.router.navigate(['landing']);
  }


}
