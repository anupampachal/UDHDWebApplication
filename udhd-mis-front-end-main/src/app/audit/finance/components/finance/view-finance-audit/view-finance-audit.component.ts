import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { FinanceAuditDTO } from '../../../models/finance-audit.model';
import { FinanceAuditService } from '../finance-audit.service';

@Component({
  selector: 'app-view-finance-audit',
  templateUrl: './view-finance-audit.component.html',
  styleUrls: ['./view-finance-audit.component.css']
})
export class ViewFinanceAuditComponent implements OnInit {
  finance$?: Observable<FinanceAuditDTO>;
  deleteError = false;
  currentScr = 'list';
  criteriaIdInfo = 0;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private financeAuditService: FinanceAuditService
  ) { }
  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
      )
      .subscribe(
        () => this.router.navigate(['../../list'], { relativeTo: this.route }),
        (error) => {
          this.deleteError = true;
          setTimeout(() => (this.deleteError = false), 5000);
        }
      );
  }

  openList($event: string) {
    this.currentScr = 'list';
  }
  setCriteriaId($event: number) {
    this.criteriaIdInfo = $event;
    this.currentScr = 'view';
  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.finance$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.financeAuditService.findFinanceAuditById(param['id'])),
      shareReplay(2)
    );
  }

}
