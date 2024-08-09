import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CAGAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-create-cag-audit-criteria',
  templateUrl: './create-cag-audit-criteria.component.html',
  styleUrls: ['./create-cag-audit-criteria.component.css']
})
export class CreateCagAuditCriteriaComponent implements OnInit {
  @Input() cagAuditId!: number;
  @Input() parent!: string;
  @Output() sendToList = new EventEmitter<string>();
  @Output() sendToView = new EventEmitter<number>();
  createCAGAuditCriteria!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cagAuditCriteriaService: CAGAuditCriteriaService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createCAGAuditCriteria = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(512),
      ]),
      cagAuditId: new FormControl(this.cagAuditId, Validators.required),
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

    if (this.createCAGAuditCriteria.valid) {
      this.cagAuditCriteriaService
        .createCAGAuditCriteria(this.createCAGAuditCriteria.value)
        .subscribe((res) =>
          this.sendToView.emit(res.id)
        );
    } else if (!!this.createCAGAuditCriteria.errors) {
      this.showFormError = this.createCAGAuditCriteria.errors;
    }
  }

  sendToListM() {
    this.sendToList.emit('list');
  }
}
