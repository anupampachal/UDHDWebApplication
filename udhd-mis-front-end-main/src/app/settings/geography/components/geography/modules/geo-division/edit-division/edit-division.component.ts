import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { GeoDivisionDTO } from 'src/app/shared/model/geo-division.model';
import { GeoDivisionService } from '../services/geo-division.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-division',
  templateUrl: './edit-division.component.html',
  styleUrls: ['./edit-division.component.css']
})
export class EditDivisionComponent implements OnInit {
  clicked = false;
  members$?: Observable<GeoDivisionDTO>;
  updateGeoDivisionForm!: FormGroup;
  routeArray: ActivatedRoute[] = [];

  data?: GeoDivisionDTO;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private geoDivisionService: GeoDivisionService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.geoDivisionService.findDivisionById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.updateGeoDivisionForm = this.fb.group({
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
      active: new FormControl(null, Validators.required),
    });
  }
  

  setData(dt:any){
    this.updateGeoDivisionForm.setValue({
      id: dt.id,
      name: dt.name,
      code: dt.code,
      active: dt.active,
    });
  }

  onSubmit(){
    this.geoDivisionService
        .createGeoDivision(this.updateGeoDivisionForm.getRawValue())
        .subscribe((res) =>{
          this._snackBar.open("Success", "Division update successful", {
            duration: 5000
          });
          this.router.navigate(['../../view/', res.id], {
            relativeTo: this.route,
          })
        },
        (err)=>{
          console.log(err);
          this._snackBar.open(err.error.status, err.error.message, {
            duration: 5000
          });
          this.clicked=false;
          
      });
  }
}
