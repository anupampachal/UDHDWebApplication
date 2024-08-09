import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { map, mergeMap, catchError, filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-edit-objective1-bc',
  templateUrl: './edit-objective1-bc.component.html',
  styleUrls: ['./edit-objective1-bc.component.css']
})
export class EditObjective1BcComponent implements OnInit {
  extraDataForm!: FormGroup;
  selectedFile!: File | any;
  fileName: any;
  showFileError!: string | any;
  isEdit = true;
  isImage = false;
  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService,
    private route: ActivatedRoute,

    )
  { }
  ngOnInit(): void {
    this.extraDataForm = this.fb.group({
      'objectives': new FormArray([this.createObjectiveForm()])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getAuditObsPartAlineItemByType(param['id'], "REPORT_ON_FINDINGS_OF_FIELD_SURVEYS")),
      tap((dt) => this.setData(dt)),
      shareReplay(2)).subscribe(
        () => {},
        error => {}
      )
  }
  setData(dt: any) {
    // 
      this.objectivesControl.at(0).patchValue(dt)
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
      'id': new FormControl(null),
      'objective': new FormControl(null, [Validators.required]),
      'criteria': new FormControl(null, [Validators.required]),
      'condition': new FormControl(null, [Validators.required]),
      'consequences': new FormControl(null, [Validators.required]),
      'cause': new FormControl(null, [Validators.required]),
      'correctiveAction': new FormControl(null, [Validators.required]),
    });
  }
  remove(index: number) {
    this.objectivesControl.controls.splice(index, 1)
  }

  public selectFile(event:any) {
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
  resetImage(event:any) {
    event.target.value = '';
    this.fileName = null;
    this.selectedFile = null;
  }
partAId: number[] = []
  save(i: number) {
    
    this.service.createUpdateAuditObsPartA({
      id: this.partAId[i] != undefined ? this.partAId[i]: null,
      ...this.objectivesControl.controls[i].value,
      partAType: "REPORT_ON_FINDINGS_OF_FIELD_SURVEYS",
      iaId: this.service1.step1Report.id,
    }).pipe(
      map(res => res),
      mergeMap((res:any) => {
        this.partAId[i] = res.id
        if(this.selectedFile) {
        return this.service.uploadFileForLineItem("REPORT_ON_FINDINGS_OF_FIELD_SURVEYS", res.id, this.service1.step1Report.id, this.selectedFile);
        }
        return of(res)
      }),
      catchError(err => {
        return of()
      } )
    )
    .subscribe((res: any) => {
      this.objectivesControl.controls[i].disable()
    })
  }

  enableControl(i: number) {
    this.objectivesControl.controls[i].enable()
  }
}
