import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { SworDTO } from 'src/app/audit/internal-audit/models/sworDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-audit-recommendation',
  templateUrl: './ia-step2-audit-recommendation.component.html',
  styleUrls: ['./ia-step2-audit-recommendation.component.css']
})
export class EditIaStep2AuditRecommendationComponent implements OnInit {
  auditRecommForm!: FormGroup;
  showFormError?: ValidationErrors;
  disableSaveIcon = false
  recomms: SworDTO[] = []
  strIndex: number[] = []

  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
  private route: ActivatedRoute,


  ) { }

  ngOnInit(): void {

    this.auditRecommForm = this.fb.group({
      descriptions: this.fb.array([])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getSworList(param['id'], "RECOMMENDATION")),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )

  }

  setData(dt: any) {
    this.recomms = dt
    for(let i = 0; i< this.recomms.length; i++) {
      this.strIndex[i] = this.recomms[i].sworId

      this.addDescription(this.recomms[i].text)
    }

  }
  onSubmit(i: number) {
    this.service.createUpdateSwor({
      sworId: this.strIndex[i] ? this.strIndex[i]: null ,
      text: this.descriptions.controls[i].value.description,
      ia: this.service1.step1Report.id,
      type: "RECOMMENDATION"
    }).subscribe((res) => {
      this.strIndex[i] = res.sworId
      this.disableSaveIcon = true
      this.descriptions.controls[i].disable()
    })
  }
  enableControl(i: number) {
    this.descriptions.controls[i].enable()
    this.disableSaveIcon = false
  }
  get descriptions() {
    return this.auditRecommForm.controls["descriptions"] as FormArray;
  }

  addDescription(descr: string) {
    const desc = this.fb.group({
      description: [descr, Validators.required]
    })
    this.descriptions.push(desc)

  }

  deleteDescription(descriptionIndex: number) {
    this.descriptions.removeAt(descriptionIndex)
  }
}
