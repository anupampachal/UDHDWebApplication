import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-add-part-aobjective',
  templateUrl: './add-part-aobjective.component.html',
  styleUrls: ['./add-part-aobjective.component.css']
})
export class AddPartAobjectiveComponent implements OnInit {
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

  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService)
  { }


  ngOnInit(): void {
    this.objectiveForm = this.fb.group({
      'objectives': new FormArray([])
    });
    this.leakageList.forEach(leak => this.objectivesControl.push(this.createObjectiveForm()));

  }
  createObjectiveForm(): FormGroup {
    return this.fb.group({
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
    
    if(this.partAtype[i] == undefined) {
      this.partAtype[i] = "OTHERS"
    }
    
    this.service.createUpdateAuditObsPartA({
      ...this.objectivesControl.controls[i].value,
      partAType: this.partAtype[i],
      iaId: this.service1.step1Report.id,
      id: this.partAId[i] != undefined ? this.partAId[i]: null
    }).pipe(
      map(res => res),
      mergeMap((res:any) => {
        this.partAId[i] = res.id
        if(this.selectedFile) {
        return this.service.uploadFileForLineItem(this.partAtype[i], res.id, this.service1.step1Report.id, this.selectedFile);
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
