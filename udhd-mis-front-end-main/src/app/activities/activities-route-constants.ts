import { environment } from "src/environments/environment";

export class ActivitiesRouteConstants {
  public static BASE_URL = environment.serverUrl;

  public static ACTIVITIES_BASE_URL = ActivitiesRouteConstants.BASE_URL + '/activity/';

  public static MEETINGS_BASE_URL = ActivitiesRouteConstants.BASE_URL + '/activity/';
  public static SEND_EMAIL_NOTIFICATION_URL = ActivitiesRouteConstants.BASE_URL + '/activities/send-notification';


}
