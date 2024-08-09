import { Component, OnInit, TemplateRef, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { votDTO } from 'src/app/audit/internal-audit/models/votDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'app-volume-of-transaction',
  templateUrl: './volume-of-transaction.component.html',
  styleUrls: ['./volume-of-transaction.component.css']
})

export class VolumeOfTransactionComponent implements OnInit {
  commentControl = new FormControl();
  volumnOfTransactionForm!: FormGroup
  isAddButtonDisabled = true;
  volumnOfTransaction: any
  votId: any;
  arrayOfKeys: any[] = [];
  transactionList = ["Period", "Opening Balance", "Receipts", "Total", "Net Expenditure", "Closing Balance"];

  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit() {
    this.initVolumnOfTransactionFormModel();
    let temp = this.transactionVolume.controls[0] as any
    this.arrayOfKeys = Object.keys(temp.controls);
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
      'total': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'netExpenditure': new FormControl(null, [Validators.required]),
      'closeExpenditure': new FormControl({ value: null, disabled: true })
    });
  }

  onSubmit(template: TemplateRef<any>) {
    
  }
  trnxId: any
  openingBalanceId: any
  receiptsId: any
  netExpenditureId: any
  save() {
    if (this.volumnOfTransactionForm.valid) {
      let transactions = this.transactionVolume.value;
      
      let trnx: votDTO = {
        id: this.trnxId != undefined ? this.trnxId: null,
        budgetFY: '',
        correspondingPrevYearFY: '',
        currentYearFY: '',
        iaId: 0,
        netExpenditure: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.netExpenditureId != undefined ? this.netExpenditureId: null,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        },
        openingBalance: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.openingBalanceId != undefined ? this.openingBalanceId: null,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        },
        previousYearFY: '',
        receipts: {
          budgetFY: 0,
          correspondingPrevYearFY: 0,
          cumulativeForCurrentPeriod: 0,
          currentYearFY: 0,
          id: this.receiptsId != undefined ? this.receiptsId: null,
          previousYearFY: 0,
          type: 'EXPENDITURE'
        }
      }
      trnx.iaId = this.service1.step1Report.id
      trnx.budgetFY = transactions[0].cummulativeForCurrentPeriod
      trnx.previousYearFY = transactions[1].cummulativeForCurrentPeriod
      trnx.correspondingPrevYearFY = transactions[2].cummulativeForCurrentPeriod
      trnx.currentYearFY = transactions[3].cummulativeForCurrentPeriod

      trnx.netExpenditure.id = this.votId!=undefined ? this.votId: null
      trnx.netExpenditure.budgetFY = transactions[0].netExpenditure
      trnx.netExpenditure.previousYearFY = transactions[1].netExpenditure
      trnx.netExpenditure.correspondingPrevYearFY = transactions[2].netExpenditure
      trnx.netExpenditure.currentYearFY = transactions[3].netExpenditure
      trnx.netExpenditure.cumulativeForCurrentPeriod = transactions[4].netExpenditure

      trnx.receipts.id= this.votId!=undefined ? this.votId: null
      trnx.receipts.budgetFY = transactions[0].receipt
      trnx.receipts.previousYearFY = transactions[1].receipt
      trnx.receipts.correspondingPrevYearFY = transactions[2].receipt
      trnx.receipts.currentYearFY = transactions[3].receipt
      trnx.receipts.cumulativeForCurrentPeriod = transactions[4].receipt

      trnx.openingBalance.id = this.votId!=undefined ? this.votId: null
      trnx.openingBalance.budgetFY = transactions[0].openingBalanace
      trnx.openingBalance.previousYearFY = transactions[1].openingBalanace
      trnx.openingBalance.correspondingPrevYearFY = transactions[2].openingBalanace
      trnx.openingBalance.currentYearFY = transactions[3].openingBalanace
      trnx.openingBalance.cumulativeForCurrentPeriod = transactions[4].openingBalanace
      this.service.createVOT(trnx).subscribe((res: any) => {
        this.votId = res.openingBalance.id
        this.trnxId = res.id
        this.openingBalanceId = res.openingBalance.id
        this.receiptsId = res.receipts.id
        this.netExpenditureId = res.netExpenditure.id

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
