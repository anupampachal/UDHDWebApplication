import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { CreateUserComponent } from "./component/create-user/create-user.component";
import { EditUserComponent } from "./component/edit-user/edit-user.component";
import { ListUserComponent } from "./component/list-user/list-user.component";
import { ViewUserComponent } from "./component/view-user/view-user.component";
import { UserMgtComponent } from "./user-mgt.component";
import { EditOtherUserComponent } from "./component/edit-user/edit-other-user/edit-other-user.component";
import { EditSlpmuUserComponent } from "./component/edit-user/edit-slpmu-user/edit-slpmu-user.component";
import { EditUdhdUserComponent } from "./component/edit-user/edit-udhd-user/edit-udhd-user.component";
import { EditUlbUserComponent } from "./component/edit-user/edit-ulb-user/edit-ulb-user.component";
const usermgtRoutes: Routes = [
    {
        path: '',
        redirectTo: 'usermgt',
        pathMatch: 'full'
    }, {
        path: 'usermgt',
        component: UserMgtComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'User Management',
            authorities: [
                AuthorityConstants.ROLE_SLPMU_IT,
                AuthorityConstants.ROLE_UDHD_IT]
        },

        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'list'
            },
            {
                path: 'list',
                component: ListUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'List',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }

            }, {
                path: 'create/:param',
                component: CreateUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Create',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            }, {
                path: 'view/:id',
                component: ViewUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'View',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            }, {
                path: 'edit/OTHERS/:id',
                component: EditOtherUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Edit',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            },{
                path: 'edit/SLPMU/:id',
                component: EditSlpmuUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Edit',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            },{
                path: 'edit/ULB/:id',
                component: EditUlbUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Edit',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            },{
                path: 'edit/UDHD/:id',
                component: EditUdhdUserComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Edit',
                    authorities: [AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_IT]
                }
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(usermgtRoutes)],
    exports: [RouterModule]
})
export class UserMgtSettingsRouteModule { }
