import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'step6-audit-observation',
  templateUrl: './step6-audit-observation.component.html',
  styleUrls: ['./step6-audit-observation.component.css']
})
export class EditStep6AuditObservationComponent implements OnInit {
  criteriaForm!: FormGroup;
  leakageForm!: FormGroup;
  leakageList: any[] = ['i) Non-levy of Property Tax/Holding Tax', 'ii) Delay in deposit of Tax Collected', 'iii) Mobile Tower Tax', 'iv) Rent on Municipal Properties', 'v) Advertisement Tax'];
  // partBsubList: any[] = ['a)   Non-maintenance of books of accounts, subsidiary registers.', 'b)   Irregularity in procurement process.', 'c)   Non-compliance of directives by UD & HD, GoB.', 'd)   Non-compliance of Act & Rules. ', 'e)   Lack of Internal control measures. ', ]
  // partBijk: string[] = ['g)   Physical verification of Inventory/Stores.', 'h)   Advances, their adjustment and recovery. ', 'i)   Any other matter as may be prescribed in due course. ']

  partBsubList: any[] = ['a)   Non-maintenance of books of accounts, subsidiary registers.', 'b)   Irregularity in procurement process.', 'c)   Non-compliance of directives by UD & HD, GoB.', 'd)   Non-compliance of Act & Rules. ', 'e)   Lack of Internal control measures. ',
  //  'f) Deficiency in Pay-roll System.','g)   Physical verification of Inventory/Stores.', 'h)   Advances, their adjustment and recovery. ', 'i)   Any other matter as may be prescribed in due course. '
]

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
  selectedFile: any;
  fileName: any;
  isEdit = true;
  isImage = false;
  showFileError!: string;
  constructor(
    private fb: FormBuilder
  ) { }
  ngOnInit(): void {
    this.leakageForm = this.fb.group({
      lForm: this.fb.array([])
    })
    // this.getIADefaultAuditCriteriaList();
    this.criteriaForm = this.fb.group({
      criteriaLayer1FormArray: this.fb.array([])
    });
    this.leakageList.forEach(leak => this.lFormControl.push(this.createAdditonalLeakageForm()));
  }
  get lFormControl() {
    return this.leakageForm.get('lForm') as FormArray;
  }

  createCriteriaLayer1Form(index: number): FormGroup {
    if (index <= 2) {
      return this.fb.group({
        'id': [],
        'auditPara': [],
        'auditParaDescription': [],
        'auditFeedback': new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(150)]),
        'compliant': new FormControl(false, [Validators.required]),
        'criteriaLayer2FormArray': new FormArray([])
      });

    }
    return this.fb.group({
      'id': [],
      'auditPara': [],
      'auditParaDescription': [],
      'amount': new FormControl(null, [Validators.required]),
      'associatedRisk': new FormControl(null, [Validators.required]),
      'auditFeedback': new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(150)]),
      'compliant': new FormControl(false, [Validators.required]),
      'criteriaLayer2FormArray': new FormArray([])
    });

  }

  createCriteriaLayer2Form(data: any): FormGroup {
    return this.fb.group({
      'id': [data.id],
      'auditPara': [data.auditPara],
      'auditParaDescription': [data.auditParaDescription],
      'auditFeedback': new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(150)]),
      'compliant': new FormControl(false, [Validators.required]),
      'file': new FormControl(null),
      'criteriaLayer3FormArray': new FormArray([])
    });
  }

  createCriteriaLayer3Form(data: any): FormGroup {
    return this.fb.group({
      'id': [data.id],
      'auditPara': [data.auditPara],
      'auditParaDescription': [data.auditParaDescription],
      'compliant': new FormControl(false, [Validators.required]),
      'file': new FormControl(null),
      'auditFeedback': new FormControl(null, [Validators.required, Validators.minLength(10), Validators.maxLength(150)]),
      'criteriaLayer4FormArray': new FormArray([])
    });
  }

  createCriteriaLayer4Form(data: any): FormGroup {
    return this.fb.group({
      'id': [data.id],
      'auditPara': [data.auditPara],
      'auditParaDescription': [data.auditParaDescription]
    });
  }

  get CriteriaLayer1FormControl() {
    return this.criteriaForm.get('criteriaLayer1FormArray') as FormArray
  }
  createAdditonalLeakageForm() {
   return this.fb.group({
      'id': new FormControl(null),
      'objective': new FormControl(null),
      'criteria': new FormControl(null),
      'condition': new FormControl(false, [Validators.required]),
      'file': new FormControl(null),
      'consequences': new FormControl(null, [Validators.required]),
      'cause': new FormControl(null, [Validators.required]),
      'correctiveActionRecommendation': new FormControl(null, [Validators.required])
    });

  }
  get criteriaLayer2FormControl() {
    return this.CriteriaLayer1FormControl.get('criteriaLayer2FormArray') as FormArray
  }
  get criteriaLayer3FormControl() {
    return this.criteriaLayer2FormControl.get('criteriaLayer3FormArray') as FormArray
  }
  get criteriaLayer4FormControl() {
    return this.criteriaLayer3FormControl.get('criteriaLayer4FormArray') as FormArray
  }

  public onSelected(event: any) {
    this.isImage = false;
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 300 * 1024) {
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      } else {
        let file: File = this.selectedFile;
        if (file.type === 'image/jpeg' || file.type === 'image/png') {
          this.isImage = true;
        }
      }
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
}

