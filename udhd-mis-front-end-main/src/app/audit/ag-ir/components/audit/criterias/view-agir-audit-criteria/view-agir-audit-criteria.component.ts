import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, shareReplay } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/ag-ir/models/agir-criteria.model';
import { AGIRAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-view-agir-audit-criteria',
  templateUrl: './view-agir-audit-criteria.component.html',
  styleUrls: ['./view-agir-audit-criteria.component.css']
})
export class ViewAgirAuditCriteriaComponent implements OnInit {

  @Input() criteriaId!: number;
  @Input() parent!: string;
  @Output() sendToParent = new EventEmitter<string>();
  deleteError = false;

  criteria$?: Observable<AuditCriteriaDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private auditCriteriaService: AGIRAuditCriteriaService
  ) { }

  showList() {
    this.sendToParent.emit('list');
  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.criteria$ = this.route.params.pipe(
      switchMap((param) => this.auditCriteriaService.findAGIRAuditCriteriaById(this.criteriaId)),
      shareReplay(2)
    );
  }

}
