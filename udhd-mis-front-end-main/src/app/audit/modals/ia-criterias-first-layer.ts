import { InternalAuditCriteriaSecoundLayerDTO } from './ia-criterias-second-layer';

export class InternalAuditCriteriaFirstLayerDTO {
	public id: number;
	public auditParaDescription: string;
	public secondLayers: InternalAuditCriteriaSecoundLayerDTO[];
	public auditPara: string;
	//public auditFeedback: string;
}