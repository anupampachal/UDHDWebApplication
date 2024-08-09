import { InternalAuditCriteriaThirdLayerDTO } from './ia-criterias-third-layer';

export class InternalAuditCriteriaSecoundLayerDTO {
    public id: number;
    public auditParaDescription: string;
    public thirdLayers: InternalAuditCriteriaThirdLayerDTO[];
    public auditPara: string;
    public firstLayerCriteriaId: number;
}