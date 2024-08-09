import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { GeoDistrictDTO } from 'src/app/shared/model/geo-district.model';
import { GeoDivisionService } from '../../geo-division/services/geo-division.service';
import { GeoDistrictService} from '../services/geo-district.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-district',
  templateUrl: './edit-district.component.html',
  styleUrls: ['./edit-district.component.css']
})
export class EditDistrictComponent implements OnInit {
  clicked = false;
  members$?: Observable<GeoDistrictDTO>;
  updateGeoDistrictForm!: FormGroup;
  routeArray: ActivatedRoute[] = [];
  //active:boolean=false;
  data?: GeoDistrictDTO;
  divisions:any;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private geoDistrictService: GeoDistrictService,
    private geoDivisionService: GeoDivisionService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.geoDivisionService.getGeoDivisionByPage({"criteria":"ALL","pageNo":0,"pageSize":100,"sortedBy":"name","directionOfSort":"ASCENDING"}).subscribe(response => {
      this.divisions = response.currentRecords.content
    })
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.geoDistrictService.findDistrictById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.updateGeoDistrictForm = this.fb.group({
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
        Validators.maxLength(10),
      ]),
      division: new FormControl(null, Validators.required),
      active: new FormControl(null, Validators.required),
    });
  }
 
  setData(dt:any){
    this.updateGeoDistrictForm.setValue({
      id: dt.id,
      name: dt.name,
      code: dt.code,
      active: dt.active,
      division: dt.division
    });
  }

  compareFn(x: GeoDistrictDTO, y: GeoDistrictDTO) {
    return x && y ? x.id === y.id : x === y;
  }


  onSubmit(){
    this.geoDistrictService
        .createUpdateGeoDistrict(this.updateGeoDistrictForm.getRawValue())
        .subscribe((res) =>{
          this._snackBar.open("Success", "District update successful", {
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
