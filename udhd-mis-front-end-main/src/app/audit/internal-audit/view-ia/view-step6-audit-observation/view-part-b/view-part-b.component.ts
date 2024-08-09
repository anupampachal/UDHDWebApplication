import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { catchError, filter, map, mergeMap, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-view-part-b',
  templateUrl: './view-part-b.component.html',
  styleUrls: ['./view-part-b.component.css']
})
export class ViewPartBComponent implements OnInit {
  partBForm!: FormGroup;
  selectedFile!: File | any;
  showFileError!: string | any;
  fileName: any;
  isEdit = true;
  isImage = false;
  urr: string = ""
  @Input() set bsub(value: number) {
    this.urr = this.partBList[value]
  }
  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService


  ) { }

  partBList: string[] = [
    'NON_MAINTENANCE_OF_BOOKS', 'IRREGULARITIES_IN_PROCUREMENT',
    'NON_COMPLIANCE_OF_DIRECTIVES', 'NON_COMPLIANCE_OF_ACT_N_RULES',
    'LACK_OF_INTERNAL_CONTROL_MEASURES', 'DEFICIENCY_IN_PAYROLL_SYSTEM',
    'PHYSICAL_VERIFICATION_OF_INVENTORY',
    'ADVANCES_N_THEIR_ADJUSTMENT', 'ANY_OTHER_MATTER_IN_DUE_COURSE',
  ]


  ngOnInit(): void {
    this.partBForm = this.fb.group({
      'details': new FormArray([this.createPartCForm(), this.createPartCForm(), this.createPartCForm()])
    });
    
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getPartBlistByType(param['id'], this.urr)),
      tap((dt) => this.setValue(dt)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )
  }
  setValue(data: any) {
    
    for (let i = 0; i < data.length; i++) {
      if(!this.detailsControl.controls[i]) {
        this.detailsControl.push(this.createPartCForm())
      }
      this.detailsControl.at(i).patchValue({
        id: data[i].id,
        description: data[i].description
      })
      
      //   this.detailsControl.at(i).value

      // )
      this.detailsControl.at(i).disable()
    }
  }
  get detailsControl(): FormArray {
    return this.partBForm.get('details') as FormArray
  }
  createPartCForm(): FormGroup {

    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'description': new FormControl({ value: null, disabled: true }, [Validators.required, Validators.minLength(2)])
    });
  }

  downloadFile(i: number, topic: any) {
    
    this.service.downloadFile(
      this.detailsControl.at(i).value.id
    ).pipe(
      map(res => res),
      tap((res) => {
        if (res.size == 0) {
         
          this.service1.show("no File to Download","error", 3000)
        }
        else {
          this._FileSaverService.save(res, new Date().getTime() + '.pdf')
          this.service1.show("File Downloaded","success", 3000)

        }}
        ),
        catchError((_error)=> {
          this.service1.show("no File to Download","error", 3000)

          return of()
        })
    )
    .subscribe(res => {
      
    })
  }
}

