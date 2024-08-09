import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { shareReplay, switchMap, tap } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/finance/models/finance-criteria.model';
import { FinanceAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-edit-finance-audit-criteria',
  templateUrl: './edit-finance-audit-criteria.component.html',
  styleUrls: ['./edit-finance-audit-criteria.component.css']
})
export class EditFinanceAuditCriteriaComponent implements OnInit {
  @Input() financeAuditCriteriaId!: number;
  @Output() sendToView = new EventEmitter<number>();
  @Output() sendToList = new EventEmitter<string>();
  criteria$?: Observable<AuditCriteriaDTO>;
  editFinanceAuditCriteriaForm!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  auditCriteriaDTO!: AuditCriteriaDTO;
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private financeAuditCriteriaService: FinanceAuditCriteriaService
  ) {

  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.editFinanceAuditCriteriaForm = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(512),
      ]),
      id: new FormControl(null, [Validators.required]),
      financeAuditId: new FormControl(null, [Validators.required]),
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
      amount: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.minLength(3), Validators.maxLength(2000)])
    });
    this.criteria$ = this.route.params.pipe(
      switchMap((param) => this.financeAuditCriteriaService.findFinanceAuditCriteriaById(this.financeAuditCriteriaId)),
      tap(data => this.setData(data)),
      shareReplay(2)
    );

    // this.criteria$.subscribe(this.setData)
  }


  onSubmit() {
    if (this.editFinanceAuditCriteriaForm.valid) {
      this.financeAuditCriteriaService
        .createFinanceAuditCriteria(this.editFinanceAuditCriteriaForm.value)
        .subscribe((res) =>
          this.sendToView.emit(res.id)
        );
    } else if (!!this.editFinanceAuditCriteriaForm.errors) {
      this.showFormError = this.editFinanceAuditCriteriaForm.errors;
    }
  }

  sendToListM(data: string) {
    this.sendToList.emit('list');
  }

  setData(data: AuditCriteriaDTO) {
    this.editFinanceAuditCriteriaForm.setValue({
      title: data.title,
      id: data.id,
      financeAuditId: data.financeAuditId,
      auditPara: data.auditPara,
      status: data.status,
      associatedRisk: data.associatedRisk,
      amount: data.amount,
      description: data.description
    });
  }
}
