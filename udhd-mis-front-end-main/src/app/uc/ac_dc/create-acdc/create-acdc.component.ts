import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { ACDCService } from '../../service/acdc.service';
import * as moment from 'moment';
@Component({
  selector: 'app-create-acdc',
  templateUrl: './create-acdc.component.html',
  styleUrls: ['./create-acdc.component.css']
})
export class CreateAcdcComponent implements OnInit {
  acdc: any;
  checked = true;
  createACDC!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs:any;
  ulbType = ["MUNICIPAL_CORPORATION","MUNICIPAL_COUNCIL","NAGAR_PANCHAYAT"];  
  //ulbs$: Observable<ULBDTO[]> = of();
  divisions: any;  
  districts: any; 
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private acDcService: ACDCService,
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createACDC = this.fb.group({
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
      division:[null, Validators.required],    
      district:[null, Validators.required],     
      ulbType:[null, Validators.required],      
      ulb:[null, Validators.required],
    });
    this.acDcService.getDivisionList().subscribe(divisions => this.divisions = divisions)
    //this.ulbs$ = this.acDcService.getULBList();
  }
  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }

  getDistricts() {
    const divisionId = this.createACDC.value.division.id
    this.acDcService.getDistrictListByDivisionId(divisionId).subscribe(districts => this.districts = districts)
  }
  getUlbList() {
    const districtId = this.createACDC.value.district.id
    const ulbName = this.createACDC.value.ulbType
    this.acDcService.getULBListByDistrictId(districtId,ulbName).subscribe(ulbs => {
      this.ulbs = ulbs
    })
  }
  onSubmit() {
    if (this.createACDC.valid) {
      this.createACDC.value.financialYearStart = this.getDateInDDMMYYYY(this.createACDC.getRawValue().financialYearStart);
      this.createACDC.value.financialYearEnd = this.getDateInDDMMYYYY(this.createACDC.getRawValue().financialYearEnd);
      this.createACDC.value.billDate = this.getDateInDDMMYYYY(this.createACDC.getRawValue().billDate);
      this.createACDC.value.sanctionOrderDate = this.getDateInDDMMYYYY(this.createACDC.getRawValue().sanctionOrderDate);

      this.acDcService.createACDC
        (this.createACDC.value)
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createACDC.errors) {
      this.showFormError = this.createACDC.errors;
    }
  }

}
