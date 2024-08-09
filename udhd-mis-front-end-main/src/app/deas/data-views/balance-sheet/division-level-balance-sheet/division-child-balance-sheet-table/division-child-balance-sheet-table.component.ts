import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/deas/models/balance-sheet-dto';
import { RouteService } from 'src/app/deas/services/route.service';

@Component({
  selector: 'app-division-child-balance-sheet-table',
  templateUrl: './division-child-balance-sheet-table.component.html',
  styleUrls: ['./division-child-balance-sheet-table.component.css']
})
export class DivisionChildBalanceSheetTableComponent implements OnInit {

  dataToDisplay!: BalanceSheetDTO[];
  @Input() set clbsData(value: any) {
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


