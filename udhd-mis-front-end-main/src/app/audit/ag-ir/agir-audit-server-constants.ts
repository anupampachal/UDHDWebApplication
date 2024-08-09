//import { environment } from "../../../../src/environments/environment";
import { environment } from "../../../environments/environment";
export class AGIRAuditServerConstant {

    public static BASE_URL = environment.serverUrl;
    public static AGIR_AUDIT_BASE_URL = AGIRAuditServerConstant.BASE_URL + '/audit/agir';

    /**AGIR Audit Server Mgt */
    public static AGIR_AUDIT_GET_AGIR_AUDIT_LIST_BY_PAGE = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/info-audit";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_BY_ID = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/get-audit";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_CRITERIA_BY_ID = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/get-criteria";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_COMPLIANCE_BY_ID = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/get-compliance";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_COMMENT_BY_ID = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/get-comment";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_ULB_LIST = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/get-ulb-list";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_CRITERIA_LIST_BY_PAGE = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/info-criteria";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_COMPLIANCE_LIST_BY_PAGE = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/info-compliance";
    public static AGIR_AUDIT_GET_AGIR_AUDIT_COMMMENTS_LIST_BY_PAGE = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/info-comments";
    public static AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/save";
    public static AGIR_AUDIT_DELETE_AGIR_AUDIT = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/delete";
    public static AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_CRITERIA = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/criteria";
    
    public static AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMPLIANCE = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/compliance";
    public static AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMMENT = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/comment";
    public static AGIR_AUDIT_GET_COMMENT_LIST = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/comment-list";
    public static AGIR_AUDIT_GET_COMPLIANCE_LIST = AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL + "/compliance-list";

    public static AGIR_AUDIT_REPORT_DOWNLOAD_PATH=AGIRAuditServerConstant.BASE_URL +"/audit/report/agir";
    public static AGIR_AUDIT_SEND_AGIR_FOR_REVIEW=AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL +"/send-agir-for-review-slpmu";
    public static AGIR_AUDIT_GET_AGIR_PENDING_FOR_REVIEW_UNASSIGNED=AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL +"/get-agir-audit-unassigned";
    public static AGIR_AUDIT_ASSIGN_TO_ME=AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL +"/assign-to-me";
    public static AGIR_AUDIT_ASSIGNED_TO_ME=AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL +"/get-agir-assigned-to-me";
    public static AGIR_AUDIT_APPROVE_REJECT=AGIRAuditServerConstant.AGIR_AUDIT_BASE_URL +"/approve-reject";

   }
