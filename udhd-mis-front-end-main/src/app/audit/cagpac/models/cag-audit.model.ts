import { ULBDTO } from "src/app/shared/model/ulb.model";

export class CAGAuditDTO {
    cagAuditId?: number;
    id?: number;
    isAssigned!:boolean;
    title!: string;//min=10, max=255,
    startDate!: string;
    description?: string;
    endDate!: string;
    ulb!: ULBDTO;
    ulbName!: string;
    ulbId!: number;
    auditStatus!: string;//valid inputs: DRAFT, IN_PROGRESS, REJECTED, PUBLISHED
}
