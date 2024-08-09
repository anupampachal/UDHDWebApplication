import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { CanLoadGuard } from './auth/services/can-load.service';
import { AuthorityConstants } from './authority-constants';
import { UnauthorisedComponent } from './unauthorised/unauthorised.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'landing',
    pathMatch: 'full',
  },
  {
    path: 'landing',
    loadChildren: () => import('./landing/landing.module').then((m) => m.LandingModule),

  },
  {
    path: 'home',
    loadChildren: () =>
      import('./dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  }, {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  }, {
    path: 'audit',
    loadChildren: () =>
      import('./audit/audit.module').then(
        (m) => m.AuditModule
      ),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      ]
    },
  }, {
    path: 'uc',
    loadChildren: () =>
      import('./uc/uc.module').then(
        (m) => m.UCModule
      ),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  }, {
    path: 'settings',
    loadChildren: () =>
      import('./settings/settings.module').then(
        (m) => m.SettingsModule
      ),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  },
  {
    path: 'unauthorized',
    component: UnauthorisedComponent,
    children:[
      {
        path:'login',
        component:LoginComponent
      }
    ]
    //loadChildren: () => import('./unauthorised/unauthorised.module').then((m) => m.UnauthorisedModule)
  }, {
    path: 'deas',
    loadChildren: () =>
      import('./deas/deas.module').then((m) => m.DeasModule),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  }, {
    path: 'activities',
    loadChildren: () => import('./activities/activities.module').then((m) => m.ActivitiesModule),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  }, {
    path: 'reports',
    loadChildren: () => import('./reports/reports.module').then((m) => m.ReportsModule),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  },
  {
    path: 'deas',
    loadChildren: () => import('./deas/deas.module').then((m) => m.DeasModule),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_ACCOUNTANT,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  },

  {
    path: 'support',
    loadChildren: () => import('./support/support.module').then((m) => m.SupportModule),
    canLoad: [CanLoadGuard],
    data: {
      authorities: [AuthorityConstants.ROLE_FLIA,
      AuthorityConstants.ROLE_INTERNAL_AUDITOR,
      AuthorityConstants.ROLE_ULB_CMO,
      AuthorityConstants.ROLE_SLPMU_ADMIN,
      AuthorityConstants.ROLE_SLPMU_ACCOUNT,
      AuthorityConstants.ROLE_SLPMU_AUDIT,
      AuthorityConstants.ROLE_SLPMU_UC,
      AuthorityConstants.ROLE_SLPMU_IT,
      AuthorityConstants.ROLE_UDHD_PSEC,
      AuthorityConstants.ROLE_UDHD_SEC,
      AuthorityConstants.ROLE_UDHD_SO,
      AuthorityConstants.ROLE_UDHD_IT]
    },
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
