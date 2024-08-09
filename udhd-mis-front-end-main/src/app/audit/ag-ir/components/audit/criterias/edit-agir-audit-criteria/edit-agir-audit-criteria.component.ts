import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/ag-ir/models/agir-criteria.model';
import { AGIRAuditCriteriaService } from '../criteria.service';
import {MatSnackBar, MatSnackBarRef, TextOnlySnackBar} from '@angular/material/snack-bar';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-edit-agir-audit-criteria',
  templateUrl: './edit-agir-audit-criteria.component.html',
  styleUrls: ['./edit-agir-audit-criteria.component.css']
})
export class EditAgirAuditCriteriaComponent implements OnInit {
  //@Input() agirAuditCriteriaId!: number;
  @Input() parent!: string;
  @Input() status!: string;
  @Output() sendToView = new EventEmitter<number>();
  @Output() sendToParent = new EventEmitter<string>();
  @Output() sendToList = new EventEmitter<string>();
  @Output() showListCriteriaEvent = new EventEmitter<AuditCriteriaDTO>();
  @Input() criteriaId!: number;
  criteria$?: Observable<AuditCriteriaDTO>;
  editAGIRAuditCriteriaForm!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  auditCriteriaDTO!: AuditCriteriaDTO;
  routeArray: ActivatedRoute[] = [];
  criteria_dt_id!: number;
  snackBarRef:any;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private agirAuditCriteriaService: AGIRAuditCriteriaService,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router,
  ) {

  }
  ngOnInit(): void {
    
    this.routeArray = this.route.pathFromRoot;
    this.editAGIRAuditCriteriaForm = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(512),
      ]),
      id: new FormControl(null, [Validators.required]),
      agirAuditId: new FormControl(this.criteriaId, [Validators.required]),
      auditPara: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(255),
      ]),
      status: new FormControl(true, [
        Validators.required,
      ]),
      associatedRisk: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(255),
      ]),
      amount: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.minLength(3), Validators.maxLength(2000)])
    });
    this.criteria$ = this.route.params.pipe(
      switchMap((param) => this.agirAuditCriteriaService.findAGIRAuditCriteriaById(this.criteriaId)),
      tap(data => this.setData(data)),
      shareReplay(2)
    );

    // this.criteria$.subscribe(this.setData)
  }


  onSubmit() {
    if (this.editAGIRAuditCriteriaForm.valid) {
      this.agirAuditCriteriaService
        .createAGIRAuditCriteria(this.editAGIRAuditCriteriaForm.value)
        .subscribe((res) => {
          this.sendToList.emit("list")
          this.showListCriteriaEvent.emit(res);
        }
        );
    } else if (!!this.editAGIRAuditCriteriaForm.errors) {
      this.showFormError = this.editAGIRAuditCriteriaForm.errors;
    }
  }

  sendToListM(data: string) {
    this.sendToList.emit('list');
   // this.showListCriteriaEvent.emit('list');
  }

  setData(data: AuditCriteriaDTO) {
    this.editAGIRAuditCriteriaForm.setValue({
      title: data.title,
      id: data.id,
      agirAuditId: this.criteriaId,
      auditPara: data.auditPara,
      status: data.status,
      associatedRisk: data.associatedRisk,
      amount: data.amount,
      description: data.description
    });
    if(data.id){
      this.criteria_dt_id=data.id;
    }
   
  }
  deleteCriteriaClicked(critId:number){
    //this.editAGIR.disable();
   const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
   this.editAGIRAuditCriteriaForm.disable();
   dialogRef
     .afterClosed()
     .pipe(
       filter((res) => res === true && !!this.criteriaId),
       switchMap(() => this.agirAuditCriteriaService.deleteAGIRAuditCriteria(critId))
     )
     .subscribe(
       res=>{
         
        this.snackBarRef= this._snackBar.open(res.message, res.message, {
           duration: 5000
         });
 
         this.snackBarRef.afterDismissed().subscribe(() => {
          this.sendToParent.emit('list');

         });
       }
     );
   
 }
}
