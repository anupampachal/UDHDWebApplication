import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateGuard } from 'src/app/auth/services/can-activate.service';
import { AuthorityConstants } from 'src/app/authority-constants';
import { PeriodicUploadsComponent } from './periodic-uploads.component';
import { CreateTrialBalanceComponent } from './trial-balance/create-trial-balance/create-trial-balance.component';
import { EditTrialBalanceComponent } from './trial-balance/edit-trial-balance/edit-trial-balance.component';
import { ListTrialBalanceComponent } from './trial-balance/list-trial-balance/list-trial-balance.component';
import { TrialBalanceComponent } from './trial-balance/trial-balance.component';
import { ViewTrialBalanceComponent } from './trial-balance/view-trial-balance/view-trial-balance.component';

const routes: Routes = [
    {
        path:'',
        pathMatch:'full',
        redirectTo:'head'
    },
    {
        path: 'head',
        component: PeriodicUploadsComponent,
        // pathMatch: 'full',
        data: {
          breadcrumb: 'Periodic Upload',
    },
        children: [
            {
                path:'',
                pathMatch:'full',
                redirectTo:'trial-balance'
            },
            {
                path: 'trial-balance',
                component: TrialBalanceComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Trialbalance',
                    authorities: [
                        AuthorityConstants.ROLE_FLIA,
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
                    ]
                },
                children: [
                    {
                        path: '',
                        redirectTo: 'list',
                        pathMatch: 'full'
                    },
                    {
                        path: 'list',
                        canActivate: [CanActivateGuard],
                        component: ListTrialBalanceComponent,
                        data: {
                            breadcrumb: 'List',
                            authorities: [
                                AuthorityConstants.ROLE_FLIA,
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
                                AuthorityConstants.ROLE_UDHD_IT
                            ]
                        }
                    }, {
                        path: 'create',
                        canActivate: [CanActivateGuard],
                        component: CreateTrialBalanceComponent,
                        data: {
                            breadcrumb: 'Create',
                            authorities: [
                                AuthorityConstants.ROLE_FLIA,
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
                                AuthorityConstants.ROLE_UDHD_IT
                            ]
                        }
                    }, {
                        path: 'view/:id',
                        canActivate: [CanActivateGuard],
                        component: ViewTrialBalanceComponent,
                        data: {
                            breadcrumb: 'View',
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
                            AuthorityConstants.ROLE_UDHD_IT
                            ]
                        }
                    }, {
                        path: 'edit/:id',
                        canActivate: [CanActivateGuard],
                        component: EditTrialBalanceComponent,
                        data: {
                            breadcrumb: 'Edit',
                            authorities: [
                                AuthorityConstants.ROLE_FLIA,
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
                                AuthorityConstants.ROLE_UDHD_IT
                            ]
                        }
                    }
                ]
            },

        ]
    },

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PeriodicUploadsRoutingModule { }
