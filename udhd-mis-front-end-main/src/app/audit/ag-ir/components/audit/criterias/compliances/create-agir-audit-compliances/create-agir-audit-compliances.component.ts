import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AGIRAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-create-agir-audit-compliances',
  templateUrl: './create-agir-audit-compliances.component.html',
  styleUrls: ['./create-agir-audit-compliances.component.css']
})
export class CreateAgirAuditCompliancesComponent implements OnInit {

  @Input() auditCriteriaId!: number;
  createAgirAuditCompliance!: FormGroup;
  showFormError?: ValidationErrors;

  @Output() listOfComplianceEvent = new EventEmitter<string>();
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirAuditComplianceService: AGIRAuditComplianceService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createAgirAuditCompliance = this.fb.group({
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
    if (this.createAgirAuditCompliance.valid) {
      this.agirAuditComplianceService
        .createAGIRAuditCompliance(this.createAgirAuditCompliance.getRawValue())
        .subscribe((res) => {
          this.listOfComplianceEvent.emit();
          
        }
        );
    } else if (!!this.createAgirAuditCompliance.errors) {
      this.showFormError = this.createAgirAuditCompliance.errors;
    }
  }



}
