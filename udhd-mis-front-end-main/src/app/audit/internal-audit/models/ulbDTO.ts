import { DistrictDTO } from "./districtDTO";

export class ulbDTO {
  id!: number;
    code!: string;
    name!: string;
    aliasName!: string;
    type!: string;
    active!: boolean;
    district!: DistrictDTO;
}
