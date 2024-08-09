import { GeoDistrictDTO } from "./geo-district.model";

export class ULBDTO {
    id!: number;
    code!: string;
    name!: string;
    aliasName!: string;
    type!: string;
    active!: boolean;
    district!: GeoDistrictDTO;
}