import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step2Service } from '../../../services/step-2.service';

@Component({
  selector: 'app-acknowledgement',
  templateUrl: './acknowledgement.component.html',
  styleUrls: ['./acknowledgement.component.css']
})
export class AcknowledgementComponent implements OnInit {
  acknowledgementForm!: FormGroup;
  ackId: any;
  showFormError?: ValidationErrors;
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }
  ngOnInit(): void {
    this.acknowledgementForm = this.fb.group({
      acknowledgement: new FormControl({ value: null, disabled: true }, [Validators.minLength(5), Validators.maxLength(2000)])
    });

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getExSummaryByIaId(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )
  }
  setData(dt: any) {
    this.ackId = dt.id
    this.acknowledgementForm.patchValue({
      acknowledgement: dt.acknowledgement
    })
    this.acknowledgementForm.disable()
  }

}
