import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedFormService } from '../../services/shared-form.service';

@Component({
  selector: 'app-common',
  templateUrl: './common.component.html',
  styleUrls: ['./common.component.css']
})
export class CommonComponent implements OnInit {
  @Input() parentGroup!: FormGroup;
  @Input() commonForm!: any;
  @Input() userType!: string
  routeArray: ActivatedRoute[] = [];
  showFormError?: ValidationErrors;
  authorityNames :any;
  addOrUpdate = "Add"
  constructor(
    private sharedfs: SharedFormService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.sharedfs.getAuthorityNames(this.userType).subscribe(res=> this.authorityNames=res);
   // console.log('authority names=> ',this.authorityNames)
    // this.authorityNames = this.sharedfs.authorityNames
    if(this.router.url.match("edit")) this.addOrUpdate = "Update"
    /*setTimeout(() => {
    this.authorityNames = this.sharedfs.authorityNames
    }, 1000);*/
  }

}
