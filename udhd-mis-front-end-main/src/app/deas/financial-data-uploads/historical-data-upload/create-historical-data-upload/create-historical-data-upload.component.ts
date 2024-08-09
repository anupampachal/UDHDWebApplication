import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, switchMap } from 'rxjs/operators';
import { HistoricalDataUploadService } from 'src/app/deas/services/historical-data-upload.service';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { HistoricalDataDTO } from '../historical-data-dto';

@Component({
  selector: 'app-create-historical-data-upload',
  templateUrl: './create-historical-data-upload.component.html',
  styleUrls: ['./create-historical-data-upload.component.css']
})
export class CreateHistoricalDataUploadComponent implements OnInit {

  createHistoricData!: FormGroup;
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
    private service: HistoricalDataUploadService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createHistoricData = this.fb.group({
      startDate: new FormControl(null, [Validators.required]),
      endDate: new FormControl(null, [Validators.required]),
      ulbId:new FormControl(null, [Validators.required]),
    });
    this.service.getULBForHistoricData().subscribe(res => {
      this.ulbs = res as any
    })
  }
  public onSelected(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10000 * 1024 || this.selectedFile.type != 'application/zip') {
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
   this.createHistoricData.value.startDate =  this.getDateInDDMMYYYY(this.createHistoricData.controls['startDate'].value)
   this.createHistoricData.value.endDate =  this.getDateInDDMMYYYY(this.createHistoricData.controls['endDate'].value)
    if (this.createHistoricData.valid) {
      this.service
        .uploadHistoricalData(this.selectedFile, this.createHistoricData.value)
        .subscribe((res:HistoricalDataDTO) => {
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
            // this.progress = Math.round(100 * res.loaded / res.total);

        });
    } else if (!!this.createHistoricData.errors) {
      this.showFormError = this.createHistoricData.errors;
    }
  }

}
