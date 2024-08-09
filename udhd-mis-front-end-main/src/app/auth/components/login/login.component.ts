import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, tap } from 'rxjs/operators';
import { LoginEmailRequestDTO } from '../../model/login-request-email.model';
import { LoginService } from '../../services/login.service';
import { Principal } from '../../services/principal.service';
//declare var grecaptcha: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  message!: string;
  loginForm!: FormGroup;
  disabled = false;
  loginErrorDisplay='';
  captchaError: boolean = false;
  constructor(private loginService: LoginService, private principal: Principal, private router: Router,
    private route: ActivatedRoute) { }

  home() {
    this.router.navigate(['landing']);
  }
  ngOnInit(): void {
    /*if(this.loginService.isLoggedIn()){
      this.router.navigate(['home']);
    }*/

    this.principal.isAuthenticated().pipe(
      filter(isLoggedIn => isLoggedIn === true),
      tap(() => this.router.navigate(['home']))
    ).subscribe();
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(8), Validators.maxLength(24)]),
      //'recaptcha': new FormControl(null, [Validators.required])
    });

  }


  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.disabled = true;
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = this.loginForm.get('email')?.value;
    loginRequest.password = this.loginForm.get('password')?.value;
    //loginRequest.recaptcha=this.loginForm.get('recaptcha')?.value;
    this.loginService.login(loginRequest)
      .subscribe(
        (user) => {
          this.router.navigateByUrl('/home/dashboard-home/income-expense');
          window.location.reload();
        },
        (error)=>{
          this.loginErrorDisplay='Invalid login credentials';
          setTimeout(() => {
              this.loginErrorDisplay = '';
              this.loginForm.patchValue({'email':'','password':''});
          }, 4000);
        }
      )
      ;
  }

  ulblogin() {
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = 'ulbaccountant@scsinfinity.com';
    loginRequest.password = 'password';
    this.specialLogin(loginRequest);
  }

  specialLogin(info: LoginEmailRequestDTO) {
    this.loginService.login(info)
      .subscribe(
        (user) => {
         
          this.router.navigate(['home']);
         // grecaptcha.reset();
        }


      )
      ;
  }

  /*slpmulogin() {
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = 'slpmuit@scsinfinity.com';
    loginRequest.password = 'password';
    this.specialLogin(loginRequest);
  }
  ialogin() {
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = 'ia@scsinfinity.com';
    loginRequest.password = 'password';
    this.specialLogin(loginRequest);
  }
  flialogin() {
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = 'flia@scsinfinity.com';
    loginRequest.password = 'password';
    this.specialLogin(loginRequest);
  }

  slpmuLogin() {
    const loginRequest = new LoginEmailRequestDTO();
    loginRequest.email = 'slpmuadmin@scsinfinity.com';
    loginRequest.password = 'password';
    this.specialLogin(loginRequest);
  }*/
}
