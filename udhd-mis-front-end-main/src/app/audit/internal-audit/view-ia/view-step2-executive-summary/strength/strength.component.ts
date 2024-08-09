import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { SworDTO } from '../../../models/sworDTO';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step2Service } from '../../../services/step-2.service';

@Component({
  selector: 'app-strength',
  templateUrl: './strength.component.html',
  styleUrls: ['./strength.component.css']
})
export class StrengthComponent implements OnInit {
  step2resultsnfindingsForm!: FormGroup;
  showFormError?: ValidationErrors;
  disableSaveIcon = false;
  strengths: SworDTO[] = []
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,


  ) { }

  ngOnInit(): void {

    this.step2resultsnfindingsForm = this.fb.group({
      descriptions: this.fb.array([])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getSworList(param['id'], "STRENGTH")),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )

  }

  setData(dt: any) {
    this.strengths = dt
    for (let i = 0; i < this.strengths.length; i++) {
      this.addDescription(this.strengths[i].text)
    this.descriptions.controls[i].disable()

    }

  }


  get descriptions() {
    return this.step2resultsnfindingsForm.controls["descriptions"] as FormArray;
  }

  addDescription(descr: string) {
    const desc = this.fb.group({
      description: [descr, Validators.required]
    })
    this.descriptions.push(desc)

  }

}
