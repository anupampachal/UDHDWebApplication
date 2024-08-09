import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuditCommentDTO } from 'src/app/audit/cagpac/models/cag-comment.model';
import { CAGAuditCommentService } from '../comments.service';
import { tap } from 'rxjs/operators';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';


@Component({
  selector: 'app-edit-cag-audit-comments',
  templateUrl: './edit-cag-audit-comments.component.html',
  styleUrls: ['./edit-cag-audit-comments.component.css']
})
export class EditCagAuditCommentsComponent implements OnInit {

  @Input() commentId!: number;
  @Input() auditCriteriaId!: number;
  @Output() listCommentEvent = new EventEmitter<string>();
  
  editCagAuditCommentForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  cagAuditComment$!: Observable<AuditCommentDTO>;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cagAuditCommentService: CAGAuditCommentService
  ) { }
  ngOnInit(): void {
    this.cagAuditComment$ = this.cagAuditCommentService
      .findCAGAuditCommentById(this.commentId)
      .pipe(tap(cagC => this.setValue(cagC)))
      ;
    this.editCagAuditCommentForm = this.fb.group({
      id: new FormControl(this.commentId, [Validators.required]),
      comment: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(2000),
      ]),
      auditCriteriaId: new FormControl(this.auditCriteriaId, [Validators.required])
    });
  }

  setValue(val: AuditCommentDTO) {
    this.editCagAuditCommentForm.setValue({
      id: val.id,
      comment: val.comment,
      auditCriteriaId: val.auditCriteriaId
    });
  }
  toList() {

  }

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.editCagAuditCommentForm.valid) {
      
      this.cagAuditCommentService
        .createCAGAuditComment(this.editCagAuditCommentForm.getRawValue())
        .subscribe((res) => {
          this.listCommentEvent.emit();
        }
        );
    } else if (!!this.editCagAuditCommentForm.errors) {
      this.showFormError = this.editCagAuditCommentForm.errors;
    }
  }

}
