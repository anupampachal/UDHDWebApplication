import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-step4-review-of-outstanding-paras',
  templateUrl: './step4-review-of-outstanding-paras.component.html',
  styleUrls: ['./step4-review-of-outstanding-paras.component.css']
})
export class Step4ReviewOfOutstandingParasComponent implements OnInit {
  @Input() reportId!: number;
  constructor() { }

  ngOnInit(): void {
  }

}
