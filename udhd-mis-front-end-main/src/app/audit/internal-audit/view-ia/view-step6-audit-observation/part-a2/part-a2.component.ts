import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { map, mergeMap, catchError, filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-part-a2',
  templateUrl: './part-a2.component.html',
  styleUrls: ['./part-a2.component.css']
})
export class PartA2Component implements OnInit {
  extraDataForm!: FormGroup;
  selectedFile!: File | any;
  fileName: any;
  showFileError!: string | any;
  isEdit = true;
  isImage = false;
  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService,
  ) { }
  ngOnInit(): void {
    this.extraDataForm = this.fb.group({
      'objectives': new FormArray([this.createObjectiveForm()])
    });

    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getAuditObsPartAlineItemByType(param['id'], "REPORT_ON_FINDINGS_OF_FIELD_SURVEYS")),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )
    this.extraDataForm.disable()

  }

  setData(dt: any) {
    // 
    this.objectivesControl.at(0).patchValue(dt)
  }

  deleteObjective(i: number) {
    const idToDelete =  this.objectivesControl.controls[i].value.id
    this.service.deleteAuditObsById(idToDelete, this.service1.step1Report.id,"REPORT_ON_FINDINGS_OF_FIELD_SURVEYS").subscribe(() => {
      this.removeObjectiveControl(i)
    })
  }
  removeObjectiveControl(i: number) {
    this.objectivesControl.controls.splice(i, 1)
  }
  pushNewObjectiveForm() {
    this.objectivesControl['controls'].push(this.createObjectiveForm());
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

  get objectivesControl() {
    return this.extraDataForm.get('objectives') as FormArray
  }
  createObjectiveForm(): FormGroup {
    return this.fb.group({
      'objective': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'criteria': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'condition': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'consequences': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'cause': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'correctiveAction': new FormControl({ value: null, disabled: true }, [Validators.required]),
    });
  }
  remove(index: number) {
    this.objectivesControl.controls.splice(index, 1)
  }

  public selectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10 * 1024 * 1024) {
        this.resetImage(event);
        return;
      }
      else {
        this.showFileError = null;
      }

      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
      // this.isDisable = true;
    }
  }
  resetImage(event: any) {
    event.target.value = '';
    this.fileName = null;
    this.selectedFile = null;
  }
  partAId: number[] = []
  save(i: number) {
    
    this.service.createUpdateAuditObsPartA({
      ...this.objectivesControl.controls[i].value,
      partAType: "REPORT_ON_FINDINGS_OF_FIELD_SURVEYS",
      iaId: this.service1.step1Report.id,
      id: this.partAId[i] != undefined ? this.partAId[i] : null
    }).pipe(
      map(res => res),
      mergeMap((res: any) => {
        this.partAId[i] = res.id
        if (this.selectedFile) {
          return this.service.uploadFileForLineItem("REPORT_ON_FINDINGS_OF_FIELD_SURVEYS", res.id, this.service1.step1Report.id, this.selectedFile);
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
  downloadObjectiveFile(i: number) {
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getFileById(param['id'], "REPORT_ON_FINDINGS_OF_FIELD_SURVEYS")),
      tap((res) => {
        if (res.size == 0) {
          
          // this.snack.show("no File to Download","error", 3000)
        }
        else {
          this._FileSaverService.save(res, new Date().getTime() + '.pdf')
        }
      }),
      shareReplay(2)
    ).subscribe(
      (res) => { },
      error => { }
    )
  }
  enableControl(i: number) {
    this.objectivesControl.controls[i].enable()
  }
}
