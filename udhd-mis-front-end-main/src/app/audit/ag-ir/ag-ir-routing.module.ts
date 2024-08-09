import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
//import { AuthorityConstants } from "src/app/authority-constants";
import { AuthorityConstants } from "../../authority-constants";
const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'ag-ir',
        pathMatch: 'full'
    }, {
        path: 'ag-ir',
        loadChildren: () => import('./components/audit/agir-audit.module').then((m) => m.AGIRAuditModuleInner),
        data: {
            breadcrumb: 'AG-IR Audit',
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
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGRouteModule { }