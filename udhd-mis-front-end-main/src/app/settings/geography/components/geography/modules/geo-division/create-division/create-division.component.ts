import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { GeoDivisionService } from '../services/geo-division.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-division',
  templateUrl: './create-division.component.html',
  styleUrls: ['./create-division.component.css']
})
export class CreateDivisionComponent implements OnInit {

  clicked = false;
  createGeoDivision!: FormGroup;
  showFormError?: ValidationErrors;
  
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private geoDivisionService: GeoDivisionService,
    private _snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createGeoDivision = this.fb.group({
      name: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
      code: new FormControl(null, [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(10),
      ]),
      active: new FormControl(true, [Validators.required])
    });
  }

  matcher = new MyErrorStateMatcher();

  onSubmit() {
  

      this.geoDivisionService
        .createGeoDivision(this.createGeoDivision.getRawValue())
        .subscribe((res) =>{
          this._snackBar.open("Success", "Division create successful", {
            duration: 5000
          });
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        },
        (err)=>{
          console.log(err);
          let snackbarRef=this._snackBar.open(err.error.status, err.error.message, {
            duration: 5000
          });
          snackbarRef.afterDismissed().subscribe(() => {
            this.createGeoDivision.reset({
              name: null,
              code: null,
              active: true
            });
            this.clicked=false;
          });
      });
      
    
  }


}
