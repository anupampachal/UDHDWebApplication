import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserMgtService } from '../../../services/user-mgt.service';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AuthEntity } from '../../../auth.model';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { ComparableSelectionModel } from 'src/app/settings/comparable-selection-model';
@Component({
  selector: 'app-create-other-user',
  templateUrl: './create-other-user.component.html',
  styleUrls: ['./create-other-user.component.css']
})
export class CreateOtherUserComponent implements OnInit {
  @ViewChild('selectedTable') selectedTable!: MatTable<any>; 
  authList:AuthEntity[]=[];
  createOtherUserForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  divisions: any;  
  districts: any;  
  ulbType = ["MUNICIPAL_CORPORATION","MUNICIPAL_COUNCIL","NAGAR_PANCHAYAT"];  
  ulbList: ULBDTO[]=[];

  dataSource = new MatTableDataSource<ULBDTO>();
  selectedDataSource:any;
  selection = new ComparableSelectionModel(true, []);
  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.createOtherUserForm = this.fb.group({
        username: new FormControl(null, [Validators.required,  Validators.email ,Validators.minLength(6),Validators.maxLength(60)]),
        name: new FormControl(null, [ Validators.required, Validators.minLength(3),Validators.maxLength(100)]),
        password: new FormControl(null, [ Validators.required, Validators.minLength(8),Validators.maxLength(20)]),
        confirmPassword: new FormControl(null, [ Validators.required, Validators.minLength(8),Validators.maxLength(20)]),
        authority: new FormControl(null, [Validators.required]),
        mobileNo: new FormControl(null, [Validators.required,Validators.minLength(10),Validators.maxLength(10)]),
        permanentAddress: new FormControl(null),
        enabled: new FormControl(true, [Validators.required]),
        mobileVerified: new FormControl(true, [Validators.required]),
        emailVerified: new FormControl(true, [Validators.required]),
        accountNonExpired: new FormControl(true, [Validators.required]),
        accountNonLocked: new FormControl(true, [Validators.required]),
        credentialsNonExpired: new FormControl(true, [Validators.required]),
        division:[null, Validators.required],    
        district:[null, Validators.required],     
        ulbType:[null, Validators.required],      
        ulbDetails:[null],
      });

      this.createOtherUserForm.addValidators(
        this.matchValidator(this.createOtherUserForm.controls['password'], 
        this.createOtherUserForm.controls['confirmPassword'])
      );
    }
  
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.userService.getAuthorityNameByAuthorityType('OTHERS').subscribe(res=>this.authList=res);
    this.userService.getDivisionList().subscribe(divisions => this.divisions = divisions)
  }
  matchValidator(
    control: AbstractControl,
    controlTwo: AbstractControl
  ): ValidatorFn {
    return () => {
      if (control.value !== controlTwo.value)
        return { 'Password':'Password and confirm password should match' };
      return null;
    };
  }
  removeFromSelection(item: ULBDTO){
    this.selection.deselect(item);
  }


  getDistricts() {
    const divisionId = this.createOtherUserForm.value.division.id
    this.userService.getDistrictListByDivisionId(divisionId).subscribe(districts => this.districts = districts)
  }
  getUlbList() {
    const districtId = this.createOtherUserForm.value.district.id
    const ulbName = this.createOtherUserForm.value.ulbType
    this.userService.getULBListByDistrictId(districtId,ulbName).subscribe(ulbs => {
     // this.ulbList = ulbs;
      this.dataSource = new MatTableDataSource(ulbs);
    })
  }
  onSubmit() {
     if(this.selection.selected.length!=0){
      this.createOtherUserForm.patchValue({
        ulbDetails:this.selection.selected
      })
       this.userService
        .createUpdateOtherUser(this.createOtherUserForm.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
            relativeTo: this.route,
          })
        ); 
      }
  }

  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.createOtherUserForm.controls[controls]
    if (control.hasError('required'))
      return 'This field is required';
    else if(control.hasError('email')) 
      return 'Invalid email';
    else if (control.hasError('minlength'))  
      return 'Below minimal length, please increase some characters';
    else if (control.hasError('maxlength'))  
      return 'Above max length, please decrease some characters.'
    else
      return 'Error'  ;
  }


  displayedColumns: string[] = [
    "select",
    "id",
    "name",
    "code",
   
  ];

  displayedColumnsForSelectedItems: string[] = [
   
    "id",
    "name",
    "code",
   
  ];
  
 
  

  
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    // if there is a selection then clear that selection
    if (this.isSomeSelected()) {
      this.selection.clear();
    } else {
      this.isAllSelected()
        ? this.selection.clear()
        : this.dataSource.data.forEach(row => this.selection.select(row));
    }
  }

  isSomeSelected() {
    return this.selection.selected.length > 0;
  }

  getSelectedItems(){
    let selectionSet= new Set<ULBDTO>(this.selection.selected);
    return selectionSet;
  }
}


