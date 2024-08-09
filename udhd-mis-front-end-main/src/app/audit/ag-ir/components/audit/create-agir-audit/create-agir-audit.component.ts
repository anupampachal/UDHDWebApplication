import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AGIRAuditService } from '../agir-audit.service';
import * as _moment from 'moment';
import * as moment from 'moment';
import { AGIRAuditDTO } from '../../../models/agir-audit.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
@Component({
  selector: 'app-create-agir-audit',
  templateUrl: './create-agir-audit.component.html',
  styleUrls: ['./create-agir-audit.component.css']
})
export class CreateAgirAuditComponent implements OnInit {
  createAGIR!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs!: ULBDTO;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirService: AGIRAuditService
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createAGIR = this.fb.group({
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
    this.agirService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }
  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }

  createAGIRAuditDTO(agirForm: FormGroup) {
    const agirAudit: AGIRAuditDTO = new AGIRAuditDTO();
    agirAudit.title = agirForm.controls['title'].value;
    agirAudit.auditStatus = 'DRAFT';
    agirAudit.description = agirForm.controls['description'].value;
    agirAudit.startDate = this.getDateInDDMMYYYY(agirForm.controls['startDate'].value);
    agirAudit.endDate = this.getDateInDDMMYYYY(agirForm.controls['endDate'].value);
    agirAudit.ulb = agirForm.controls['ulb'].value;
    return agirAudit;

  }
  onSubmit() {


    if (this.createAGIR.valid) {
      this.agirService
        .createAGIRAudit(this.createAGIRAuditDTO(this.createAGIR))
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createAGIR.errors) {
      
      this.showFormError = this.createAGIR.errors;
    }
  }


}
