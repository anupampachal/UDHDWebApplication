import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, tap, switchMap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'bank-reconcilation',
  templateUrl: './bank-reconcilation.component.html',
  styleUrls: ['./bank-reconcilation.component.css']
})

export class EditBankReconcilationComponent implements OnInit {
  bankReconciliationForm!: FormGroup;
  isAddButtonDisabled = true;
  commentControl = new FormControl();
  buttonText: string = "Save";
  bankReconIndex: number[] = []

  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.bankReconciliationForm = this.formBuilder.group({
      'bankReconciliations': new FormArray([
        this.createBankReconciliationForm(),
        this.createBankReconciliationForm(),
        this.createBankReconciliationForm()
      ])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getAllBankReconStatement(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe()
  }


  setData(brData: any) {
    iaId: this.service1.step1Report.id
    for (let i = 0; i < brData.length; i++) {
      if(this.bankReconcilationForm.controls[i] == undefined) {
        this.bankReconcilationForm.push(this.createBankReconciliationForm())
      }}
    for (let i = 0; i < brData.length; i++) {
      this.bankReconIndex[i] = brData[i].id
    let brd = this.bankReconcilationForm.value

      brd[i].bankName = brData[i].bankName
      brd[i].purposeOfBankAccount = brData[i].projectSchemeName
      brd[i].accountNumber = brData[i].accountNumber
      brd[i].balanceAsPerBankStatement = brData[i].bankBalancePerPassbook
      brd[i].balanceAsPerCashBook = brData[i].cashBalancePerCashbook
      brd[i].reconcilieEnum = brData[i].amountReconciled
      brd[i].brsStatus = brData[i].brsstatus
      brd[i].id = brData[i].id
      brd[i].iaId = brData[i].iaId
      this.bankReconcilationForm.at(i).patchValue(brd[i])
    }

  }
  createBankReconciliationForm() {
    return this.formBuilder.group({
      'iaId': new FormControl(null),
      'id': new FormControl(null),
      'bankName': new FormControl(null, [Validators.required]),
      'accountNumber': new FormControl(null, [Validators.required]),
      'purposeOfBankAccount': new FormControl(null, [Validators.required]),
      'balanceAsPerCashBook': new FormControl(null, [Validators.required]),
      'balanceAsPerBankStatement': new FormControl(null, [Validators.required]),
      'differences': new FormControl(0, [Validators.required]),
      'reconcilieEnum': new FormControl(null, [Validators.required]),
      'brsStatus': new FormControl(null, [Validators.required]),
    });
  }

  get bankReconcilationForm() {
    return this.bankReconciliationForm.controls['bankReconciliations'] as FormArray
  }

  pushNewForm() {
    (<FormArray>this.bankReconciliationForm.get('bankReconciliations')).push(this.createBankReconciliationForm());
  }

  save(index: number) {
    this.service.createUpdateBankReconciliation({
      iaId:this.service1.step1Report.id,
      id: this.bankReconIndex[index] != undefined ? this.bankReconIndex[index]: null,
      bankName: this.bankReconcilationForm.controls[index].value.bankName,
      projectSchemeName: this.bankReconcilationForm.controls[index].value.purposeOfBankAccount,
      accountNumber: this.bankReconcilationForm.controls[index].value.accountNumber,
      bankBalancePerPassbook: this.bankReconcilationForm.controls[index].value.balanceAsPerBankStatement,
      cashBalancePerCashbook: this.bankReconcilationForm.controls[index].value.balanceAsPerCashBook,
      amountReconciled: this.bankReconcilationForm.controls[index].value.reconcilieEnum,
      BRSStatus: this.bankReconcilationForm.controls[index].value.brsStatus
    }).subscribe((res: any) => {
      this.bankReconIndex[index] = res.id
      this.bankReconcilationForm.controls[index].disable()
    },
    error => {}
    )
  }

  enableControl(i: number) {
    this.bankReconcilationForm.controls[i].enable()
  }

  remove(index: number) {
    let bankReconciliations = <FormArray>this.bankReconciliationForm.controls['bankReconciliations'];
    let bankReconciliation = bankReconciliations['controls'][index].value;
    bankReconciliations.removeAt(index);
  }

}
