import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { CAGAuditDTO } from '../../../models/cag-audit.model';
import { CAGAuditService } from '../cag-audit.service';
import {MatSnackBar, MatSnackBarRef, TextOnlySnackBar} from '@angular/material/snack-bar';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
@Component({
  selector: 'app-edit-cag-audit',
  templateUrl: './edit-cag-audit.component.html',
  styleUrls: ['./edit-cag-audit.component.css']
})
export class EditCagAuditComponent implements OnInit {
  editCAG!: FormGroup;
  id!: number;
  cag$?: Observable<CAGAuditDTO>;
  cag!:CAGAuditDTO;
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
    private cagService: CAGAuditService,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog,

  ) { }
  ngOnInit(): void {
    this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.cagService.findCAGAuditById(param['id'])),
      shareReplay(2)
    ).subscribe(res => {
      this.setVal(res);
      this.cag=res;
    });

    
   

    this.routeArray = this.route.pathFromRoot;
    this.editCAG = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
      ]),
      startDate: new FormControl(null, [Validators.required,]),
      endDate: new FormControl(null, [Validators.required]),
      ulb: new FormControl(null, Validators.required),
      description: new FormControl(null, [Validators.minLength(5), Validators.maxLength(2000)]),
      cagAuditId: new FormControl(null),
      id: new FormControl({ value: null, disabled: true }),
      auditStatus: new FormControl({ value: null, disabled: true })
    });
    this.cagService.getULBForCreation().subscribe(ulbs => this.ulbs = ulbs)
  }



  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD/MM/YYYY');
  }
  convertDDMMYYYToMMDDYYYYForEndDate(date: string) {
    const dateArr: string[] = date.split('/');
    return dateArr[1] + '/' + dateArr[0] + '/' + dateArr[2];
  }

  compareFn(x: CAGAuditDTO, y: CAGAuditDTO) {
    return x && y ? x.id === y.id : x === y;
  }
  setVal(dto: CAGAuditDTO) {
  
    this.editCAG.setValue({
      cagAuditId: dto.cagAuditId,
      id: dto.id,
      title: dto.title,
      startDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.startDate)),
      endDate: new Date(this.convertDDMMYYYToMMDDYYYYForEndDate(dto.endDate)),
      description: dto.description,
      ulb: dto.ulb,
      auditStatus: dto.auditStatus
    });
  }

  editCagAuditDTO(cagForm: FormGroup) {
    const cagAudit: CAGAuditDTO = new CAGAuditDTO();
    cagAudit.title = cagForm.controls['title'].value;
    cagAudit.auditStatus = 'DRAFT';
    cagAudit.description = cagForm.controls['description'].value
    cagAudit.id = cagForm.controls['id'].value;
    cagAudit.startDate = this.getDateInDDMMYYYY(cagForm.controls['startDate'].value);
    cagAudit.endDate = this.getDateInDDMMYYYY(cagForm.controls['endDate'].value);
    cagAudit.ulb = cagForm.controls['ulb'].value;
    return cagAudit;

  }
  onSubmit() {


    if (this.editCAG.valid) {
     

      this.cagService
        .createCAGAudit(this.editCagAuditDTO(this.editCAG))
        .subscribe((res) =>
          this.router.navigate(['../../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.editCAG.errors) {
     
      this.showFormError = this.editCAG.errors;
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
    this.editCAG.disable();
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.cagService.deleteCAGAudit(id))
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
