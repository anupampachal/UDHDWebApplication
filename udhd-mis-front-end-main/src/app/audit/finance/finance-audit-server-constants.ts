import { environment } from "../../../../src/environments/environment";
export class FinanceAuditServerConstant {

    public static BASE_URL = environment.serverUrl;
    public static Finance_AUDIT_BASE_URL = FinanceAuditServerConstant.BASE_URL + '/audit/finance';

    /**Finance Audit Server Mgt */
    public static Finance_AUDIT_GET_Finance_AUDIT_LIST_BY_PAGE = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/info-audit";
    public static Finance_AUDIT_GET_Finance_AUDIT_BY_ID = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/get-audit";
    public static Finance_AUDIT_GET_Finance_AUDIT_CRITERIA_BY_ID = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/get-criteria";
    public static Finance_AUDIT_GET_Finance_AUDIT_COMPLIANCE_BY_ID = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/get-compliance";
    public static Finance_AUDIT_GET_Finance_AUDIT_COMMENT_BY_ID = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/get-comment";
    public static Finance_AUDIT_GET_Finance_AUDIT_ULB_LIST = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/get-ulb-list";
    public static Finance_AUDIT_GET_Finance_AUDIT_CRITERIA_LIST_BY_PAGE = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/info-criteria";
    public static Finance_AUDIT_GET_Finance_AUDIT_COMPLIANCE_LIST_BY_PAGE = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/info-compliance";
    public static Finance_AUDIT_GET_Finance_AUDIT_COMMMENTS_LIST_BY_PAGE = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/info-comments";
    public static Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/save";
    public static Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_CRITERIA = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/criteria";
    public static Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMPLIANCE = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/compliance";
    public static Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMMENT = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/comment";
    public static Finance_AUDIT_GET_COMMENT_LIST = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/comment-list";
    public static Finance_AUDIT_GET_COMPLIANCE_LIST = FinanceAuditServerConstant.Finance_AUDIT_BASE_URL + "/compliance-list";
}
