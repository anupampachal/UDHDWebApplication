import { Component, OnInit, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step5Service } from 'src/app/audit/internal-audit/services/step-5.service';

@Component({
  selector: 'municipal-account-committee-status',
  templateUrl: './municipal-account-committee-status.component.html',
  styleUrls: ['./municipal-account-committee-status.component.css']
})
export class EditMunicipalAccountCommitteeStatusComponent implements OnInit {
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
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.municipalAccountCommitteeStatusForm = this.formBuilder.group({
      'id': new FormControl(),
      'commentDescription': new FormControl(null, [Validators.required, Validators.maxLength(10000)])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getStatusOfImpOfDeas(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => {},
        error => {}
      )
  }

  setData(data: any) {
    this.municipalAccountCommitteeStatusForm.patchValue({
      commentDescription:  JSON.parse(data.statusOfMunicipalAccountsCommittee).statusOfMunicipalAccCommitteeText
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
  save() {
    let municipalAccountCommitteeStatus = this.municipalAccountCommitteeStatusForm.value.commentDescription;
    this.service.createUpdateMAC(this.service1.step1Report.id, {
      statusOfMunicipalAccCommitteeText: municipalAccountCommitteeStatus,
    }).subscribe(res => {
      this.municipalAccountCommitteeStatusForm.disable()
    })
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
