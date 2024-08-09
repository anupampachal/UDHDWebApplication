import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { CAGAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-create-cag-audit-compliances',
  templateUrl: './create-cag-audit-compliances.component.html',
  styleUrls: ['./create-cag-audit-compliances.component.css']
})
export class CreateCagAuditCompliancesComponent implements OnInit {

  @Input() auditCriteriaId!: number;
  createCagAuditCompliance!: FormGroup;
  showFormError?: ValidationErrors;

  @Output() listOfComplianceEvent = new EventEmitter<string>();
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cagAuditComplianceService: CAGAuditComplianceService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createCagAuditCompliance = this.fb.group({
      comment: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(2000),
      ]),
      status: new FormControl(true, Validators.required),
      auditCriteriaId: new FormControl(this.auditCriteriaId, [Validators.required])
    });
  }

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.createCagAuditCompliance.valid) {
      this.cagAuditComplianceService
        .createCAGAuditCompliance(this.createCagAuditCompliance.getRawValue())
        .subscribe((res) => {
          this.listOfComplianceEvent.emit();
          
        }
        );
    } else if (!!this.createCagAuditCompliance.errors) {
      this.showFormError = this.createCagAuditCompliance.errors;
    }
  }



}
