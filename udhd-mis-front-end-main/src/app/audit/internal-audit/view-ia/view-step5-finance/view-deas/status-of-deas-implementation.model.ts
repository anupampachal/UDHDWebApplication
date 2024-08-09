import { IAFinanceStatementStatusDeasDTO } from "./finance-statement-status.model";
import { IAParticularsWithFinanceStatusDTO } from "./particulars-with-finance.model";
import { IATallyInfoDTO } from "./tally-info.model";

export interface IAStatusOfImplementationOfDeasDTO {
    iaId: number;
    statusId: number;
    tallyDetails: IATallyInfoDTO;
    description: string;
    financeStatementStatus: IAFinanceStatementStatusDeasDTO;
    particularsWithFinanceStatus: IAParticularsWithFinanceStatusDTO;
    statusOfMunicipalAccCommitteeFileId: string;
    statusOfMunicipalAccountsCommittee: string;
}