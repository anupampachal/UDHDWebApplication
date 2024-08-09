import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { revCapExpDTO } from 'src/app/audit/internal-audit/models/revCapExpDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'revenue-and-capital-expenditure',
  templateUrl: './revenue-and-capital-expenditure.component.html',
  styleUrls: ['./revenue-and-capital-expenditure.component.css']
})
export class EditRevenueAndCapitalExpenditureComponent implements OnInit {
  @Input() reportId!: number;
  revenueCapitalExpenditureForm!: FormGroup;
  arrayOfKeys: any[] = [];
  secondLayerOfKeys: any[] = [];
  revCapExpId: any;
  subList1 = ["Adminstrative Expenses,Establishment and Salaries (All Departments-Regular and Contractual Staffs)", "Operation and Maintenance (O&M)", "Loan repayment (Interest payments)", "Others(any other revenue expenditure which is not salaries,O&M or Interest Payment)"
  ];
  subList2 = ["All developmental works under Central/State specific schemes", "Loan Repayments(Prinicipal Amount)", "Other Capital expenditure"
  ];
  expenditureList = ["Details", "Total Expenditure (1+2)",
    "Revenue Expenditure", "Adminstrative Expenses,Establishment and Salaries (All Departments-Regular and Contractual Staffs)", "Operation and Maintenance (O&M)", "Loan repayment (Interest payments)", "Others(any other revenue expenditure which is not salaries,O&M or Interest Payment)",
    "Capital Expenditure", "All developmental works under Central/State specific schemes", "Loan Repayments(Prinicipal Amount)", "Other Capital expenditure"
  ];
  serialNo = ["S.No.", "", "1", "1.1", "1.2", "1.3", "1.4", "2", "2.1", "2.2", "2.3"]

  expenditures: any[] = [];
  isDisabled!: boolean;
  alerts = new Set();
  expendId: number = 0;
  detailsId = null
  administrativeExpensesId = null
  operationAndMaintenanceId = null
  loanRepaymentId = null
  otherExpenditureId = null
  developmentWorksId = null
  otherCapitalExpenditureId = null
  constructor(private fb: FormBuilder,
    private service: Step5Service,
    private route: ActivatedRoute,
    private service1: Step1ReportService
  ) { }
  ngOnInit() {
    this.initRevenueCapitalExpenditureForm();
    let temp = this.revenueCapitalReceipt.controls[0] as any
    this.arrayOfKeys = Object.keys(temp.controls['expenditures']['controls'][0].controls)
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getRevenueCapitalExByIaId(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => {},
        error => {}
      )
  }
  currId: any;
  setData(expData: any) {
    let formVal = this.revenueCapitalReceipt.value;
    this.currId = expData.id
    this.detailsId = expData.details.id
    this.administrativeExpensesId = expData.administrativeExpenses.id
    this.operationAndMaintenanceId = expData.operationAndMaintenance.id
    this.loanRepaymentId = expData.loanRepayment.id
    this.otherExpenditureId = expData.otherExpenditure.id
    this.developmentWorksId = expData.developmentWorks.id
    this.otherCapitalExpenditureId = expData.otherCapitalExpenditure.id

    formVal[0].financeYear = expData.fy1L
    formVal[1].financeYear = expData.fy2L
    formVal[2].financeYear = expData.fy3L

    formVal[0].expenditures[0].year = expData.details.fy1
    formVal[0].expenditures[1].year = expData.details.fy2
    formVal[1].expenditures[0].year = expData.details.fy3
    formVal[1].expenditures[1].year = expData.details.fy4
    formVal[2].expenditures[0].year = expData.details.fy5
    formVal[2].expenditures[1].year = expData.details.fy6

    formVal[0].expenditures[0].administrativeExpenseAmount = expData.administrativeExpenses.fy1Amt
    formVal[0].expenditures[1].administrativeExpenseAmount = expData.administrativeExpenses.fy2Amt
    formVal[1].expenditures[0].administrativeExpenseAmount = expData.administrativeExpenses.fy3Amt
    formVal[1].expenditures[1].administrativeExpenseAmount = expData.administrativeExpenses.fy4Amt
    formVal[2].expenditures[0].administrativeExpenseAmount = expData.administrativeExpenses.fy5Amt
    formVal[2].expenditures[1].administrativeExpenseAmount = expData.administrativeExpenses.fy6Amt

    formVal[0].expenditures[0].oMAmount = expData.operationAndMaintenance.fy1Amt
    formVal[0].expenditures[1].oMAmount = expData.operationAndMaintenance.fy2Amt
    formVal[1].expenditures[0].oMAmount = expData.operationAndMaintenance.fy3Amt
    formVal[1].expenditures[1].oMAmount = expData.operationAndMaintenance.fy4Amt
    formVal[2].expenditures[0].oMAmount = expData.operationAndMaintenance.fy5Amt
    formVal[2].expenditures[1].oMAmount = expData.operationAndMaintenance.fy6Amt

    formVal[0].expenditures[0].loanRepaymentAmount = expData.loanRepayment.fy1Amt
    formVal[0].expenditures[1].loanRepaymentAmount = expData.loanRepayment.fy2Amt
    formVal[1].expenditures[0].loanRepaymentAmount = expData.loanRepayment.fy3Amt
    formVal[1].expenditures[1].loanRepaymentAmount = expData.loanRepayment.fy4Amt
    formVal[2].expenditures[0].loanRepaymentAmount = expData.loanRepayment.fy5Amt
    formVal[2].expenditures[1].loanRepaymentAmount = expData.loanRepayment.fy6Amt


    formVal[0].expenditures[0].othersAmount = expData.otherExpenditure.fy1Amt
    formVal[0].expenditures[1].othersAmount = expData.otherExpenditure.fy2Amt
    formVal[1].expenditures[0].othersAmount = expData.otherExpenditure.fy3Amt
    formVal[1].expenditures[1].othersAmount = expData.otherExpenditure.fy4Amt
    formVal[2].expenditures[0].othersAmount = expData.otherExpenditure.fy5Amt
    formVal[2].expenditures[1].othersAmount = expData.otherExpenditure.fy6Amt

    formVal[0].expenditures[0].schemeAmount = expData.developmentWorks.fy1Amt
    formVal[0].expenditures[1].schemeAmount = expData.developmentWorks.fy2Amt
    formVal[1].expenditures[0].schemeAmount = expData.developmentWorks.fy3Amt
    formVal[1].expenditures[1].schemeAmount = expData.developmentWorks.fy4Amt
    formVal[2].expenditures[0].schemeAmount = expData.developmentWorks.fy5Amt
    formVal[2].expenditures[1].schemeAmount = expData.developmentWorks.fy6Amt

    formVal[0].expenditures[0].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy1Amt
    formVal[0].expenditures[1].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy2Amt
    formVal[1].expenditures[0].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy3Amt
    formVal[1].expenditures[1].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy4Amt
    formVal[2].expenditures[0].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy5Amt
    formVal[2].expenditures[1].othersCapitalExpenditureAmount = expData.otherCapitalExpenditure.fy6Amt

    for (let i = 0; i < formVal.length; i++) {
      this.revenueCapitalReceipt.at(i).patchValue(formVal[i])
    }

    for(let i=0;i<3;i++) {
      for(let j = 0; j<2; j++) {
        this.onKeyUpSetReceiptAmount(i,j)
        this.onKeyUpSetCapitalAmount(i,j)
      }
    }
  }

  get revenueCapitalReceipt() {
    return this.revenueCapitalExpenditureForm.controls['financeYears'] as any
  }
  initRevenueCapitalExpenditureForm() {
    this.revenueCapitalExpenditureForm = this.fb.group({
      'financeYears': new FormArray([this.createFinanceForm(), this.createFinanceForm(), this.createFinanceForm()])
    });
  }

  createFinanceForm(): FormGroup {
    return this.fb.group({
      'financeYear': new FormControl(null),
      'expenditures': new FormArray([this.createRevenueCapitalExpenditureForm(), this.createRevenueCapitalExpenditureForm()])
    });
  }

  createRevenueCapitalExpenditureForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl(null),
      'year': new FormControl(null, [Validators.required]),
      'total': new FormControl({ value: null, disabled: true },),
      'receiptAmount': new FormControl({ value: null, disabled: true },),
      'administrativeExpenseAmount': new FormControl(null, [Validators.required]),
      'oMAmount': new FormControl(null, [Validators.required]),
      'loanRepaymentAmount': new FormControl(null, [Validators.required]),
      'othersAmount': new FormControl(null, [Validators.required]),
      'capitalAmount': new FormControl({ value: null, disabled: true },),
      'schemeAmount': new FormControl(null, [Validators.required]),
      'loanRepaymentPrincipalAmount': new FormControl(null,),
      'othersCapitalExpenditureAmount': new FormControl(null, [Validators.required]),
    });

  }


  save() {
    let exVal: revCapExpDTO = {
      fy1L: '',
      fy2L: '',
      fy3L: '',
      iaId: 0,
      id: this.service1.step1Report.id,
      administrativeExpenses: {
        expenditureType: '',
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,

      },
      details: {
        fy1: '',
        fy2: '',
        fy3: '',
        fy4: '',
        fy5: '',
        fy6: '',
        iaId: 0,
        id: 0
      },
      developmentWorks: {
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,
        expenditureType: ''
      },
      loanRepayment: {
        expenditureType: '',
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,

      },
      operationAndMaintenance: {
        expenditureType: '',
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,

      },
      otherCapitalExpenditure: {
        expenditureType: '',
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,

      },
      otherExpenditure: {
        expenditureType: '',
        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revCapExpId != 0 ? this.revCapExpId : null,

      }
    }

    const formVal = this.revenueCapitalReceipt.value
    exVal.iaId = this.service1.step1Report.id
    exVal.id = this.expendId != 0 ? this.expendId : null
    exVal.fy1L = formVal[0].financeYear
    exVal.fy2L = formVal[1].financeYear
    exVal.fy3L = formVal[2].financeYear
    exVal.details.iaId = this.service1.step1Report.id
    exVal.details.fy1 = formVal[0].expenditures[0].year
    exVal.details.fy2 = formVal[0].expenditures[1].year
    exVal.details.fy3 = formVal[1].expenditures[0].year
    exVal.details.fy4 = formVal[1].expenditures[1].year
    exVal.details.fy5 = formVal[2].expenditures[0].year
    exVal.details.fy6 = formVal[2].expenditures[1].year
    exVal.details.id = this.detailsId
    exVal.administrativeExpenses.fy1Amt = formVal[0].expenditures[0].administrativeExpenseAmount
    exVal.administrativeExpenses.fy2Amt = formVal[0].expenditures[1].administrativeExpenseAmount
    exVal.administrativeExpenses.fy3Amt = formVal[1].expenditures[0].administrativeExpenseAmount
    exVal.administrativeExpenses.fy4Amt = formVal[1].expenditures[1].administrativeExpenseAmount
    exVal.administrativeExpenses.fy5Amt = formVal[2].expenditures[0].administrativeExpenseAmount
    exVal.administrativeExpenses.fy6Amt = formVal[2].expenditures[1].administrativeExpenseAmount
    exVal.administrativeExpenses.expenditureType = "ADMINISTRATIVE_EXPENSES"
    exVal.administrativeExpenses.id = this.administrativeExpensesId
    exVal.operationAndMaintenance.fy1Amt = formVal[0].expenditures[0].oMAmount
    exVal.operationAndMaintenance.fy2Amt = formVal[0].expenditures[1].oMAmount
    exVal.operationAndMaintenance.fy3Amt = formVal[1].expenditures[0].oMAmount
    exVal.operationAndMaintenance.fy4Amt = formVal[1].expenditures[1].oMAmount
    exVal.operationAndMaintenance.fy5Amt = formVal[2].expenditures[0].oMAmount
    exVal.operationAndMaintenance.fy6Amt = formVal[2].expenditures[1].oMAmount
    exVal.operationAndMaintenance.expenditureType = "OPERATION_AND_MAINTENANCE"
    exVal.operationAndMaintenance.id = this.operationAndMaintenanceId
    exVal.loanRepayment.id = this.loanRepaymentId
    exVal.loanRepayment.fy1Amt = formVal[0].expenditures[0].loanRepaymentAmount
    exVal.loanRepayment.fy2Amt = formVal[0].expenditures[1].loanRepaymentAmount
    exVal.loanRepayment.fy3Amt = formVal[1].expenditures[0].loanRepaymentAmount
    exVal.loanRepayment.fy4Amt = formVal[1].expenditures[1].loanRepaymentAmount
    exVal.loanRepayment.fy5Amt = formVal[2].expenditures[0].loanRepaymentAmount
    exVal.loanRepayment.fy6Amt = formVal[2].expenditures[1].loanRepaymentAmount
    exVal.loanRepayment.expenditureType = "LOAN_REPAYMENT"

    exVal.otherExpenditure.fy1Amt = formVal[0].expenditures[0].othersAmount
    exVal.otherExpenditure.fy2Amt = formVal[0].expenditures[1].othersAmount
    exVal.otherExpenditure.fy3Amt = formVal[1].expenditures[0].othersAmount
    exVal.otherExpenditure.fy4Amt = formVal[1].expenditures[1].othersAmount
    exVal.otherExpenditure.fy5Amt = formVal[2].expenditures[0].othersAmount
    exVal.otherExpenditure.fy6Amt = formVal[2].expenditures[1].othersAmount
    exVal.otherExpenditure.expenditureType = "OTHER_EXPENDITURE"
    exVal.otherExpenditure.id = this.otherExpenditureId
    exVal.developmentWorks.id = this.developmentWorksId
    exVal.developmentWorks.fy1Amt = formVal[0].expenditures[0].schemeAmount
    exVal.developmentWorks.fy2Amt = formVal[0].expenditures[1].schemeAmount
    exVal.developmentWorks.fy3Amt = formVal[1].expenditures[0].schemeAmount
    exVal.developmentWorks.fy4Amt = formVal[1].expenditures[1].schemeAmount
    exVal.developmentWorks.fy5Amt = formVal[2].expenditures[0].schemeAmount
    exVal.developmentWorks.fy6Amt = formVal[2].expenditures[1].schemeAmount
    exVal.developmentWorks.expenditureType = "DEVELOPMENT_WORKS"
    exVal.otherCapitalExpenditure.id = this.otherCapitalExpenditureId
    exVal.otherCapitalExpenditure.fy1Amt = formVal[0].expenditures[0].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.fy2Amt = formVal[0].expenditures[1].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.fy3Amt = formVal[1].expenditures[0].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.fy4Amt = formVal[1].expenditures[1].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.fy5Amt = formVal[2].expenditures[0].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.fy6Amt = formVal[2].expenditures[1].othersCapitalExpenditureAmount
    exVal.otherCapitalExpenditure.expenditureType = "OTHER_CAPITAL_EXPENDITURE"

    this.service.createUpdateRevenueCapitalEx(exVal).subscribe((res: any) => {
      this.currId = res.id
      this.detailsId = res.details.id
      this.administrativeExpensesId = res.administrativeExpenses.id
      this.operationAndMaintenanceId = res.operationAndMaintenance.id
      this.loanRepaymentId = res.loanRepayment.id
      this.otherExpenditureId = res.otherExpenditure.id
      this.developmentWorksId = res.developmentWorks.id
      this.otherCapitalExpenditureId = res.otherCapitalExpenditure.id
      this.expendId = res.id
      this.revenueCapitalExpenditureForm.disable()

    })
  }

  enableForm() {
    this.revenueCapitalExpenditureForm.enable()
  }
  onKeyUpSetReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['expenditures']['controls'][subIndex];
    var administrativeExpenseAmount = control.controls.administrativeExpenseAmount.value;
    var oMAmount = control.controls.oMAmount.value;
    var loanRepaymentAmount = control.controls.loanRepaymentAmount.value;
    var othersAmount = control.controls.othersAmount.value;
    var amount = administrativeExpenseAmount + oMAmount + loanRepaymentAmount + othersAmount;
    control.patchValue({
      'receiptAmount': amount
    });
  }

  onKeyUpSetCapitalAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['expenditures']['controls'][subIndex];
    var schemeAmount = control.controls.schemeAmount.value;
    var loanRepaymentPrincipalAmount = control.controls.loanRepaymentPrincipalAmount.value;
    var othersCapitalExpenditureAmount = control.controls.othersCapitalExpenditureAmount.value;
    var amount = schemeAmount + loanRepaymentPrincipalAmount +  othersCapitalExpenditureAmount;
    control.patchValue({
      'capitalAmount': amount
    });
  }

  handleClick(value: string) {

  }
}
