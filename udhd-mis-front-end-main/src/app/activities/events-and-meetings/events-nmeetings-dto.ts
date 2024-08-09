import { Time } from "@angular/common";

export class EventsNMeetingsDTO {
  id?: number;
  meetingHeader!: string;//min 3 max100
  meetingLocation!: string; //min 6 max=50
  description!: string;//min 2max 50
  startDate!: string;
  endDate!: string;
  startTime!: Time;
  endTime!: Time;
  requiredAttendies!: string[];
  optionalAttendies!: string[];

}
