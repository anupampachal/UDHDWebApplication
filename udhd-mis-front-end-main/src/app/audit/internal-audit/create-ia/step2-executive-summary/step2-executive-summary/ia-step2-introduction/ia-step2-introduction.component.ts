import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'app-ia-step2-introduction',
  templateUrl: './ia-step2-introduction.component.html',
  styleUrls: ['./ia-step2-introduction.component.css']
})
export class IaStep2IntroductionComponent implements OnInit {
  step2IntroductionForm!: FormGroup;
  showFormError?: ValidationErrors;
  introId: any;
  @Input() set st1Repo(value: any) {
    console.log('value: ',value)
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
    private step1Service: Step1ReportService
  ) { }

  ngOnInit(): void {
    this.step2IntroductionForm = this.fb.group({
      id: [],
      municipalityName: new FormControl(),
      executiveOfficerNameForPeriod: new FormControl(null, [Validators.required]),
      periodCovered: new FormControl(null,[Validators.required]),
    });
    this.step2IntroductionForm.patchValue({
      municipalityName: this.step1Service.step1Report.ulbName
    })
  }

  onSubmit() {
    this.service.createUpdateStep2Intro({
      id: this.introId? this.introId : null,
      ia: this.step1Service.step1Report.id,
      executiveOfficerNameForPeriod: this.step2IntroductionForm.value.executiveOfficerNameForPeriod,
      periodCovered: this.step2IntroductionForm.value.periodCovered
    }).subscribe((res) => {
      // 
      this.introId = res.id
      this.step2IntroductionForm.disable()

    })

  }
  enableForm() {

    this.step2IntroductionForm.enable()
  }
}
