import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CAGAuditService } from '../cag-audit.service';
import * as _moment from 'moment';
import * as moment from 'moment';

import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { CAGAuditDTO } from '../../../models/cag-audit.model';
@Component({
  selector: 'app-create-cag-audit',
  templateUrl: './create-cag-audit.component.html',
  styleUrls: ['./create-cag-audit.component.css']
})
export class CreateCagAuditComponent implements OnInit {
  createCAG!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs!: ULBDTO;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cagService: CAGAuditService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createCAG = this.fb.group({
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
    this.cagService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }
  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }

  createCAGAuditDTO(cagForm: FormGroup) {
    const cagAudit: CAGAuditDTO = new CAGAuditDTO();
    cagAudit.title = cagForm.controls['title'].value;
    cagAudit.auditStatus = 'DRAFT';
    cagAudit.description = cagForm.controls['description'].value;
    cagAudit.startDate = this.getDateInDDMMYYYY(cagForm.controls['startDate'].value);
    cagAudit.endDate = this.getDateInDDMMYYYY(cagForm.controls['endDate'].value);
    cagAudit.ulb = cagForm.controls['ulb'].value;
    return cagAudit;

  }
  onSubmit() {


    if (this.createCAG.valid) {
      this.cagService
        .createCAGAudit(this.createCAGAuditDTO(this.createCAG))
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createCAG.errors) {
      
      this.showFormError = this.createCAG.errors;
    }
  }


}
