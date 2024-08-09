import { ULBDTO } from "src/app/shared/model/ulb.model";

export class AGIRAuditDTO {
    agirAuditId?: number;
    id?: number;
    title!: string;//min=10, max=255,
    startDate!: string;
    isAssigned!:boolean;
    description?:string;
    endDate!: string;
    ulb!: ULBDTO;
    ulbName!: string;
    ulbId!: number;
    auditStatus!: string;//valid inputs: DRAFT, IN_PROGRESS, REJECTED, PUBLISHED
}
