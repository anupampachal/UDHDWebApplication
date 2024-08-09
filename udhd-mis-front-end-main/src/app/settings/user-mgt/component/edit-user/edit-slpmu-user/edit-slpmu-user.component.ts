import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { SLPMUUserInfoDTO } from 'src/app/settings/model/slpmu-user-info.model';
import { SharedFormService } from '../../../services/shared-form.service';
import { UserMgtService } from '../../../services/user-mgt.service';
import { UserInfoDTO } from 'src/app/settings/model/user-mgt.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AuthEntity } from '../../../auth.model';

@Component({
  selector: 'app-edit-slpmu-user',
  templateUrl: './edit-slpmu-user.component.html',
  styleUrls: ['./edit-slpmu-user.component.css']
})
export class EditSlpmuUserComponent implements OnInit {
  authList:AuthEntity[]=[];
  updateSLPMUUser!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.updateSLPMUUser = this.fb.group({
        id: new FormControl({ value: null, disabled: true },[Validators.required]),
        username: new FormControl(null, [Validators.required,  Validators.email ,Validators.minLength(6),Validators.maxLength(60)]),
        name: new FormControl(null, [ Validators.required, Validators.minLength(3),Validators.maxLength(100)]),
        authority: new FormControl(null, [Validators.required]),
        mobileNo: new FormControl(null, [Validators.required,Validators.minLength(10),Validators.maxLength(10)]),
        permanentAddress: new FormControl(null),
        enabled: new FormControl(true, [Validators.required]),
        mobileVerified: new FormControl(true, [Validators.required]),
       // emailVerified: new FormControl(true, [Validators.required]),
        accountNonExpired: new FormControl(true, [Validators.required]),
        accountNonLocked: new FormControl(true, [Validators.required]),
        credentialsNonExpired: new FormControl(true, [Validators.required]),
      });
    }
  
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.userService.findUserById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.userService.getAuthorityNameByAuthorityType('SLPMU').subscribe(res=>this.authList=res);
  }

  onSubmit() {
    if (this.updateSLPMUUser.valid) {
      this.userService
        .createUpdateSLMPUUser(this.updateSLPMUUser.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.updateSLPMUUser.errors) {
      this.showFormError = this.updateSLPMUUser.errors;
    }
  }

  setData(res:UserInfoDTO){
    this.updateSLPMUUser.setValue({
      id: res.id ,
      username: res.username ,
      name: res.name ,
      authority: res.authority ,
      mobileNo: res.mobileNo ,
      permanentAddress : res.permanentAddress ,
      enabled: res.enabled ,
      mobileVerified: res.mobileVerified ,
     // emailVerified: res.emailVerified ,
      accountNonExpired: res.accountNonExpired ,
      accountNonLocked: res.accountNonLocked ,
      credentialsNonExpired: res.credentialsNonExpired ,

    });
  }
  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.updateSLPMUUser.controls[controls]
    if (control.hasError('required'))
      return 'This field is required';
    else if(control.hasError('email')) 
      return 'Invalid email';
    else if (control.hasError('minlength'))  
      return 'Below minimal length, please increase some characters';
    else if (control.hasError('maxlength'))  
      return 'Above max length, please decrease some characters.'
    else
      return 'Error'  ;
  }
}
