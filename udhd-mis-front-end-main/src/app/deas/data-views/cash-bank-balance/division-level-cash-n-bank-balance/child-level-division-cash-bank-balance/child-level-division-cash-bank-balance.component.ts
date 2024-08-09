import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/deas/models/cash-bank-balance-curr-level-dto';
import { RouteService } from 'src/app/deas/services/route.service';

@Component({
  selector: 'app-child-level-division-cash-bank-balance',
  templateUrl: './child-level-division-cash-bank-balance.component.html',
  styleUrls: ['./child-level-division-cash-bank-balance.component.css']
})
export class ChildLevelDivisionCashBankBalanceComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO[];

  @Input() set clieData(value: any) {

    this.dataToDisplay = value
   }


  constructor(private routeService: RouteService) { }

  ngOnInit(): void {

  }
  goToDistrict(id: number, name: string) {
    this.routeService.geography$.next({
      level: 'district',
      particular: name,
      id: id
    })
  }
}



