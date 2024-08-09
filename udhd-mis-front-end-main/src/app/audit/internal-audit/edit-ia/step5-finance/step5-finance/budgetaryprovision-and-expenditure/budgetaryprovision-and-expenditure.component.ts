import { Component, OnInit, TemplateRef, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, AbstractControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, tap, switchMap, shareReplay } from 'rxjs/operators';
import { budgetariesDTO } from 'src/app/audit/internal-audit/models/budgetariesDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'budgetaryprovision-and-expenditure',
  templateUrl: './budgetaryprovision-and-expenditure.component.html',
  styleUrls: ['./budgetaryprovision-and-expenditure.component.css']
})
export class EditBudgetaryprovisionAndExpenditureComponent implements OnInit {


  budgetaryProvisionAndExpenditureForm!: FormGroup;
  arrayOfKeys: any[] = [];
  budgetaryList = ["Period", "Final/Revised Budget Data", "Actual Expenditure Data", "Savings (+)/Excess (-)"];
  isDisabled!: boolean;
  alerts = new Set();
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private service: Step5Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit() {
    this.budgetaryProvisionAndExpenditureForm = this.formBuilder.group({
      'budgetary': new FormArray([this.createBudgetaryForm(), this.createBudgetaryForm()
        , this.createBudgetaryForm()])
    });

    let temp = this.budgetaryProvision.controls[0] as any
    this.arrayOfKeys = Object.keys(temp.controls);
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.findBudgetById(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => {},
        error => {}
      )
  }

  bExid = 0
  bFinId = 0
  setData(budgetaryData: any) {
    // 
    let budgetaries = this.budgetaryProvision.value;
    // let budgetaryData = new budgetariesDTO()
    budgetaryData.iaId = this.service1.step1Report.id
    this.bExid = budgetaryData.actualExpenditureData.id
    this.bFinId = budgetaryData.finalBudgetData.id
    budgetaries[0].periodOfYear = budgetaryData.fy1
    budgetaries[1].periodOfYear = budgetaryData.fy3
    budgetaries[2].periodOfYear = budgetaryData.fy2
    budgetaries[0].actualExpenditureData = budgetaryData.actualExpenditureData.fy1
    budgetaries[1].actualExpenditureData = budgetaryData.actualExpenditureData.fy2
    budgetaries[2].actualExpenditureData = budgetaryData.actualExpenditureData.fy3
    budgetaries[0].finalOrRevisedData = budgetaryData.finalBudgetData.fy1
    budgetaries[1].finalOrRevisedData = budgetaryData.finalBudgetData.fy2
    budgetaries[2].finalOrRevisedData = budgetaryData.finalBudgetData.fy3
    for(let i=0; i<budgetaries.length; i++) {
      this.budgetaryProvision.at(i).patchValue(budgetaries[i])
    }
  }

  get budgetaryProvision() {
    return this.budgetaryProvisionAndExpenditureForm.controls['budgetary'] as FormArray
  }

  onKeyUpSetCloseExpenditureAmount(index: number) {
    let control = this.budgetaryProvision.controls[index];
    var finalOrRevisedData = control.value.finalOrRevisedData;
    var actualExpenditureData = control.value.actualExpenditureData;
    var totalSavingData = finalOrRevisedData - actualExpenditureData;
    control.patchValue({
      'totalSavingData': totalSavingData
    });
  }

  createBudgetaryForm(): FormGroup {
    return this.formBuilder.group({
      'id': new FormControl(null),
      'periodOfYear': new FormControl(null, [Validators.required]),
      'finalOrRevisedData': new FormControl(null, [Validators.required]),
      'actualExpenditureData': new FormControl(null, [Validators.required]),
      'totalSavingData': new FormControl({ value: null, disabled: true }),
    });
  }

  save() {
    if (this.budgetaryProvisionAndExpenditureForm.valid) {
      let budgetaries = this.budgetaryProvision.value;
      let budgetaryData = new budgetariesDTO()
      budgetaryData.iaId = this.service1.step1Report.id
      budgetaryData.fy1 = budgetaries[0].periodOfYear
      budgetaryData.fy3 = budgetaries[1].periodOfYear
      budgetaryData.fy2 = budgetaries[2].periodOfYear
      budgetaryData.actualExpenditureDataFy1 = budgetaries[0].actualExpenditureData
      budgetaryData.actualExpenditureDataFy2 = budgetaries[1].actualExpenditureData
      budgetaryData.actualExpenditureDataFy3 = budgetaries[2].actualExpenditureData
      budgetaryData.finRevBudgetDataFy1 = budgetaries[0].finalOrRevisedData
      budgetaryData.finRevBudgetDataFy2 = budgetaries[1].finalOrRevisedData
      budgetaryData.finRevBudgetDataFy3 = budgetaries[2].finalOrRevisedData
      this.service.createBudget(budgetaryData).subscribe(res => {
        this.budgetaryProvisionAndExpenditureForm.disable()
      })
    }
  }
  enableForm() {
    this.budgetaryProvisionAndExpenditureForm.enable()
  }

}
