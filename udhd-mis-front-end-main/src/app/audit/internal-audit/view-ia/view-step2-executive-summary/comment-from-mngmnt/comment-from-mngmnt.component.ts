import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step2Service } from '../../../services/step-2.service';

@Component({
  selector: 'app-comment-from-mngmnt',
  templateUrl: './comment-from-mngmnt.component.html',
  styleUrls: ['./comment-from-mngmnt.component.css']
})
export class CommentFromMngmntComponent implements OnInit {
  commentMngmntForm!: FormGroup;
  showFormError?: ValidationErrors;


  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.commentMngmntForm = this.fb.group({
      id: new FormControl({ value: null, disabled: true }),
      comment: new FormControl({ value: null, disabled: true }, [Validators.minLength(5), Validators.maxLength(2000)])
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
  coId = ''
  setData(dt: any) {
    this.coId = dt.id
    this.commentMngmntForm.patchValue({
      comment: dt.commentFromMgt,
      id: dt.id
    })
    this.commentMngmntForm.disable()
  }


  downloadFile() {
    this.service.downloadFile(this.coId).pipe(
      map(res => this._FileSaverService.save(res, 'file.pdf'))
    )
    .subscribe(res => {
      this.service1.show("File downloaded successfully", "success", 3000)
    })
  }
}
