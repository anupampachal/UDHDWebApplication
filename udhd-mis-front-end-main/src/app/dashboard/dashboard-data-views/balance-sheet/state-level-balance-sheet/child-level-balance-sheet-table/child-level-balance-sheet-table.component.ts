import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';
import { RouteService } from 'src/app/dashboard/services/route.service';

@Component({
  selector: 'app-child-level-balance-sheet-table',
  templateUrl: './child-level-balance-sheet-table.component.html',
  styleUrls: ['./child-level-balance-sheet-table.component.css']
})
export class ChildLevelBalanceSheetTableComponent implements OnInit {


  dataToDisplay!: BalanceSheetDTO[];
  @Input() set clbsData(value: any) {
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


