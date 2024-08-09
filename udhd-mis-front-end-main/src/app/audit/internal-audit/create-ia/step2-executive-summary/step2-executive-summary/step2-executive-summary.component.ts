import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-step2-executive-summary',
  templateUrl: './step2-executive-summary.component.html',
  styleUrls: ['./step2-executive-summary.component.css']
})
export class Step2ExecutiveSummaryComponent implements OnInit {

  constructor() { }

  temp: any;
  @Input() set step1Report(value: any) {
    this.temp = value
  }
  ngOnInit(): void {
  }

}
