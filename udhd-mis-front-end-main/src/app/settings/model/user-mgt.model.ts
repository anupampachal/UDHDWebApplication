import { ULBDTO } from "src/app/shared/model/ulb.model";

export class UserInfoDTO {
    id?: number;
    username!: string;
    name!: string;
    authority!: string;
    mobileNo!: string;
    permanentAddress?: string;
    enabled!: boolean;
    mobileVerified!: boolean;
    emailVerified!: boolean;
    accountNonExpired!: boolean;
    accountNonLocked!: boolean;
    credentialsNonExpired!: boolean;
    ulbDetails?: ULBDTO[];
    authorityType!:string;
}