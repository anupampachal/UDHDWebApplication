import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-uc',
  template: `
  <router-outlet></router-outlet>
  `,
  styles: [
  ]
})
export class UcComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
