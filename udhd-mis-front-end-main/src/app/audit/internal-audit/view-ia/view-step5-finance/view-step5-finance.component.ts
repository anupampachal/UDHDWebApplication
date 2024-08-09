import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, catchError } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { InternalAuditService } from '../../internal-audit-service.service';
import { Step5Service } from '../../services/step-5.service';

@Component({
  selector: 'app-view-step5-finance',
  templateUrl: './view-step5-finance.component.html',
  styleUrls: ['./view-step5-finance.component.css']
})
export class ViewStep5FinanceComponent implements OnInit {

  deleteError = false;
  step5BudgetReport$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  step5VolOfTrnx$?: Observable<any>;
  bankRecon$? : Observable<any>
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: Step5Service
  ) { }
  // confirmDelete() {
  //   const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
  //   dialogRef
  //     .afterClosed()
  //     .pipe(
  //       filter((res) => res === true && !!this.id),
  //       switchMap(() => this.service.deleteReportByID(this.id))
  //     )
  //     .subscribe(
  //       () => this.router.navigate(['../../list'], { relativeTo: this.route }),
  //       (error) => {
  //         this.deleteError = true;
  //         setTimeout(() => (this.deleteError = false), 5000);
  //       }
  //     );
  // }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.step5BudgetReport$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findBudgetById(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );
    this.step5VolOfTrnx$ =  this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findVOTById(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );
    this.bankRecon$ =  this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.getAllBankReconStatement(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );
      this.bankRecon$.subscribe(res => {}, error =>{})
  }

}
