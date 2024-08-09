import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { SupportService } from '../../services/support.service';

@Component({
  selector: 'app-create-ticket',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.css']
})
export class CreateTicketComponent implements OnInit {

  createTicket!: FormGroup;
  showFormError?: ValidationErrors;
  allTeams = ["SLPMU", "UDHD", "ULB"]
  routeArray: ActivatedRoute[] = [];
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isFile = false;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: SupportService,
  ) { }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createTicket = this.fb.group({
      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
      assignToTeam: new FormControl(null, [
        Validators.required,
      ]),
      description: new FormControl(null, [Validators.required])
    });
  }

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.createTicket.valid) {
      this.service
        .createUpdateTicket(this.createTicket.getRawValue())
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createTicket.errors) {
      this.showFormError = this.createTicket.errors;
    }
  }

  public onSelected(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      // if (this.selectedFile.size > 1000 * 1024 || this.selectedFile.type != 'application/pdf') {
        if (this.selectedFile.size > 3000 * 1024) {
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;

      }

      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
}
