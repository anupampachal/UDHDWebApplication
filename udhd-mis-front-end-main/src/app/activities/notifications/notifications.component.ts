import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ActivitiesService } from '../activities.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  sendEmailForm!: FormGroup;

  showFormError?: ValidationErrors;
  active = false;
  sendSuccess = false;
  routeArray: ActivatedRoute[] = [];
  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: 'auto',
    minHeight: '0',
    maxHeight: 'auto',
    width: 'auto',
    minWidth: '0',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    placeholder: 'Enter text here...',
    defaultParagraphSeparator: '',
    defaultFontName: '',
    defaultFontSize: '',
    fonts: [
      { class: 'arial', name: 'Arial' },
      { class: 'times-new-roman', name: 'Times New Roman' },
      { class: 'calibri', name: 'Calibri' },
      { class: 'comic-sans-ms', name: 'Comic Sans MS' }
    ],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ],
    sanitize: true,
    toolbarPosition: 'top',
    toolbarHiddenButtons: [
      ['insertImage', 'insertVideo']
    ]
  };

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: ActivitiesService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.sendEmailForm = this.fb.group({
      emailIdsCSV: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(100),
      ]),
      subject: new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]),
      body: new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(20000)]),
    });
  }

  onSubmit() {
   
    if (this.sendEmailForm.valid) {
      this.service
        .sendEmail(this.sendEmailForm.value)
        .subscribe(res => {
          if (res == true)
            this.sendSuccess = true
          else
            this.sendSuccess = false

          this.sendEmailForm.reset();
        });
    } else if (!!this.sendEmailForm.errors) {
      this.showFormError = this.sendEmailForm.errors;
    }
    else if (!!this.sendEmailForm.errors) {
      
      this.showFormError = this.sendEmailForm.errors;
    }

  }

}
