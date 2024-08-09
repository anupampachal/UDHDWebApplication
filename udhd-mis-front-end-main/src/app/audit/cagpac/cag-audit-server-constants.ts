import { environment } from "../../../environments/environment";
export class CAGAuditServerConstant {

    public static BASE_URL = environment.serverUrl;
    public static CAG_AUDIT_BASE_URL = CAGAuditServerConstant.BASE_URL + '/audit/cag';
/**CAG Audit Server Mgt */
public static CAG_AUDIT_GET_CAG_AUDIT_LIST_BY_PAGE = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/info-audit";
public static CAG_AUDIT_GET_CAG_AUDIT_BY_ID = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/get-audit";
public static CAG_AUDIT_GET_CAG_AUDIT_CRITERIA_BY_ID = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/get-criteria";
public static CAG_AUDIT_GET_CAG_AUDIT_COMPLIANCE_BY_ID = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/get-compliance";
public static CAG_AUDIT_GET_CAG_AUDIT_COMMENT_BY_ID = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/get-comment";
public static CAG_AUDIT_GET_CAG_AUDIT_ULB_LIST = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/get-ulb-list";
public static CAG_AUDIT_GET_CAG_AUDIT_CRITERIA_LIST_BY_PAGE = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/info-criteria";
public static CAG_AUDIT_GET_CAG_AUDIT_COMPLIANCE_LIST_BY_PAGE = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/info-compliance";
public static CAG_AUDIT_GET_CAG_AUDIT_COMMMENTS_LIST_BY_PAGE = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/info-comments";
public static CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/save";
public static CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_CRITERIA = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/criteria";
public static CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMPLIANCE = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/compliance";
public static CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMMENT = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/comment";
public static CAG_AUDIT_GET_COMMENT_LIST = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/comment-list";
public static CAG_AUDIT_GET_COMPLIANCE_LIST = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/compliance-list";

public static AGIR_AUDIT_DELETE_CAG_AUDIT = CAGAuditServerConstant.CAG_AUDIT_BASE_URL + "/delete";

public static CAG_AUDIT_REPORT_DOWNLOAD_PATH=CAGAuditServerConstant.BASE_URL +"/audit/report/cag";
public static CAG_AUDIT_SEND_CAG_FOR_REVIEW=CAGAuditServerConstant.CAG_AUDIT_BASE_URL +"/send-cag-for-review-slpmu";
public static CAG_AUDIT_GET_CAG_PENDING_FOR_REVIEW_UNASSIGNED=CAGAuditServerConstant.CAG_AUDIT_BASE_URL +"/get-cag-audit-unassigned";
public static CAG_AUDIT_ASSIGN_TO_ME=CAGAuditServerConstant.CAG_AUDIT_BASE_URL +"/assign-to-me";
public static CAG_AUDIT_ASSIGNED_TO_ME=CAGAuditServerConstant.CAG_AUDIT_BASE_URL +"/get-cag-assigned-to-me";
public static CAG_AUDIT_APPROVE_REJECT=CAGAuditServerConstant.CAG_AUDIT_BASE_URL +"/approve-reject";

}
