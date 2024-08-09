import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RouteService } from 'src/app/landing/services/route.service';

@Component({
  selector: 'app-cash-and-bank-balance',
  templateUrl: './cash-and-bank-balance.component.html',
  styleUrls: ['./cash-and-bank-balance.component.css']
})
export class CashAndBankBalanceComponent implements OnInit {
  currentLevel!: string;
  constructor(private routeService: RouteService) { }

  ngOnInit(): void {
    this.routeService.geography$.subscribe(level => {
      this.currentLevel = level.level
    })
  }
  }


