import { environment } from "../../../../src/environments/environment";
export class InternalAuditServerConstant {

    public static BASE_URL = environment.serverUrl;
    public static INTERNAL_AUDIT_BASE_URL = InternalAuditServerConstant.BASE_URL + '/audit/internal-audit';

    /**Internal Audit Server Mgt */
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_LIST_BY_PAGE = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/info-audit";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_BY_ID = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/get-audit";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_CRITERIA_BY_ID = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/get-criteria";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_COMPLIANCE_BY_ID = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/get-compliance";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_COMMENT_BY_ID = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/get-comment";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_ULB_LIST = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/get-ulb-list";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_CRITERIA_LIST_BY_PAGE = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/info-criteria";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_COMPLIANCE_LIST_BY_PAGE = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/info-compliance";
    public static INTERNAL_AUDIT_GET_INTERNAL_AUDIT_COMMMENTS_LIST_BY_PAGE = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/info-comments";
    public static INTERNAL_AUDIT_CREATE_UPDATE_INTERNAL_AUDIT = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/save";
    public static INTERNAL_AUDIT_CREATE_UPDATE_INTERNAL_AUDIT_CRITERIA = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/criteria";
    public static INTERNAL_AUDIT_CREATE_UPDATE_INTERNAL_AUDIT_COMPLIANCE = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/compliance";
    public static INTERNAL_AUDIT_CREATE_UPDATE_INTERNAL_AUDIT_COMMENT = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/comment";
    public static INTERNAL_AUDIT_GET_COMMENT_LIST = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/comment-list";
    public static INTERNAL_AUDIT_GET_COMPLIANCE_LIST = InternalAuditServerConstant.INTERNAL_AUDIT_BASE_URL + "/compliance-list";
}
