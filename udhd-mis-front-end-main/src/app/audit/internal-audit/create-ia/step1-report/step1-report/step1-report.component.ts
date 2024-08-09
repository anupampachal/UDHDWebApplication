import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import * as moment from 'moment';
import { ulbDTO } from '../../../models/ulbDTO';
import { Step1ReportService } from '../../../services/step-1-report.service';

@Component({
  selector: 'app-step1-report',
  templateUrl: './step1-report.component.html',
  styleUrls: ['./step1-report.component.css']
})
export class Step1ReportComponent implements OnInit {
  @Input()  step1Form!: FormGroup;
  showFormError?: ValidationErrors;
  ulbs: ulbDTO[] = []

  @Output() nextStep = new EventEmitter();

  constructor(
    private fb: FormBuilder,
    private service: Step1ReportService
  ) { }

  ngOnInit(): void {

    this.step1Form = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulbId: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000), Validators.required])
    });
    this.service.getULBsToCreateStep1Report().subscribe(ulbs => {
      this.ulbs = ulbs
    })
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
  onSubmit() {
    this.step1Form.value.startDate = this.getDateInDDMMYYYY(this.step1Form.getRawValue().startDate)
    this.step1Form.value.endDate = this.getDateInDDMMYYYY(this.step1Form.getRawValue().endDate)
    this.service.createStep1Report(this.step1Form.value).subscribe((response) =>
     {
      this.nextStep.emit(response)
      this.service.show("Report Created Successfully ", "success")
      this.service.step1Report = {
        id: response.id,
        ulbName: response.ulb.ulbName
      }

     }
    )

  }

}
