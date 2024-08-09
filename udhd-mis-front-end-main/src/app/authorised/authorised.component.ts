import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoginUserResponseDTO } from '../auth/model/login-response.model';
import { LoginService } from '../auth/services/login.service';
import { Principal } from '../auth/services/principal.service';
import { NavItem } from '../shared/model/nav-item.model';
import { NavService } from '../shared/service/nav.service';
import { SideNavToggleService } from '../shared/service/side-nav-toggle.service';

@Component({
  selector: 'app-authorised',
  templateUrl: './authorised.component.html',
  styleUrls: ['./authorised.component.css']
})
export class AuthorisedComponent implements OnInit {
  constructor(private loginService:LoginService,private sidenavToggleService:SideNavToggleService,
    private principal: Principal,private router:Router, private navService: NavService) { }
  currentUser$!:Observable<LoginUserResponseDTO>;
  emptyUser: LoginUserResponseDTO = new LoginUserResponseDTO();
  sidenavToggled$?:Observable<boolean>;
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
    this.navItems=this.navService.getNavItemForCustomer();
    this.currentUser$= this.principal.getAuthenticationInfo();
    this.currentUser$.subscribe(
      res => {
        if (!!res.username) {
          this.router.navigate(['home', 'dashboard-home']);
        }
      }
    )

    this.sidenavToggled$ =this.sidenavToggleService.getMinifiedState();
  }
  logout(){
    this.loginService.logout();
    this.router.navigate(['auth','auth','login']);
  }


}
