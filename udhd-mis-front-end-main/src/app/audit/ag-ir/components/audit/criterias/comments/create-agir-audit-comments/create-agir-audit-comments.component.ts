import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AGIRAuditCommentService } from '../comments.service';

@Component({
  selector: 'app-create-agir-audit-comments',
  templateUrl: './create-agir-audit-comments.component.html',
  styleUrls: ['./create-agir-audit-comments.component.css']
})
export class CreateAgirAuditCommentsComponent implements OnInit {

  @Input()criteriaId!:number;
  @Input()parent!:string;
  routeArray: ActivatedRoute[] = [];
  showFormError?: ValidationErrors;
  createAgirAuditComment!:FormGroup;
  @Output()listCommentEvent= new EventEmitter();
  constructor(private agirAuditCommentsService: AGIRAuditCommentService,private fb: FormBuilder,
    private route: ActivatedRoute,) { 
  }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createAgirAuditComment = this.fb.group({
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
    if (this.createAgirAuditComment.valid) {
      //console.log('create cliecked')
      this.agirAuditCommentsService
        .createAGIRAuditComment(this.createAgirAuditComment.getRawValue())
        .subscribe((res) => {
          this.listCommentEvent.emit();
        }
        );
    } else if (!!this.createAgirAuditComment.errors) {
      this.showFormError = this.createAgirAuditComment.errors;
    }
  }
}
