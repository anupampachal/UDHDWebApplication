import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step3Service } from '../../../services/step-3.service';

@Component({
  selector: 'step3-detailed-audit',
  templateUrl: './step3-detailed-audit.component.html',
  styleUrls: ['./step3-detailed-audit.component.css']
})
export class EditStep3DetailedAuditComponent implements OnInit {
  step3Form!: FormGroup;
  adIntro!: FormGroup;
  memberType: string[] = [
    "MUNICIPAL_ASSISTANTS", "MUNICIPAL_EXPERTS", "TEAM_LEAD"
  ]
  showFormError?: ValidationErrors;
  @Output() nextStep = new EventEmitter();

  constructor(
    private fb: FormBuilder,
    private service: Step3Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.adIntro = this.fb.group({
      member: this.fb.array([])
    })
    this.memberType.forEach(member => {
      this.service.getMemberListByType(this.service1.step1Report.id, member).pipe(
        tap(dt => this.setMembers(dt))
      ).subscribe()
    })



    this.step3Form = this.fb.group({
      internalAuditId: new FormControl({ value: "", disabled: true }),
      nameOfChairmanMayor: new FormControl(null, [
        Validators.required,
      ]),
      nameOfCMO: new FormControl(null, [Validators.required,]),
      periodOfServiceChairman: new FormControl(null, [Validators.required]),
      periodOfServiceCMO: new FormControl(null, Validators.required),
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.findIaAdminById(param['id'])),
      tap((dt) => this.setData(dt)),
      // shareReplay(2)
    ).subscribe(
      () => { },
      error => { }
    )
  }

  get memberArr(): FormArray{
  return  this.adIntro.get('member') as FormArray
  }

  setMembers(dt:any) {
    
    for(let i=0; i<dt.length; i++) {
      this.memberArr.push(this.createMember())
      this.memberArr.at(this.memberArr.length - 1).patchValue(dt[i])
     
    }
  }
  value() {
    

  }


  addNewMember() {
    this.memberArr.push(this.createMember())
  }
  createMember(): FormGroup {
    return this.fb.group({
      id: new FormControl(),
      name: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required])
    })
  }
  setData(dt: any) {
    this.step3Form.patchValue({
      nameOfChairmanMayor: dt.nameOfChairmanMayor,
      nameOfCMO: dt.nameOfCMO,
      periodOfServiceChairman: dt.periodOfServiceChairman,
      periodOfServiceCMO: dt.periodOfServiceCMO,
    })
    this.step3Form.setValue(dt)


  }
  onSubmit() {
    this.service.createAdministration({
      internalAuditId: this.service1.step1Report.id,
      ...this.step3Form.value

    }).subscribe(res => {
      this.step3Form.disable()
      // this.nextStep.emit(res)
    })
  }
  saveMember(i: number) {
    this.service.createUpdateMember(this.service1.step1Report.id, {
      ...this.memberArr.at(i).value
    }).subscribe((res:any) => {
      this.memberArr.controls[i].patchValue(res)
      this.memberArr.controls[i].disable()
     
    }, err => { })
  }

  enableAdIntro(i: number) {
    this.memberArr.controls[i].enable()
  }
  enableForm() {
    this.step3Form.enable()
  }
}
