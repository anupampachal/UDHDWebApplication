import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'app-ia-step2-opinion',
  templateUrl: './ia-step2-opinion.component.html',
  styleUrls: ['./ia-step2-opinion.component.css']
})
export class IaStep2OpinionComponent implements OnInit {
  acknowledgementForm!: FormGroup;
  showFormError?: ValidationErrors;
  opId: any;
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService

  ) { }

  ngOnInit(): void {
    this.acknowledgementForm = this.fb.group({
      acknowledgement: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
    });
  }
  onSubmit() {


    this.service.createUpdateCoack({
      id: this.opId ? this.opId: null,
      text: this.acknowledgementForm.value.acknowledgement,
      iaId: this.service1.step1Report.id,
      type: "OPINION"
    }).subscribe((res) => {
      // 
      this.opId = res.id
      this.acknowledgementForm.disable()
    })
  }
  enableForm() {
    this.acknowledgementForm.enable()
  }
}
