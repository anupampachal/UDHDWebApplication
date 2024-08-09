import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap } from 'rxjs/operators';
import { MyProfileResponseDTO } from '../../model/myprofile-response.model';
import { MyProfileMgtService } from '../services/my-profile-mgt.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  myProfileRes$!: Observable<MyProfileResponseDTO>;
  routeArray: ActivatedRoute[] = [];
  logourl$:any;
  constructor(private myProfileService: MyProfileMgtService,  private _sanitizer: DomSanitizer,private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.myProfileRes$ = this.myProfileService.getMyProfileInformation();
    this.myProfileRes$.pipe(filter(res=>!!res.fileId),
                                switchMap(res=>this.myProfileService.getMyProfileImage()),
                                tap(res=>{
                                  const file=new Blob([res],{type:'image/jpeg'});
                                  this.logourl$=this._sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file))
                                })).subscribe();
  }


}
