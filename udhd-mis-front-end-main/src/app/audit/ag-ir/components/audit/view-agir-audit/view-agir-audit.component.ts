import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { AGIRAuditDTO } from '../../../models/agir-audit.model';
import { AGIRAuditService } from '../agir-audit.service';

@Component({
  selector: 'app-view-agir-audit',
  templateUrl: './view-agir-audit.component.html',
  styleUrls: ['./view-agir-audit.component.css']
})
export class ViewAgirAuditComponent implements OnInit {
  agir$?: Observable<AGIRAuditDTO>;
  deleteError = false;
  currentScr = 'list';
  criteriaIdInfo = 0;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  authority=localStorage.getItem('authority')
  messageStatus!:boolean;
  message!:string;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private agirAuditService: AGIRAuditService,
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
    this.agir$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.agirAuditService.findAGIRAuditById(param['id'])),
      shareReplay(2)
    );
  }

  exportTableToPdf() {
    //this.dmt.exportToCSV([7])
    if(this.id)
    this.agirAuditService.downloadAGIRAuditReport(this.id).pipe(
      map(res => this._FileSaverService.save(res, 'agir-report.pdf')))
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
        switchMap(() => this.agirAuditService.sendAGIRForAudit(id))
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
        switchMap(() => this.agirAuditService.assignAgirAuditToMe(id))
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
        switchMap(() => this.agirAuditService.approveRejectAGIRAudit(id,view))
      )
      .subscribe(
        res=>{
          this.router.navigate(['../../list-assigned-to-me'], { relativeTo: this.route })
        }
       
      );
  }
 
}
