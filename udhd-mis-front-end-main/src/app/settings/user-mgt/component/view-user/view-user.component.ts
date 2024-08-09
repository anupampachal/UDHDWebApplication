import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { UserInfoDTO } from 'src/app/settings/model/user-mgt.model';
import { UserMgtService } from '../../services/user-mgt.service';

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.css']
})
export class ViewUserComponent implements OnInit {
  user$?: Observable<UserInfoDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  authorityType="";
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userMgtService: UserMgtService
  ) { }

    ngOnInit(): void {
      this.routeArray = this.route.pathFromRoot;
      this.user$ = this.route.params.pipe(
        filter((params) => params && params['id']),
        tap((params) => (this.id = params['id'])),
        switchMap((param) => this.userMgtService.findUserById(param['id'])),
        tap(user => this.authorityType = user.authorityType),
        shareReplay(2)
      );
  }
  

}
