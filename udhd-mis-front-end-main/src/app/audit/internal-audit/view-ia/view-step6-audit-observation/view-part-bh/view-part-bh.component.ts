import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, catchError, map } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-view-part-bh',
  templateUrl: './view-part-bh.component.html',
  styleUrls: ['./view-part-bh.component.css']
})
export class ViewPartBHComponent implements OnInit {
  grantUtilizationFormModel!: FormGroup;
  selectedFile!: File | any;
  fileName!: any;
  showFileError!: string | any;
  totalAmount!: number;
  totalExpenseAmount!: number;
  totalPLAAmount!: number;
  totalPendingAmount!: number;
  totalSubmittedAmount!: number;
  isImage = false;
  isFile = false;
  iconDis = false

  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.grantUtilizationFormModel = this.fb.group({
      'utilizations': new FormArray([
        this.createGrantUtiliztionForm(),
        this.createGrantUtiliztionForm(),
        this.createGrantUtiliztionForm()
      ])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAllPartBHLineItems(param['id'])),
      tap((data) => {
        this.setData(data)
      }),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )
  }
  setData(dt: any) {
    for (let i = 0; i < dt.length; i++) {
      if (!this.utilizationsControl.controls[i]) {
        this.utilizationsControl.push(this.createGrantUtiliztionForm())
      }
      this.utilizationsControl.at(i).patchValue(dt[i])
      this.utilizationsControl.at(i).disable()
    }
  }

  get utilizationsControl(): FormArray {
    return this.grantUtilizationFormModel.get('utilizations') as FormArray
  }

  get FormGroupsArray(): FormGroup[] {
    return this.utilizationsControl.controls as FormGroup[]
  }

  createGrantUtiliztionForm() {
    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'schemeName': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'fundReceived': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'expenses': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'balanceInPLA': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'pendingUC': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'submittedUC': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'letterNoNDate': new FormControl({ value: null, disabled: true }, [Validators.required]),
    });
  }

  pushNewForm() {
    (<FormArray>this.grantUtilizationFormModel.get('utilizations')).push(this.createGrantUtiliztionForm());
  }

  remove(index: number) {
    let utilizations = <FormArray>this.grantUtilizationFormModel.controls['utilizations'];
    utilizations.removeAt(index);
  }
  getTotalAmount(index: number, item: any) {
    var fundReceived = this.utilizationsControl.controls[index].value.fundReceived;
    var expenses = this.utilizationsControl.controls[index].value.expenses
    var total = fundReceived - expenses;
    this.utilizationsControl.controls[index].patchValue({
      'balanceInPLA': total
    });

    var balanceInPLA = this.utilizationsControl.controls[index].value.valuebalanceInPLA;


    this.totalAmount = 0;
    this.totalExpenseAmount = 0;
    this.totalPLAAmount = 0;
    this.totalPendingAmount = 0;
    this.totalSubmittedAmount = 0;
    for (let item of this.FormGroupsArray) {
      var fundReceived = item.getRawValue().fundReceived;
      var expenses = item.getRawValue().expenses;
      var balanceInPLA = item.getRawValue().balanceInPLA;
      var pendingUC = item.getRawValue().pendingUC;
      var submittedUC = item.getRawValue().submittedUC;
      this.totalAmount += fundReceived;
      this.totalExpenseAmount += expenses;
      this.totalPLAAmount += balanceInPLA;
      this.totalPendingAmount += pendingUC;
      this.totalSubmittedAmount += submittedUC;

    }

  }

  downloadFile() {
    this.service.getPartBHFile(this.service1.step1Report.id).pipe(
      map(res => res),
      tap((res) => {
        if (res.size == 0) {
         
          this.service1.show("no File to Download","error", 3000)
        }
        else {
          this._FileSaverService.save(res, new Date().getTime() + '.pdf')
          this.service1.show("File Downloaded","success", 3000)

        }}
        ),
        catchError((_error)=> {
          this.service1.show("no File to Download","error", 3000)

          return of()
        })
    ).subscribe()
  }
}
