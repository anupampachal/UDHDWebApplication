import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormControl, Validators, FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-add-partb-f',
  templateUrl: './add-partb-f.component.html',
  styleUrls: ['./add-partb-f.component.css']
})
export class AddPartbFComponent implements OnInit {
  tdsForm!: FormGroup;
  selectedFile: File[] | any[] = [];
  fileName: any;
  showFileError!: string | any;
  isImage = false;
  tdsFormDescriptions: any[] = ['i) TDS: ', ' ii) VAT/GST: ', ' iii) ROYALTY/SEIGNIORAGE FEE: ', ' iv) TDS ON GST: ', ' v) LABOUR CESS: ']
  tdsTypeList = [ "TDS","VAT_GST","ROYALTY","TGS_ON_GST", "LABOUR_CESS" ]
  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService) { }

  ngOnInit(): void {
    this.tdsForm = this.fb.group({
      'tds': new FormArray([])
    });

    this.tdsFormDescriptions.forEach(tdsfd=>this.tdsControl.push(this.createTdsForm()))


  }

  get tdsControl(): FormArray {
    return this.tdsForm.get('tds') as FormArray
  }
  createTdsForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl(null),
      'name': new FormControl(null,),
      'description': new FormControl(null, [Validators.required]),
      'amountInvolved': new FormControl(null, [Validators.required]),
      'file': new FormControl()
    });
  }
  addTDSForm() {
    this.tdsControl['controls'].push(this.createTdsForm());
  }
  pushNewForm() {
    this.tdsControl['controls'].push(this.createTdsForm())
  }
  remove(index: number) {
    if (index < this.tdsFormDescriptions.length) {
      this.tdsFormDescriptions.splice(index, 1)
      this.tdsTypeList.splice(index,1)

    }
    this.tdsControl.controls.splice(index, 1)
  }
  public onSelected(event: any, i: number) {
    this.isImage = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile[i] = event.target.files[0];
      if (this.selectedFile[i].size > 300 * 1024) {
        this.selectedFile[i] = null;
        this.fileName = null;
        event.target.value = null;
        return;
      } else {
        let file: File = this.selectedFile[i];
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

  saveTDS(i: number) {
    if(this.tdsTypeList[i] == undefined) {
      this.tdsTypeList[i] =  "OTHERS"
    }
    this.service.createUpdatePartBF({
      id: this.tdsControl.at(i).value.id,
      iaId: this.service1.step1Report.id,
      name:this.tdsControl.at(i).value.name,
      description: this.tdsControl.at(i).value.description,
      amountInvolved: this.tdsControl.at(i).value.amountInvolved,
      type: this.tdsTypeList[i]
    }).pipe(
      mergeMap((res:any) => {
        this.tdsControl.at(i).patchValue({
          id: res.id
        })
        if(this.selectedFile[i]) {
         return this.service.uploadPartBfFile(res.id, res.iaId,this.tdsTypeList[i],this.selectedFile[i])
        }
        return of(res)
      })
    ).subscribe(res => {
      this.tdsControl.at(i).disable()
    },
    error => {}
    )
  }

enableControl(i: number) {
  this.tdsControl.controls[i].enable()
}
}
