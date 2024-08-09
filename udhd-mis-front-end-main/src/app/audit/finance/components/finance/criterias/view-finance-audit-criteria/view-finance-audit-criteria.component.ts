import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, shareReplay } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/finance/models/finance-criteria.model';
import { FinanceAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-view-finance-audit-criteria',
  templateUrl: './view-finance-audit-criteria.component.html',
  styleUrls: ['./view-finance-audit-criteria.component.css']
})
export class ViewFinanceAuditCriteriaComponent implements OnInit {

  @Input() criteriaId!: number;
  @Output() sendToParent = new EventEmitter<string>();
  deleteError = false;

  criteria$?: Observable<AuditCriteriaDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private auditCriteriaService: FinanceAuditCriteriaService
  ) { }

  showList() {
    this.sendToParent.emit('list');
  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.criteria$ = this.route.params.pipe(
      switchMap((param) => this.auditCriteriaService.findFinanceAuditCriteriaById(this.criteriaId)),
      shareReplay(2)
    );
  }

}
