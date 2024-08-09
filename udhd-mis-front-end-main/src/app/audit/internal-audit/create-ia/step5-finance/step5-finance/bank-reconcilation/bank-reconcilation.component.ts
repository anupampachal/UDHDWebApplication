import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'app-bank-reconcilation',
  templateUrl: './bank-reconcilation.component.html',
  styleUrls: ['./bank-reconcilation.component.css']
})

export class BankReconcilationComponent implements OnInit {
  bankReconciliationForm!: FormGroup;
  isAddButtonDisabled = true;
  commentControl = new FormControl();
  buttonText: string = "Save";
  bankReconIndex: number[] = []

  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService
    ) { }

  ngOnInit() {
    this.bankReconciliationForm = this.formBuilder.group({
      'bankReconciliations': new FormArray([
        this.createBankReconciliationForm(),
        this.createBankReconciliationForm(),
        this.createBankReconciliationForm()
      ])
    });
  }

  createBankReconciliationForm() {
    return this.formBuilder.group({
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
        iaId: this.service1.step1Report.id,
        id: this.bankReconIndex[index] ? this.bankReconIndex[index] : null,
        bankName: this.bankReconcilationForm.controls[index].value.bankName,
        projectSchemeName: this.bankReconcilationForm.controls[index].value.purposeOfBankAccount,
        accountNumber: this.bankReconcilationForm.controls[index].value.accountNumber,
        bankBalancePerPassbook: this.bankReconcilationForm.controls[index].value.balanceAsPerBankStatement,
        cashBalancePerCashbook: this.bankReconcilationForm.controls[index].value.balanceAsPerCashBook,
        amountReconciled: this.bankReconcilationForm.controls[index].value.reconcilieEnum,
        BRSStatus:  this.bankReconcilationForm.controls[index].value.brsStatus=="true" ? true:false
    }).subscribe((res:any) => {
      this.bankReconIndex[index] = res.id
      this.bankReconcilationForm.controls[index].disable()
    })
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
