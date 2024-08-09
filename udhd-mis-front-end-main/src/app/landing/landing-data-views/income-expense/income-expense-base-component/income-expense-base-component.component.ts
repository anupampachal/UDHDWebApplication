import { Component, OnInit } from '@angular/core';
import { RouteService } from 'src/app/landing/services/route.service';

@Component({
  selector: 'app-income-expense-base-component',
  templateUrl: './income-expense-base-component.component.html',
  styleUrls: ['./income-expense-base-component.component.css']
})
export class IncomeExpenseBaseComponentComponent implements OnInit {

  currentLevel!: string;
  constructor(private routeService: RouteService) { }

  ngOnInit(): void {

    this.routeService.geography$.subscribe(level => {
      this.currentLevel = level.level
    })
  }

}
