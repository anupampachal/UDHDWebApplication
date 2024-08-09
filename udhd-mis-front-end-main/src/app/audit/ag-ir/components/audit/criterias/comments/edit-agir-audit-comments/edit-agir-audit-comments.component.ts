import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuditCommentDTO } from 'src/app/audit/ag-ir/models/agircomment.model';
import { AGIRAuditCommentService } from '../comments.service';
import { tap } from 'rxjs/operators';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';


@Component({
  selector: 'app-edit-agir-audit-comments',
  templateUrl: './edit-agir-audit-comments.component.html',
  styleUrls: ['./edit-agir-audit-comments.component.css']
})
export class EditAgirAuditCommentsComponent implements OnInit {

  @Input() commentId!: number;
  @Input() auditCriteriaId!: number;
  @Output() listCommentEvent = new EventEmitter<string>();
  
  editAgirAuditCommentForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  agirAuditComment$!: Observable<AuditCommentDTO>;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirAuditCommentService: AGIRAuditCommentService
  ) { }
  ngOnInit(): void {
    this.agirAuditComment$ = this.agirAuditCommentService
      .findAGIRAuditCommentById(this.commentId)
      .pipe(tap(agirC => this.setValue(agirC)))
      ;
    this.editAgirAuditCommentForm = this.fb.group({
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
    this.editAgirAuditCommentForm.setValue({
      id: val.id,
      comment: val.comment,
      auditCriteriaId: val.auditCriteriaId
    });
  }
  toList() {

  }

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.editAgirAuditCommentForm.valid) {
      
      this.agirAuditCommentService
        .createAGIRAuditComment(this.editAgirAuditCommentForm.getRawValue())
        .subscribe((res) => {
          this.listCommentEvent.emit();
        }
        );
    } else if (!!this.editAgirAuditCommentForm.errors) {
      this.showFormError = this.editAgirAuditCommentForm.errors;
    }
  }

}
