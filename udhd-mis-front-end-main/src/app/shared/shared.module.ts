import { CommonModule, DatePipe } from "@angular/common";
import { NgModule } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbarModule } from "@angular/material/toolbar";
import { RouterModule } from "@angular/router";
import { SidenavComponent } from './layout/sidenav/sidenav.component';
import { HasAnyAuthorityDirective } from "./directives/has-any-authority.directive";
import { HeaderComponent } from "./layout/header/header.component";
import { NavService } from "./service/nav.service";
import { SideNavToggleService } from "./service/side-nav-toggle.service";
import { BreadcrumbComponent } from './layout/breadcrumb/breadcrumb.component';
import { AngularMaterialModule } from "./angular-material.module";
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from "@angular/material/sort";
import { DynamicMatTable } from "./components/dynamic-mat-table/dynamic-mat-table.component";
import { MatTableDataUpdateService } from "./service/mat-table-data-update-event.service";
import { ConfirmDialogBoxComponent } from "./components/confirm-dialog-box/confirm-dialog-box.component";
import { MatDialogModule } from "@angular/material/dialog";
import { SafePipe } from "./pipes/safe.pipe";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatSelectModule } from "@angular/material/select";
import { FilterComponent } from "./components/filter/filter.component";
import { MatInputModule } from "@angular/material/input";
import { MatErrorMessagesDirective } from "./directives/mat-error-msg.directive";
import { MatTableExporterModule } from "mat-table-exporter";
import { MatTooltipModule } from "@angular/material/tooltip";
//import { ComparableSelectionModel } from "./utils/comparable-selection-model";
@NgModule({
  declarations: [
    ConfirmDialogBoxComponent,
    HeaderComponent,
    SidenavComponent,
    HasAnyAuthorityDirective,
    BreadcrumbComponent,
    DynamicMatTable,
    SafePipe,
    FilterComponent,
    MatErrorMessagesDirective
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatSelectModule,
    MatSidenavModule,
    AngularMaterialModule,
    MatTableModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatTableExporterModule,
    MatTooltipModule
  ],
  providers: [NavService, SideNavToggleService, MatTableDataUpdateService, DatePipe],
  exports: [
    HeaderComponent,
    SidenavComponent,
    HasAnyAuthorityDirective,
    BreadcrumbComponent,
    AngularMaterialModule,
    DynamicMatTable,
    ConfirmDialogBoxComponent,
    SafePipe,
    FilterComponent,
    MatErrorMessagesDirective,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatSelectModule,
    MatSidenavModule,
    MatTableModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    //ComparableSelectionModel,
  ]
})
export class SharedModule { }
