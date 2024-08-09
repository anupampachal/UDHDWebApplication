import { StepperSelectionEvent } from '@angular/cdk/stepper';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute, Router } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { Observable } from 'rxjs';
import { filter, map, switchMap, tap } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { Step1ReportService } from '../services/step-1-report.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IAStep1ResponseDTO } from '../models/step1DTO';


@Component({
  selector: 'app-view-ia',
  templateUrl: './view-ia.component.html',
  styleUrls: ['./view-ia.component.css']
})
export class ViewIaComponent implements OnInit {
  @ViewChild('stepper') stepper!: MatStepper;
  currentStep = 'Report';
  authority=localStorage.getItem('authority')
  step1Report$?: Observable<any>;
  deleteError = false;
  currentScr = 'list';
  criteriaIdInfo = 0;
  step1ResponseDTO!:IAStep1ResponseDTO;
  snackBarRef:any;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  isSendForReviewDisabled=false;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private service: Step1ReportService,
    private _FileSaverService: FileSaverService
  ) { }
  confirmDelete(iaId:number) {
    
      
      const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
      dialogRef
        .afterClosed()
        .pipe(
          filter((res) => res === true && !!this.id),
          switchMap(res=> this.service.deleteStep1Report(iaId))
        )
        .subscribe(
          () =>{
            this.snackBarRef= this._snackBar.open("Delete successful!", "Delete successful!", {
              duration: 5000
            });
    
            this.snackBarRef.afterDismissed().subscribe(() => {
              this.router.navigate(['../../ia-list'], { relativeTo: this.route })
            });
          }, 
          (error) => {
            this.snackBarRef= this._snackBar.open("Delete failure!", "Delete failure!", {
              duration: 5000
            });
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
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((params)=>this.service.findStep1ReportById(params['id'])),
      tap((params) => {
        if(params['id']){
          this.service.step1Report.id = params['id']
          this.id = params['id']
        }
      }),
     
    ).subscribe(
      res=> this.step1ResponseDTO= res
    );

    
  }
  nextStep() {
    this.stepper.next()
  }

  selectionChange(event: StepperSelectionEvent) {
    this.currentStep = event.selectedStep.label;
  }

  exportToPdf(){
    if(this.id)
    this.service.downloadIAReport(this.id).pipe(
      map(res => this._FileSaverService.save(res, 'ia-report.pdf')))
      .subscribe()
  }

  sendForReview(iaId:number){
    this.service.sendForReview(iaId)
    .subscribe(res=>{
      this.isSendForReviewDisabled=true;
      if(res.response){
        this.snackBarRef= this._snackBar.open("Sent for Review.", "IA Audit successfully sent to SLPMU Audit team", {
          duration: 5000
        });
      }else{
        this.snackBarRef= this._snackBar.open("Please fill all the necessary fields.", res.message, {
          duration: 5000
        });
      }
    })
  }

  assignToMe(id:number){
   
      const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
      dialogRef
        .afterClosed()
        .pipe(
          filter((res) => res === true && !!this.id),
          switchMap(() => this.service.assignIAAuditToMe(id))
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
        switchMap(() => this.service.approveRejectIAAudit(id,view))
      )
      .subscribe(
        res=>{
          this.router.navigate(['../../list-assigned-to-me'], { relativeTo: this.route })
        }
       
      );
  }
}
