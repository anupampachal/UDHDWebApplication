import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateGuard } from 'src/app/auth/services/can-activate.service';
import { AuthorityConstants } from 'src/app/authority-constants';
import { AfsBaseComponent } from './afs-base.component';
import { AnnualFinStComponent } from './annualFinancialStatement/annual-fin-st.component';
import { CreateAnnualFinancialStatementComponent } from './annualFinancialStatement/create-annual-financial-statement/create-annual-financial-statement.component';
import { EditAnnualFinancialStatementComponent } from './annualFinancialStatement/edit-annual-financial-statement/edit-annual-financial-statement.component';
import { ListAnnualFinancialStatementComponent } from './annualFinancialStatement/list-annual-financial-statement/list-annual-financial-statement.component';
import { ViewAnnualFinancialStatementComponent } from './annualFinancialStatement/view-annual-financial-statement/view-annual-financial-statement.component';
import { BudgetUploadComponent } from './budget-upload/budget-upload.component';
import { CreateBudgetUploadComponent } from './budget-upload/create-budget-upload/create-budget-upload.component';
import { EditBudgetUploadComponent } from './budget-upload/edit-budget-upload/edit-budget-upload.component';
import { ListBudgetUploadComponent } from './budget-upload/list-budget-upload/list-budget-upload.component';
import { ViewBudgetUploadComponent } from './budget-upload/view-budget-upload/view-budget-upload.component';
import { CreateFixedAssetComponent } from './fixed-asset/create-fixed-asset/create-fixed-asset.component';
import { EditFixedAssetComponent } from './fixed-asset/edit-fixed-asset/edit-fixed-asset.component';
import { FixedAssetComponent } from './fixed-asset/fixed-asset.component';
import { ListFixedAssetComponent } from './fixed-asset/list-fixed-asset/list-fixed-asset.component';
import { ViewFixedAssetComponent } from './fixed-asset/view-fixed-asset/view-fixed-asset.component';
import { CreatePropertyTaxRegisterComponent } from './property-tax-register/create-property-tax-register/create-property-tax-register.component';
import { EditPropertyTaxRegisterComponent } from './property-tax-register/edit-property-tax-register/edit-property-tax-register.component';
import { ListPropertyTaxRegisterComponent } from './property-tax-register/list-property-tax-register/list-property-tax-register.component';
import { PropertyTaxRegisterComponent } from './property-tax-register/property-tax-register.component';
import { ViewPropertyTaxRegisterComponent } from './property-tax-register/view-property-tax-register/view-property-tax-register.component';

const routes: Routes = [
  {
      path:'',
      pathMatch:'full',
      redirectTo:'head'
  },
  {
      path: 'head',
      component: AfsBaseComponent,
      // pathMatch: 'full',
      data: {
        breadcrumb: 'Annual Financial Statement',
  },
      children: [
          {
              path:'',
              pathMatch:'full',
              redirectTo:'afs'
          },
          {
            path: 'afs',
            component: AnnualFinStComponent,
            canActivate: [CanActivateGuard],
            data: {
                breadcrumb: 'afs uplaoad',
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
                    component: ListAnnualFinancialStatementComponent,
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
                    component: CreateAnnualFinancialStatementComponent,
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
                    component: ViewAnnualFinancialStatementComponent,
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
                    component: EditAnnualFinancialStatementComponent,
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
          {
              path: 'budget-upload',
              component: BudgetUploadComponent,
              canActivate: [CanActivateGuard],
              data: {
                  breadcrumb: 'Budget-Upload',
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
                      component: ListBudgetUploadComponent,
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
                      component: CreateBudgetUploadComponent,
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
                      component: ViewBudgetUploadComponent,
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
                      component: EditBudgetUploadComponent,
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
          {
              path: 'property-tax-register',
              component: PropertyTaxRegisterComponent,
              canActivate: [CanActivateGuard],
              data: {
                  breadcrumb: 'Property Tax Register',
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
                      component: ListPropertyTaxRegisterComponent,
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
                      component: CreatePropertyTaxRegisterComponent,
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
                      component: ViewPropertyTaxRegisterComponent,
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
                      component: EditPropertyTaxRegisterComponent,
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
          {
              path: 'fixed-asset',
              component: FixedAssetComponent,
              canActivate: [CanActivateGuard],
              data: {
                  breadcrumb: 'Fixed Assets',
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
                      component: ListFixedAssetComponent,
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
                      component: CreateFixedAssetComponent,
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
                      component: ViewFixedAssetComponent,
                      data: {
                          breadcrumb: 'View ',
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
                      component: EditFixedAssetComponent,
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
export class AnnualFinancialStatementRoutingModule { }
