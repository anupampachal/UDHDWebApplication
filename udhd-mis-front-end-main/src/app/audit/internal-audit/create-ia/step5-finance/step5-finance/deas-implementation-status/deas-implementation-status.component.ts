import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { DoubleEntryAccountingSystemRegister } from 'src/app/audit/internal-audit/models/double-entry-accounting-system-register';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'app-deas-implementation-status',
  templateUrl: './deas-implementation-status.component.html',
  styleUrls: ['./deas-implementation-status.component.css']
})
export class DeasImplementationStatusComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  descForm!: FormGroup;
  doubleEntryAccountingSystemForm!: FormGroup
  deasArrayForm!: FormGroup
  showFormError?: ValidationErrors;
  budgetaryList = ["PROPERTY TAX REGISTER", "FIXED ASSETS REGISTER", "OPENING BALANCE SHEET", "ANNUAL FINANCIAL STATEMENT"];

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService

  ) { }
  isDisable = false
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.descForm = this.fb.group({
      description: [null, Validators.required]
    })
    this.doubleEntryAccountingSystemForm = this.fb.group({
      id: [null],
      nameOfImplAgency: [null, Validators.required],
      startDate: [null, Validators.required],
      endDate: [null, Validators.required],
      tallySerialNo: [null, Validators.required],
      tallyId: [null, Validators.required],
    })
    this.deasArrayForm = this.fb.group({
      financeYearId: [],
      financeYear: [],
      details: new FormArray([]),
    })
    this.addBudgetFields()
    // 

  }
  addBudgetFields() {
    this.budgetaryList.forEach(field => {
      this.deasFormArrayDetails.push(this.createArrayForm(field));
    })
  }
  get deasFormArrayDetails(): FormArray {
    return this.deasArrayForm.get('details') as FormArray
  }
  saveDescription() {
    this.service.createImpOfDeas(this.service1.step1Report.id, {
      dto: this.descForm.value.description
    }).subscribe(res => {
      
      this.descForm.disable()
    })
  }
  enableDeasForm() {
    this.doubleEntryAccountingSystemForm.enable()
  }
  enableForm() {
    this.descForm.enable()
  }
  doubleEntryAccountingSystem: any
  dRegId = 0
  dId = 0
  saveDEASRegister() {
    
    

    this.service.createUpdateFinStatus({
      iaId: this.service1.step1Report.id,
      statusOfDeasId: this.dRegId!=0 ?this.dRegId: null,
      id: this.dId!=0 ? this.dId : null,

      particulars: this.deasArrayForm.value.financeYear,
      propertyTaxRegister: this.deasFormArrayDetails.controls[0].value.annualFinancialStatement,
      fixedAssetsRegister: this.deasFormArrayDetails.controls[1].value.annualFinancialStatement,
      openingBalanceRegister: this.deasFormArrayDetails.controls[2].value.annualFinancialStatement,
      annualFinanceStatement: this.deasFormArrayDetails.controls[3].value.annualFinancialStatement,
    }).subscribe((res: any) => {
      this.dRegId = res.statusOfDeasId,
        this.dId = res.id
        this.isDisable = true
      this.deasArrayForm.disable()
    })
  }

  saveParticulars(i: number) {

  }
  createArrayForm(desc: string): FormGroup {
    return this.fb.group({
      'id': new FormControl(null),
      'annualFinancialStatement': new FormControl(null),
      'description': new FormControl(desc),
      'deasRegisterId': new FormControl(),
    });
  }


  deleteControl(descriptionIndex: number) {
    this.deasFormArrayDetails.removeAt(descriptionIndex)
  }
  strIndex: number[] = []
partId: any
  onSubmit(i: number) {
    
    
    this.service.createUpdateParticularsWithFinStatus({
      iaId: this.service1.step1Report.id,
      id: this.strIndex[i] != undefined? this.strIndex[i]: null,
      statusOfDeasId: this.partId != undefined? this.partId: null,
      particulars: this.deasFormArrayDetails.controls[i].value.description,
      value: this.deasFormArrayDetails.controls[i].value.annualFinancialStatement
    }).subscribe((res: any) => {
      // 
      this.partId = res.statusOfDeasId
      this.strIndex[i] = res.id
      this.deasFormArrayDetails.controls[i].disable()
    })
  }
  enableControl(i: number) {
    this.deasFormArrayDetails.controls[i].enable()
    // this.disableSaveIcon = false
  }


  pushNew() {
    const description = "";
    this.deasFormArrayDetails.push(this.createArrayForm(description));

  }

  initDoubleEntryAccountingSystemFormModel() {
    this.doubleEntryAccountingSystemForm = this.fb.group({
      'id': new FormControl(),
      'nameOfImplAgency': new FormControl(null, [Validators.required]),
      'startDate': new FormControl(null, [Validators.required]),
      'endDate': new FormControl(null, [Validators.required]),
      'tallySerialNo': new FormControl(null, [Validators.required]),
      'tallyId': new FormControl(null, [Validators.required]),

    });
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
  deasId = 0
  statusOfDeasId = 0
  saveDeas() {
    this.doubleEntryAccountingSystemForm.value.startDate = this.getDateInDDMMYYYY(this.doubleEntryAccountingSystemForm.getRawValue().startDate)
    this.doubleEntryAccountingSystemForm.value.endDate = this.getDateInDDMMYYYY(this.doubleEntryAccountingSystemForm.getRawValue().endDate)
    this.service.createUpdateTallyStatus({
      iaId: this.service1.step1Report.id,
      id: this.deasId != 0 ? this.deasId : null,
      periodFrom: this.doubleEntryAccountingSystemForm.value.startDate,
      periodTo: this.doubleEntryAccountingSystemForm.value.endDate,
      statusOfDeasId: this.statusOfDeasId != 0 ? this.statusOfDeasId : null,
      tallyId: this.doubleEntryAccountingSystemForm.value.tallyId,
      tallySerialNo: this.doubleEntryAccountingSystemForm.value.tallySerialNo,
    }).subscribe((res: any) => {
      this.deasId = res.id
      this.statusOfDeasId = res.statusOfDeasId
      this.doubleEntryAccountingSystemForm.disable()
    })
  }
  enableDeasArrayForm() {
    this.deasArrayForm.enable()
    this.isDisable = false
  }
}
