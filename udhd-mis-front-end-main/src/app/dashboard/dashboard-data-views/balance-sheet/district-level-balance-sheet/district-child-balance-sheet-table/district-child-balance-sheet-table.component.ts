import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';
import { RouteService } from 'src/app/dashboard/services/route.service';

@Component({
  selector: 'app-district-child-balance-sheet-table',
  templateUrl: './district-child-balance-sheet-table.component.html',
  styleUrls: ['./district-child-balance-sheet-table.component.css']
})
export class DistrictChildBalanceSheetTableComponent implements OnInit {

  dataToDisplay!: BalanceSheetDTO[];
  @Input() set clbsData(value: any) {
    this.dataToDisplay = value
   }

  constructor(private routeService: RouteService) { }

  ngOnInit(): void {

  }
  goToUlb(id: number, name: string) {
    this.routeService.geography$.next({
      level: 'ulb',
      particular: name,
      id: id
    })
  }
}


