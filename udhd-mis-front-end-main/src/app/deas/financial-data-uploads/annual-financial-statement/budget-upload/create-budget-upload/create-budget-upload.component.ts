import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, switchMap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { FinYearDateDTO } from '../../fin-year-date-dto';
import { BudgetUploadDTO } from '../../models/budget-upload-dto';
import { BudgetUploadService } from '../../services/budget-upload.service';

@Component({
  selector: 'app-create-budget-upload',
  templateUrl: './create-budget-upload.component.html',
  styleUrls: ['./create-budget-upload.component.css']
})
export class CreateBudgetUploadComponent implements OnInit {

  createBudgetUploadData!: FormGroup;
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  progress = 0;
  message = '';
  errorMsg = '';
  ulbs: ULBDTO[] = []
  finYears!: any;
  // quarters = ['Q1', 'Q2', 'Q3', 'Q4']
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: BudgetUploadService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createBudgetUploadData = this.fb.group({
      inputData: new FormControl(null, [Validators.required]),
      ulbId: new FormControl(null, [Validators.required]),

    });
    this.service.getULBForBudget().subscribe(res => {
      this.ulbs = res as any
    })

    this.service.getFinancialYearData().subscribe(fyears => {
      this.finYears = fyears
    })
  }
  public onSelected(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10000 * 1024 || this.selectedFile.type != 'application/pdf') {
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      }

      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
  onSubmit() {
    // this.createBudgetUploadData.value.inputData.quarter =  this.createBudgetUploadData.value.quarter
  

    if (this.createBudgetUploadData.valid) {
        this.service
          .uploadBudgetData(this.selectedFile, this.createBudgetUploadData.value)
          .subscribe(res => {
            this.router.navigate(['../view/', res.id], {
              relativeTo: this.route,
            })
          });
      } else if (!!this.createBudgetUploadData.errors) {
        this.showFormError = this.createBudgetUploadData.errors;
      }
  }

}
