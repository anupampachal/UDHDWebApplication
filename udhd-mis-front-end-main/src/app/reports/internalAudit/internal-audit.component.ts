import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-internal-audit',
  template: `
     <router-outlet></router-outlet>
  `,
  styles: [
  ]
})
export class InternalAuditComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
