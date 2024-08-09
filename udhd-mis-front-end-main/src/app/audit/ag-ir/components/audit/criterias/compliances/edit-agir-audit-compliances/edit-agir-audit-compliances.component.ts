import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuditComplianceDTO } from 'src/app/audit/ag-ir/models/agircompliances.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AGIRAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-edit-agir-audit-compliances',
  templateUrl: './edit-agir-audit-compliances.component.html',
  styleUrls: ['./edit-agir-audit-compliances.component.css']
})
export class EditAgirAuditCompliancesComponent implements OnInit {
  @Input() auditCriteriaId!: number;
  @Input() complianceId!: number;
  @Output() listOfComplianceEvent = new EventEmitter<string>();

  editAgirAuditComplianceForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  agirAuditCompliance$!: Observable<AuditComplianceDTO>;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirAuditComplianceService: AGIRAuditComplianceService
  ) { }
  ngOnInit(): void {
    this.agirAuditCompliance$ = this.agirAuditComplianceService
      .findAGIRAuditComplianceById(this.complianceId)
      .pipe(tap(agirC => this.setValue(agirC)))
      ;
    this.editAgirAuditComplianceForm = this.fb.group({
      id: new FormControl(this.complianceId, [Validators.required]),
      comment: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(2000),
      ]),
      status: new FormControl(true, Validators.required),
      auditCriteriaId: new FormControl(this.auditCriteriaId, [Validators.required])
    });
  }

  setValue(val: AuditComplianceDTO) {
    this.editAgirAuditComplianceForm.setValue({
      id: val.id,
      comment: val.comment,
      status: val.status,
      auditCriteriaId: val.auditCriteriaId
    });
  }
  toList() {

  }

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.editAgirAuditComplianceForm.valid) {
      
      this.agirAuditComplianceService
        .createAGIRAuditCompliance(this.editAgirAuditComplianceForm.getRawValue())
        .subscribe((res) => {
          this.listOfComplianceEvent.emit();
        }
        );
    } else if (!!this.editAgirAuditComplianceForm.errors) {
      this.showFormError = this.editAgirAuditComplianceForm.errors;
    }
  }




}
