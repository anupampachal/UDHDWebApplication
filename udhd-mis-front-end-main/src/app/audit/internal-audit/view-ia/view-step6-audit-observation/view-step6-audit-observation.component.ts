import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { InternalAuditService } from '../../internal-audit-service.service';

@Component({
  selector: 'app-view-step6-audit-observation',
  templateUrl: './view-step6-audit-observation.component.html',
  styleUrls: ['./view-step6-audit-observation.component.css']
})
export class ViewStep6AuditObservationComponent implements OnInit {
  partCdescriptionList: string[] = [
    "a)   Auditor should report in a separate section for non-compliance of rules/directives of UD&HD,GoB; Auditor should see the compliance of Bihar Municipal Act and specifically Chapter IX to XV and related rules and regulations as well as relateddirectives by UD&HD. ",
    "b)   Auditor should Report in a separate chapter on implementation of SAS of Property Tax in the ULB; Internal Auditor should witness some assessment procedures to check any in-consistencies in assessment. At least 20 value properties in the city/town (irrespective of the fact that SAS is received or not) must besurveyed and checked in each quarter and reported variations, if any, in PTRs and Actuals as per InternalAudits. ",
    "c)   Auditor should report on compliance of Bihar Municipal Accounting Manual, Bihar Municipal Accounts Rules, 2014 and Bihar Municipal Budget Manual with special attention to following Rules of BMAR: RULE 22 : All moneys to be brought to account. RULE 27 : Collections to be deposited into Bank on the same day. RULE 69: Grant related Compliance RULE 120-121: Monthly Receipt & Payment Account and Trial Balance RULE 130 : Audit to be completed & reported within 6 month. ",
    "d)   Report on compliance of financial guidelines of schemes of MOHUA and UD & HD, GoB. ",
    "e)   Auditor should Report and quantify all major own revenue losses and opportunities lost or missed including in the area of Property Tax, Mobile Transmission Towers Tax, Rental of Municipal properties, Advertisement Taxes/Fees, Sairat, etc; ",
    "f)   Auditor should report on adequacy and appropriateness of the documentation, approvals, compliance of procedures etc. of all payments above Rs 10,000 and above. ",
    "g)   Auditor should report on procurement made including through E-Tendering and E-Auction indicating exceptions, if any and whether a register is kept for all Procurementswith value above Rs 15,000/- ",
    "h)   Auditor should report on presence or absence of a system of issuance of UC for the different schemes for any utilisation made during the reporting period; Where there is no system for issuance of U/Cs, the Internal Audit report shall prepare Utilisation Certificate for various schemes/grants as per the guidelines of such scheme available on the UD & HD website. If no system for UCs in the ULB Internal Auditor has to prepare UCs for the reporting period for whichaudit has been conducted. ",
    "i)   Auditor should report instances of losses, failures or inefficiencies and recommendations and/or measures which can be taken to avoid theirrecurrence in future. ",
    "j)   Internal Auditor will report on each payment that the payment terms & conditions of tenders and rate offers are according to procurement law andpolicies. ",
    // "k)   Internal Auditor will report on each payment that the payment terms & conditions of tenders and rate offers are according to procurement law andpolicies. ",
    "l)   Auditor will report on that the fixed deposit and other funds should be in nationalized banks/Approved financial institutions and should earn maximuminterest at their gestation period. ",
    "m)   Internal Auditor will identify major areas of ULBs own revenue loss and auditor will access the loss andPrepare a statement of loss. ",
    "n)   Auditor will report on that all kind of tax deductions i.e. Commercial Tax, Income Tax, Provident Fund, etc. should be deducted from the payments as applicable, deposited properly and also should be properly recorded in appropriate ledgers. ",
    "o)   Internal Auditor will ensure that all the C&AG audit & Internal audit paras has been compiled by the ULBs, if not complied the Internal Audit shall help the ULBs staffs to prepare the compliance report. ",

  ]
  partBsubList: any[] = ['a)   Non-maintenance of books of accounts, subsidiary registers.', 'b)   Irregularity in procurement process.', 'c)   Non-compliance of directives by UD & HD, GoB.', 'd)   Non-compliance of Act & Rules. ', 'e)   Lack of Internal control measures. ',
  // 'f) Deficiency in Pay-roll System.','g)   Physical verification of Inventory/Stores.', 'h)   Advances, their adjustment and recovery. ', 'i)   Any other matter as may be prescribed in due course. '
]
  deleteError = false;
  step1Report$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: InternalAuditService
  ) { }
  // confirmDelete() {
  //   const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
  //   dialogRef
  //     .afterClosed()
  //     .pipe(
  //       filter((res) => res === true && !!this.id),
  //       switchMap(() => this.service.deleteReportByID(this.id))
  //     )
  //     .subscribe(
  //       () => this.router.navigate(['../../list'], { relativeTo: this.route }),
  //       (error) => {
  //         this.deleteError = true;
  //         setTimeout(() => (this.deleteError = false), 5000);
  //       }
  //     );
  // }

  ngOnInit(): void {


  }

}
