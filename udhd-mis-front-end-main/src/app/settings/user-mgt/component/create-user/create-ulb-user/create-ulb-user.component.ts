import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserMgtService } from '../../../services/user-mgt.service';
import { AuthEntity } from '../../../auth.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';

@Component({
  selector: 'app-create-ulb-user',
  templateUrl: './create-ulb-user.component.html',
  styleUrls: ['./create-ulb-user.component.css']
})
export class CreateUlbUserComponent implements OnInit {
  authList:AuthEntity[]=[];
  createULBUserForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  divisions: any;  
  districts: any;  
  ulbType = ["MUNICIPAL_CORPORATION","MUNICIPAL_COUNCIL","NAGAR_PANCHAYAT"];  
  ulbList: any;
  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.createULBUserForm = this.fb.group({
        username: new FormControl(null, [Validators.required,  Validators.email ,Validators.minLength(6),Validators.maxLength(60)]),
        name: new FormControl(null, [ Validators.required, Validators.minLength(3),Validators.maxLength(100)]),
        password: new FormControl(null, [ Validators.required, Validators.minLength(8),Validators.maxLength(20)]),
        confirmPassword: new FormControl(null, [ Validators.required, Validators.minLength(8),Validators.maxLength(20)]),
        authority: new FormControl(null, [Validators.required]),
        mobileNo: new FormControl(null, [Validators.required,Validators.minLength(10),Validators.maxLength(10)]),
        permanentAddress: new FormControl(null),
        enabled: new FormControl(true, [Validators.required]),
        mobileVerified: new FormControl(true, [Validators.required]),
        emailVerified: new FormControl(true, [Validators.required]),
        accountNonExpired: new FormControl(true, [Validators.required]),
        accountNonLocked: new FormControl(true, [Validators.required]),
        credentialsNonExpired: new FormControl(true, [Validators.required]),
        division:[null, Validators.required],    
        district:[null, Validators.required],     
        ulbType:[null, Validators.required],      
        ulbId:[null, Validators.required],
      });
      this.createULBUserForm.addValidators(
        this.matchValidator(this.createULBUserForm.controls['password'], 
        this.createULBUserForm.controls['confirmPassword'])
      );
    }
  
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.userService.getAuthorityNameByAuthorityType('ULB').subscribe(res=>this.authList=res);
    this.userService.getDivisionList().subscribe(divisions => this.divisions = divisions)
  }
  matchValidator(
    control: AbstractControl,
    controlTwo: AbstractControl
  ): ValidatorFn {
    return () => {
      if (control.value !== controlTwo.value)
        return { 'Password':'Password and confirm password should match' };
      return null;
    };
  }

  getDistricts() {
    const divisionId = this.createULBUserForm.value.division.id
    this.userService.getDistrictListByDivisionId(divisionId).subscribe(districts => this.districts = districts)
  }
  getUlbList() {
    const districtId = this.createULBUserForm.value.district.id
    const ulbName = this.createULBUserForm.value.ulbType
    this.userService.getULBListByDistrictId(districtId,ulbName).subscribe(ulbs => {
      this.ulbList = ulbs
    })
  }
  onSubmit() {
    if (this.createULBUserForm.valid) {
      this.userService
        .createUpdateULBUser(this.createULBUserForm.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createULBUserForm.errors) {
      this.showFormError = this.createULBUserForm.errors;
    }
  }

  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.createULBUserForm.controls[controls]
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
