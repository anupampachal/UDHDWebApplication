import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { ACDCService } from '../../service/acdc.service';
import { ACDCULBBasedDTO } from '../model/ac-dc.model';

@Component({
  selector: 'app-update-acdc',
  templateUrl: './update-acdc.component.html',
  styleUrls: ['./update-acdc.component.css']
})
export class UpdateAcdcComponent implements OnInit {

  ulbs: ULBDTO[] = [];
  acdc$?: Observable<ACDCULBBasedDTO>;
  updateACDC!: FormGroup;
  routeArray: ActivatedRoute[] = [];
  active: boolean = false;
  data?: ACDCULBBasedDTO;
  selectedULB?: ULBDTO;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private acdcService: ACDCService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.acdcService.findACDCById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.updateACDC = this.fb.group({
      id: new FormControl({ value: null, disabled: true }, [
        Validators.required,
      ]),
      treasuryCode: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
      treasuryName: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(200),
      ]),
      financialYearStart: new FormControl(null, [
        Validators.required
      ]),
      financialYearEnd: new FormControl(null, [
        Validators.required
      ]),
      plannedNonPlanned: new FormControl(null, Validators.required),
      budgetCode: new FormControl(null, Validators.required),
      status: new FormControl(null, Validators.required),
      billNo: new FormControl(null, Validators.required),
      billDate: new FormControl(null, Validators.required),
      sanctionOrderNo: new FormControl(null, Validators.required),
      sanctionOrderDate: new FormControl(null, Validators.required),
      billTVNo: new FormControl(null, Validators.required),
      drawn: new FormControl(null, Validators.required),
      adjusted: new FormControl(null, Validators.required),
      unadjusted: new FormControl(null, Validators.required),
      ddoCode: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(10),
      ]),
      remarks: new FormControl(null, [
        Validators.minLength(3),
        Validators.maxLength(200),
      ]),
      ulb: new FormControl(null, [
        Validators.required,
      ]),
    });
  }
  setData(dt: ACDCULBBasedDTO) {
    this.data = dt;
    this.active = dt.status;
    this.selectedULB = dt.ulb;
    this.updateACDC.setValue({
      id: dt.id,
      treasuryCode: dt.treasuryCode,
      treasuryName: dt.treasuryName,
      financialYearStart: dt.financialYearStart,
      financialYearEnd: dt.financialYearEnd,
      plannedNonPlanned: dt.plannedNonPlanned,
      budgetCode: dt.budgetCode,
      status: dt.status,
      billNo: dt.billNo,
      billDate: dt.billDate,
      sanctionOrderNo: dt.sanctionOrderNo,
      sanctionOrderDate: dt.sanctionOrderDate,
      billTVNo: dt.billTVNo,
      drawn: dt.drawn,
      adjusted: dt.adjusted,
      unadjusted: dt.unadjusted,
      ddoCode: dt.ddoCode,
      remarks: dt.remarks,
      ulb: dt.ulb
    });
    this.acdcService.getULBList().subscribe(res => this.ulbs = res);
  }


  onSubmit() {
    let acdc: ACDCULBBasedDTO = new ACDCULBBasedDTO();
    if (this.updateACDC.valid && !!this.data && !!this.data.id) {

      acdc = this.updateACDC.value;
      acdc.id = this.data.id;
      this.acdcService.createACDC(acdc).subscribe((res) =>
        this.router.navigate(['../../view/', res.id], {
          relativeTo: this.route,
        })
      );
    }

  }
  compareFn(x: ULBDTO, y: ULBDTO) {
    return x && y ? x.id === y.id : x === y;
  }

}
