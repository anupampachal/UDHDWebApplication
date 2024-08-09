import { InternalAuditCriteriaThirdLayerDTO } from './ia-criterias-third-layer';

export class InternalAuditCriteriaFourthLayerDTO {
    public id!: number;
    public auditParaDescription!: string;
    public auditPara!: string;
    public thirdLayerCriteriaId!: number;
    public thirdLayer! : InternalAuditCriteriaThirdLayerDTO;
}
