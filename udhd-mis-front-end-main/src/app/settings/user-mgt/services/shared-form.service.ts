import { Injectable } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { map, tap } from 'rxjs/operators';
import { UserMgtService } from './user-mgt.service';

@Injectable({
  providedIn: 'root'
})
export class SharedFormService {

  

  constructor(private fb: FormBuilder, private userMgtService: UserMgtService) {

  }

  sharedForm(): FormGroup {
    const fg = this.fb.group({
      username: new FormControl(null, [Validators.required]),
      name: new FormControl(null, [
        Validators.required,
        Validators.minLength(5),
      ]),
      authority: new FormControl(null, [Validators.required]),
      mobileNo: new FormControl(null, [Validators.required]),
      permanentAddress: new FormControl(null),
      enabled: new FormControl(false),
      mobileVerified: new FormControl(false),
      emailVerified: new FormControl(false),
      accountNonExpired: new FormControl(false),
      accountNonLocked: new FormControl(false),
      credentialsNonExpired: new FormControl(false),
    });

    return fg;
  }

  getAuthorityNames(userType:string) {
    return this.userMgtService.getAuthorityNameByAuthorityType(userType);
    //.subscribe(val => this.authorityNames = val)
  }
}
