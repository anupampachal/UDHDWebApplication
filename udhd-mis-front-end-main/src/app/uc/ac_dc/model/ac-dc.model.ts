import { ULBDTO } from "src/app/shared/model/ulb.model";

export class ACDCULBBasedDTO {
    id?: number;
    treasuryCode!: string;
    treasuryName!: string;
    financialYearStart!: Date;
    financialYearEnd!: Date;
    plannedNonPlanned!: boolean;
    budgetCode!: string;
    status!: boolean;
    billNo!: number;
    billDate!: Date;
    sanctionOrderNo!: number;
    sanctionOrderDate!: Date;
    billTVNo!: number;
    drawn!: number;
    adjusted!: number;
    unadjusted!: number;
    ddoCode!: string;
    remarks?: string;
    ulb?: ULBDTO;
}