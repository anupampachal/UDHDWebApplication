import { Component, OnInit, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'app-municipal-account-committee-status',
  templateUrl: './municipal-account-committee-status.component.html',
  styleUrls: ['./municipal-account-committee-status.component.css']
})
export class MunicipalAccountCommitteeStatusComponent implements OnInit {
  municipalAccountCommitteeStatusForm!: FormGroup;
  showFormError?: ValidationErrors;
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  fileUpload  = false
  constructor(
    private formBuilder: FormBuilder,
    private service: Step5Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit(): void {
    this.municipalAccountCommitteeStatusForm = this.formBuilder.group({
      'id': new FormControl(),
      'commentDescription': new FormControl(null, [Validators.required, Validators.maxLength(10000)])
    });
  }

  save() {
    let municipalAccountCommitteeStatus = this.municipalAccountCommitteeStatusForm.value.commentDescription;
    this.service.createUpdateMAC(this.service1.step1Report.id, {
      statusOfMunicipalAccCommitteeText: municipalAccountCommitteeStatus,
    }).subscribe(res => {
      this.municipalAccountCommitteeStatusForm.disable()
    })
  }
  public onSelected(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.isFile = true
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10000 * 1024) {
        this.isFile = false;
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      }

      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  iconDis = false
  uploadFile() {
    this.service.uploadStatusOfImpFile(this.selectedFile, this.service1.step1Report.id).subscribe(res => {
      this.iconDis = true
    })
  }

  enableForm() {
    this.municipalAccountCommitteeStatusForm.enable()
  }
}
