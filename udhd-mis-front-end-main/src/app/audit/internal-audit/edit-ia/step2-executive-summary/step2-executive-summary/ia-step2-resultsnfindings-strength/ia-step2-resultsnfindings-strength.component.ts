import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { SworDTO } from 'src/app/audit/internal-audit/models/sworDTO';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step2Service } from 'src/app/audit/internal-audit/services/step-2.service';

@Component({
  selector: 'ia-step2-resultsnfindings-strength',
  templateUrl: './ia-step2-resultsnfindings-strength.component.html',
  styleUrls: ['./ia-step2-resultsnfindings-strength.component.css']
})
export class EditIaStep2ResultsnfindingsStrengthComponent implements OnInit {
  step2resultsnfindingsForm!: FormGroup;
  showFormError?: ValidationErrors;
  disableSaveIcon = false;
  strengths: SworDTO[] = []
  strIndex: number[] = []
  snackBarRef:any;
  constructor(
    private fb: FormBuilder,
    private service: Step2Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,

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
      this.strIndex[i] = this.strengths[i].sworId
      this.addDescription(this.strengths[i].text)
    }

  }
  onSubmit(i: number) {
    this.service.createUpdateSwor({
      sworId: this.strIndex[i] ? this.strIndex[i]: null ,
      text: this.descriptions.controls[i].value.description,
      ia: this.service1.step1Report.id,
      type: "STRENGTH"
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

  deleteDescription(i: number) {
    this.service.deleteSwor({
      sworId: this.strIndex[i] ? this.strIndex[i]: null ,
      text: this.descriptions.controls[i].value.description,
      ia: this.service1.step1Report.id,
      type: "STRENGTH"
    }).subscribe((res) => {
      this.descriptions.removeAt(i);
      this.snackBarRef= this._snackBar.open(res.message, res.message, {
        duration: 5000
      });
      
    })
    
  }
}
