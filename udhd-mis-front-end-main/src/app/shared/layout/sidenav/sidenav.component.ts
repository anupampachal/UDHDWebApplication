import { Component, HostBinding, Input, OnInit } from '@angular/core';
import {  Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { NavItem } from '../../model/nav-item.model';
import { NavService } from '../../service/nav.service';
import { SideNavToggleService } from '../../service/side-nav-toggle.service';
import { Observable } from 'rxjs';
import { Principal } from 'src/app/auth/services/principal.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
  animations: [
    trigger('indicatorRotate', [
      state('collapsed', style({ transform: 'rotate(0deg)' })),
      state('expanded', style({ transform: 'rotate(180deg)' })),
      transition('expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
      ),
    ])
  ]

})
export class SidenavComponent implements OnInit {
  expanded: boolean = false;
  @HostBinding('attr.aria-expanded') ariaExpanded = this.expanded;
  @Input() item!: NavItem;
  @Input() authority!: string[];
  @Input() depth: number = 0;
  isToggled$!: Observable<boolean>;
  currentRole!: string;

  constructor(private navService: NavService,
    private principal: Principal,
    private sideNavToggleService: SideNavToggleService,
    public router: Router) {
    if (this.depth === undefined) {
      this.depth = 0;
    }
  }

  ngOnInit() {
    this.principal.getAuthenticationInfo()
      .pipe(filter(userInfo => !!userInfo.authority && userInfo.authority != ''))
      .subscribe(userInfo => this.currentRole = userInfo.authority ? userInfo.authority : '');
    this.isToggled$ = this.sideNavToggleService.getMinifiedState();

    this.navService.currentUrl.subscribe((url: string) => {
      if (this.item.route && url) {
        
        this.expanded = url.indexOf(`/${this.item.route}`) === 0;
        this.ariaExpanded = this.expanded;
        
      }
    });
  }

  onItemSelected(item: NavItem) {
    if (!item.children || !item.children.length) {
      this.router.navigate([item.route]);
      this.navService.closeNav();
    }
    if (item.children && item.children.length) {
      this.expanded = !this.expanded;
    }
  }
}
