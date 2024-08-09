import { InternalAuditCriteriaFourthLayerDTO } from './ia-criterias-fourth-layer';

export class InternalAuditCriteriaThirdLayerDTO {
    public id!: number;
    public auditParaDescription!: string;
    public auditPara!: string;
    public secondLayerCriteriaId!: number;
    public fourthLayers!: InternalAuditCriteriaFourthLayerDTO[];
}
