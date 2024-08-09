import { Component, OnInit } from '@angular/core';
import { AuthorityConstants } from 'src/app/authority-constants';
import { NavItem } from 'src/app/shared/model/nav-item.model';

@Component({
  selector: 'app-periodic-uploads',
  template: `
    <nav mat-tab-nav-bar ngClass="tabs">
      <a
        mat-tab-link
        *ngFor="let tabItem of tabs"
        [routerLink]="tabItem.route"
        routerLinkActive="active-link"
      >
        <mat-icon *ngIf="tabItem.iconType" class="mr-8">{{
          tabItem.iconName
        }}</mat-icon>
        <mat-icon
          fontSet="fontawesome"
          *ngIf="!tabItem.iconType"
          class="fa-lg"
          color="primary"
          fontIcon="{{ tabItem.iconName }}"
        ></mat-icon>
        {{ tabItem.displayName }}
      </a>
    </nav>

    <router-outlet></router-outlet>
  `,
  styles: [
    `
      .active-link {
        color: #313aa7;
      }
      a {
        font-size: large;
      }
    `,
  ],
})
export class PeriodicUploadsComponent {
  constructor() {}
  tabs: NavItem[] = [
    {
      displayName: 'Trial Balance',
      iconName: 'swipe_left_alt',
      iconType: true,
      route:
        '/deas/deas/financial-data-uploads/deascomp/periodic-uploads/head/trial-balance',
      authorities: [
        AuthorityConstants.ROLE_FLIA,
        AuthorityConstants.ROLE_SLPMU_IT,
        AuthorityConstants.ROLE_SLPMU_ACCOUNT,
        AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      ],
    },
   ]
    }

