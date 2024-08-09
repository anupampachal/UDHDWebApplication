import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step2Service } from '../../../services/step-2.service';

@Component({
  selector: 'app-introduction',
  templateUrl: './introduction.component.html',
  styleUrls: ['./introduction.component.css']
})
export class IntroductionComponent implements OnInit {
  step2IntroductionForm!: FormGroup;
  showFormError?: ValidationErrors;
  @Input() set st1Repo(value: any) {
    if (value) {
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
    this.mName = this.step1Service.step1Report.ulbName
    this.step2IntroductionForm = this.fb.group({
      internalAuditId: [],
      id: [],
      municipalityName: new FormControl({ value: null, disabled: true }),
      executiveOfficerNameForPeriod: new FormControl({ value: null, disabled: true }, [Validators.required]),
      periodCovered: new FormControl({ value: null, disabled: true }, [Validators.required]),
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getExSummaryByIaId(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => { },
      error => { }
    )
  }

  setData(dt: any) {
    this.step2IntroductionForm.patchValue({
      id: dt.id,
      municipalityName: dt.name,
      internalAuditId: dt.internalAuditId,
      executiveOfficerNameForPeriod: dt.executiveOfficerNameForPeriod,
      periodCovered: dt.periodCovered,
    })
    this.step2IntroductionForm.disable()
  }

}
