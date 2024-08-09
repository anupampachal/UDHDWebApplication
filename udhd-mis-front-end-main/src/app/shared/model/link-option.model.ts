
export class LinkOptionModel {
  authority: string[];
  routerLink: string;
  title: string;
  icon: string;
  child : LinkOptionModel[]
  constructor(authority: string[], routerLink: string, title: string, icon: string, child : LinkOptionModel[]) {
      this.authority = authority;
      this.routerLink = routerLink;
      this.title = title;
      this.icon = icon;
      this.child = child
  }
}
