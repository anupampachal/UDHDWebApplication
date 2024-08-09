import { environment } from "src/environments/environment";

export class ReportsServerRouteConstants {
  public static BASE_URL = environment.serverUrl;

  public static REPORTS_BASE_URL = ReportsServerRouteConstants.BASE_URL + '/reports';

  /** agir urls */
  public static AGIR_BASE_URL = ReportsServerRouteConstants.REPORTS_BASE_URL + "/agir";
  public static STNG_FIND_AGIR_BY_ID_URL = ReportsServerRouteConstants.AGIR_BASE_URL;
  public static STNG_GET_AGIR_BY_PAGE_URL = ReportsServerRouteConstants.AGIR_BASE_URL + "/post/info";

  /** CAGPAC URLS */

  public static CAGPAC_BASE_URL = ReportsServerRouteConstants.REPORTS_BASE_URL + "/cagpac";
  public static STNG_FIND_CAGPAC_BY_ID_URL = ReportsServerRouteConstants.CAGPAC_BASE_URL;
  public static STNG_GET_CAGPAC_BY_PAGE_URL = ReportsServerRouteConstants.CAGPAC_BASE_URL + "/post/info";


  /** FINANCE AUDIT URLS */

 public static FINANCE_AUDIT_BASE_URL = ReportsServerRouteConstants.REPORTS_BASE_URL + "/finance-audit";
 public static STNG_FIND_FINANCE_AUDIT_BY_ID_URL = ReportsServerRouteConstants.FINANCE_AUDIT_BASE_URL;
 public static STNG_GET_FINANCE_AUDIT_BY_PAGE_URL = ReportsServerRouteConstants.FINANCE_AUDIT_BASE_URL + "/post/info";

  /** INTERNAL AUDIT URLS */
 public static INTERNAL_AUDIT_BASE_URL = ReportsServerRouteConstants.REPORTS_BASE_URL + "/internal-audit";
 public static STNG_FIND_INTERNAL_AUDIT_BY_ID_URL = ReportsServerRouteConstants.INTERNAL_AUDIT_BASE_URL;
 public static STNG_GET_INTERNAL_AUDIT_BY_PAGE_URL = ReportsServerRouteConstants.INTERNAL_AUDIT_BASE_URL + "/post/info";

 /** UC URLS */
 public static UC_BASE_URL = ReportsServerRouteConstants.REPORTS_BASE_URL + "/uc";
 public static STNG_FIND_UC_BY_ID_URL = ReportsServerRouteConstants.UC_BASE_URL;
 public static STNG_GET_UC_BY_PAGE_URL = ReportsServerRouteConstants.UC_BASE_URL + "/post/info";

}
