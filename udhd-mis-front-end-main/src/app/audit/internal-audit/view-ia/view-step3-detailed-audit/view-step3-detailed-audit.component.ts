import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, switchMap, tap, shareReplay, catchError } from 'rxjs/operators';
import { Step1ReportService } from '../../services/step-1-report.service';
import { Step3Service } from '../../services/step-3.service';

@Component({
  selector: 'app-view-step3-detailed-audit',
  templateUrl: './view-step3-detailed-audit.component.html',
  styleUrls: ['./view-step3-detailed-audit.component.css']
})
export class ViewStep3DetailedAuditComponent implements OnInit {

  step3Form!: FormGroup;
  showFormError?: ValidationErrors;
  @Output() nextStep = new EventEmitter();
  adIntro!: FormGroup;
  memberType: string[] = [
    "MUNICIPAL_ASSISTANTS", "MUNICIPAL_EXPERTS", "TEAM_LEAD"
  ]
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
      internalAuditId: new FormControl({value:null, disabled: true}),
      nameOfChairmanMayor: new FormControl({value:null, disabled: true}, [
        Validators.required,
      ]),
      nameOfCMO: new FormControl({value:null, disabled: true}, [Validators.required,]),
      periodOfServiceChairman: new FormControl({value:null, disabled: true}, [Validators.required]),
      periodOfServiceCMO: new FormControl({value:null, disabled: true}, Validators.required),
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.findIaAdminById(param['id'])),
      tap((dt) => this.setData(dt)),
      // shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )
  }
  setData(dt: any) {
    this.step3Form.patchValue({
      nameOfChairmanMayor: dt.nameOfChairmanMayor,
      nameOfCMO: dt.nameOfCMO,
      periodOfServiceChairman: dt.periodOfServiceChairman,
      periodOfServiceCMO: dt.periodOfServiceCMO,
    })
    this.step3Form.setValue(dt)
    this.step3Form.disable()
  }

  get memberArr(): FormArray{
    return  this.adIntro.get('member') as FormArray
    }

    setMembers(dt:any) {
      for(let i=0; i<dt.length; i++) {
        this.memberArr.push(this.createMember(dt[i]))
        this.memberArr.at(this.memberArr.length - 1).patchValue(dt[i])
        this.memberArr.at(this.memberArr.length - 1).disable()

      }
    }

    createMember(data: any): FormGroup {
      return this.fb.group({
        id: new FormControl(),
        name: new FormControl(null, [Validators.required]),
        type: new FormControl(null, [Validators.required])
      })
    }

}
