import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { FinanceAuditDTO } from '../../../models/finance-audit.model';
import { FinanceAuditService } from '../finance-audit.service';

@Component({
  selector: 'app-edit-finance-audit',
  templateUrl: './edit-finance-audit.component.html',
  styleUrls: ['./edit-finance-audit.component.css']
})
export class EditFinanceAuditComponent implements OnInit {
  editFinance!: FormGroup;
  id!: number;
  finance$?: Observable<FinanceAuditDTO>;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs!: ULBDTO;

  criteriaIdInfo = 0;
  currentScr = 'list';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private financeService: FinanceAuditService
  ) { }
  ngOnInit(): void {
    this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.financeService.findFinanceAuditById(param['id'])),
      shareReplay(2)
    ).subscribe(res => this.setVal(res));


    this.routeArray = this.route.pathFromRoot;
    this.editFinance = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulb: new FormControl(null, Validators.required),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)]),
      financeAuditId: new FormControl(null),
      id: new FormControl({ value: null, disabled: true }),
      auditStatus: new FormControl({ value: null, disabled: true })
    });
    this.financeService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }



  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }
  convertDDMMYYYToMMDDYYYYForEndDate(date: string) {
    const dateArr: string[] = date.split('/');
    return dateArr[1] + '/' + dateArr[0] + '/' + dateArr[2];
  }

  compareFn(x: FinanceAuditDTO, y: FinanceAuditDTO) {
    return x && y ? x.id === y.id : x === y;
  }
  setVal(dto: FinanceAuditDTO) {
    this.editFinance.setValue({
      financeAuditId: dto.financeAuditId,
      id: dto.id,
      title: dto.title,
      startDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.startDate)),
      endDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.endDate)),
      description: dto.description,
      ulb: dto.ulb,
      auditStatus: dto.auditStatus
    });
  }

  editFinanceAuditDTO(financeForm: FormGroup) {
    const financeAudit: FinanceAuditDTO = new FinanceAuditDTO();
    financeAudit.title = financeForm.controls['title'].value;
    financeAudit.auditStatus = 'DRAFT';
    financeAudit.description = financeForm.controls['description'].value
    financeAudit.id = financeForm.controls['id'].value;
    financeAudit.startDate = this.getDateInDDMMYYYY(financeForm.controls['startDate'].value);
    financeAudit.endDate = this.getDateInDDMMYYYY(financeForm.controls['endDate'].value);
    financeAudit.ulb = financeForm.controls['ulb'].value;
    return financeAudit;

  }
  onSubmit() {


    if (this.editFinance.valid) {

      this.financeService
        .createFinanceAudit(this.editFinanceAuditDTO(this.editFinance))
        .subscribe((res) =>
          this.router.navigate(['../../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.editFinance.errors) {
      this.showFormError = this.editFinance.errors;
    }
  }

  openList($event: string) {
    this.currentScr = 'list';
  }
  setCriteriaId($event: number) {
    this.criteriaIdInfo = $event;
    this.currentScr = 'view';
  }

  sendToEdit($event: number) {
    this.currentScr = 'edit';
    this.criteriaIdInfo = $event;
  }
}
