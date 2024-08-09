import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuditComplianceDTO } from 'src/app/audit/cagpac/models/cag-compliances.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { CAGAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-edit-cag-audit-compliances',
  templateUrl: './edit-cag-audit-compliances.component.html',
  styleUrls: ['./edit-cag-audit-compliances.component.css']
})
export class EditCagAuditCompliancesComponent implements OnInit {
  @Input() auditCriteriaId!: number;
  @Input() complianceId!: number;
  @Output() listOfComplianceEvent = new EventEmitter<string>();

  editCagAuditComplianceForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  cagAuditCompliance$!: Observable<AuditComplianceDTO>;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cagAuditComplianceService: CAGAuditComplianceService
  ) { }
  ngOnInit(): void {
    this.cagAuditCompliance$ = this.cagAuditComplianceService
      .findCAGAuditComplianceById(this.complianceId)
      .pipe(tap(cagC => this.setValue(cagC)))
      ;
    this.editCagAuditComplianceForm = this.fb.group({
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
    this.editCagAuditComplianceForm.setValue({
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
    if (this.editCagAuditComplianceForm.valid) {
      
      this.cagAuditComplianceService
        .createCAGAuditCompliance(this.editCagAuditComplianceForm.getRawValue())
        .subscribe((res) => {
          this.listOfComplianceEvent.emit();
        }
        );
    } else if (!!this.editCagAuditComplianceForm.errors) {
      this.showFormError = this.editCagAuditComplianceForm.errors;
    }
  }




}
