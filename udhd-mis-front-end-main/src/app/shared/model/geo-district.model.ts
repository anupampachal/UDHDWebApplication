import { GeoDivisionDTO } from "./geo-division.model";

export class GeoDistrictDTO {
    id?: number;
    code!: string;
    name!: string;
    active!: boolean;
    division!:GeoDivisionDTO;
}