import { environment } from "src/environments/environment";

export class SettingsServerRouteConstants {
  public static BASE_URL = environment.serverUrl;

  public static SETTINGS_BASE_URL = SettingsServerRouteConstants.BASE_URL + '/settings';

  /** user group urls */
  public static USER_GROUP_BASE_URL = SettingsServerRouteConstants.SETTINGS_BASE_URL + "/user-group";
  public static STNG_UG_FIND_USER_GROUP_BY_ID_URL = SettingsServerRouteConstants.USER_GROUP_BASE_URL;
  public static STNG_UG_GET_USER_GROUP_BY_PAGE_URL = SettingsServerRouteConstants.USER_GROUP_BASE_URL + "/post/info";
  public static STNG_UG_CREATE_UPDATE_USER_GROUP_URL = SettingsServerRouteConstants.USER_GROUP_BASE_URL + "/post/save";

  /**Geo Division urls */
  public static GEO_DIVISION_BASE_URL = SettingsServerRouteConstants.SETTINGS_BASE_URL + "/division";
  public static STNG_GEODIVISION_FIND_DIVISION_BY_ID_URL = SettingsServerRouteConstants.GEO_DIVISION_BASE_URL;
  public static STNG_GEODIVISION_GET_DIVISION_BY_PAGE_URL = SettingsServerRouteConstants.GEO_DIVISION_BASE_URL + "/info";
  public static STNG_GEODIVISION_CREATE_UPDATE_GEO_DIVISION_URL = SettingsServerRouteConstants.GEO_DIVISION_BASE_URL + "/save";

  /**Geo District urls */
  public static GEO_DISTRICT_BASE_URL = SettingsServerRouteConstants.SETTINGS_BASE_URL + "/district";
  public static STNG_GEODISTRICT_FIND_DISTRICT_BY_ID_URL = SettingsServerRouteConstants.GEO_DISTRICT_BASE_URL;
  public static STNG_GEODISTRICT_GET_DISTRICT_BY_PAGE_URL = SettingsServerRouteConstants.GEO_DISTRICT_BASE_URL + "/info";
  public static STNG_GEODISTRICT_CREATE_UPDATE_GEO_DISTRICT_URL = SettingsServerRouteConstants.GEO_DISTRICT_BASE_URL + "/save";
  public static STNG_GEODISTRICT_GET_DISTRICT_BY_PAGE_FOR_DIVISION = SettingsServerRouteConstants.GEO_DISTRICT_BASE_URL + "/info-div";

  /** ULB urls */
  public static ULB_BASE_URL = SettingsServerRouteConstants.SETTINGS_BASE_URL + "/ulb";
  public static STNG_ULB_FIND_ULB_BY_ID_URL = SettingsServerRouteConstants.ULB_BASE_URL;
  public static STNG_ULB_GET_ULB_BY_PAGE_URL = SettingsServerRouteConstants.ULB_BASE_URL + "/info";
  public static STNG_ULB_CREATE_UPDATE_ULB_URL = SettingsServerRouteConstants.ULB_BASE_URL + "/save";
  public static STNG_ULB_GET_ULB_BY_PAGE_FOR_DISTRICT = SettingsServerRouteConstants.ULB_BASE_URL + "/info-div";

  /**User Mgt */
  public static USER_MGT_BASE_URL = SettingsServerRouteConstants.SETTINGS_BASE_URL + "/user-mgt";
  public static USER_MGT_AUTHORITY_URL = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/auth"
  public static STNG_USER_MGT_BY_PAGE_URL = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/info";
  public static STNG_USER_MGT_CREATE_UPDATE_ULB_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/ulb";
  public static STNG_USER_MGT_CREATE_UPDATE_UDHD_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/udhd";
  public static STNG_USER_MGT_CREATE_UPDATE_SLPMU_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/slpmu";
  public static STNG_USER_MGT_CREATE_UPDATE_OTHER_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/others";
  public static STNG_USER_MGT_GET_ULB_LIST_FOR_OTHER_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/other";
  public static STNG_USER_MGT_GET_ULB_FOR_ULB_USER = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/ulb";
  public static STNG_USER_MGT_GET_ULB_LIST = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/ulb-list";
  public static STNG_USER_MGT_GET_GEO_DIVISION_LIST = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/division";
  public static STNG_USER_MGT_GET_GEO_DISTRICT_LIST_FOR_DIVISION = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/district";
  public static STNG_USER_MGT_GET_GEO_ULB_LIST_FOR_DISTRICT = SettingsServerRouteConstants.USER_MGT_BASE_URL + "/ulb-dis";

  /*My Profile Service URL*/
  public static STNG_MY_PROFILE_BASE_URL=SettingsServerRouteConstants.SETTINGS_BASE_URL+"/profile";
  public static STNG_MY_PROFILE_GET_PROFILE_LOGO=SettingsServerRouteConstants.STNG_MY_PROFILE_BASE_URL+"/logo";


}
