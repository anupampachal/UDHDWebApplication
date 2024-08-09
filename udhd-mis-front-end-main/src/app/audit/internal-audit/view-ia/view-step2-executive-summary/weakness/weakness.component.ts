import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { SworDTO } from '../../../models/sworDTO';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step2Service } from '../../../services/step-2.service';

@Component({
  selector: 'app-weakness',
  templateUrl: './weakness.component.html',
  styleUrls: ['./weakness.component.css']
})
export class WeaknessComponent implements OnInit {
  step2resultsnfindingsForm!: FormGroup;
    showFormError?: ValidationErrors;
    disableSaveIcon = false;
  weaknesses: SworDTO[] = []

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
        switchMap((param) => this.service.getSworList(param['id'], "WEAKNESS")),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      ).subscribe(
        () => {},
        error => {}
      )

    }

    setData(dt: any) {
      this.weaknesses = dt
      for(let i = 0; i< this.weaknesses.length; i++) {
        this.addDescription(this.weaknesses[i].text)
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
