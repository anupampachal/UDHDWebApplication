import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { AGIRAuditDTO } from '../../../models/agir-audit.model';
import { AGIRAuditService } from '../agir-audit.service';
import {MatSnackBar, MatSnackBarRef, TextOnlySnackBar} from '@angular/material/snack-bar';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
@Component({
  selector: 'app-edit-agir-audit',
  templateUrl: './edit-agir-audit.component.html',
  styleUrls: ['./edit-agir-audit.component.css']
})
export class EditAgirAuditComponent implements OnInit {
  editAGIR!: FormGroup;
  id!: number;
  agir$?: Observable<AGIRAuditDTO>;
  agir!:AGIRAuditDTO;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  ulbs!: ULBDTO;
  //snackBarRef:MatSnackBarRef<TextOnlySnackBar>=new MatSnackBarRef<TextOnlySnackBar>(null,"");
  snackBarRef:any;
  criteriaIdInfo = 0;
  currentScr = 'list';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private agirService: AGIRAuditService,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog,

  ) { }
  ngOnInit(): void {
    this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.agirService.findAGIRAuditById(param['id'])),
      shareReplay(2)
    ).subscribe(res => {
      this.setVal(res);
      this.agir=res;
    });

    
   

    this.routeArray = this.route.pathFromRoot;
    this.editAGIR = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulb: new FormControl(null, Validators.required),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)]),
      agirAuditId: new FormControl(null),
      id: new FormControl({ value: null, disabled: true }),
      auditStatus: new FormControl({ value: null, disabled: true })
    });
    this.agirService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }



  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }
  convertDDMMYYYToMMDDYYYYForEndDate(date: string) {
    const dateArr: string[] = date.split('/');
    return dateArr[1] + '/' + dateArr[0] + '/' + dateArr[2];
  }

  compareFn(x: AGIRAuditDTO, y: AGIRAuditDTO) {
    return x && y ? x.id === y.id : x === y;
  }
  setVal(dto: AGIRAuditDTO) {
  
    this.editAGIR.setValue({
      agirAuditId: dto.agirAuditId,
      id: dto.id,
      title: dto.title,
      startDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.startDate)),
      endDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.endDate)),
      description: dto.description,
      ulb: dto.ulb,
      auditStatus: dto.auditStatus
    });
  }

  editAgirAuditDTO(agirForm: FormGroup) {
    const agirAudit: AGIRAuditDTO = new AGIRAuditDTO();
    agirAudit.title = agirForm.controls['title'].value;
    agirAudit.auditStatus = 'DRAFT';
    agirAudit.description = agirForm.controls['description'].value
    agirAudit.id = agirForm.controls['id'].value;
    agirAudit.startDate = this.getDateInDDMMYYYY(agirForm.controls['startDate'].value);
    agirAudit.endDate = this.getDateInDDMMYYYY(agirForm.controls['endDate'].value);
    agirAudit.ulb = agirForm.controls['ulb'].value;
    return agirAudit;

  }
  onSubmit() {


    if (this.editAGIR.valid) {
     

      this.agirService
        .createAGIRAudit(this.editAgirAuditDTO(this.editAGIR))
        .subscribe((res) =>
          this.router.navigate(['../../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.editAGIR.errors) {
     
      this.showFormError = this.editAGIR.errors;
    }
  }

  openList($event: string) {
    this.currentScr = 'list';
   
  }
  setCriteriaId($event: number) {
    this.criteriaIdInfo = $event;
    this.currentScr = 'view';
  }

  sendToEdit($event: number) {
    this.currentScr = 'edit';
    this.criteriaIdInfo = $event;
  }

  deleteAgAudit(id:number){
    this.editAGIR.disable();
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.agirService.deleteAGIRAudit(id))
      )
      .subscribe(
        res=>{
          
          
         this.snackBarRef= this._snackBar.open(res.message, res.message, {
            duration: 5000
          });
  
          this.snackBarRef.afterDismissed().subscribe(() => {
            this.router.navigate(['../../list/'], {
              relativeTo: this.route,
            })
          });
        }
      );
    
  }
}
