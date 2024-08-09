import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as _moment from 'moment';
import * as moment from 'moment';
import { DateRangeDTO } from '../date-range-dto';


@Component({
  selector: 'app-date-filter',
  templateUrl: './date-filter.component.html',
  styleUrls: ['./date-filter.component.css']
})
export class DateFilterComponent implements OnInit {
  dateRangeForm!:FormGroup;
  @Output() dateRangeValue = new EventEmitter()

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.dateRangeForm = this.fb.group({
      startDate: [null, Validators.required],
      endDate:[null, Validators.required],
    })
  }
  searchByDate() {
    const dateRange: DateRangeDTO = new DateRangeDTO()
    dateRange.startDate =  this.getDateInDDMMYYYY(this.dateRangeForm.controls['startDate'].value);
    dateRange.endDate =  this.getDateInDDMMYYYY(this.dateRangeForm.controls['endDate'].value);
    this.dateRangeValue.emit(dateRange)
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }
}
