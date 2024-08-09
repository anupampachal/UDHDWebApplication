import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { of } from 'rxjs';
import { filter, tap, shareReplay, switchMap, mergeMap, catchError, map } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-view-part-bf',
  templateUrl: './view-part-bf.component.html',
  styleUrls: ['./view-part-bf.component.css']
})
export class ViewPartBFComponent implements OnInit {
  tdsForm!: FormGroup;
  selectedFile: File[] | any[] = [];
  fileName: any;
  showFileError!: string | any;
  isImage = false;
  tdsFormDescriptions: any[] = ['i) TDS: ', ' ii) VAT/GST: ', ' iii) ROYALTY/SEIGNIORAGE FEE: ', ' iv) TDS ON GST: ', ' v) LABOUR CESS: ']
  tdsTypeList = ["TDS", "VAT_GST", "ROYALTY", "TGS_ON_GST", "LABOUR_CESS"]
  constructor(private fb: FormBuilder, private service: Step6Service, private service1: Step1ReportService,
    private route: ActivatedRoute,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.tdsForm = this.fb.group({
      'tds': new FormArray([])
    });

    this.tdsFormDescriptions.forEach(tdsfd => this.tdsControl.push(this.createTdsForm()))

    this.route.params.pipe(
      filter((params) => params && params['id']),
      // switchMap((param) =>
      //   this.service.getPartC(param['id'], this.currentType)),
      tap((param) => {
      for(let i=0;i< this.tdsTypeList.length; i++) {
        this.service.getPartBfMain(param['id'], this.tdsTypeList[i]).subscribe((res: any) => {
          this.tdsControl.at(i).patchValue({
            id: res.id,
            description: res.description,
            amountInvolved: res.amountInvolved,
            name: res.name
          })
        this.tdsControl.at(i).disable()

        },
        error => { })
      }
      }),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )

      this.route.params.pipe(
        filter((params) => params && params['id']),
        switchMap((param) =>
          this.service.getPartBFOtherTypeList(param['id'])),
        tap((data) => {
          this.setData(data)
        }),
        shareReplay(2)).subscribe(
          () => { },
          error => { }
        )
  }

  setData(data: any) {
    
    for(let i=0;i<data.length;i++) {
      this.tdsControl.push(this.createTdsForm())
      this.tdsControl.at(this.tdsControl.length-1).patchValue({ ...data[i] })
      this.tdsControl.at(this.tdsControl.length-1).disable()

    }
  }

  get tdsControl(): FormArray {
    return this.tdsForm.get('tds') as FormArray
  }
  createTdsForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl({ value: null, disabled: true }),
      'name': new FormControl({ value: null, disabled: true }, ),
      'description': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'amountInvolved': new FormControl({ value: null, disabled: true }, [Validators.required]),
      'fileId': new FormControl()
    });
  }
  addTDSForm() {
    this.tdsControl['controls'].push(this.createTdsForm());
  }
  pushNewForm() {
    this.tdsControl['controls'].push(this.createTdsForm())
  }

downloadFile(i: number) {
  const fileId = this.tdsControl.controls[i].value.fileId
  this.service.downloadPartBfFile(fileId).pipe(
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
  ).subscribe()
}

}
