import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ulbDTO } from '../../../models/ulbDTO';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { IAStep1ResponseDTO } from '../../../models/step1DTO'
import * as moment from 'moment';
@Component({
  selector: 'step1-report',
  templateUrl: './step1-report.component.html',
  styleUrls: ['./step1-report.component.css']
})
export class EditStep1ReportComponent implements OnInit {
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
      id: new FormControl( [
        Validators.required,
      ]),
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulbId: new FormControl('null', Validators.required),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)])
    });
    this.service.getULBsToCreateStep1Report().subscribe(ulbs => {
      this.ulbs = ulbs
    })
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.service.findStep1ReportById(param['id'])),
        tap((dt) => {
          this.setData(dt)
          if(dt['id']){
            this.service.step1Report.id = dt.id
          }
          
        }),
        shareReplay(2)
      )
      .subscribe(
        () => {},
        error => {}
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
    
  }

  compareFn(x: IAStep1ResponseDTO, y: IAStep1ResponseDTO) {
    return x && y ? x.id === y.id : x === y;
  }
  onSubmit() {
    this.step1Form.value.startDate = this.getDateInDDMMYYYY(this.step1Form.getRawValue().startDate)
    this.step1Form.value.endDate = this.getDateInDDMMYYYY(this.step1Form.getRawValue().endDate)
    this.service.createStep1Report(this.step1Form.value).subscribe((response) =>
     {
      this.nextStep.emit(response)

     })
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
  }


