import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-hierarchy-data',
  templateUrl: './hierarchy-data.component.html',
  styleUrls: ['./hierarchy-data.component.css']
})
export class HierarchyDataComponent implements OnInit {
  tableStartDate!: Date;
  tableEndDate!: Date;
  _hierarchyData!: any;
  @Input() set hierarchyData(value: any) {
    this._hierarchyData = value;
    
  }

  constructor() { }

  ngOnInit(): void {
    this.tableStartDate = new Date();
    this.tableEndDate = new Date();
    this.tableStartDate.setFullYear(this.tableStartDate.getFullYear() - 1);
    this.tableStartDate.setDate(1);
    this.tableStartDate.setMonth(3);
  }

}
