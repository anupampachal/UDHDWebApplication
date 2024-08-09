import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorityConstants } from "src/app/authority-constants";
const cagPacRoutes: Routes = [
    {
        path: '',
        redirectTo: 'cagpac',
        pathMatch: 'full'
    }, {
        path: 'cagpac',
        loadChildren: () => import('./components/audit/cag-audit.module').then((m) => m.CAGAuditModuleInner),
        data: {
            breadcrumb: 'CAG/PAC Audit',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
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
    }
];
@NgModule({
    imports: [RouterModule.forChild(cagPacRoutes)],
    exports: [RouterModule]
})
export class CAGRouteModule { }
