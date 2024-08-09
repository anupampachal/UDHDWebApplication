import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { map, mergeMap, catchError, filter, shareReplay, switchMap, tap } from 'rxjs/operators';
// import { SnackService } from '../../../services/snack.service';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-view-part-aobjectives',
  templateUrl: './view-part-aobjectives.component.html',
  styleUrls: ['./view-part-aobjectives.component.css']
})
export class ViewPartAObjectivesComponent implements OnInit {
  objectiveForm!: FormGroup;
  leakageList: any[] = ['i) Non-levy of Property Tax/Holding Tax', 'ii) Delay in deposit of Tax Collected', 'iii) Mobile Tower Tax', 'iv) Rent on Municipal Properties', 'v) Advertisement Tax',
  ];
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


  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService,
    // private snack: SnackService
  ) { }

  deleteObjective(i: number) {
    const idToDelete =  this.objectivesControl.controls[i].value.id
    if(this.partAtype[i] == undefined) {
      this.partAtype[i] = "OTHERS"
    }
    this.service.deleteAuditObsById(idToDelete, this.service1.step1Report.id, this.partAtype[i]).subscribe(() => {
      this.removeObjectiveControl(i)
    })

  }
  removeObjectiveControl(i: number) {

    if (i < this.leakageList.length) {
      this.leakageList.splice(i, 1)
    }
    this.objectivesControl.controls.splice(i, 1)
  }
  ngOnInit(): void {
    this.objectiveForm = this.fb.group({
      'objectives': new FormArray([])
    });
    this.leakageList.forEach(leak => this.objectivesControl.push(this.createObjectiveForm()));
    this.objectiveForm.disable()

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
      this.objectivesControl.at(this.objectivesControl.length - 1).disable()
    }
  }

  createObjectiveForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'objective': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'criteria': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'condition': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'consequences': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'cause': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'correctiveAction': new FormControl({ value: null, disabled: true }, [Validators.required]),
    });
  }

  get objectivesControl() {
    return this.objectiveForm.get('objectives') as FormArray
  }
  pushNewForm() {
    this.objectivesControl.push(this.createObjectiveForm())
  }

  addNewObjective() {
    this.objectivesControl.push(this.createObjectiveForm())
  }

  downloadObjectiveFile(i: number) {
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getFileById(param['id'], this.partAtype[i])),
      tap((res) => {
        if (res.size == 0) {
          this.service1.show("No File To Download", "error",3000)
        }
        else {
          this._FileSaverService.save(res, new Date().getTime() + '.pdf')
          this.service1.show("File Downloaded", "success",3000)

        }
      }),
      shareReplay(2)
    ).subscribe(
      (res) => { },
      error => { }
    )
  }



}
