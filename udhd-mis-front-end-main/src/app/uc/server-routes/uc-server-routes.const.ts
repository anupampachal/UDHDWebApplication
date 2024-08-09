import { environment } from "src/environments/environment";

export class UCServerRouteConstants {
    public static BASE_URL = environment.serverUrl;
    public static UC_BASE_URL = UCServerRouteConstants.BASE_URL + '/audit';

    public static ACDC_BASE_URL = UCServerRouteConstants.UC_BASE_URL + "/ac-dc";
    public static AC_DC_FIND_BY_ID_URL = UCServerRouteConstants.ACDC_BASE_URL;
    public static AC_DC_FIND_BY_PAGE_URL = UCServerRouteConstants.ACDC_BASE_URL + "/post/info";
    public static AC_DC_CREATE_UPDATE_URL = UCServerRouteConstants.ACDC_BASE_URL + "/post/save";
    public static AC_DC_ULB_LIST_URL = UCServerRouteConstants.ACDC_BASE_URL + "/ulb";
    public static AC_DC_GET_GEOGRAPHY_DIVISION=UCServerRouteConstants.BASE_URL +'/settings/user-mgt/division';
    public static AC_DC_GET_GEOGRAPHY_DISTRICT=UCServerRouteConstants.BASE_URL +'/settings/user-mgt/district';
    public static AC_DC_GET_GEOGRAPHY_ULB=UCServerRouteConstants.BASE_URL +"/settings/user-mgt/ulb-dis";


}