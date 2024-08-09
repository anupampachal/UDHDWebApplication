import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { budgetariesDTO } from '../models/budgetariesDTO';
import { votDTO } from '../models/votDTO';
import { IAStatusOfImplementationOfDeasDTO } from '../view-ia/view-step5-finance/view-deas/status-of-deas-implementation.model';

@Injectable()
export class Step5Service {
  public static BASE_URL = environment.serverUrl;
  public static BUDGETARY_EXPENDITURE_URL = Step5Service.BASE_URL + '/audit/ia/step5/budgetaryprov';
  public static VOLUME_OF_TRANSACTION_URL = Step5Service.BASE_URL + '/audit/ia/step5/volume-of-transaction';
  public static BANK_RECONCILIATION_URL = Step5Service.BASE_URL + '/audit/ia/step5/bank-reconciliation';
  public static REVENUE_CAPITAL_RECEIPT_URL = Step5Service.BASE_URL + '/audit/ia/step5/revenue-capital';
  public static REVENUE_CAPITAL_EXPENDITURE_URL = Step5Service.BASE_URL + '/audit/ia/step5/revenue-capital-ex';
  public static IMPLEMENTATION_OF_DEAS_URL = Step5Service.BASE_URL + '/audit/ia/step5/status-of-deas';


  constructor(private http: HttpClient) { }


  findBudgetById(id: number): Observable<any> {
    return this.http.get<any>(Step5Service.BUDGETARY_EXPENDITURE_URL + "/" + id);
  }


  createBudget(requestData: budgetariesDTO): Observable<any> {
    return this.http.put<any>(Step5Service.BUDGETARY_EXPENDITURE_URL, requestData);
  }


  findVOTById(id: number): Observable<any> {
    return this.http.get<any>(Step5Service.VOLUME_OF_TRANSACTION_URL + "/" + id);
  }


  createVOT(requestData: votDTO) {
    return this.http.put(Step5Service.VOLUME_OF_TRANSACTION_URL, requestData);
  }


  createUpdateBankReconciliation(requestData: any) {
    return this.http.put(Step5Service.BANK_RECONCILIATION_URL, requestData);
  }

  getAllBankReconStatement(id: number) {
    return this.http.get(Step5Service.BANK_RECONCILIATION_URL + '/' + id)
  }

  deleteBankReconById(brid: number, iaId: number) {
    return this.http.delete(Step5Service.BANK_RECONCILIATION_URL + `/${brid}/ia/${iaId}`)
  }

  getBankReconById(brid: number, id: number) {
    return this.http.get(Step5Service.BANK_RECONCILIATION_URL + `/${brid}/ia/${id}`)
  }


  createUpdateRevenueCapitalReceipt(requestData: any) {
    return this.http.put(Step5Service.REVENUE_CAPITAL_RECEIPT_URL, requestData);
  }
  getRevenueCapitalReceiptByIaId(id: number) {
    return this.http.get(Step5Service.REVENUE_CAPITAL_RECEIPT_URL + '/' + id)
  }
  createUpdateRevenueCapitalEx(requestData: any) {
    return this.http.put(Step5Service.REVENUE_CAPITAL_EXPENDITURE_URL, requestData);
  }
  getRevenueCapitalExByIaId(id: number) {
    return this.http.get(Step5Service.REVENUE_CAPITAL_EXPENDITURE_URL + '/' + id)
  }

  createImpOfDeas(id: number, reqBody: any) {
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/' + id, reqBody)
  }

  getStatusOfImpOfDeas(id: number): Observable<IAStatusOfImplementationOfDeasDTO> {
    return this.http.get<IAStatusOfImplementationOfDeasDTO>(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/' + id)
  }

  uploadStatusOfImpFile(file: File, id: number) {

    const formdata: FormData = new FormData()
    formdata.append('File', file);
    formdata.append('iaId', id.toString())
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/file',
      formdata
    )

  }

  getStImpFile(id: number) {
    return this.http.get(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/file/' + id, { responseType: 'blob' })
  }

  getFinStatementStatusDeas(id: number) {
    return this.http.get(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/fin-status/ + id')
  }

  createUpdateFinStatus(reqBody: any) {
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/finance', reqBody)
  }

  createUpdateMAC(id: number, reqBody: any) {
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/mac/' + id, reqBody)
  }

  createUpdateParticularsWithFinStatus(reqBody: any) {
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/particulars', reqBody)
  }

  createUpdateTallyStatus(reqBody: any) {
    return this.http.put(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/tally', reqBody)
  }
  getTallyInfoDTO(id: number) {
    return this.http.get(Step5Service.IMPLEMENTATION_OF_DEAS_URL + '/tallyInfo/' + id)
  }
}
