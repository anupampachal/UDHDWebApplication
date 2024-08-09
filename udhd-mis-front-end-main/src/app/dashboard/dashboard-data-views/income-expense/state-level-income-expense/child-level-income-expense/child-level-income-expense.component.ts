import { Component, Input, OnInit } from '@angular/core';
import { RouteService } from 'src/app/dashboard/services/route.service';
import { ChildLevelDTO } from '../../model/child-level-dto';

@Component({
  selector: 'app-child-level-income-expense',
  templateUrl: './child-level-income-expense.component.html',
  styleUrls: ['./child-level-income-expense.component.css']
})
export class ChildLevelIncomeExpenseComponent implements OnInit {

  currentPeriod!: string;
  prevPeriod!: string;
  dataToDisplay!: ChildLevelDTO[];
  _hierarchyData!: any;
  @Input() set clieData(value: any) {

    this.dataToDisplay = value
    if (this.dataToDisplay.length) {
      this.currentPeriod = this.dataToDisplay[0].currentPeriod
      this.prevPeriod = this.dataToDisplay[0].previousPeriod
    }
  }
  @Input() set hierarchyData(value: any) {
    this._hierarchyData = value;

  }

  constructor(private routeService: RouteService) { }

  ngOnInit(): void {

  }
  goToDivision(id: number, name: string) {

    this.routeService.geography$.next({
      level: 'division',
      particular: name,
      id: id
    })
  }
}

