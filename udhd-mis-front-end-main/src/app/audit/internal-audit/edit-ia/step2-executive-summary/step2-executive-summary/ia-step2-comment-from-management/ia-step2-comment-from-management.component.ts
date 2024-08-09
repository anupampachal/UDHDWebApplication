import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-comment-from-management',
  templateUrl: './ia-step2-comment-from-management.component.html',
  styleUrls: ['./ia-step2-comment-from-management.component.css']
})
export class EditIaStep2CommentFromManagementComponent implements OnInit {
  commentMngmntForm!: FormGroup;
  showFormError?: ValidationErrors;
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  fileUpload = false

  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.commentMngmntForm = this.fb.group({
      id: new FormControl(null),
      comment: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
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
    this.commentMngmntForm.patchValue({
      comment: dt.commentFromMgt,
      id: dt.id
    })
  }
  onSubmit() {


    this.service.createUpdateCoack({
      text: this.commentMngmntForm.value.comment,
      id: this.commentMngmntForm.value.id,
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
      this.fileUpload = true
      
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
