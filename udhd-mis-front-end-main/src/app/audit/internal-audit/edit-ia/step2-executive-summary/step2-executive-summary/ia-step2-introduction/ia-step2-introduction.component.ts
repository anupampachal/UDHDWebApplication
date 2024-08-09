import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-introduction',
  templateUrl: './ia-step2-introduction.component.html',
  styleUrls: ['./ia-step2-introduction.component.css']
})
export class EditIaStep2IntroductionComponent implements OnInit {
  step2IntroductionForm!: FormGroup;
  showFormError?: ValidationErrors;
  @Input() set st1Repo(value: any) {
    if(value) {
      this.step2IntroductionForm.patchValue({
        municipalityName: value.ulb.name,
        periodCovered: value.startDate +' - '+value.endDate,
      })
    }
  }
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private step1Service: Step1ReportService,
    private route: ActivatedRoute,

  ) { }
  mName = ""

  ngOnInit(): void {
    setTimeout(() => {

      this.mName = this.step1Service.step1Report.ulbName
    }, 1000);

    this.step2IntroductionForm = this.fb.group({
      internalAuditId:[],
      id: [],
      municipalityName: new FormControl({value:"", disabled: true}),
      executiveOfficerNameForPeriod: new FormControl(null, [Validators.required]),
      periodCovered: new FormControl(null,[Validators.required]),
    });
    // this.step2IntroductionForm.patchValue({
    //   municipalityName: this.step1Service.step1Report.ulbName
    // })
     this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getExSummaryByIaId(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )
  }

  setData(dt: any) {
    this.step2IntroductionForm.patchValue({
      id: dt.id,
      internalAuditId: dt.internalAuditId,
      executiveOfficerNameForPeriod : dt.executiveOfficerNameForPeriod,
      periodCovered : dt.periodCovered,
    })
  }

  onSubmit() {
    this.service.createUpdateStep2Intro({
      ia: this.step1Service.step1Report.id,
      executiveOfficerNameForPeriod: this.step2IntroductionForm.value.executiveOfficerNameForPeriod,
      periodCovered: this.step2IntroductionForm.value.periodCovered
    }).subscribe((res) => {this.step2IntroductionForm.disable()

    })

  }
  enableForm() {
    this.step2IntroductionForm.enable()
  }
}
