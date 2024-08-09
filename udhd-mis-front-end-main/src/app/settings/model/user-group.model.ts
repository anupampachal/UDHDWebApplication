export class UserGroupDTO {

	name!: string;//min 3, max 200
	id?: string;
	description?: string;//min 3, max 2000
	status!: boolean;
}