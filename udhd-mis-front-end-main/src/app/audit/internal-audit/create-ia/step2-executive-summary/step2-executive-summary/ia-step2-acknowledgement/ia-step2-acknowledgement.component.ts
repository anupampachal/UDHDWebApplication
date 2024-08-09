import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'app-ia-step2-acknowledgement',
  templateUrl: './ia-step2-acknowledgement.component.html',
  styleUrls: ['./ia-step2-acknowledgement.component.css']
})
export class IaStep2AcknowledgementComponent implements OnInit {
  acknowledgementForm!:FormGroup;
  showFormError?: ValidationErrors;
  ackId: any;
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit(): void {
    this.acknowledgementForm = this.fb.group({
      acknowledgement: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000),Validators.required])
    });
} onSubmit() {

  this.service.createUpdateCoack({
    id: this.ackId ? this.ackId: null,
    text: this.acknowledgementForm.value.acknowledgement,
    iaId: this.service1.step1Report.id,
    type: "ACKNOWLEDGEMENT"
  }).subscribe((res) => {
    this.ackId = res.id
    this.acknowledgementForm.disable()

  })
}
enableForm() {
  this.acknowledgementForm.enable()
}
}
