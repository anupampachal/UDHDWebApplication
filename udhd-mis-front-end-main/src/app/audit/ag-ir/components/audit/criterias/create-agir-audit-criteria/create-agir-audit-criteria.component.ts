import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AGIRAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-create-agir-audit-criteria',
  templateUrl: './create-agir-audit-criteria.component.html',
  styleUrls: ['./create-agir-audit-criteria.component.css']
})
export class CreateAgirAuditCriteriaComponent implements OnInit {
  @Input() agirAuditId!: number;
  @Input() parent!: string;
  @Output() sendToList = new EventEmitter<string>();
  @Output() sendToView = new EventEmitter<number>();
  createAGIRAuditCriteria!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirAuditCriteriaService: AGIRAuditCriteriaService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createAGIRAuditCriteria = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(512),
      ]),
      agirAuditId: new FormControl(this.agirAuditId, Validators.required),
      auditPara: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(255),
      ]),
      status: new FormControl(true, [
        Validators.required,
      ]),
      associatedRisk: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(255),
      ]),
      amount: new FormControl(null, Validators.required),
      description: new FormControl(null, [Validators.minLength(3), Validators.maxLength(2000)])
    });
  }

  onSubmit() {

    if (this.createAGIRAuditCriteria.valid) {
      this.agirAuditCriteriaService
        .createAGIRAuditCriteria(this.createAGIRAuditCriteria.value)
        .subscribe((res) =>
          this.sendToView.emit(res.id)
        );
    } else if (!!this.createAGIRAuditCriteria.errors) {
      this.showFormError = this.createAGIRAuditCriteria.errors;
    }
  }

  sendToListM() {
    this.sendToList.emit('list');
  }
}
