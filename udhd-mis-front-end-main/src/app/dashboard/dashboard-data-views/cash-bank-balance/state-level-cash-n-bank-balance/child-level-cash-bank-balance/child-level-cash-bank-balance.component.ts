import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/dashboard/models/cash-bank-balance-curr-level-dto';
import { RouteService } from 'src/app/dashboard/services/route.service';
import { ChildLevelDTO } from '../../../income-expense/model/child-level-dto';

@Component({
  selector: 'app-child-level-cash-bank-balance',
  templateUrl: './child-level-cash-bank-balance.component.html',
  styleUrls: ['./child-level-cash-bank-balance.component.css']
})
export class ChildLevelCashBankBalanceComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO[];
  @Input() set clieData(value: any) {

    this.dataToDisplay = value
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


