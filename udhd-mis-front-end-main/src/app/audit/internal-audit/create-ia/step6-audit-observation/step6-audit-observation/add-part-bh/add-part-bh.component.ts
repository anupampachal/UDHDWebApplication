import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-add-part-bh',
  templateUrl: './add-part-bh.component.html',
  styleUrls: ['./add-part-bh.component.css']
})
export class AddPartBHComponent implements OnInit {
  grantUtilizationFormModel!: FormGroup;
  selectedFile!: File | any;
  fileName!: any;
  showFileError!: string | any;
  totalAmount!: number;
  totalExpenseAmount!: number;
  totalPLAAmount!: number;
  totalPendingAmount!: number;
  totalSubmittedAmount!: number;
  isImage = false;

  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit(): void {
    this.grantUtilizationFormModel = this.fb.group({
      'utilizations': new FormArray([
        this.createGrantUtiliztionForm(),
        this.createGrantUtiliztionForm(),
        this.createGrantUtiliztionForm()
      ])
    });
  }
  get utilizationsControl(): FormArray {
    return this.grantUtilizationFormModel.get('utilizations') as FormArray
  }

  get FormGroupsArray(): FormGroup[] {
    return this.utilizationsControl.controls as FormGroup[]
  }

  createGrantUtiliztionForm() {
    return this.fb.group({
      'id': new FormControl(null),
      'schemeName': new FormControl(null, [Validators.required]),
      'fundReceived': new FormControl(null, [Validators.required]),
      'expenses': new FormControl(null, [Validators.required]),
      'balanceInPLA': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'pendingUC': new FormControl(null, [Validators.required]),
      'submittedUC': new FormControl(null, [Validators.required]),
      'letterNoNDate': new FormControl(null, [Validators.required]),
    });
  }

  pushNewForm() {
    (<FormArray>this.grantUtilizationFormModel.get('utilizations')).push(this.createGrantUtiliztionForm());
  }

  remove(index: number) {
    let utilizations = <FormArray>this.grantUtilizationFormModel.controls['utilizations'];
    utilizations.removeAt(index);
  }
  getTotalAmount(index: number, item: any) {
    var fundReceived = this.utilizationsControl.controls[index].value.fundReceived;
    var expenses = this.utilizationsControl.controls[index].value.expenses
    var total = fundReceived - expenses;
    this.utilizationsControl.controls[index].patchValue({
      'balanceInPLA': total
    });

    var balanceInPLA = this.utilizationsControl.controls[index].value.valuebalanceInPLA;


    this.totalAmount = 0;
    this.totalExpenseAmount = 0;
    this.totalPLAAmount = 0;
    this.totalPendingAmount = 0;
    this.totalSubmittedAmount = 0;
    for (let item of this.FormGroupsArray) {
      var fundReceived = item.getRawValue().fundReceived;
      var expenses = item.getRawValue().expenses;
      var balanceInPLA = item.getRawValue().balanceInPLA;
      var pendingUC = item.getRawValue().pendingUC;
      var submittedUC = item.getRawValue().submittedUC;
      this.totalAmount += fundReceived;
      this.totalExpenseAmount += expenses;
      this.totalPLAAmount += balanceInPLA;
      this.totalPendingAmount += pendingUC;
      this.totalSubmittedAmount += submittedUC;

    }
    // for (var i = 0; i < this.utilizationsControl.length; i++) {
    //   var fundReceived =  this.utilizationsControl.controls[i].value.fundReceived;
    //   var expenses =  this.utilizationsControl.controls[i].value.expenses;
    //   var balanceInPLA =  this.utilizationsControl.controls[i].value.balanceInPLA;
    //   var pendingUtilizationCertificate =  this.utilizationsControl.controls[i].value.pendingUtilizationCertificate;
    //   var submittedUtilizationCertificate =  this.utilizationsControl.controls[i].value.submittedUtilizationCertificate;
    //   var tempGroup = (<FormArray>this.grantUtilizationFormModel.get('utilizations')).at(i).value
    //   this.totalAmount += fundReceived;
    //   this.totalExpenseAmount += expenses;
    //   this.totalPLAAmount += balanceInPLA;
    //   this.totalPendingAmount += pendingUtilizationCertificate;
    //   this.totalSubmittedAmount += submittedUtilizationCertificate;

    //   this.totalAmount = parseFloat(tempGroup.fundReceived) + this.totalAmount;
    // }
  }
  isFile = false
  iconDis = false

  public onSelected(event: any) {
    this.isImage = false;
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.isFile = true
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 300 * 1024) {
        this.isFile = false;
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
  uploadFile() {
    this.service.uploadPartBHFile(this.service1.step1Report.id, this.selectedFile,).subscribe(res => {
      this.iconDis = true
    })
  }
  save(i: number) {
    this.service.createUpdateBH({
      iaId: this.service1.step1Report.id,
      ...this.utilizationsControl.at(i).value
    }).subscribe((res: any) => {
      this.utilizationsControl.at(i).patchValue({ id: res.id })
      this.utilizationsControl.at(i).disable()
      document.getElementById('del' + i)?.classList.add('dis')
    })
  }

  enableControl(i: number) {
    this.utilizationsControl.at(i).enable()
  }
}
