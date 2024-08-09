import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-acknowledgement',
  templateUrl: './ia-step2-acknowledgement.component.html',
  styleUrls: ['./ia-step2-acknowledgement.component.css']
})
export class EditIaStep2AcknowledgementComponent implements OnInit {
  acknowledgementForm!: FormGroup;
  ackId: any;
  showFormError?: ValidationErrors;
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }
  ngOnInit(): void {
    this.acknowledgementForm = this.fb.group({
      acknowledgement: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
    });

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
    this.ackId = dt.id
    this.acknowledgementForm.patchValue({
      acknowledgement: dt.acknowledgement

    })
  }
  onSubmit() {
    this.service.createUpdateCoack({
      id: this.ackId ? this.ackId : null,
      text: this.acknowledgementForm.value.acknowledgement,
      iaId: this.service1.step1Report.id,
      type: "ACKNOWLEDGEMENT"
    }).subscribe((res) => {
      this.ackId = res.id

      this.acknowledgementForm.disable();
    })
  }
  enableForm() {
    this.acknowledgementForm.enable()
  }
}
