export class AuditCommentDTO {
    comment!: string;//min=3,max=2000
    id?: number;
    auditCriteriaId!: number;
}