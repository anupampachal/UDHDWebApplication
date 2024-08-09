import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuditComplianceDTO } from 'src/app/audit/ag-ir/models/agircompliances.model';
import { AGIRAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-view-agir-audit-compliances',
  templateUrl: './view-agir-audit-compliances.component.html',
  styleUrls: ['./view-agir-audit-compliances.component.css']
})
export class ViewAgirAuditCompliancesComponent implements OnInit {

  @Input() complianceId!: number;
  @Input() parent!: string;
  @Output() listComplianceEvent = new EventEmitter<string>();
  @Output() editComplianceEvent = new EventEmitter<number>();
  agirAuditCompliance$!: Observable<AuditComplianceDTO>;

  constructor(private agirAuditComplianceService: AGIRAuditComplianceService,
   ) { }

  ngOnInit(): void {
    this.agirAuditCompliance$ = this.agirAuditComplianceService.findAGIRAuditComplianceById(this.complianceId);
  }

  toList() {
    this.listComplianceEvent.emit(this.parent);
  }
  toEdit($event:number){
    this.editComplianceEvent.emit($event);
  }
}
