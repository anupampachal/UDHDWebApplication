import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-view-finance-audit-compliances',
  templateUrl: './view-finance-audit-compliances.component.html',
  styleUrls: ['./view-finance-audit-compliances.component.css']
})
export class ViewFinanceAuditCompliancesComponent implements OnInit {

  @Input() complianceId!: number;
  constructor() { }

  ngOnInit(): void {
    alert(this.complianceId);
  }

}
