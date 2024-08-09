import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FinanceAuditService } from '../finance-audit.service';
import * as _moment from 'moment';
import * as moment from 'moment';
import { FinanceAuditDTO } from '../../../models/finance-audit.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
@Component({
  selector: 'app-create-finance-audit',
  templateUrl: './create-finance-audit.component.html',
  styleUrls: ['./create-finance-audit.component.css']
})
export class CreateFinanceAuditComponent implements OnInit {
  createFinance!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs!: ULBDTO;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private financeService: FinanceAuditService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createFinance = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulb: new FormControl(null, Validators.required),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
    });
    this.financeService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }

  createFinanceAuditDTO(financeForm: FormGroup) {
    const financeAudit: FinanceAuditDTO = new FinanceAuditDTO();
    financeAudit.title = financeForm.controls['title'].value;
    financeAudit.auditStatus = 'DRAFT';
    financeAudit.description = financeForm.controls['description'].value;
    financeAudit.startDate = this.getDateInDDMMYYYY(financeForm.controls['startDate'].value);
    financeAudit.endDate = this.getDateInDDMMYYYY(financeForm.controls['endDate'].value);
    financeAudit.ulb = financeForm.controls['ulb'].value;
    return financeAudit;

  }
  onSubmit() {


    if (this.createFinance.valid) {
      this.financeService
        .createFinanceAudit(this.createFinanceAuditDTO(this.createFinance))
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createFinance.errors) {
      this.showFormError = this.createFinance.errors;
    }
  }


}
