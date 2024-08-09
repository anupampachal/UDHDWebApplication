import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FinanceAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-create-finance-audit-criteria',
  templateUrl: './create-finance-audit-criteria.component.html',
  styleUrls: ['./create-finance-audit-criteria.component.css']
})
export class CreateFinanceAuditCriteriaComponent implements OnInit {
  @Input() financeAuditId!: number;
  @Output() sendToList = new EventEmitter<string>();
  @Output() sendToView = new EventEmitter<number>();
  createFinanceAuditCriteria!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private financeAuditCriteriaService: FinanceAuditCriteriaService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createFinanceAuditCriteria = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(512),
      ]),
      financeAuditId: new FormControl(this.financeAuditId, Validators.required),
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

    if (this.createFinanceAuditCriteria.valid) {
      this.financeAuditCriteriaService
        .createFinanceAuditCriteria(this.createFinanceAuditCriteria.value)
        .subscribe((res) =>
          this.sendToView.emit(res.id)
        );
    } else if (!!this.createFinanceAuditCriteria.errors) {
      this.showFormError = this.createFinanceAuditCriteria.errors;
    }
  }

  sendToListM() {
    this.sendToList.emit('list');
  }
}
