import { Component, OnInit } from '@angular/core';
import { TemplateRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { TrialBalanceService } from '../../services/trial-balance.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-trial-balance',
  templateUrl: './create-trial-balance.component.html',
  styleUrls: ['./create-trial-balance.component.css']
})
export class CreateTrialBalanceComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  createTrialBalanceUploadData!: FormGroup;
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  showFormError?: ValidationErrors;
  active = false
  progress = 0;
  message = '';
  errorMsg = '';
  ulbs: ULBDTO[] = []
  finYears!: any;
  quarters = ['Q1', 'Q2', 'Q3', 'Q4']
  constructor(
    private route: ActivatedRoute,
    private service: TrialBalanceService,
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;

  }
  public onSelected(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10000 * 1024 || this.selectedFile.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      }

      const reader = new FileReader();

      reader.onload = (event: any) => {
        this.fileName = event.target.result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  onSubmit() {
        this.service
          .uploadTrialBalanceData(this.selectedFile)
          .subscribe((res) => {
            if(res) {
              this._snackBar.open("Success", "File upload successful", {
                duration: 5000
              });
              this.router.navigate(['../list/'], {
                relativeTo: this.route,
            })
          }
          },
          (err)=>{
            console.log(err);
            this._snackBar.open(err.error.status, err.error.message, {
              duration: 5000
            });
            
         
      });
  }

}
