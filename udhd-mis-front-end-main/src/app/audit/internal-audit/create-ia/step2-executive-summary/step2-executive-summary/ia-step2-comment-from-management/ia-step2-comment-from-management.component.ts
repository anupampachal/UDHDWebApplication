import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'app-ia-step2-comment-from-management',
  templateUrl: './ia-step2-comment-from-management.component.html',
  styleUrls: ['./ia-step2-comment-from-management.component.css']
})
export class IaStep2CommentFromManagementComponent implements OnInit {
  commentMngmntForm!: FormGroup;
  showFormError?: ValidationErrors;
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  commIndex: any;
  fileUpload  = false

  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit(): void {
    this.commentMngmntForm = this.fb.group({
      comment: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
    });
  }
  onSubmit() {
    this.service.createUpdateCoack({
      id: this.commIndex ? this.commIndex : null,
      text: this.commentMngmntForm.value.comment,
      iaId: this.service1.step1Report.id,
      type: "COMMENT"
    }).pipe(
      map(res => res),
      mergeMap((res:any) => {
        if(this.selectedFile) {
        return this.service.uploadBudget(this.selectedFile, this.service1.step1Report.id.toString());
        }
        return of(res)
      })
    )
    .subscribe((res) => {
      this.commIndex = res.id
      this.fileUpload = true
      // 
      this.commentMngmntForm.disable()
    })
  }
  enableForm() {
    this.commentMngmntForm.enable()
    this.fileUpload = false
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
  uploadFile() {
    this.service.uploadBudget(this.selectedFile, this.service1.step1Report.id.toString())
      .subscribe()
  }
}
