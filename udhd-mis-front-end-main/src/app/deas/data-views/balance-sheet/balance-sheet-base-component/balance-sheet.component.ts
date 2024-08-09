import { Component, OnInit } from '@angular/core';
import { RouteService } from 'src/app/deas/services/route.service';

@Component({
  selector: 'app-balance-sheet',
  templateUrl: './balance-sheet.component.html',
  styleUrls: ['./balance-sheet.component.css']
})
export class BalanceSheetComponent implements OnInit {
  currentLevel!: string;
  constructor(private routeService: RouteService) { }

  ngOnInit(): void {
    this.routeService.geography$.subscribe(level => {
      this.currentLevel = level.level
    })
  }
  }
