import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { CAGAuditCommentService } from '../comments.service';

@Component({
  selector: 'app-create-cag-audit-comments',
  templateUrl: './create-cag-audit-comments.component.html',
  styleUrls: ['./create-cag-audit-comments.component.css']
})
export class CreateCagAuditCommentsComponent implements OnInit {

  @Input()criteriaId!:number;
  @Input()parent!:string;
  routeArray: ActivatedRoute[] = [];
  showFormError?: ValidationErrors;
  createCagAuditComment!:FormGroup;
  @Output()listCommentEvent= new EventEmitter();
  constructor(private cagAuditCommentsService: CAGAuditCommentService,private fb: FormBuilder,
    private route: ActivatedRoute,) { 
  }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createCagAuditComment = this.fb.group({
      comment: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(2000),
      ]),
      auditCriteriaId: new FormControl(this.criteriaId, [Validators.required])
    });
    
  }
  matcher = new MyErrorStateMatcher();

  onSubmit() {
    if (this.createCagAuditComment.valid) {
      //console.log('create cliecked')
      this.cagAuditCommentsService
        .createCAGAuditComment(this.createCagAuditComment.getRawValue())
        .subscribe((res) => {
          this.listCommentEvent.emit();
        }
        );
    } else if (!!this.createCagAuditComment.errors) {
      this.showFormError = this.createCagAuditComment.errors;
    }
  }
}
