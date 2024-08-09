import { divisionDTO } from "./divisionDTO";

export class DistrictDTO {
  id?: number;
  code!: string;
  name!: string;
  active!: boolean;
  division!:divisionDTO;
}
