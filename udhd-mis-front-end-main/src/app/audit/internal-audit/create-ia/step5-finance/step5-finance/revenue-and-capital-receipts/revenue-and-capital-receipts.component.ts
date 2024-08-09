import { Component, OnInit, TemplateRef, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { revenueCapitalReceiptDTO } from 'src/app/audit/internal-audit/models/revenueCapitalReceiptDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';
import { RevenueAndCapitalReceiptHeadingList } from 'src/app/audit/modals/revenue-and-capital-receipts-list';

@Component({
  selector: 'app-revenue-and-capital-receipts',
  templateUrl: './revenue-and-capital-receipts.component.html',
  styleUrls: ['./revenue-and-capital-receipts.component.css']
})
export class RevenueAndCapitalReceiptsComponent implements OnInit {

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
  isDisabled!: boolean;
  count = 0;
  revId: number = 0;
  propertyTaxAmountId = 0
  userChargesAmountId = 0
  sFCGrantsAmountId = 0
  stateCapitalAccountAmountId = 0
  stateAssignedRevenueAmountId = 0
  municipalLandAmountId = 0
  otherSalesAmountId = 0
  otherTaxAmountId = 0
  otherStateGovernmentTransfersAmountId = 0
  otherRevenueIncomeAmountId = 0
  centralCapitalAccountAmountId = 0
  cFCGrantAmountId = 0
  detailsId = 0
  finesAmountId = 0
  incomeFromInterestAmountId = 0
  loanAmountId = 0
  octroiCompensationAmountId = 0
  othersCapitalReceiptAmountId = 0
  otherCentralGovernmentTransfersAmountId = 0
  othersAmountId = 0
  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService
  ) { }
  ngOnInit() {
    this.initFinanceLayerFirstFormModel();
    this.initRevenueCapitalReceiptForm();

    let temp = this.revenueCapitalReceipt.controls[0] as any
    // this.arrayOfKeys = Object.keys(temp.controls);
    this.arrayOfKeys = Object.keys(temp.controls['receipts']['controls'][0].controls)
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
      'financeYear': new FormControl(null),
      'receipts': new FormArray([this.createRevenueCapitalReceiptForm(), this.createRevenueCapitalReceiptForm()])
    });
  }

  createRevenueCapitalReceiptForm(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl(null),
      'year': new FormControl(null, [Validators.required]),
      'total': new FormControl({ value: null, disabled: true },),
      'revenueReceiptsAmount': new FormControl({ value: null, disabled: true }),
      'ownRevenueReceiptsAmount': new FormControl({ value: null, disabled: true }),
      'taxRevenueAmount': new FormControl({ value: null, disabled: true }),
      'propertyTaxAmount': new FormControl(null, [Validators.required]),
      'otherTaxAmount': new FormControl(null, [Validators.required]),
      'nontaxRevenueAmount': new FormControl({ value: null, disabled: true }),
      'finesAmount': new FormControl(null, [Validators.required]),
      'userChargesAmount': new FormControl(null, [Validators.required]),
      'othersAmount': new FormControl(null, [Validators.required]),
      'otherRevenueReceiptsAmount': new FormControl({ value: null, disabled: true }),
      'incomeFromInterestAmount': new FormControl(null, [Validators.required]),
      'otherRevenueIncomeAmount': new FormControl(null, [Validators.required]),
      'assignedRevenuesAmount': new FormControl({ value: null, disabled: true }),
      'stateAssignedRevenueAmount': new FormControl(null),
      'sFCGrantsAmount': new FormControl(null, [Validators.required]),
      'octroiCompensationAmount': new FormControl(null, [Validators.required]),
      'otherStateGovernmentTransfersAmount': new FormControl(null, [Validators.required]),
      'cFCGrantAmount': new FormControl(null, [Validators.required]),
      'otherCentralGovernmentTransfersAmount': new FormControl(null, [Validators.required]),
      'otherSalesAmount': new FormControl(null, [Validators.required]),
      'capitalReceiptsAmount': new FormControl({ value: null, disabled: true }),
      'municipalLandAmount': new FormControl(null, [Validators.required]),
      'loanAmount': new FormControl(null, [Validators.required]),
      'stateCapitalAccountAmount': new FormControl(null, [Validators.required]),
      'centralCapitalAccountAmount': new FormControl(null, [Validators.required]),
      'othersCapitalReceiptAmount': new FormControl(null, [Validators.required]),
      // 'subYears': new FormArray([this.createSubYear(), this.createSubYear()])

    });

  }

  createSubYear(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl(null),
      'sessionYear': new FormControl(null, [Validators.required])
    })
  }

  initFinanceLayerFirstFormModel() {
    this.financeLayerFirstFormModel = this.formBuilder.group({
      "id": new FormControl(),
      "heading": new FormControl(),
      "amount": new FormControl(),
      "description": new FormControl(),
      "year": new FormControl()
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
  save() {
    let receipts = this.revenueCapitalReceipt.value;
    let rcReceipt: revenueCapitalReceiptDTO = {
      centralCapitalAccountGrants: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      cfcGrants: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      details: {
        fy1: '',
        fy2: '',
        fy3: '',
        fy4: '',
        fy5: '',
        fy6: '',
        iaId: this.service1.step1Report.id,
        id: this.revId != 0 ? this.revId : null
      },
      feesAndFines: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      fy1L: '',
      fy2L: '',
      fy3L: '',
      iaId: this.service1.step1Report.id,
      id: this.revId != 0 ? this.revId : null,
      incomeFromInterest: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      loans: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      octraiCompensation: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherCapitalReceipts: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherCentralGovtTransfers: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherNonTaxRevenue: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherRevenueIncome: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherStateGovtTransfers: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      otherTax: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      others: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      propertyTax: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      saleOfMunicipalLand: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      stateAssignedRevenue: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      stateCapitalAccountGrants: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      stateFCGrants: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      },
      userCharges: {

        fy1Amt: 0,
        fy2Amt: 0,
        fy3Amt: 0,
        fy4Amt: 0,
        fy5Amt: 0,
        fy6Amt: 0,
        id: this.revId != 0 ? this.revId : null,
        type: ''
      }
    }
    rcReceipt.iaId = this.service1.step1Report.id
    rcReceipt.fy1L = receipts[0].financeYear
    rcReceipt.fy3L = receipts[1].financeYear
    rcReceipt.fy2L = receipts[2].financeYear
    // rcReceipt.propertyTax.expenditureType = receipts[0].actualExpenditureData
    rcReceipt.propertyTax.fy1Amt = receipts[0].receipts[0].propertyTaxAmount
    rcReceipt.propertyTax.fy2Amt = receipts[0].receipts[1].propertyTaxAmount
    rcReceipt.propertyTax.fy3Amt = receipts[1].receipts[0].propertyTaxAmount
    rcReceipt.propertyTax.fy4Amt = receipts[1].receipts[1].propertyTaxAmount
    rcReceipt.propertyTax.fy5Amt = receipts[2].receipts[0].propertyTaxAmount
    rcReceipt.propertyTax.fy6Amt = receipts[2].receipts[1].propertyTaxAmount
    rcReceipt.propertyTax.type = "PROPERTY_TAX"
    rcReceipt.propertyTax.id = this.propertyTaxAmountId
    // rcReceipt.userCharges.expenditureType
    rcReceipt.userCharges.fy1Amt = receipts[0].receipts[0].userChargesAmount
    rcReceipt.userCharges.id = this.userChargesAmountId
    rcReceipt.userCharges.fy2Amt = receipts[0].receipts[1].userChargesAmount
    rcReceipt.userCharges.fy3Amt = receipts[1].receipts[0].userChargesAmount
    rcReceipt.userCharges.fy4Amt = receipts[1].receipts[1].userChargesAmount
    rcReceipt.userCharges.fy5Amt = receipts[2].receipts[0].userChargesAmount
    rcReceipt.userCharges.fy6Amt = receipts[2].receipts[1].userChargesAmount
    rcReceipt.userCharges.type = "USER_CHARGES"
    // rcReceipt.stateFCGrants.expenditureType
    rcReceipt.stateFCGrants.fy1Amt = receipts[0].receipts[0].sFCGrantsAmount
    rcReceipt.stateFCGrants.id = this.sFCGrantsAmountId
    rcReceipt.stateFCGrants.fy2Amt = receipts[0].receipts[1].sFCGrantsAmount
    rcReceipt.stateFCGrants.fy3Amt = receipts[1].receipts[0].sFCGrantsAmount
    rcReceipt.stateFCGrants.fy4Amt = receipts[1].receipts[1].sFCGrantsAmount
    rcReceipt.stateFCGrants.fy5Amt = receipts[2].receipts[0].sFCGrantsAmount
    rcReceipt.stateFCGrants.fy6Amt = receipts[2].receipts[1].sFCGrantsAmount
    rcReceipt.stateFCGrants.type = "STATE_FC_GRANTS"
    // rcReceipt.stateCapitalAccountGrants.expenditureType
    rcReceipt.stateCapitalAccountGrants.fy1Amt = receipts[0].receipts[0].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.id = this.stateCapitalAccountAmountId
    rcReceipt.stateCapitalAccountGrants.fy2Amt = receipts[0].receipts[1].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.fy3Amt = receipts[1].receipts[0].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.fy4Amt = receipts[1].receipts[1].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.fy5Amt = receipts[2].receipts[0].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.fy6Amt = receipts[2].receipts[1].stateCapitalAccountAmount
    rcReceipt.stateCapitalAccountGrants.type = "STATE_CAPITAL_ACCOUNT_GRANTS"
    // rcReceipt.stateAssignedRevenue.expenditureType
    rcReceipt.stateAssignedRevenue.fy1Amt = receipts[0].receipts[0].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.id = this.stateAssignedRevenueAmountId
    rcReceipt.stateAssignedRevenue.fy2Amt = receipts[0].receipts[1].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.fy3Amt = receipts[1].receipts[0].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.fy4Amt = receipts[1].receipts[1].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.fy5Amt = receipts[2].receipts[0].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.fy6Amt = receipts[2].receipts[1].stateAssignedRevenueAmount
    rcReceipt.stateAssignedRevenue.type = "STATE_ASSIGNED_REVENUE"
    // rcReceipt.saleOfMunicipalLand.expenditureType
    rcReceipt.saleOfMunicipalLand.fy1Amt = receipts[0].receipts[0].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.id = this.municipalLandAmountId
    rcReceipt.saleOfMunicipalLand.fy2Amt = receipts[0].receipts[1].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.fy3Amt = receipts[1].receipts[0].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.fy4Amt = receipts[1].receipts[1].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.fy5Amt = receipts[2].receipts[0].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.fy6Amt = receipts[2].receipts[1].municipalLandAmount
    rcReceipt.saleOfMunicipalLand.type = "SALES_OF_MUNICIPAL_LAND"
    // rcReceipt.others.expenditureType
    rcReceipt.others.fy1Amt = receipts[0].receipts[0].otherSalesAmount
    rcReceipt.others.id = this.otherSalesAmountId
    rcReceipt.others.fy2Amt = receipts[0].receipts[1].otherSalesAmount
    rcReceipt.others.fy3Amt = receipts[1].receipts[0].otherSalesAmount
    rcReceipt.others.fy4Amt = receipts[1].receipts[1].otherSalesAmount
    rcReceipt.others.fy5Amt = receipts[2].receipts[0].otherSalesAmount
    rcReceipt.others.fy6Amt = receipts[2].receipts[1].otherSalesAmount
    rcReceipt.others.type = "OTHERS"
    // rcReceipt.otherTax.expenditureType
    rcReceipt.otherTax.fy1Amt = receipts[0].receipts[0].otherTaxAmount
    rcReceipt.otherTax.id = this.otherTaxAmountId
    rcReceipt.otherTax.fy2Amt = receipts[0].receipts[1].otherTaxAmount
    rcReceipt.otherTax.fy3Amt = receipts[1].receipts[0].otherTaxAmount
    rcReceipt.otherTax.fy4Amt = receipts[1].receipts[1].otherTaxAmount
    rcReceipt.otherTax.fy5Amt = receipts[2].receipts[0].otherTaxAmount
    rcReceipt.otherTax.fy6Amt = receipts[2].receipts[1].otherTaxAmount
    rcReceipt.otherTax.type = "OTHER_TAX"
    // rcReceipt.otherStateGovtTransfers.expenditureType
    rcReceipt.otherStateGovtTransfers.fy1Amt = receipts[0].receipts[0].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.id = this.otherStateGovernmentTransfersAmountId
    rcReceipt.otherStateGovtTransfers.fy2Amt = receipts[0].receipts[1].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.fy3Amt = receipts[1].receipts[0].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.fy4Amt = receipts[1].receipts[1].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.fy5Amt = receipts[2].receipts[0].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.fy6Amt = receipts[2].receipts[1].otherStateGovernmentTransfersAmount
    rcReceipt.otherStateGovtTransfers.type = "OTHER_STATE_GOVT_TRANSFERS"
    // rcReceipt.otherRevenueIncome.expenditureType
    rcReceipt.otherRevenueIncome.fy1Amt = receipts[0].receipts[0].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.id = this.otherRevenueIncomeAmountId
    rcReceipt.otherRevenueIncome.fy2Amt = receipts[0].receipts[1].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.fy3Amt = receipts[1].receipts[0].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.fy4Amt = receipts[1].receipts[1].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.fy5Amt = receipts[2].receipts[0].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.fy6Amt = receipts[2].receipts[1].otherRevenueIncomeAmount
    rcReceipt.otherRevenueIncome.type = "OTHER_REVENUE_INCOME"
    // rcReceipt.centralCapitalAccountGrants.expenditureType
    rcReceipt.centralCapitalAccountGrants.fy1Amt = receipts[0].receipts[0].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.id = this.centralCapitalAccountAmountId
    rcReceipt.centralCapitalAccountGrants.fy2Amt = receipts[0].receipts[1].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.fy3Amt = receipts[1].receipts[0].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.fy4Amt = receipts[1].receipts[1].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.fy5Amt = receipts[2].receipts[0].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.fy6Amt = receipts[2].receipts[1].centralCapitalAccountAmount
    rcReceipt.centralCapitalAccountGrants.type = "CENTRAL_CAPITAL_ACCOUNT_GRANTS"
    // rcReceipt.cfcGrants.expenditureType
    rcReceipt.cfcGrants.fy1Amt = receipts[0].receipts[0].cFCGrantAmount
    rcReceipt.cfcGrants.id = this.cFCGrantAmountId
    rcReceipt.cfcGrants.fy2Amt = receipts[0].receipts[1].cFCGrantAmount
    rcReceipt.cfcGrants.fy3Amt = receipts[1].receipts[0].cFCGrantAmount
    rcReceipt.cfcGrants.fy4Amt = receipts[1].receipts[1].cFCGrantAmount
    rcReceipt.cfcGrants.fy5Amt = receipts[2].receipts[0].cFCGrantAmount
    rcReceipt.cfcGrants.fy6Amt = receipts[2].receipts[1].cFCGrantAmount
    rcReceipt.cfcGrants.type = "CFC_GRANTS"
    rcReceipt.details.fy1 = receipts[0].receipts[0].year
    rcReceipt.details.id = this.detailsId
    rcReceipt.details.fy2 = receipts[0].receipts[1].year
    rcReceipt.details.fy3 = receipts[1].receipts[0].year
    rcReceipt.details.fy4 = receipts[1].receipts[1].year
    rcReceipt.details.fy5 = receipts[2].receipts[0].year
    rcReceipt.details.fy6 = receipts[2].receipts[1].year
    rcReceipt.details.iaId = this.service1.step1Report.id
    // rcReceipt.feesAndFines.expenditureType
    rcReceipt.feesAndFines.fy1Amt = receipts[0].receipts[0].finesAmount
    rcReceipt.feesAndFines.id = this.finesAmountId
    rcReceipt.feesAndFines.fy2Amt = receipts[0].receipts[1].finesAmount
    rcReceipt.feesAndFines.fy3Amt = receipts[1].receipts[0].finesAmount
    rcReceipt.feesAndFines.fy4Amt = receipts[1].receipts[1].finesAmount
    rcReceipt.feesAndFines.fy5Amt = receipts[2].receipts[0].finesAmount
    rcReceipt.feesAndFines.fy6Amt = receipts[2].receipts[1].finesAmount
    rcReceipt.feesAndFines.type = "FEES_AND_FINES"
    // rcReceipt.incomeFromInterest.expenditureType
    rcReceipt.incomeFromInterest.fy1Amt = receipts[0].receipts[0].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.id = this.incomeFromInterestAmountId
    rcReceipt.incomeFromInterest.fy2Amt = receipts[0].receipts[1].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.fy3Amt = receipts[1].receipts[0].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.fy4Amt = receipts[1].receipts[1].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.fy5Amt = receipts[2].receipts[0].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.fy6Amt = receipts[2].receipts[1].incomeFromInterestAmount
    rcReceipt.incomeFromInterest.type = "INCOME_FROM_INTEREST"
    // rcReceipt.loans.expenditureType
    rcReceipt.loans.fy1Amt = receipts[0].receipts[0].loanAmount
    rcReceipt.loans.id = this.loanAmountId
    rcReceipt.loans.fy2Amt = receipts[0].receipts[1].loanAmount
    rcReceipt.loans.fy3Amt = receipts[1].receipts[0].loanAmount
    rcReceipt.loans.fy4Amt = receipts[1].receipts[1].loanAmount
    rcReceipt.loans.fy5Amt = receipts[2].receipts[0].loanAmount
    rcReceipt.loans.fy6Amt = receipts[2].receipts[1].loanAmount
    rcReceipt.loans.type = "LOANS"
    // rcReceipt.octraiCompensation.expenditureType
    rcReceipt.octraiCompensation.fy1Amt = receipts[0].receipts[0].octroiCompensationAmount
    rcReceipt.octraiCompensation.id = this.octroiCompensationAmountId
    rcReceipt.octraiCompensation.fy2Amt = receipts[0].receipts[1].octroiCompensationAmount
    rcReceipt.octraiCompensation.fy3Amt = receipts[1].receipts[0].octroiCompensationAmount
    rcReceipt.octraiCompensation.fy4Amt = receipts[1].receipts[1].octroiCompensationAmount
    rcReceipt.octraiCompensation.fy5Amt = receipts[2].receipts[0].octroiCompensationAmount
    rcReceipt.octraiCompensation.fy6Amt = receipts[2].receipts[1].octroiCompensationAmount
    rcReceipt.octraiCompensation.type = "OCTRAI_COMPENSATION"
    // rcReceipt.otherCapitalReceipts.expenditureType
    rcReceipt.otherCapitalReceipts.fy1Amt = receipts[0].receipts[0].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.id = this.othersCapitalReceiptAmountId
    rcReceipt.otherCapitalReceipts.fy2Amt = receipts[0].receipts[1].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.fy3Amt = receipts[1].receipts[0].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.fy4Amt = receipts[1].receipts[1].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.fy5Amt = receipts[2].receipts[0].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.fy6Amt = receipts[2].receipts[1].othersCapitalReceiptAmount
    rcReceipt.otherCapitalReceipts.type = "OTHER_CAPITAL_RECEIPTS"
    // rcReceipt.otherCentralGovtTransfers.expenditureType
    rcReceipt.otherCentralGovtTransfers.fy1Amt = receipts[0].receipts[0].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.id = this.otherCentralGovernmentTransfersAmountId
    rcReceipt.otherCentralGovtTransfers.fy2Amt = receipts[0].receipts[1].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.fy3Amt = receipts[1].receipts[0].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.fy4Amt = receipts[1].receipts[1].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.fy5Amt = receipts[2].receipts[0].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.fy6Amt = receipts[2].receipts[1].otherCentralGovernmentTransfersAmount
    rcReceipt.otherCentralGovtTransfers.type = "OTHER_CENTRAL_GOVT_TRANSFERS"
    // rcReceipt.otherNonTaxRevenue.expenditureType
    rcReceipt.otherNonTaxRevenue.fy1Amt = receipts[0].receipts[0].othersAmount
    rcReceipt.otherNonTaxRevenue.id = this.othersAmountId
    rcReceipt.otherNonTaxRevenue.fy2Amt = receipts[0].receipts[1].othersAmount
    rcReceipt.otherNonTaxRevenue.fy3Amt = receipts[1].receipts[0].othersAmount
    rcReceipt.otherNonTaxRevenue.fy4Amt = receipts[1].receipts[1].othersAmount
    rcReceipt.otherNonTaxRevenue.fy5Amt = receipts[2].receipts[0].othersAmount
    rcReceipt.otherNonTaxRevenue.fy6Amt = receipts[2].receipts[1].othersAmount
    rcReceipt.otherNonTaxRevenue.type = "OTHER_NON_TAX_REVENUE"


    this.service.createUpdateRevenueCapitalReceipt(rcReceipt).subscribe((res: any) => {
      this.revenueCapitalReceiptForm.disable()
      this.revId = res.id
      this.propertyTaxAmountId = res.propertyTax.id
      this.userChargesAmountId = res.userCharges.id
      this.sFCGrantsAmountId = res.stateFCGrants.id
      this.stateCapitalAccountAmountId = res.stateCapitalAccountGrants.id
      this.stateAssignedRevenueAmountId = res.stateAssignedRevenue.id
      this.municipalLandAmountId = res.saleOfMunicipalLand.id
      this.otherSalesAmountId = res.others.id
      this.otherTaxAmountId = res.otherTax.id
      this.otherStateGovernmentTransfersAmountId = res.otherStateGovtTransfers.id
      this.otherRevenueIncomeAmountId = res.otherRevenueIncome.id
      this.centralCapitalAccountAmountId = res.centralCapitalAccountGrants.id
      this.cFCGrantAmountId = res.cfcGrants.id
      this.detailsId = res.details.id
      this.finesAmountId = res.feesAndFines.id
      this.incomeFromInterestAmountId = res.incomeFromInterest.id
      this.loanAmountId = res.loans.id
      this.octroiCompensationAmountId = res.octraiCompensation.id
      this.othersCapitalReceiptAmountId = res.otherCapitalReceipts.id
      this.otherCentralGovernmentTransfersAmountId = res.otherCentralGovtTransfers.id
      this.othersAmountId = res.otherNonTaxRevenue.id
      
    })
    // 
  }
  onKeyUpSetRevenueReceiptAmount(index: number, subIndex: number) {
    let control = this.revenueCapitalReceipt['controls'][index]['controls']['receipts']['controls'][subIndex];

    var ownRevenueReceiptsAmount = control.controls.ownRevenueReceiptsAmount.value;
    var otherRevenueReceiptsAmount = control.controls.otherRevenueReceiptsAmount.value;
    var assignedRevenuesAmount = control.controls.assignedRevenuesAmount.value;
    var amount = ownRevenueReceiptsAmount + otherRevenueReceiptsAmount + assignedRevenuesAmount;
    control.patchValue({
      'revenueReceiptsAmount': amount
    });
    this.setTotalReceiptsAmount(index, subIndex)
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
  enableForm() {
    this.revenueCapitalReceiptForm.enable()
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
    this.setTotalReceiptsAmount(index, subIndex)

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


