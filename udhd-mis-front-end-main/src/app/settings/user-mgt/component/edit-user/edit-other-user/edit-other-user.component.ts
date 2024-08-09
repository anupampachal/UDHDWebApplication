import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { UserInfoDTO } from 'src/app/settings/model/user-mgt.model';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { AuthEntity } from '../../../auth.model';
import { UserMgtService } from '../../../services/user-mgt.service';
import { OtherTypeUserInfoDTO } from 'src/app/settings/model/other-user-info.model';
import { MatTableDataSource } from '@angular/material/table';
import { ComparableSelectionModel } from 'src/app/settings/comparable-selection-model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { GeoDistrictDTO } from 'src/app/shared/model/geo-district.model';

@Component({
  selector: 'app-edit-other-user',
  templateUrl: './edit-other-user.component.html',
  styleUrls: ['./edit-other-user.component.css']
})
export class EditOtherUserComponent implements OnInit {
  selectedDistrict?:GeoDistrictDTO;  
  authList:AuthEntity[]=[];
  updateULBUserForm!: FormGroup;
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  divisions: any; 
  savedAuthority!:string; 
  user?: OtherTypeUserInfoDTO;
  districts: any;  
  ulbType = ["MUNICIPAL_CORPORATION","MUNICIPAL_COUNCIL","NAGAR_PANCHAYAT"];  
  ulbList: any;

  dataSource = new MatTableDataSource<ULBDTO>();
  selectedDataSource:any;
  selection = new ComparableSelectionModel(true, []);


  constructor(
    private userService:UserMgtService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router){
      this.updateULBUserForm = this.fb.group({
        id: new FormControl({ value: null, disabled: true },[Validators.required]),
        username: new FormControl(null, [Validators.required,  Validators.email ,Validators.minLength(6),Validators.maxLength(60)]),
        name: new FormControl(null, [ Validators.required, Validators.minLength(3),Validators.maxLength(100)]),
        authority: new FormControl(null, [Validators.required]),
        mobileNo: new FormControl(null, [Validators.required,Validators.minLength(10),Validators.maxLength(10)]),
        permanentAddress: new FormControl(null),
        enabled: new FormControl(true, [Validators.required]),
        mobileVerified: new FormControl(true, [Validators.required]),
        //emailVerified: new FormControl(true, [Validators.required]),
        accountNonExpired: new FormControl(true, [Validators.required]),
        accountNonLocked: new FormControl(true, [Validators.required]),
        credentialsNonExpired: new FormControl(true, [Validators.required]),
        //division:[null, Validators.required],    
        //district:[null, Validators.required],     
        //ulbType:[null, Validators.required],      
        ulbDetails:[null, Validators.required],
      });
    }
  
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.userService.findUserById(param['id'])),
        tap((dt) => this.setData(dt)),
        switchMap(()=> this.userService.getAuthorityNameByAuthorityType('OTHERS')),
        tap(res=>this.authList=res),
        switchMap(()=>this.userService.getDivisionList()),
        tap(res=>this.divisions=res),
        shareReplay()
      )
      .subscribe();

   // this.userService.getAuthorityNameByAuthorityType('ULB').subscribe(res=>this.authList=res);
    //this.userService.getDivisionList().subscribe(divisions => this.divisions = divisions)
  }


  compareWithFunc = (a: any, b: any) => a == b;


  setData(res:UserInfoDTO){
    this.user=res;
    this.savedAuthority=res.authority;
    this.updateULBUserForm.setValue({
      id: res.id ,
      username: res.username ,
      name: res.name ,
      authority: res.authority ,
      mobileNo: res.mobileNo ,
      permanentAddress : res.permanentAddress ,
      enabled: res.enabled ,
      mobileVerified: res.mobileVerified ,
     // emailVerified: res.enabled ,
     ulbDetails: res.ulbDetails,
      accountNonExpired: res.accountNonExpired ,
      accountNonLocked: res.accountNonLocked ,
      credentialsNonExpired: res.credentialsNonExpired ,
      
    });
  }
  
  

  setDistrict(event:any){
    this.selectedDistrict=event.value;
  }
  getDistricts(event:any) {
    this.userService.getDistrictListByDivisionId(event.value.id).subscribe(districts => this.districts = districts)
  }
  getUlbList(event:any) {
    let ulbType=event.value;
    //const ulbName = this.updateULBUserForm.value.ulbType
    if(!!this.selectedDistrict && !!this.selectedDistrict.id)
      this.userService.getULBListByDistrictId(this.selectedDistrict.id,ulbType).subscribe(ulbs => {
        //this.ulbList = ulbs
        this.dataSource = new MatTableDataSource(ulbs);
      })
  }
  onSubmit() {
    if(this.selection.selected.length!=0)
        this.updateULBUserForm.patchValue({
          ulbDetails:this.selection.selected
        })
    this.userService
      .createUpdateOtherUser(this.updateULBUserForm.getRawValue())
      .subscribe((res) =>
        this.router.navigate(['/settings/settings-home/user-mgt/usermgt/view/', res.id], {
          relativeTo: this.route,
        })
      );
    
  }

  matcher = new MyErrorStateMatcher();
  getErrorMessages(controls:string){
   
    let control= this.updateULBUserForm.controls[controls]
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
  
 
  
  removeFromSelection(item: ULBDTO){
    this.selection.deselect(item);
  }

  
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
