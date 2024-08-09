import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable, of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, catchError } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { InternalAuditService } from '../../internal-audit-service.service';
import { IAStep1ResponseDTO } from '../../models/step1DTO';
import { ulbDTO } from '../../models/ulbDTO';
import { Step1ReportService } from '../../services/step-1-report.service';

@Component({
  selector: 'app-view-step1-report',
  templateUrl: './view-step1-report.component.html',
  styleUrls: ['./view-step1-report.component.css']
})
export class ViewStep1ReportComponent implements OnInit {

  step1Form!: FormGroup;
  showFormError?: ValidationErrors;
  ulbs: ulbDTO[] = []
  @Output() nextStep = new EventEmitter();

  constructor(
    private fb: FormBuilder,
    private service: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.step1Form = this.fb.group({
      auditId: new FormControl(),
      id: new FormControl([{ value: null, disabled: true },
        Validators.required,
      ]),
      title: new FormControl({ value: null, disabled: true }, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl({ value: null, disabled: true }, [Validators.required,]),
      endDate: new FormControl({ value: null, disabled: true }, [Validators.required]),
      ulbId: new FormControl({ value: null, disabled: true }, Validators.required),
      description: new FormControl({ value: null, disabled: true }, [Validators.minLength(5), Validators.maxLength(2000)])
    });
    this.service.getULBsToCreateStep1Report().subscribe(ulbs => {
      this.ulbs = ulbs
    })
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        tap((dt) => {
          if(dt['id']){
           
            this.service.step1Report.id = dt.id
          }
         
        } ),
        switchMap((param) => this.service.findStep1ReportById(param['id'])),
        shareReplay(2)
      )
      .subscribe(
        (dt) => {  this.setData(dt)},
        error => { }
      );
  }


  setData(dt: any) {
    var sd = moment(dt.startDate, 'DD/MM/YYYY')
    var ed = moment(dt.endDate, 'DD/MM/YYYY')
    // this.step1Form.patchValue(dt)
    this.step1Form.patchValue({
      auditId: dt.auditReportId,
      id: dt.id,
      title: dt.title,
      description: dt.description,
      ulbId: dt.ulb.id,
      startDate: new Date(moment(sd).format("MM/DD/YYYY")),
      endDate: new Date(moment(ed).format("MM/DD/YYYY")),
    })
    this.service.step1Report = {
      id: dt.id,
      ulbName: dt.ulb.name
    }
    // this.step1Form.disable()
  }

  compareFn(x: IAStep1ResponseDTO, y: IAStep1ResponseDTO) {
    return x && y ? x.id === y.id : x === y;
  }


  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
}


