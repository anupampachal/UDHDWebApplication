import { Component, OnInit, TemplateRef, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { votDTO } from 'src/app/audit/internal-audit/models/votDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'volume-of-transaction',
  templateUrl: './volume-of-transaction.component.html',
  styleUrls: ['./volume-of-transaction.component.css']
})

export class EditVolumeOfTransactionComponent implements OnInit {
  commentControl = new FormControl();
  volumnOfTransactionForm!: FormGroup
  isAddButtonDisabled = true;
  volumnOfTransaction: any
  arrayOfKeys: any[] = [];
  transactionList = ["Period", "Opening Balance", "Receipts", "Total", "Net Expenditure", "Closing Balance"];

  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private route: ActivatedRoute,
    private service1: Step1ReportService
  ) { }

  ngOnInit() {
    this.initVolumnOfTransactionFormModel();
    let temp = this.transactionVolume.controls[0] as any
    this.arrayOfKeys = Object.keys(temp.controls);

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.findVOTById(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => {},
        error => {}
      )
  }
  trnxId: any
  openingBalanceId: any
  receiptsId: any
  netExpenditureId: any
  setData(trnx: any) {
    // 
    let transactions = this.transactionVolume.value;
    this.trnxId = trnx.id
    this.openingBalanceId = trnx.openingBalance.id
    this.receiptsId = trnx.receipts.id
    this.netExpenditureId = trnx.netExpenditure.id


    transactions[0].cummulativeForCurrentPeriod = trnx.budgetFY
    transactions[1].cummulativeForCurrentPeriod = trnx.previousYearFY
    transactions[2].cummulativeForCurrentPeriod = trnx.correspondingPrevYearFY
    transactions[3].cummulativeForCurrentPeriod = trnx.currentYearFY
    transactions[0].netExpenditure = trnx.netExpenditure.budgetFY
    transactions[1].netExpenditure = trnx.netExpenditure.previousYearFY
    transactions[2].netExpenditure = trnx.netExpenditure.correspondingPrevYearFY
    transactions[3].netExpenditure = trnx.netExpenditure.currentYearFY
    transactions[4].netExpenditure = trnx.netExpenditure.cumulativeForCurrentPeriod
    transactions[0].receipt = trnx.receipts.budgetFY

    transactions[1].receipt = trnx.receipts.previousYearFY
    transactions[2].receipt = trnx.receipts.correspondingPrevYearFY
    transactions[3].receipt = trnx.receipts.currentYearFY
    transactions[4].receipt = trnx.receipts.cumulativeForCurrentPeriod
    transactions[0].openingBalanace = trnx.openingBalance.budgetFY
    transactions[1].openingBalanace = trnx.openingBalance.previousYearFY
    transactions[2].openingBalanace = trnx.openingBalance.correspondingPrevYearFY
    transactions[3].openingBalanace = trnx.openingBalance.currentYearFY
    transactions[4].openingBalanace = trnx.openingBalance.cumulativeForCurrentPeriod

    for (let i = 0; i < transactions.length; i++) {
      this.transactionVolume.at(i).patchValue(transactions[i])
    }
  }

  initVolumnOfTransactionFormModel() {
    this.volumnOfTransactionForm = this.formBuilder.group({
      'transactions': new FormArray([])
    });
    for (let i = 0; i < 5; i++) {
      (<FormArray>this.volumnOfTransactionForm.get('transactions')).push(this.createVolumnOfTransactionForm());
    }
  }

  get transactionVolume() {
    return this.volumnOfTransactionForm.controls['transactions'] as FormArray
  }

  getChangedValue() {
    if ((this.commentControl.value).length > 0) {
      this.isAddButtonDisabled = false;
    } else {
      this.isAddButtonDisabled = true;
    }
  }



  createVolumnOfTransactionForm(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl(null),
      'cummulativeForCurrentPeriod': new FormControl(null),
      'openingBalanace': new FormControl(null, [Validators.required]),
      'receipt': new FormControl(null, [Validators.required]),
      'total': new FormControl({ value: null, disabled: true },),
      'netExpenditure': new FormControl(null, [Validators.required]),
      'closeExpenditure': new FormControl({ value: null, disabled: true })
    });
  }

  onSubmit(template: TemplateRef<any>) {
    
  }

  save() {
    
    if (this.volumnOfTransactionForm.valid) {
      let transactions = this.transactionVolume.value;
      
      let trnx: votDTO = {
        id: 0,
        budgetFY: '',
        correspondingPrevYearFY: '',
        currentYearFY: '',
        iaId: 0,
        netExpenditure: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.netExpenditureId,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        },
        openingBalance: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.openingBalanceId,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        },
        previousYearFY: '',
        receipts: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.receiptsId,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        }
      }
      trnx.id = this.trnxId
      trnx.iaId = this.service1.step1Report.id
      trnx.budgetFY = transactions[0].cummulativeForCurrentPeriod
      trnx.previousYearFY = transactions[1].cummulativeForCurrentPeriod
      trnx.correspondingPrevYearFY = transactions[2].cummulativeForCurrentPeriod
      trnx.currentYearFY = transactions[3].cummulativeForCurrentPeriod
      trnx.netExpenditure.budgetFY = transactions[0].netExpenditure
      trnx.netExpenditure.previousYearFY = transactions[1].netExpenditure
      trnx.netExpenditure.correspondingPrevYearFY = transactions[2].netExpenditure
      trnx.netExpenditure.currentYearFY = transactions[3].netExpenditure
      trnx.netExpenditure.cumulativeForCurrentPeriod = transactions[4].netExpenditure
      trnx.receipts.budgetFY = transactions[0].receipt
      trnx.receipts.previousYearFY = transactions[1].receipt
      trnx.receipts.correspondingPrevYearFY = transactions[2].receipt
      trnx.receipts.currentYearFY = transactions[3].receipt
      trnx.receipts.cumulativeForCurrentPeriod = transactions[4].receipt
      trnx.openingBalance.budgetFY = transactions[0].openingBalanace
      trnx.openingBalance.previousYearFY = transactions[1].openingBalanace
      trnx.openingBalance.correspondingPrevYearFY = transactions[2].openingBalanace
      trnx.openingBalance.currentYearFY = transactions[3].openingBalanace
      trnx.openingBalance.cumulativeForCurrentPeriod = transactions[4].openingBalanace
      this.service.createVOT(trnx).subscribe(res => {
        this.volumnOfTransactionForm.disable()
      })
    }

  }
  enableForm() {
    this.volumnOfTransactionForm.enable()
  }
  onKeyUpSetCloseExpenditureAmount(index: number) {
    let control = this.transactionVolume.controls[index];
    var openingBalanceAmount = control.value.openingBalanace;
    var receiptsAmount = control.value.receipt;
    var netExpenditureAmount = control.value.netExpenditure;
    var total = openingBalanceAmount + receiptsAmount;
    var amount = openingBalanceAmount + receiptsAmount - netExpenditureAmount;
    control.patchValue({
      'closeExpenditure': amount,
      'total': total
    });
  }

}
