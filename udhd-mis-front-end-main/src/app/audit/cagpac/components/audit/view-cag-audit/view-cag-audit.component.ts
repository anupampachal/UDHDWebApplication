import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { CAGAuditDTO } from '../../../models/cag-audit.model';
import { CAGAuditService } from '../cag-audit.service';

@Component({
  selector: 'app-view-cag-audit',
  templateUrl: './view-cag-audit.component.html',
  styleUrls: ['./view-cag-audit.component.css']
})
export class ViewCagAuditComponent implements OnInit {
  cag$?: Observable<CAGAuditDTO>;
  deleteError = false;
  currentScr = 'list';
  criteriaIdInfo = 0;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  messageStatus!:boolean;
  message!:string;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private cagAuditService: CAGAuditService,
    private _FileSaverService: FileSaverService
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
    this.cag$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.cagAuditService.findCAGAuditById(param['id'])),
      shareReplay(2)
    );
  }

  exportTableToPdf() {
    //this.dmt.exportToCSV([7])
    if(this.id)
    this.cagAuditService.downloadCAGAuditReport(this.id).pipe(
      map(res => this._FileSaverService.save(res, 'cag-report.pdf')))
      .subscribe()
  }

  sendForReview(id:number){
     this.confirmSendForReview(id)
  }

  assignToMe(id:number){
    this.confirmAssignToMe(id);
  }
  confirmSendForReview(id:number) {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.cagAuditService.sendCAGForAudit(id))
      )
      .subscribe(
        res=>{
          this.message=res.message;
          this.messageStatus=res.response;
          setTimeout(()=>{
            this.router.navigate(['../../list'], { relativeTo: this.route })
          })
        }
       
      );
  }

  confirmAssignToMe(id:number) {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.cagAuditService.assignCagAuditToMe(id))
      )
      .subscribe(
        res=>{
          this.router.navigate(['../../list-assigned-to-me'], { relativeTo: this.route })
        }
       
      );
  }

  approveReject(id:number,view:boolean){
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.cagAuditService.approveRejectCAGAudit(id,view))
      )
      .subscribe(
        res=>{
          this.router.navigate(['../../list-assigned-to-me'], { relativeTo: this.route })
        }
       
      );
  }
 
}
