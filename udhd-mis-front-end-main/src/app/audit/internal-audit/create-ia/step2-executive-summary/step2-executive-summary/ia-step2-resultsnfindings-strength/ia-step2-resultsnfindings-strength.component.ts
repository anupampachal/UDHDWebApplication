import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'app-ia-step2-resultsnfindings-strength',
  templateUrl: './ia-step2-resultsnfindings-strength.component.html',
  styleUrls: ['./ia-step2-resultsnfindings-strength.component.css']
})
export class IaStep2ResultsnfindingsStrengthComponent implements OnInit {
    step2resultsnfindingsForm!: FormGroup;
    showFormError?: ValidationErrors;
    disableSaveIcon = false
  strIndex: number[] = []
    constructor(
      private fb: FormBuilder,
      private service: Step2Service,
      private service1: Step1ReportService

    ) { }

    ngOnInit(): void {
      this.step2resultsnfindingsForm = this.fb.group({
        descriptions: this.fb.array([])
      });
      this.addDescription()
      this.addDescription()
      this.addDescription()
    }

    onSubmit(i: number) {
      
      this.service.createUpdateSwor({
        sworId: this.strIndex[i] ? this.strIndex[i]: null,
        text: this.descriptions.controls[i].value.description,
        ia: this.service1.step1Report.id,
        type: "STRENGTH"
      }).subscribe((res) => {
        // 
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
      return this.step2resultsnfindingsForm.controls["descriptions"] as FormArray;
    }

    addDescription() {
      const desc = this.fb.group({
        description: [null, Validators.required]
      })
      this.descriptions.push(desc)

    }

    deleteDescription(descriptionIndex: number) {
      this.descriptions.removeAt(descriptionIndex)
    }
  }
