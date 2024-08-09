import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-add-part-bdescription',
  templateUrl: './add-part-bdescription.component.html',
  styleUrls: ['./add-part-bdescription.component.css']
})
export class AddPartBDescriptionComponent implements OnInit {
  partBForm!: FormGroup;
  selectedFile!: File | any;
  showFileError!: string | any;
  fileName: any;
  isEdit = true;
  isImage = false;
  urr: string = ""
  @Input() set bsub(value: number) {
    this.urr = this.partBList[value]
  }
  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService
  ) { }

  partBList: string[] = [
    'NON_MAINTENANCE_OF_BOOKS', 'IRREGULARITIES_IN_PROCUREMENT',
    'NON_COMPLIANCE_OF_DIRECTIVES', 'NON_COMPLIANCE_OF_ACT_N_RULES',
    'LACK_OF_INTERNAL_CONTROL_MEASURES', 'DEFICIENCY_IN_PAYROLL_SYSTEM',
    'PHYSICAL_VERIFICATION_OF_INVENTORY',
    'ADVANCES_N_THEIR_ADJUSTMENT', 'ANY_OTHER_MATTER_IN_DUE_COURSE',
  ]


  ngOnInit(): void {
    this.partBForm = this.fb.group({
      'details': new FormArray([this.createPartCForm(), this.createPartCForm(), this.createPartCForm()])
    });

  }
  get detailsControl(): FormArray {
    return this.partBForm.get('details') as FormArray
  }
  createPartCForm(): FormGroup {

    return this.fb.group({
      'id': new FormControl(null),
      'description': new FormControl(null, [Validators.required, Validators.minLength(2)])
    });
  }

  pushNewForm() {
    this.detailsControl.push(this.createPartCForm());
  }

  remove(index: number) {
    this.detailsControl.controls.splice(index, 1)

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
  savePartB(i: number) {
    this.service.createUpdatePartB({
      iaId: this.service1.step1Report.id,
      description:  this.detailsControl.controls[i].value.description,
      type: this.urr,
      id: this.detailsControl.controls[i].value.id
    }).pipe(
      map(res => res),
      mergeMap((res:any) => {
        this.detailsControl.at(i).patchValue({
          id: res.id
        })
        if(this.selectedFile) {
        return this.service.uploadFilePartB(res.id, this.service1.step1Report.id,this.selectedFile);
        }
        return of(res)
      })
    )

    .subscribe((res: any) => {

      this.detailsControl.controls[i].disable()
    },
    err => {}
    )
  }
enableControl(i: number) {
  this.detailsControl.controls[i].enable()
}
}
