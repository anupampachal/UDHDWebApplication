import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { map, mergeMap, catchError, filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-edit-part-aobjective',
  templateUrl: './edit-part-aobjective.component.html',
  styleUrls: ['./edit-part-aobjective.component.css']
})
export class EditPartAobjectiveComponent implements OnInit {
  objectiveForm!: FormGroup;
  leakageList: any[] = ['i) Non-levy of Property Tax/Holding Tax', 'ii) Delay in deposit of Tax Collected', 'iii) Mobile Tower Tax', 'iv) Rent on Municipal Properties', 'v) Advertisement Tax'];
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isImage = false;
  showFileError!: string;

  partAtype: string[] = [
    "PROPERTY_HOLDING_TAX",
    "DELAY_IN_TAX",
    "MOBILE_TOWER_TAX",
    "RENT_ON_MUNICIPAL_PROPERTIES",
    "ADVERTISEMENT_TAX",
  ]

  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }


  ngOnInit(): void {
    this.objectiveForm = this.fb.group({
      'objectives': new FormArray([])
    });
    this.leakageList.forEach(leak => this.objectivesControl.push(this.createObjectiveForm()));

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAuditObsPartAlineItemByType(param['id'], "PROPERTY_HOLDING_TAX")),
      tap((dt) => this.setProperyHoldingData(dt, 0)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAuditObsPartAlineItemByType(param['id'], "DELAY_IN_TAX")),
      tap((dt) => this.setProperyHoldingData(dt, 1)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAuditObsPartAlineItemByType(param['id'], "RENT_ON_MUNICIPAL_PROPERTIES")),
      tap((dt) => this.setProperyHoldingData(dt, 3)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAuditObsPartAlineItemByType(param['id'], "ADVERTISEMENT_TAX")),
      tap((dt) => this.setProperyHoldingData(dt, 4)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getAuditObsPartAlineItemByType(param['id'], "MOBILE_TOWER_TAX")),
      tap((dt) => this.setProperyHoldingData(dt, 2)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

      this.route.params.pipe(
        filter((params) => params && params['id']),
        switchMap((param) =>
          this.service.getOtherTypeObjective(param['id'])),
        tap((dt) => this.setOtherObjTypeData(dt)),
        shareReplay(2)).subscribe(
          () => { },
          error => { }
        )


  }


  setProperyHoldingData(dt: any, index: number) {
    this.objectivesControl.at(index).patchValue(dt)
  }
  setOtherObjTypeData(dt: any) {
    for(let i=0; i<dt.length; i++) {
      this.objectivesControl.push(this.createObjectiveForm())
      this.objectivesControl.at(this.objectivesControl.length - 1).patchValue(dt[i])
    }
  }
  createObjectiveForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl(null),
      'objective': new FormControl(null, [Validators.required]),
      'criteria': new FormControl(null, [Validators.required]),
      'condition': new FormControl(null, [Validators.required]),
      'consequences': new FormControl(null, [Validators.required]),
      'cause': new FormControl(null, [Validators.required]),
      'correctiveAction': new FormControl(null, [Validators.required]),
    });
  }

  get objectivesControl() {
    return this.objectiveForm.get('objectives') as FormArray
  }
  pushNewForm() {
    this.objectivesControl.push(this.createObjectiveForm())
  }

  public onSelected(event: any) {
    this.isImage = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 300 * 1024) {
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      } else {
        let file: File = this.selectedFile;
        if (file.type === 'image/jpeg' || file.type === 'image/png') {
          this.isImage = true;
        }
      }
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  addNewObjective() {
    this.objectivesControl.push(this.createObjectiveForm())
  }

  removeObjectiveControl(i: number) {
    
    if (i < this.leakageList.length) {
      this.leakageList.splice(i, 1)
    }
    this.objectivesControl.controls.splice(i, 1)
  }
  partAId: number[] = []
  save(i: number) {
    // 
    if (this.partAtype[i] == undefined) {
      this.partAtype[i] = "OTHERS"
    }
    // 
    this.service.createUpdateAuditObsPartA({
      id: this.partAId[i] != undefined ? this.partAId[i] : null,
      ...this.objectivesControl.controls[i].value,
      partAType: this.partAtype[i],
      iaId: this.service1.step1Report.id,
    }).pipe(
      map(res => res),
      mergeMap((res: any) => {
        this.partAId[i] = res.id
        this.objectivesControl.at(i).patchValue(res)
        if (this.selectedFile) {
          return this.service.uploadFileForLineItem(this.partAtype[i], res.id, this.service1.step1Report.id, this.selectedFile);
        }
        return of(res)
      }),
      catchError(err => {
        return of()
      })
    )
      .subscribe((res: any) => {
        this.objectivesControl.controls[i].disable()
      })
  }

  enableControl(i: number) {
    this.objectivesControl.controls[i].enable()
  }
}
