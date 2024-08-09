import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, switchMap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { FinYearDateDTO } from '../../fin-year-date-dto';
import { FixedAssetService } from '../../services/fixed-asset.service';

@Component({
  selector: 'app-create-fixed-asset',
  templateUrl: './create-fixed-asset.component.html',
  styleUrls: ['./create-fixed-asset.component.css']
})
export class CreateFixedAssetComponent implements OnInit {

  createFixedAssetData!: FormGroup;
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
  quarters = ['Q1', 'Q2', 'Q3', 'Q4']
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: FixedAssetService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createFixedAssetData = this.fb.group({
      inputData: new FormControl(null, [Validators.required]),
      ulbId: new FormControl(null, [Validators.required]),

    });
    this.service.getULBForFixedAsset().subscribe(res => {
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
    if (this.createFixedAssetData.valid) {
      this.service
        .uploadFixedAssetData(this.selectedFile, this.createFixedAssetData.value)
        .subscribe((res:FinYearDateDTO) => {
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
            // this.progress = Math.round(100 * res.loaded / res.total);

        });
    } else if (!!this.createFixedAssetData.errors) {
      this.showFormError = this.createFixedAssetData.errors;
    }
  }

}
