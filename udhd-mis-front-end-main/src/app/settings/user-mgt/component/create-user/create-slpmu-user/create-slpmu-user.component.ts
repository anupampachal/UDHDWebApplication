import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedFormService } from '../../../services/shared-form.service';
import { UserMgtService } from '../../../services/user-mgt.service';
import { AuthEntity } from '../../../auth.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';

@Component({
  selector: 'app-create-slpmu-user',
  templateUrl: './create-slpmu-user.component.html',
  styleUrls: ['./create-slpmu-user.component.css'],
})
export class CreateSlpmuUserComponent implements OnInit {
  authList:AuthEntity[]=[];
  createSLPMUUserForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.createSLPMUUserForm = this.fb.group({
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
        
      });
      this.createSLPMUUserForm.addValidators(
        this.matchValidator(this.createSLPMUUserForm.controls['password'], 
        this.createSLPMUUserForm.controls['confirmPassword'])
      );
    }
  
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.userService.getAuthorityNameByAuthorityType('SLPMU').subscribe(res=>this.authList=res);
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

  onSubmit() {
    if (this.createSLPMUUserForm.valid) {
      this.userService
        .createUpdateSLMPUUser(this.createSLPMUUserForm.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createSLPMUUserForm.errors) {
      this.showFormError = this.createSLPMUUserForm.errors;
    }
  }

  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.createSLPMUUserForm.controls[controls]
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
