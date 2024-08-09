import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuditComplianceDTO } from 'src/app/audit/cagpac/models/cag-compliances.model';
import { CAGAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-view-cag-audit-compliances',
  templateUrl: './view-cag-audit-compliances.component.html',
  styleUrls: ['./view-cag-audit-compliances.component.css']
})
export class ViewCagAuditCompliancesComponent implements OnInit {

  @Input() complianceId!: number;
  @Input() parent!: string;
  @Output() listComplianceEvent = new EventEmitter<string>();
  @Output() editComplianceEvent = new EventEmitter<number>();
  cagAuditCompliance$!: Observable<AuditComplianceDTO>;

  constructor(private cagAuditComplianceService: CAGAuditComplianceService,
   ) { }

  ngOnInit(): void {
    this.cagAuditCompliance$ = this.cagAuditComplianceService.findCAGAuditComplianceById(this.complianceId);
  }

  toList() {
    this.listComplianceEvent.emit(this.parent);
  }
  toEdit($event:number){
    this.editComplianceEvent.emit($event);
  }
}
