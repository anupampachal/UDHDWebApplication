import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, switchMap } from 'rxjs/operators';
import { FinancialStatementsService } from 'src/app/deas/services/financial-statements.service';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { AFSDTO } from '../../afs-dto';

@Component({
  selector: 'app-create-annual-financial-statement',
  templateUrl: './create-annual-financial-statement.component.html',
  styleUrls: ['./create-annual-financial-statement.component.css']
})
export class CreateAnnualFinancialStatementComponent implements OnInit {

  createAFS!: FormGroup;
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

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: FinancialStatementsService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createAFS = this.fb.group({
      startDate: new FormControl(null, [Validators.required]),
      endDate: new FormControl(null, [Validators.required]),
      ulbId: new FormControl(null, [Validators.required]),
    });
    this.service.getULBForAFS().subscribe(res => {
      this.ulbs = res as any
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
    this.createAFS.value.startDate = this.getDateInDDMMYYYY(this.createAFS.controls['startDate'].value)
    this.createAFS.value.endDate = this.getDateInDDMMYYYY(this.createAFS.controls['endDate'].value)
    if (this.createAFS.valid) {
      this.service
        .uploadAFSData(this.selectedFile, this.createAFS.value)
        .subscribe((res: AFSDTO) => {
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
          // this.progress = Math.round(100 * res.loaded / res.total);

        });
    } else if (!!this.createAFS.errors) {
      this.showFormError = this.createAFS.errors;
    }
  }
}
