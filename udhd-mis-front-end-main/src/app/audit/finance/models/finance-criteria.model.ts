export class AuditCriteriaDTO {
    id?: number;
    title!: string;//min-3,max=512
    description!: string;//min =3, max=2000
    auditPara!: string;//min=3, max=255
    status!: boolean;
    associatedRisk!: string;//min=3, max=255
    amount!: number;
    financeAuditId!:number;
}