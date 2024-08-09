import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { FileSaverService } from 'ngx-filesaver';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step5Service } from '../../../services/step-5.service';
import { IAStatusOfImplementationOfDeasDTO } from './status-of-deas-implementation.model';

@Component({
  selector: 'app-view-deas',
  templateUrl: './view-deas.component.html',
  styleUrls: ['./view-deas.component.css']
})
export class ViewDeasComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  descForm!: FormGroup;
  statusOfDeasObs$!: Observable<IAStatusOfImplementationOfDeasDTO>;

  doubleEntryAccountingSystemForm!: FormGroup
  deasArrayForm!: FormGroup
  showFormError?: ValidationErrors;
  budgetaryList = ["PROPERTY TAX REGISTER", "FIXED ASSETS REGISTER", "OPENING BALANCE SHEET", "ANNUAL FINANCIAL STATEMENT"];
  dRegId = 0
  dId = 0
  deasId = 0
  statusOfDeasId = 0
  mncommittee = ""
  fileId = ""
  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService,
    private _FileSaverService: FileSaverService


  ) { }
  ngOnInit(): void {
    this.statusOfDeasObs$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getStatusOfImpOfDeas(param['id'])),
      //tap((dt) => this.setData(dt)),
      shareReplay(2));

  }
  /*
    ngOnInit(): void {
      this.routeArray = this.route.pathFromRoot;
      this.descForm = this.fb.group({
        description: [null, Validators.required]
      })
      this.doubleEntryAccountingSystemForm = this.fb.group({
        id: [],
        iaId: [],
        startDate: [null, Validators.required],
        endDate: [null, Validators.required],
        tallySerialNo: [null, Validators.required],
        tallyId: [null, Validators.required],
      })
      this.deasArrayForm = this.fb.group({
        financeYear: [],
        details: new FormArray([]),
      })
      this.addBudgetFields()
      //
      this.route.params.pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.service.getStatusOfImpOfDeas(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)).subscribe(
          () => { },
          error => { }
        )
  
      this.descForm.disable()
      this.doubleEntryAccountingSystemForm.disable()
    }*/

  addParticulars(desc: string, val: string): FormGroup {
    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'annualFinancialStatement': new FormControl({ value: val, disabled: true }),
      'description': new FormControl({ value: desc, disabled: true }),
      'deasRegisterId': new FormControl({ value: null, disabled: true }),
    });
  }
  setData(data: any) {
    
    this.descForm.patchValue({
      description: JSON.parse(data.description).dto
    })
    this.mncommittee = JSON.parse(data.statusOfMunicipalAccountsCommittee).statusOfMunicipalAccCommitteeText
    this.fileId = data.statusOfMunicipalAccCommitteeFileId
    this.doubleEntryAccountingSystemForm.patchValue({
      iaId: data.tallyDetails.iaId,
      id: data.tallyDetails.id,
      // startDate: new Date(moment(data.tallyDetails.periodFrom).format("MM/DD/YYYY")),
      //endDate: new Date(moment(data.tallyDetails.periodTo).format("MM/DD/YYYY")),
      tallySerialNo: data.tallyDetails.tallySerialNo,
      tallyId: data.tallyDetails.tallyId
    })
    
    this.deasArrayForm.patchValue({
      financeYear: data.financeStatementStatus.particulars
    })
    this.deasFormArrayDetails.controls[0].patchValue({ annualFinancialStatement: data.financeStatementStatus.propertyTaxRegister })
    this.deasFormArrayDetails.controls[1].patchValue({ annualFinancialStatement: data.financeStatementStatus.fixedAssetsRegister })
    this.deasFormArrayDetails.controls[2].patchValue({ annualFinancialStatement: data.financeStatementStatus.openingBalanceRegister })
    this.deasFormArrayDetails.controls[3].patchValue({ annualFinancialStatement: data.financeStatementStatus.annualFinanceStatement })
    for (let i = 0; i < data.particularsWithFinanceStatus.length; i++) {
      this.deasFormArrayDetails.push(this.addParticulars(data.particularsWithFinanceStatus[i].particulars, data.particularsWithFinanceStatus[i].value))
    }
    this.dId = data.financeStatementStatus.id
    this.dRegId = data.financeStatementStatus.statusOfDeasId
    this.deasId = data.tallyDetails.id
    this.statusOfDeasId = data.tallyDetails.statusOfDeasId
    this.deasArrayForm.disable()

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

  saveDEASRegister() {

    this.service.createUpdateFinStatus({
      iaId: this.service1.step1Report.id,
      statusOfDeasId: this.dRegId != 0 ? this.dRegId : null,
      id: this.dId != 0 ? this.dId : null,
      particulars: this.deasArrayForm.value.financeYear,
      propertyTaxRegister: this.deasFormArrayDetails.controls[0].value.annualFinancialStatement,
      fixedAssetsRegister: this.deasFormArrayDetails.controls[1].value.annualFinancialStatement,
      openingBalanceRegister: this.deasFormArrayDetails.controls[2].value.annualFinancialStatement,
      annualFinanceStatement: this.deasFormArrayDetails.controls[3].value.annualFinancialStatement,
    }).subscribe((res: any) => {
      this.dRegId = res.statusOfDeasId,
        this.dId = res.id
      this.deasArrayForm.disable()
    })
  }

  createArrayForm(desc: string): FormGroup {
    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'annualFinancialStatement': new FormControl({ value: null, disabled: true }),
      'description': new FormControl({ value: desc, disabled: true }),
      'deasRegisterId': new FormControl({ value: null, disabled: true }),
    });
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
  downloadFile() {
    this.service.getStImpFile(this.service1.step1Report.id)
      .pipe(
        map(res => this._FileSaverService.save(res, 'municipalCommitteeFile.pdf'))
      )
      .subscribe(res => {

      })
  }
  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }

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
  }
  getDataInString(data: string) {
    return JSON.parse(data).statusOfMunicipalAccCommitteeText;
  }
  getDescription(data: string) {
    return JSON.parse(data).dto;
  }
}
