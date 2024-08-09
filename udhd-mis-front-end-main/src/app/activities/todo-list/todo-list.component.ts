import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {
  showFormError?: ValidationErrors;
  routeArray: ActivatedRoute[] = [];
  todoListForm!: FormGroup
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;

    this.todoListForm = this.fb.group({
      title: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      dueDate: new FormControl(null, [Validators.required,]),

    })
  }
  onSubmit() {

  }
}
