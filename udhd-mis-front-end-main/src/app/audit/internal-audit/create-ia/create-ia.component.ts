import { StepperSelectionEvent } from '@angular/cdk/stepper';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute } from '@angular/router';
import { ulbDTO } from '../models/ulbDTO';
import { Step1ReportService } from '../services/step-1-report.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-ia',
  templateUrl: './create-ia.component.html',
  styleUrls: ['./create-ia.component.css']
})
export class CreateIaComponent implements OnInit {
  @ViewChild('stepper') stepper!: MatStepper;
  routeArray: ActivatedRoute[] = []
  constructor(  private route: ActivatedRoute,
    private service: Step1ReportService
    ) { }
  step1Rep: any;
  step1completed=false;
  step2completed=false;
  step3completed=false;
  step4completed=false;
  step5completed=false;

  indexes:number[] = [];
  form1!:FormGroup;
  form2!:FormGroup;
  form3!:FormGroup;
  form4!:FormGroup;
  form5!:FormGroup;


  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
   // console.log('goToStepCalled',this.step1completed);


  }
  goToStep(stepIndex:number, event:any) {
    if(stepIndex){
      if(stepIndex==1 ){
        this.step1completed=true;
      }else if(stepIndex==2 ){
        this.step2completed=true;
      }else if(stepIndex==3 ){
        this.step3completed=true;
      }else if(stepIndex==4 ){
        this.step4completed=true;
      }else if(stepIndex==5 ){
        this.step5completed=true;
      }

      let currentStep=this.stepper.steps.get(stepIndex-1);
      if(currentStep){
        currentStep.completed=true;
      }
    }
    

    
    this.stepper.next();
    
  }

  goToNextStepFromStep3(event: any) {
    this.stepper.next()
    
  }

  get form1FG(){
    return this.form1;
  }
  
  get form2FG(){
    return this.form2;
  }
}
