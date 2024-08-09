import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoginUserResponseDTO } from '../auth/model/login-response.model';
import { LoginService } from '../auth/services/login.service';
import { Principal } from '../auth/services/principal.service';
import { NavItem } from '../shared/model/nav-item.model';
import { NavService } from '../shared/service/nav.service';

@Component({
  selector: 'app-unauthorised',
  templateUrl: './unauthorised.component.html',
  styleUrls: ['./unauthorised.component.css']
})
export class UnauthorisedComponent implements OnInit {

  constructor(
  ) { }
  ngOnInit(): void {
  }

}
