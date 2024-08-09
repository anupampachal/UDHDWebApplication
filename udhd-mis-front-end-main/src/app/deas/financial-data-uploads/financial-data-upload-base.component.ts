import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-financial-data-upload-base',
  template: `
   <router-outlet></router-outlet>
  `,
  styles: [
  ]
})
export class FinancialDataUploadBaseComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
