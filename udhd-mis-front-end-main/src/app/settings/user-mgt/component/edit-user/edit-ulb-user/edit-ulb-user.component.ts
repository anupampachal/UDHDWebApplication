import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AuthEntity } from '../../../auth.model';
import { UserMgtService } from '../../../services/user-mgt.service';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { UserInfoDTO } from 'src/app/settings/model/user-mgt.model';
import { ULBUserInfoDTO } from 'src/app/settings/model/ulb-user-info.model';
import { GeoDistrictDTO } from 'src/app/shared/model/geo-district.model';

@Component({
  selector: 'app-edit-ulb-user',
  templateUrl: './edit-ulb-user.component.html',
  styleUrls: ['./edit-ulb-user.component.css']
})
export class EditUlbUserComponent implements OnInit {

  authList:AuthEntity[]=[];
  updateULBUserForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  divisions: any;  
  districts: any;
  selectedDistrict?:GeoDistrictDTO;  
  ulbType = ["MUNICIPAL_CORPORATION","MUNICIPAL_COUNCIL","NAGAR_PANCHAYAT"];  
  ulbList: any;
  user?:ULBUserInfoDTO;
  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.updateULBUserForm = this.fb.group({
        id: new FormControl({value:null, disabled:true}, [Validators.required]),
        username: new FormControl(null, [Validators.required,  Validators.email ,Validators.minLength(6),Validators.maxLength(60)]),
        name: new FormControl(null, [ Validators.required, Validators.minLength(3),Validators.maxLength(100)]),
        authority: new FormControl(null, [Validators.required]),
        mobileNo: new FormControl(null, [Validators.required,Validators.minLength(10),Validators.maxLength(10)]),
        permanentAddress: new FormControl(null),
        enabled: new FormControl(true, [Validators.required]),
        mobileVerified: new FormControl(true, [Validators.required]),
        emailVerified: new FormControl(true, [Validators.required]),
        accountNonExpired: new FormControl(true, [Validators.required]),
        accountNonLocked: new FormControl(true, [Validators.required]),
        credentialsNonExpired: new FormControl(true, [Validators.required]),
            
        ulbId:[null, Validators.required],
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

    this.userService.getAuthorityNameByAuthorityType('ULB').subscribe(res=>this.authList=res);
    this.userService.getDivisionList().subscribe(divisions => this.divisions = divisions)
  }


  compareWithFunc = (a: any, b: any) => a == b;


  setData(res:UserInfoDTO){
    this.user=res;
    
     
    this.updateULBUserForm.setValue({
      id: res.id ,
      username: res.username ,
      name: res.name ,
      authority: res.authority ,
      mobileNo: res.mobileNo ,
      permanentAddress : res.permanentAddress ,
      enabled: res.enabled ,
      mobileVerified: res.mobileVerified ,
      emailVerified: res.enabled ,
      accountNonExpired: res.accountNonExpired ,
      accountNonLocked: res.accountNonLocked ,
      credentialsNonExpired: res.credentialsNonExpired ,
     
      ulbId:!!res.ulbDetails?res.ulbDetails[0].id:null,
    });
  }
  setDistrict(event:any){
    this.selectedDistrict=event.value;
  }
  getDistricts(event:any) {
    this.userService.getDistrictListByDivisionId(event.value.id).subscribe(districts => this.districts = districts)
  }
  getUlbList(event:any) {
    let ulbType=event.value;
    //const ulbName = this.updateULBUserForm.value.ulbType
    if(!!this.selectedDistrict && !!this.selectedDistrict.id)
      this.userService.getULBListByDistrictId(this.selectedDistrict.id,ulbType).subscribe(ulbs => {
        this.ulbList = ulbs
      })
  }

  onNewULBSelect(event: any){
    this.updateULBUserForm.patchValue({ulbId:event.value});
  }
  onSubmit() {
    
      this.userService
        .createUpdateULBUser(this.updateULBUserForm.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
            relativeTo: this.route,
          })
        );
    
  }

  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.updateULBUserForm.controls[controls]
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
