export class NavItem {
  displayName!: string;
  disabled?: boolean;
  iconName!: string;
  iconType!:boolean;
  route?: string;
  children?: NavItem[];
  authorities!:string[]
}
