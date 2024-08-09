import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { RevenueAndCapitalReceiptHeadingList } from 'src/app/audit/modals/revenue-and-capital-receipts-list';
import { revenueCapitalReceiptDTO } from '../../../models/revenueCapitalReceiptDTO';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step5Service } from '../../../services/step-5.service';

@Component({
  selector: 'app-view-rev-cap-receipt',
  templateUrl: './view-rev-cap-receipt.component.html',
  styleUrls: ['./view-rev-cap-receipt.component.css']
})
export class ViewRevCapReceiptComponent implements OnInit {

  isMeaageForFinanceLayerFirst = false;
  revenueAndCapitalReceipts: any;
  commentsControl = new FormControl();
  financeLayerFirstFormModel!: FormGroup;
  isAddButtonDisabled = true;
  revenueCapitalReceiptForm!: FormGroup;
  arrayOfKeys: any[] = [];
  financeLayerFirst: any
  receiptHeadingList = RevenueAndCapitalReceiptHeadingList.receiptHeadingList;
  serialNoList = RevenueAndCapitalReceiptHeadingList.serialNoList;
  receipts: any[] = [];
  revId: number  = 0;

  isDisabled!: boolean;
  count = 0;
  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private route: ActivatedRoute,
    private service1: Step1ReportService

    )
     { }

  ngOnInit() {
    this.initFinanceLayerFirstFormModel();
    this.initRevenueCapitalReceiptForm();

    let temp = this.revenueCapitalReceipt.controls[0] as any
    this.arrayOfKeys = Object.keys(temp.controls['receipts']['controls'][0].controls)
    // 

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getRevenueCapitalReceiptByIaId(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(res => {
        this.revenueCapitalReceiptForm.disable()
      },
      error => {

      }
      )
  }
  headingFix(item: string) {
    if(item == "Total Receipts (A+B)" ) {
      return false
    }
    if(item == "Revenue Receipts (1+2+3)" ) {
      return false
    }
    if(item == "Own Revenue Receipts(a+b)" ) {
      return false
    }
    if(item == "Tax Revenue (levied and collected by municipal body)" ) {
      return false
    }
    if(item == "Other Revenue Receipts" ) {
      return false
    }
    if(item == "Non-tax revenue (levied and collected by municipal body)" ) {
      return false
    }
    if(item == "Transfers/Grants/Assigned Revenues" ) {
      return false
    }
    if(item == "Capital Receipts(1+2+3+4+5+6)" ) {
      return false
    }

    return true
  }
  setData(rcReceipt: any) {
    // 
    let receipts = this.revenueCapitalReceipt.value;
    this.revId = rcReceipt.id
    rcReceipt.iaId = this.service1.step1Report.id
    receipts[0].financeYear = rcReceipt.fy1L
    receipts[0].id = rcReceipt.id
    receipts[2].id = rcReceipt.id
    receipts[1].id = rcReceipt.id
    receipts[1].financeYear = rcReceipt.fy3L
    receipts[2].financeYear = rcReceipt.fy2L
    // rcReceipt.propertyTax.expenditureType = receipts[0].actualExpenditureData
   receipts[0].receipts[0].propertyTaxAmount =  rcReceipt.propertyTax.fy1Amt
   receipts[0].receipts[1].propertyTaxAmount =  rcReceipt.propertyTax.fy2Amt
   receipts[1].receipts[0].propertyTaxAmount =  rcReceipt.propertyTax.fy3Amt
   receipts[1].receipts[1].propertyTaxAmount =  rcReceipt.propertyTax.fy4Amt
   receipts[2].receipts[0].propertyTaxAmount =  rcReceipt.propertyTax.fy5Amt
   receipts[2].receipts[1].propertyTaxAmount =  rcReceipt.propertyTax.fy6Amt
    rcReceipt.propertyTax.type = "PROPERTY_TAX"
    // rcReceipt.userCharges.expenditureType
   receipts[0].receipts[0].userChargesAmount=  rcReceipt.userCharges.fy1Amt
   receipts[0].receipts[1].userChargesAmount=  rcReceipt.userCharges.fy2Amt
   receipts[1].receipts[0].userChargesAmount=  rcReceipt.userCharges.fy3Amt
   receipts[1].receipts[1].userChargesAmount=  rcReceipt.userCharges.fy4Amt
   receipts[2].receipts[0].userChargesAmount=  rcReceipt.userCharges.fy5Amt
   receipts[2].receipts[1].userChargesAmount=  rcReceipt.userCharges.fy6Amt
    rcReceipt.userCharges.type = "USER_CHARGES"
    // rcReceipt.stateFCGrants.expenditureType
    receipts[0].receipts[0].sFCGrantsAmount = rcReceipt.stateFCGrants.fy1Amt
    receipts[0].receipts[1].sFCGrantsAmount = rcReceipt.stateFCGrants.fy2Amt
    receipts[1].receipts[0].sFCGrantsAmount = rcReceipt.stateFCGrants.fy3Amt
    receipts[1].receipts[1].sFCGrantsAmount = rcReceipt.stateFCGrants.fy4Amt
    receipts[2].receipts[0].sFCGrantsAmount = rcReceipt.stateFCGrants.fy5Amt
    receipts[2].receipts[1].sFCGrantsAmount = rcReceipt.stateFCGrants.fy6Amt
    rcReceipt.stateFCGrants.type = "STATE_FC_GRANTS"
    // rcReceipt.stateCapitalAccountGrants.expenditureType
     receipts[0].receipts[0].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy1Amt
     receipts[0].receipts[1].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy2Amt
     receipts[1].receipts[0].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy3Amt
     receipts[1].receipts[1].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy4Amt
     receipts[2].receipts[0].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy5Amt
     receipts[2].receipts[1].stateCapitalAccountAmount = rcReceipt.stateCapitalAccountGrants.fy6Amt
    rcReceipt.stateCapitalAccountGrants.type = "STATE_CAPITAL_ACCOUNT_GRANTS"
    // rcReceipt.stateAssignedRevenue.expenditureType
    receipts[0].receipts[0].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy1Amt
    receipts[0].receipts[1].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy2Amt
    receipts[1].receipts[0].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy3Amt
    receipts[1].receipts[1].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy4Amt
    receipts[2].receipts[0].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy5Amt
    receipts[2].receipts[1].stateAssignedRevenueAmount = rcReceipt.stateAssignedRevenue.fy6Amt
    rcReceipt.stateAssignedRevenue.type = "STATE_ASSIGNED_REVENUE"
    // rcReceipt.saleOfMunicipalLand.expenditureType
    receipts[0].receipts[0].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy1Amt
    receipts[0].receipts[1].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy2Amt
    receipts[1].receipts[0].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy3Amt
    receipts[1].receipts[1].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy4Amt
    receipts[2].receipts[0].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy5Amt
    receipts[2].receipts[1].municipalLandAmount = rcReceipt.saleOfMunicipalLand.fy6Amt
    rcReceipt.saleOfMunicipalLand.type = "SALES_OF_MUNICIPAL_LAND"
    // rcReceipt.others.expenditureType
    receipts[0].receipts[0].otherSalesAmount = rcReceipt.others.fy1Amt
    receipts[0].receipts[1].otherSalesAmount = rcReceipt.others.fy2Amt
    receipts[1].receipts[0].otherSalesAmount = rcReceipt.others.fy3Amt
    receipts[1].receipts[1].otherSalesAmount = rcReceipt.others.fy4Amt
    receipts[2].receipts[0].otherSalesAmount = rcReceipt.others.fy5Amt
    receipts[2].receipts[1].otherSalesAmount = rcReceipt.others.fy6Amt
    rcReceipt.others.type = "OTHERS"
    // rcReceipt.otherTax.expenditureType
     receipts[0].receipts[0].otherTaxAmount = rcReceipt.otherTax.fy1Amt
     receipts[0].receipts[1].otherTaxAmount = rcReceipt.otherTax.fy2Amt
     receipts[1].receipts[0].otherTaxAmount = rcReceipt.otherTax.fy3Amt
     receipts[1].receipts[1].otherTaxAmount = rcReceipt.otherTax.fy4Amt
     receipts[2].receipts[0].otherTaxAmount = rcReceipt.otherTax.fy5Amt
     receipts[2].receipts[1].otherTaxAmount = rcReceipt.otherTax.fy6Amt
    rcReceipt.otherTax.type = "OTHER_TAX"
    // rcReceipt.otherStateGovtTransfers.expenditureType
     receipts[0].receipts[0].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy1Amt
     receipts[0].receipts[1].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy2Amt
     receipts[1].receipts[0].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy3Amt
     receipts[1].receipts[1].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy4Amt
     receipts[2].receipts[0].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy5Amt
     receipts[2].receipts[1].otherStateGovernmentTransfersAmount = rcReceipt.otherStateGovtTransfers.fy6Amt
    rcReceipt.otherStateGovtTransfers.type = "OTHER_STATE_GOVT_TRANSFERS"
    // rcReceipt.otherRevenueIncome.expenditureType
     receipts[0].receipts[0].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy1Amt
     receipts[0].receipts[1].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy2Amt
     receipts[1].receipts[0].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy3Amt
     receipts[1].receipts[1].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy4Amt
     receipts[2].receipts[0].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy5Amt
     receipts[2].receipts[1].otherRevenueIncomeAmount = rcReceipt.otherRevenueIncome.fy6Amt
    rcReceipt.otherRevenueIncome.type = "OTHER_REVENUE_INCOME"
    // rcReceipt.centralCapitalAccountGrants.expenditureType
     receipts[0].receipts[0].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy1Amt
     receipts[0].receipts[1].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy2Amt
     receipts[1].receipts[0].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy3Amt
     receipts[1].receipts[1].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy4Amt
     receipts[2].receipts[0].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy5Amt
     receipts[2].receipts[1].centralCapitalAccountAmount = rcReceipt.centralCapitalAccountGrants.fy6Amt
    rcReceipt.centralCapitalAccountGrants.type = "CENTRAL_CAPITAL_ACCOUNT_GRANTS"
    // rcReceipt.cfcGrants.expenditureType
    receipts[0].receipts[0].cFCGrantAmount = rcReceipt.cfcGrants.fy1Amt
    receipts[0].receipts[1].cFCGrantAmount = rcReceipt.cfcGrants.fy2Amt
    receipts[1].receipts[0].cFCGrantAmount = rcReceipt.cfcGrants.fy3Amt
    receipts[1].receipts[1].cFCGrantAmount = rcReceipt.cfcGrants.fy4Amt
    receipts[2].receipts[0].cFCGrantAmount = rcReceipt.cfcGrants.fy5Amt
    receipts[2].receipts[1].cFCGrantAmount = rcReceipt.cfcGrants.fy6Amt
    rcReceipt.cfcGrants.type = "CFC_GRANTS"
     receipts[0].receipts[0].year = rcReceipt.details.fy1
     receipts[0].receipts[1].year = rcReceipt.details.fy2
     receipts[1].receipts[0].year = rcReceipt.details.fy3
     receipts[1].receipts[1].year = rcReceipt.details.fy4
     receipts[2].receipts[0].year = rcReceipt.details.fy5
     receipts[2].receipts[1].year = rcReceipt.details.fy6
    rcReceipt.details.iaId = this.service1.step1Report.id
    // rcReceipt.feesAndFines.expenditureType
    receipts[0].receipts[0].finesAmount = rcReceipt.feesAndFines.fy1Amt
    receipts[0].receipts[1].finesAmount = rcReceipt.feesAndFines.fy2Amt
    receipts[1].receipts[0].finesAmount = rcReceipt.feesAndFines.fy3Amt
    receipts[1].receipts[1].finesAmount = rcReceipt.feesAndFines.fy4Amt
    receipts[2].receipts[0].finesAmount = rcReceipt.feesAndFines.fy5Amt
    receipts[2].receipts[1].finesAmount = rcReceipt.feesAndFines.fy6Amt
    rcReceipt.feesAndFines.type = "FEES_AND_FINES"
    // rcReceipt.incomeFromInterest.expenditureType
    receipts[0].receipts[0].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy1Amt
    receipts[0].receipts[1].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy2Amt
    receipts[1].receipts[0].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy3Amt
    receipts[1].receipts[1].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy4Amt
    receipts[2].receipts[0].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy5Amt
    receipts[2].receipts[1].incomeFromInterestAmount = rcReceipt.incomeFromInterest.fy6Amt
    rcReceipt.incomeFromInterest.type = "INCOME_FROM_INTEREST"
    // rcReceipt.loans.expenditureType
    receipts[0].receipts[0].loanAmount = rcReceipt.loans.fy1Amt
    receipts[0].receipts[1].loanAmount = rcReceipt.loans.fy2Amt
    receipts[1].receipts[0].loanAmount = rcReceipt.loans.fy3Amt
    receipts[1].receipts[1].loanAmount = rcReceipt.loans.fy4Amt
    receipts[2].receipts[0].loanAmount = rcReceipt.loans.fy5Amt
    receipts[2].receipts[1].loanAmount = rcReceipt.loans.fy6Amt
    rcReceipt.loans.type = "LOANS"
    // rcReceipt.octraiCompensation.expenditureType
     receipts[0].receipts[0].octroiCompensationAmount = rcReceipt.octraiCompensation.fy1Amt
     receipts[0].receipts[1].octroiCompensationAmount = rcReceipt.octraiCompensation.fy2Amt
     receipts[1].receipts[0].octroiCompensationAmount = rcReceipt.octraiCompensation.fy3Amt
     receipts[1].receipts[1].octroiCompensationAmount = rcReceipt.octraiCompensation.fy4Amt
     receipts[2].receipts[0].octroiCompensationAmount = rcReceipt.octraiCompensation.fy5Amt
     receipts[2].receipts[1].octroiCompensationAmount = rcReceipt.octraiCompensation.fy6Amt
    rcReceipt.octraiCompensation.type = "OCTRAI_COMPENSATION"
    // rcReceipt.otherCapitalReceipts.expenditureType
     receipts[0].receipts[0].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy1Amt
     receipts[0].receipts[1].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy2Amt
     receipts[1].receipts[0].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy3Amt
     receipts[1].receipts[1].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy4Amt
     receipts[2].receipts[0].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy5Amt
     receipts[2].receipts[1].othersCapitalReceiptAmount = rcReceipt.otherCapitalReceipts.fy6Amt
    rcReceipt.otherCapitalReceipts.type = "OTHER_CAPITAL_RECEIPTS"
    // rcReceipt.otherCentralGovtTransfers.expenditureType
    receipts[0].receipts[0].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy1Amt
    receipts[0].receipts[1].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy2Amt
    receipts[1].receipts[0].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy3Amt
    receipts[1].receipts[1].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy4Amt
    receipts[2].receipts[0].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy5Amt
    receipts[2].receipts[1].otherCentralGovernmentTransfersAmount = rcReceipt.otherCentralGovtTransfers.fy6Amt
    rcReceipt.otherCentralGovtTransfers.type = "OTHER_CENTRAL_GOVT_TRANSFERS"
    // rcReceipt.otherNonTaxRevenue.expenditureType
     receipts[0].receipts[0].othersAmount = rcReceipt.otherNonTaxRevenue.fy1Amt
     receipts[0].receipts[1].othersAmount = rcReceipt.otherNonTaxRevenue.fy2Amt
     receipts[1].receipts[0].othersAmount = rcReceipt.otherNonTaxRevenue.fy3Amt
     receipts[1].receipts[1].othersAmount = rcReceipt.otherNonTaxRevenue.fy4Amt
     receipts[2].receipts[0].othersAmount = rcReceipt.otherNonTaxRevenue.fy5Amt
     receipts[2].receipts[1].othersAmount = rcReceipt.otherNonTaxRevenue.fy6Amt
    rcReceipt.otherNonTaxRevenue.type = "OTHER_NON_TAX_REVENUE"

    for (let i = 0; i < receipts.length; i++) {
      this.revenueCapitalReceipt.at(i).patchValue(receipts[i])
    }
    // 
  }



  get revenueCapitalReceipt() {
    // return this.revenueCapitalReceiptForm.controls['financeYears'] as FormArray
    return this.revenueCapitalReceiptForm.controls['financeYears'] as any
  }

  initRevenueCapitalReceiptForm() {
    this.revenueCapitalReceiptForm = this.formBuilder.group({
      'financeYears': new FormArray([this.createFinanceForm(), this.createFinanceForm(), this.createFinanceForm()])
    });
  }

  createFinanceForm(): FormGroup {
    return this.formBuilder.group({
      'financeYear': new FormControl({ value: { value: null, disabled: true }, disabled: true }),
      'receipts': new FormArray([this.createRevenueCapitalReceiptForm(), this.createRevenueCapitalReceiptForm()])
    });
  }

  createRevenueCapitalReceiptForm(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl({ value: null, disabled: true }),
      'year': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'total': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'revenueReceiptsAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'ownRevenueReceiptsAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'taxRevenueAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'propertyTaxAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherTaxAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'nontaxRevenueAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'finesAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'userChargesAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'othersAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherRevenueReceiptsAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'incomeFromInterestAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherRevenueIncomeAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'assignedRevenuesAmount': new FormControl({ value: null, disabled: true }),
      'stateAssignedRevenueAmount': new FormControl({ value: null, disabled: true }),
      'sFCGrantsAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'octroiCompensationAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherStateGovernmentTransfersAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'cFCGrantAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherCentralGovernmentTransfersAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'otherSalesAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'capitalReceiptsAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'municipalLandAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'loanAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'stateCapitalAccountAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'centralCapitalAccountAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'othersCapitalReceiptAmount': new FormControl({ value: null, disabled: true }, [Validators.required]),
      // 'subYears': new FormArray([this.createSubYear(), this.createSubYear()])

    });

  }

  createSubYear(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl({ value: null, disabled: true }),
      'sessionYear': new FormControl({ value: null, disabled: true }, [Validators.required])
    })
  }

  initFinanceLayerFirstFormModel() {
    this.financeLayerFirstFormModel = this.formBuilder.group({
      "id": new FormControl({ value: null, disabled: true }),
      "heading": new FormControl({ value: null, disabled: true }),
      "amount": new FormControl({ value: null, disabled: true }),
      "description": new FormControl({ value: null, disabled: true }),
      "year": new FormControl({ value: null, disabled: true })
    })
  }


  getFinanceLayerFirstFormModelValue() {
    this.financeLayerFirst.id = this.financeLayerFirstFormModel.controls['id'].value;
    this.financeLayerFirst.heading = this.financeLayerFirstFormModel.controls['heading'].value;
    this.financeLayerFirst.description = this.financeLayerFirstFormModel.controls['description'].value;
    this.financeLayerFirst.amount = this.financeLayerFirstFormModel.controls['amount'].value;
    this.financeLayerFirst.year = (this.financeLayerFirstFormModel.controls['year'].value);
    this.revenueAndCapitalReceipts.layer1.push(this.financeLayerFirst);
    this.financeLayerFirstFormModel.reset();
  }
  // save() {
  //   let receipts = this.revenueCapitalReceipt.value;
  //   let rcReceipt: revenueCapitalReceiptDTO = {
  //     centralCapitalAccountGrants: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     cfcGrants: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     details: {
  //       fy1: '',
  //       fy2: '',
  //       fy3: '',
  //       fy4: '',
  //       fy5: '',
  //       fy6: '',
  //       iaId: this.service1.step1Report.id,
  //       id: this.revId!=0 ? this.revId: null
  //     },
  //     feesAndFines: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     fy1L: '',
  //     fy2L: '',
  //     fy3L: '',
  //     iaId: this.service1.step1Report.id,
  //     id: this.revId != 0 ? this.revId : null,
  //     incomeFromInterest: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     loans: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     octraiCompensation: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherCapitalReceipts: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherCentralGovtTransfers: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherNonTaxRevenue: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherRevenueIncome: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherStateGovtTransfers: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     otherTax: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     others: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     propertyTax: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     saleOfMunicipalLand: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     stateAssignedRevenue: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     stateCapitalAccountGrants: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     stateFCGrants: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     },
  //     userCharges: {

  //       fy1Amt: 0,
  //       fy2Amt: 0,
  //       fy3Amt: 0,
  //       fy4Amt: 0,
  //       fy5Amt: 0,
  //       fy6Amt: 0,
  //       id: this.revId != 0 ? this.revId : null,
  //       type: ''
  //     }
  //   }
  //   rcReceipt.iaId = this.service1.step1Report.id
  //   rcReceipt.id = this.revId
  //   rcReceipt.fy1L = receipts[0].financeYear
  //   rcReceipt.fy3L = receipts[1].financeYear
  //   rcReceipt.fy2L = receipts[2].financeYear
  //   // rcReceipt.propertyTax.expenditureType = receipts[0].actualExpenditureData
  //   rcReceipt.propertyTax.fy1Amt = receipts[0].receipts[0].propertyTaxAmount
  //   rcReceipt.propertyTax.fy2Amt = receipts[0].receipts[1].propertyTaxAmount
  //   rcReceipt.propertyTax.fy3Amt = receipts[1].receipts[0].propertyTaxAmount
  //   rcReceipt.propertyTax.fy4Amt = receipts[1].receipts[1].propertyTaxAmount
  //   rcReceipt.propertyTax.fy5Amt = receipts[2].receipts[0].propertyTaxAmount
  //   rcReceipt.propertyTax.fy6Amt = receipts[2].receipts[1].propertyTaxAmount
  //   rcReceipt.propertyTax.type = "PROPERTY_TAX"
  //   rcReceipt.propertyTax.id = this.revId
  //   // rcReceipt.userCharges.expenditureType
  //   rcReceipt.userCharges.fy1Amt = receipts[0].receipts[0].userChargesAmount
  //   rcReceipt.userCharges.fy2Amt = receipts[0].receipts[1].userChargesAmount
  //   rcReceipt.userCharges.fy3Amt = receipts[1].receipts[0].userChargesAmount
  //   rcReceipt.userCharges.fy4Amt = receipts[1].receipts[1].userChargesAmount
  //   rcReceipt.userCharges.fy5Amt = receipts[2].receipts[0].userChargesAmount
  //   rcReceipt.userCharges.fy6Amt = receipts[2].receipts[1].userChargesAmount
  //   rcReceipt.userCharges.type = "USER_CHARGES"
  //   rcReceipt.userCharges.id = this.revId
  //   // rcReceipt.stateFCGrants.expenditureType
  //   rcReceipt.stateFCGrants.fy1Amt = receipts[0].receipts[0].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.fy2Amt = receipts[0].receipts[1].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.fy3Amt = receipts[1].receipts[0].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.fy4Amt = receipts[1].receipts[1].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.fy5Amt = receipts[2].receipts[0].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.fy6Amt = receipts[2].receipts[1].sFCGrantsAmount
  //   rcReceipt.stateFCGrants.type = "STATE_FC_GRANTS"
  //   rcReceipt.stateFCGrants.id = this.revId
  //   // rcReceipt.stateCapitalAccountGrants.expenditureType
  //   rcReceipt.stateCapitalAccountGrants.fy1Amt = receipts[0].receipts[0].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.fy2Amt = receipts[0].receipts[1].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.fy3Amt = receipts[1].receipts[0].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.fy4Amt = receipts[1].receipts[1].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.fy5Amt = receipts[2].receipts[0].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.fy6Amt = receipts[2].receipts[1].stateCapitalAccountAmount
  //   rcReceipt.stateCapitalAccountGrants.type = "STATE_CAPITAL_ACCOUNT_GRANTS"
  //    rcReceipt.stateCapitalAccountGrants.id = this.revId
  //   // rcReceipt.stateAssignedRevenue.expenditureType
  //   rcReceipt.stateAssignedRevenue.fy1Amt = receipts[0].receipts[0].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.fy2Amt = receipts[0].receipts[1].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.fy3Amt = receipts[1].receipts[0].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.fy4Amt = receipts[1].receipts[1].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.fy5Amt = receipts[2].receipts[0].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.fy6Amt = receipts[2].receipts[1].stateAssignedRevenueAmount
  //   rcReceipt.stateAssignedRevenue.type = "STATE_ASSIGNED_REVENUE"
  //   rcReceipt.stateAssignedRevenue.id = this.revId
  //   // rcReceipt.saleOfMunicipalLand.expenditureType
  //   rcReceipt.saleOfMunicipalLand.fy1Amt = receipts[0].receipts[0].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.fy2Amt = receipts[0].receipts[1].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.fy3Amt = receipts[1].receipts[0].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.fy4Amt = receipts[1].receipts[1].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.fy5Amt = receipts[2].receipts[0].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.fy6Amt = receipts[2].receipts[1].municipalLandAmount
  //   rcReceipt.saleOfMunicipalLand.type = "SALES_OF_MUNICIPAL_LAND"
  //   rcReceipt.saleOfMunicipalLand.id = this.revId
  //   // rcReceipt.others.expenditureType
  //   rcReceipt.others.fy1Amt = receipts[0].receipts[0].otherSalesAmount
  //   rcReceipt.others.fy2Amt = receipts[0].receipts[1].otherSalesAmount
  //   rcReceipt.others.fy3Amt = receipts[1].receipts[0].otherSalesAmount
  //   rcReceipt.others.fy4Amt = receipts[1].receipts[1].otherSalesAmount
  //   rcReceipt.others.fy5Amt = receipts[2].receipts[0].otherSalesAmount
  //   rcReceipt.others.fy6Amt = receipts[2].receipts[1].otherSalesAmount
  //   rcReceipt.others.type = "OTHERS"
  //   rcReceipt.others.id = this.revId
  //   // rcReceipt.otherTax.expenditureType
  //   rcReceipt.otherTax.fy1Amt = receipts[0].receipts[0].otherTaxAmount
  //   rcReceipt.otherTax.fy2Amt = receipts[0].receipts[1].otherTaxAmount
  //   rcReceipt.otherTax.fy3Amt = receipts[1].receipts[0].otherTaxAmount
  //   rcReceipt.otherTax.fy4Amt = receipts[1].receipts[1].otherTaxAmount
  //   rcReceipt.otherTax.fy5Amt = receipts[2].receipts[0].otherTaxAmount
  //   rcReceipt.otherTax.fy6Amt = receipts[2].receipts[1].otherTaxAmount
  //   rcReceipt.otherTax.type = "OTHER_TAX"
  //    rcReceipt.otherTax.id = this.revId
  //   // rcReceipt.otherStateGovtTransfers.expenditureType
  //   rcReceipt.otherStateGovtTransfers.fy1Amt = receipts[0].receipts[0].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.fy2Amt = receipts[0].receipts[1].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.fy3Amt = receipts[1].receipts[0].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.fy4Amt = receipts[1].receipts[1].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.fy5Amt = receipts[2].receipts[0].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.fy6Amt = receipts[2].receipts[1].otherStateGovernmentTransfersAmount
  //   rcReceipt.otherStateGovtTransfers.type = "OTHER_STATE_GOVT_TRANSFERS"
  //   rcReceipt.otherStateGovtTransfers.id = this.revId
  //   // rcReceipt.otherRevenueIncome.expenditureType
  //   rcReceipt.otherRevenueIncome.fy1Amt = receipts[0].receipts[0].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.fy2Amt = receipts[0].receipts[1].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.fy3Amt = receipts[1].receipts[0].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.fy4Amt = receipts[1].receipts[1].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.fy5Amt = receipts[2].receipts[0].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.fy6Amt = receipts[2].receipts[1].otherRevenueIncomeAmount
  //   rcReceipt.otherRevenueIncome.type = "OTHER_REVENUE_INCOME"
  //   rcReceipt.otherRevenueIncome.id = this.revId
  //   // rcReceipt.centralCapitalAccountGrants.expenditureType
  //   rcReceipt.centralCapitalAccountGrants.fy1Amt = receipts[0].receipts[0].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.fy2Amt = receipts[0].receipts[1].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.fy3Amt = receipts[1].receipts[0].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.fy4Amt = receipts[1].receipts[1].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.fy5Amt = receipts[2].receipts[0].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.fy6Amt = receipts[2].receipts[1].centralCapitalAccountAmount
  //   rcReceipt.centralCapitalAccountGrants.type = "CENTRAL_CAPITAL_ACCOUNT_GRANTS"
  //    rcReceipt.centralCapitalAccountGrants.id = this.revId
  //   // rcReceipt.cfcGrants.expenditureType
  //   rcReceipt.cfcGrants.fy1Amt = receipts[0].receipts[0].cFCGrantAmount
  //   rcReceipt.cfcGrants.fy2Amt = receipts[0].receipts[1].cFCGrantAmount
  //   rcReceipt.cfcGrants.fy3Amt = receipts[1].receipts[0].cFCGrantAmount
  //   rcReceipt.cfcGrants.fy4Amt = receipts[1].receipts[1].cFCGrantAmount
  //   rcReceipt.cfcGrants.fy5Amt = receipts[2].receipts[0].cFCGrantAmount
  //   rcReceipt.cfcGrants.fy6Amt = receipts[2].receipts[1].cFCGrantAmount
  //   rcReceipt.cfcGrants.type = "CFC_GRANTS"
  //   rcReceipt.cfcGrants.id = this.revId
  //   rcReceipt.details.fy1 = receipts[0].receipts[0].year
  //   rcReceipt.details.fy2 = receipts[0].receipts[1].year
  //   rcReceipt.details.fy3 = receipts[1].receipts[0].year
  //   rcReceipt.details.fy4 = receipts[1].receipts[1].year
  //   rcReceipt.details.fy5 = receipts[2].receipts[0].year
  //   rcReceipt.details.fy6 = receipts[2].receipts[1].year
  //   rcReceipt.details.iaId = this.service1.step1Report.id
  //   rcReceipt.details.id = this.revId
  //   // rcReceipt.feesAndFines.expenditureType
  //   rcReceipt.feesAndFines.fy1Amt = receipts[0].receipts[0].finesAmount
  //   rcReceipt.feesAndFines.fy2Amt = receipts[0].receipts[1].finesAmount
  //   rcReceipt.feesAndFines.fy3Amt = receipts[1].receipts[0].finesAmount
  //   rcReceipt.feesAndFines.fy4Amt = receipts[1].receipts[1].finesAmount
  //   rcReceipt.feesAndFines.fy5Amt = receipts[2].receipts[0].finesAmount
  //   rcReceipt.feesAndFines.fy6Amt = receipts[2].receipts[1].finesAmount
  //   rcReceipt.feesAndFines.type = "FEES_AND_FINES"
  //    rcReceipt.feesAndFines.id = this.revId
  //   // rcReceipt.incomeFromInterest.expenditureType
  //   rcReceipt.incomeFromInterest.fy1Amt = receipts[0].receipts[0].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.fy2Amt = receipts[0].receipts[1].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.fy3Amt = receipts[1].receipts[0].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.fy4Amt = receipts[1].receipts[1].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.fy5Amt = receipts[2].receipts[0].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.fy6Amt = receipts[2].receipts[1].incomeFromInterestAmount
  //   rcReceipt.incomeFromInterest.type = "INCOME_FROM_INTEREST"
  //   rcReceipt.incomeFromInterest.id = this.revId
  //   // rcReceipt.loans.expenditureType
  //   rcReceipt.loans.fy1Amt = receipts[0].receipts[0].loanAmount
  //   rcReceipt.loans.fy2Amt = receipts[0].receipts[1].loanAmount
  //   rcReceipt.loans.fy3Amt = receipts[1].receipts[0].loanAmount
  //   rcReceipt.loans.fy4Amt = receipts[1].receipts[1].loanAmount
  //   rcReceipt.loans.fy5Amt = receipts[2].receipts[0].loanAmount
  //   rcReceipt.loans.fy6Amt = receipts[2].receipts[1].loanAmount
  //   rcReceipt.loans.type = "LOANS"
  //   rcReceipt.loans.id = this.revId
  //   // rcReceipt.octraiCompensation.expenditureType
  //   rcReceipt.octraiCompensation.fy1Amt = receipts[0].receipts[0].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.fy2Amt = receipts[0].receipts[1].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.fy3Amt = receipts[1].receipts[0].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.fy4Amt = receipts[1].receipts[1].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.fy5Amt = receipts[2].receipts[0].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.fy6Amt = receipts[2].receipts[1].octroiCompensationAmount
  //   rcReceipt.octraiCompensation.type = "OCTRAI_COMPENSATION"
  //    rcReceipt.octraiCompensation.id = this.revId
  //   // rcReceipt.otherCapitalReceipts.expenditureType
  //   rcReceipt.otherCapitalReceipts.fy1Amt = receipts[0].receipts[0].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.fy2Amt = receipts[0].receipts[1].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.fy3Amt = receipts[1].receipts[0].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.fy4Amt = receipts[1].receipts[1].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.fy5Amt = receipts[2].receipts[0].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.fy6Amt = receipts[2].receipts[1].othersCapitalReceiptAmount
  //   rcReceipt.otherCapitalReceipts.type = "OTHER_CAPITAL_RECEIPTS"
  //   rcReceipt.otherCapitalReceipts.id = this.revId
  //   // rcReceipt.otherCentralGovtTransfers.expenditureType
  //   rcReceipt.otherCentralGovtTransfers.fy1Amt = receipts[0].receipts[0].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.fy2Amt = receipts[0].receipts[1].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.fy3Amt = receipts[1].receipts[0].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.fy4Amt = receipts[1].receipts[1].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.fy5Amt = receipts[2].receipts[0].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.fy6Amt = receipts[2].receipts[1].otherCentralGovernmentTransfersAmount
  //   rcReceipt.otherCentralGovtTransfers.type = "OTHER_CENTRAL_GOVT_TRANSFERS"
  //   rcReceipt.otherCentralGovtTransfers.id = this.revId
  //   // rcReceipt.otherNonTaxRevenue.expenditureType
  //   rcReceipt.otherNonTaxRevenue.fy1Amt = receipts[0].receipts[0].othersAmount
  //   rcReceipt.otherNonTaxRevenue.fy2Amt = receipts[0].receipts[1].othersAmount
  //   rcReceipt.otherNonTaxRevenue.fy3Amt = receipts[1].receipts[0].othersAmount
  //   rcReceipt.otherNonTaxRevenue.fy4Amt = receipts[1].receipts[1].othersAmount
  //   rcReceipt.otherNonTaxRevenue.fy5Amt = receipts[2].receipts[0].othersAmount
  //   rcReceipt.otherNonTaxRevenue.fy6Amt = receipts[2].receipts[1].othersAmount
  //   rcReceipt.otherNonTaxRevenue.type = "OTHER_NON_TAX_REVENUE"
  //   rcReceipt.otherNonTaxRevenue.id = this.revId


  //   this.service.createUpdateRevenueCapitalReceipt(rcReceipt).subscribe((res:any) => {
  //     
  //     this.revId = res.id
  //   })
  //   // 
  // }
  onKeyUpSetRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];

    var ownRevenueReceiptsAmount = control.controls.ownRevenueReceiptsAmount.value;
    var otherRevenueReceiptsAmount = control.controls.otherRevenueReceiptsAmount.value;
    var assignedRevenuesAmount = control.controls.assignedRevenuesAmount.value;
    var amount = ownRevenueReceiptsAmount + otherRevenueReceiptsAmount + assignedRevenuesAmount;
    control.patchValue({
      'revenueReceiptsAmount': amount
    });
    this.setTotalReceiptsAmount(index,subIndex)
  }


  onKeyUpSetOwnTaxRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var propertyTaxAmount = control.value.propertyTaxAmount;
    var otherTaxAmount = control.value.otherTaxAmount;
    var amount = propertyTaxAmount + otherTaxAmount;
    control.patchValue({
      'taxRevenueAmount': amount
    });
    this.onKeyUpSetOwnRevenueReceiptAmount(index, subIndex);
  }

  onKeyUpSetOwnNonTaxRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var finesAmount = control.value.finesAmount;
    var userChargesAmount = control.value.userChargesAmount;
    var othersAmount = control.value.othersAmount;
    var amount = finesAmount + userChargesAmount + othersAmount;
    control.patchValue({
      'nontaxRevenueAmount': amount
    });
    this.onKeyUpSetOwnRevenueReceiptAmount(index, subIndex);
  }

  onKeyUpSetOwnRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var taxRevenueAmount = control.controls.taxRevenueAmount.value;
    var nontaxRevenueAmount = control.controls.nontaxRevenueAmount.value;
    var amount = taxRevenueAmount + nontaxRevenueAmount;
    control.patchValue({
      'ownRevenueReceiptsAmount': amount
    });
    this.onKeyUpSetRevenueReceiptAmount(index, subIndex);
  }

  onKeyUpSetOtherRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var incomeFromInterestAmount = control.value.incomeFromInterestAmount;
    var otherRevenueIncomeAmount = control.value.otherRevenueIncomeAmount;
    var amount = incomeFromInterestAmount + otherRevenueIncomeAmount;
    control.patchValue({
      'otherRevenueReceiptsAmount': amount
    });
    this.onKeyUpSetRevenueReceiptAmount(index, subIndex);
  }

  onKeyUpSetAssignedRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];

    var stateAssignedRevenueAmount = control.value.stateAssignedRevenueAmount;
    var sFCGrantsAmount = control.value.sFCGrantsAmount;
    var octroiCompensationAmount = control.value.octroiCompensationAmount;
    var otherStateGovernmentTransfersAmount = control.value.otherStateGovernmentTransfersAmount;
    var cFCGrantAmount = control.value.cFCGrantAmount;
    var otherCentralGovernmentTransfersAmount = control.value.otherCentralGovernmentTransfersAmount;
    var otherSalesAmount = control.value.otherSalesAmount;

    var amount = stateAssignedRevenueAmount + sFCGrantsAmount + octroiCompensationAmount + otherStateGovernmentTransfersAmount + cFCGrantAmount
      + otherCentralGovernmentTransfersAmount + otherSalesAmount;
    control.patchValue({
      'assignedRevenuesAmount': amount
    });
    this.onKeyUpSetRevenueReceiptAmount(index, subIndex);
  }

  onKeyUp(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var municipalLandAmount = control.value.municipalLandAmount;
    var loanAmount = control.value.loanAmount;
    var stateCapitalAccountAmount = control.value.stateCapitalAccountAmount;
    var centralCapitalAccountAmount = control.value.centralCapitalAccountAmount;
    var othersCapitalReceiptAmount = control.value.othersCapitalReceiptAmount;
    var amount = municipalLandAmount + loanAmount + stateCapitalAccountAmount + centralCapitalAccountAmount + othersCapitalReceiptAmount;
    control.patchValue({
      'capitalReceiptsAmount': amount
    });
    this.setTotalReceiptsAmount(index,subIndex)

  }

  setTotalReceiptsAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];
    var revenueReceiptAmount = control.controls.revenueReceiptsAmount.value;
    var capitalReceiptsAmount = control.controls.capitalReceiptsAmount.value;
    var amount = revenueReceiptAmount + capitalReceiptsAmount;
    control.patchValue({
      'total': amount
    });
  }
}


