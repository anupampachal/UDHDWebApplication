import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/deas/models/cash-bank-balance-curr-level-dto';
import { RouteService } from 'src/app/deas/services/route.service';

@Component({
  selector: 'app-child-level-district',
  templateUrl: './child-level-district.component.html',
  styleUrls: ['./child-level-district.component.css']
})
export class ChildLevelDistrictComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO[];

  @Input() set clieData(value: any) {

    this.dataToDisplay = value
   }


  constructor(private routeService: RouteService) { }

  ngOnInit(): void {
    this.routeService.geography$.subscribe()
  }
  goToUlb(id: number, name: string) {

    this.routeService.geography$.next({
      level: 'ulb',
      particular: name,
      id: id
    })
  }
}


