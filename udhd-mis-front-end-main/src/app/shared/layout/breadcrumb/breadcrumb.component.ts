import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BreadcrumbModel } from '../../model/breadcrumb.model';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {
  // @Input() breadcrumbItems?:BreadcrumbModel[];

  @Input() routeArr!: ActivatedRoute[];
  breadcrumbItems: BreadcrumbModel[] = [];
  constructor() { }

  ngOnInit(): void {

    var pathStr = "";
    for (var i = 1; i < this.routeArr.length; i++) {
      if (this.routeArr[i].snapshot.url && this.routeArr[i].snapshot.url[0] && this.routeArr[i].snapshot.url[0].path !== '') {
        pathStr = pathStr + "/" + this.routeArr[i].snapshot.url[0];
        if (!!this.routeArr[i].snapshot.data && this.routeArr[i].snapshot.data.breadcrumb) {
          const breadcrumb = new BreadcrumbModel();
          breadcrumb.label = this.routeArr[i].snapshot.data.breadcrumb;
          breadcrumb.route = pathStr;
          this.breadcrumbItems.push(breadcrumb);
        }
      }
    }
  }


}
