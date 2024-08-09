import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { GeoDistrictService } from '../../geo-district/services/geo-district.service';
import { ULBService } from '../services/ulb.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-ulb',
  templateUrl: './edit-ulb.component.html',
  styleUrls: ['./edit-ulb.component.css']
})
export class EditUlbComponent implements OnInit {
  clicked = false;
  members$?: Observable<ULBDTO>;
  updataUlbForm!: FormGroup;
  routeArray: ActivatedRoute[] = [];
  //active: boolean = false;
  data?: ULBDTO;
  districts: any;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ulbService: ULBService,
    private geoDistrictService: GeoDistrictService,
    private _snackBar: MatSnackBar

  ) { }

  ngOnInit(): void {
    this.geoDistrictService.getGeoDistrictByPage({ "criteria": "ALL", "pageNo": 0, "pageSize": 500, "sortedBy": "name", "directionOfSort": "ASCENDING" })
      .pipe(
        map((response: any) => response.currentRecords.content),
        tap(val => {
          this.districts = val
        })
      ).subscribe()
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.ulbService.findULBById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.updataUlbForm = this.fb.group({
      id: new FormControl({ value: null, disabled: true }, [
        Validators.required,
      ]),
      name: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
      code: new FormControl(null, [
        Validators.required,
        Validators.minLength(2),
      ]),
      aliasName: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required]),
      district: new FormControl(null, [
        Validators.required]),
      active: new FormControl(null, Validators.required),
    });
  }
  setData(dt: ULBDTO) {
    this.updataUlbForm.setValue({
      id: dt.id,
      name: dt.name,
      code: dt.code,
      aliasName: dt.aliasName,
      type: dt.type,
      district: dt.district,
      active: dt.active,
    });
  }

  compareFn(x: any, y: any) {
    return x && y ? x.id === y.id : x === y;
  }

  compareType(x: string, y: string) {
    return x && y ? x === y : false;
  }

  onSubmit() {
    

    this.ulbService
    .createULB(this.updataUlbForm.getRawValue())
    .subscribe((res) =>{
      this._snackBar.open("Success", "Ulb update successful", {
        duration: 5000
      });
      this.router.navigate(['../../view/', res.id], {
        relativeTo: this.route,
      })
      
    }
      ,
    (err)=>{
      console.log(err);
      this._snackBar.open(err.error.status, err.error.message, {
        duration: 5000
      });
      this.clicked=false;
      
  });


  }

}
