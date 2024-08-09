import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, shareReplay } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/cagpac/models/cag-criteria.model';
import { CAGAuditCriteriaService } from '../criteria.service';


@Component({
  selector: 'app-view-cag-audit-criteria',
  templateUrl: './view-cag-audit-criteria.component.html',
  styleUrls: ['./view-cag-audit-criteria.component.css']
})
export class ViewCagAuditCriteriaComponent implements OnInit {

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
    private auditCriteriaService: CAGAuditCriteriaService
  ) { }

  showList() {
    this.sendToParent.emit('list');
  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.criteria$ = this.route.params.pipe(
      switchMap((param) => this.auditCriteriaService.findCAGAuditCriteriaById(this.criteriaId)),
      shareReplay(2)
    );
  }

}
