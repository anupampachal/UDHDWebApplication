import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step3Service } from '../../../services/step-3.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-step3-detailed-audit',
  templateUrl: './step3-detailed-audit.component.html',
  styleUrls: ['./step3-detailed-audit.component.css']
})
export class Step3DetailedAuditComponent implements OnInit {
  step3Form!: FormGroup;
  adIntro!: FormGroup;
  showFormError?: ValidationErrors;
  snackBarRef:any;

  memberType: string[] = [
    "MUNICIPAL_ASSISTANTS", "MUNICIPAL_EXPERTS", "TEAM_LEAD"
  ]
@Output() nextStep = new EventEmitter();
  adminBtnDisabled=false;
  memberBtnDisabled=false;
  constructor(
    private fb: FormBuilder,
    private service: Step3Service,
    private service1: Step1ReportService,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.adIntro = this.fb.group({
      member: this.fb.array([this.createMember()])
    })
    this.step3Form = this.fb.group({
      nameOfChairmanMayor: new FormControl(null, [
        Validators.required,
      ]),
      nameOfCMO: new FormControl(null, [Validators.required,]),
      periodOfServiceChairman: new FormControl(null, [Validators.required]),
      periodOfServiceCMO: new FormControl(null, Validators.required),
    });
  }
  addNewMember() {
    this.memberArr.push(this.createMember())
  }
  get memberArr(): FormArray{
    return  this.adIntro.get('member') as FormArray
    }


  createMember(): FormGroup {
    return this.fb.group({
      id: new FormControl(),
      name: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required])
    })
  }

  onSubmit() {
    this.service.createAdministration({
      internalAuditId: this.service1.step1Report.id,
      ...this.step3Form.value

    })
    //.subscribe(res =>  this.nextStep.emit(res))
    .subscribe(()=>{
      this.adminBtnDisabled=true;
      this.snackBarRef= this._snackBar.open('Administration member saved successfully!', '', {
        duration: 5000
      });
    }
     
    )
  }

  saveMember(i: number) {
    
    this.service.createUpdateMember(this.service1.step1Report.id, {
      ...this.memberArr.at(i).value
    }).subscribe((res:any) => {
      this.memberBtnDisabled=true;
      this.memberArr.controls[i].patchValue(res)
      this.memberArr.controls[i].disable()
      this.snackBarRef= this._snackBar.open('Member saved successfully!', '', {
        duration: 5000
      });

      
      
    }, err => { })
  }

  enableAdIntro(i: number) {
    this.memberArr.controls[i].enable()
    this.memberBtnDisabled=false;
  }

}
