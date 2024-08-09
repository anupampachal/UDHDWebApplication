export class AuditComplianceDTO {
    id?: number;
    comment!: string;//min=3,max=2000
    status!: Boolean;
    auditCriteriaId!: number;
}