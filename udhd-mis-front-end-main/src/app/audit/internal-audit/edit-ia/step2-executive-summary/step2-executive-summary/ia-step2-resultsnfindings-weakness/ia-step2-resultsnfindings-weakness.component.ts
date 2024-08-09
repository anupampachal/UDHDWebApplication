import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { SworDTO } from 'src/app/audit/internal-audit/models/sworDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-resultsnfindings-weakness',
  templateUrl: './ia-step2-resultsnfindings-weakness.component.html',
  styleUrls: ['./ia-step2-resultsnfindings-weakness.component.css']
})
export class EditIaStep2ResultsnfindingsWeaknessComponent implements OnInit {
  step2resultsnfindingsForm!: FormGroup;
    showFormError?: ValidationErrors;
    disableSaveIcon = false;
  weaknesses: SworDTO[] = []
  strIndex: number[] = []

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
      this.strIndex[i] = this.weaknesses[i].sworId
        this.addDescription(this.weaknesses[i].text)
      }

    }
    onSubmit(i: number) {
      this.service.createUpdateSwor({
      sworId: this.strIndex[i] ? this.strIndex[i]: null ,
        text: this.descriptions.controls[i].value.description,
        ia: this.service1.step1Report.id,
        type: "WEAKNESS"
      }).subscribe((res) => {
        this.strIndex[i] = res.sworId
        this.disableSaveIcon = true
        this.descriptions.controls[i].disable()
      })
    }
    enableControl(i: number) {
      this.descriptions.controls[i].enable()
      this.disableSaveIcon = false
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

    deleteDescription(descriptionIndex: number) {
      this.descriptions.removeAt(descriptionIndex)
    }
  }
