import { StepperSelectionEvent } from '@angular/cdk/stepper';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute } from '@angular/router';
import { Step1ReportService } from '../services/step-1-report.service';

@Component({
  selector: 'app-edit-ia',
  templateUrl: './edit-ia.component.html',
  styleUrls: ['./edit-ia.component.css']
})
export class EditIaComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  currentStep = 'Report';
  @ViewChild('stepper') stepper!: MatStepper;

  constructor(  private route: ActivatedRoute, private service1: Step1ReportService) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
  }
  goToNextStep(event:any) {
    this.service1.step1Report = event
    this.stepper.next()
  }

  selectionChange(event: StepperSelectionEvent) {
    this.currentStep = event.selectedStep.label;
  }

}
