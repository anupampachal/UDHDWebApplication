import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, tap } from 'rxjs/operators';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { GeoDistrictService } from '../../geo-district/services/geo-district.service';
import { ULBService } from '../services/ulb.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-ulb',
  templateUrl: './create-ulb.component.html',
  styleUrls: ['./create-ulb.component.css']
})
export class CreateUlbComponent implements OnInit {
  clicked = false;
  districts: any;
  createULB!: FormGroup;
  showFormError?: ValidationErrors;
  //active:boolean=false;

  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ulbService: ULBService,
    private geoDistrictService: GeoDistrictService,
    private _snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.geoDistrictService.getGeoDistrictByPage({"criteria":"ALL","pageNo":0,"pageSize":500,"sortedBy":"name","directionOfSort":"ASCENDING"})
      .pipe(
        map((response: any) => response.currentRecords.content),
        tap(val => {
          this.districts = val
        })
      ).subscribe()

    this.routeArray = this.route.pathFromRoot;
    this.createULB = this.fb.group({
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
      aliasName: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required]),
      district: new FormControl(null, [Validators.required]),
      active: new FormControl(true, [Validators.required]),
    });
  }

  matcher = new MyErrorStateMatcher();

  onSubmit() {
   
      this.ulbService
        .createULB(this.createULB.getRawValue())
        .subscribe((res) =>{
            this._snackBar.open("Success", "ULB create successful", {
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
              this.createULB.reset({
                name: null,
                code: null,
                aliasName:null,
                type:null,
                active: true,
                district: null,
              });
              this.clicked=false;
            });
        }

        );
    
  }

  

}
