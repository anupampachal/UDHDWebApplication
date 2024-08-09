import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSlideToggleRequiredValidator } from '@angular/material/slide-toggle';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { GeoDivisionService } from '../../geo-division/services/geo-division.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-district',
  templateUrl: './create-district.component.html',
  styleUrls: ['./create-district.component.css']
})
export class CreateDistrictComponent implements OnInit {
  clicked = false;
  divisions: any
  createGeoDistrict!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private geoDistrictService: GeoDistrictService,
    private geoDivisonService: GeoDivisionService,
    private _snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createGeoDistrict = this.fb.group({
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
      division: new FormControl(null, [
        Validators.required
      ]),
     active: new FormControl(true,[ Validators.required]),
    });

    this.geoDivisonService.getGeoDivisionByPage({ "criteria": "ALL", "pageNo": 0, "pageSize": 10, "sortedBy": "name", "directionOfSort": "ASCENDING" }).subscribe(response => {
      this.divisions = response.currentRecords.content
    })
  }

  matcher = new MyErrorStateMatcher();
  
  onSubmit() {
    
      this.geoDistrictService
        .createUpdateGeoDistrict(this.createGeoDistrict.getRawValue())
        .subscribe((res) =>{
          this._snackBar.open("Success", "District create successful", {
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
              this.createGeoDistrict.reset({
                name: null,
                code: null,
                active: true,
                division: null,
              });
              this.clicked=false;
            });
        }
        );
    
  }


}
