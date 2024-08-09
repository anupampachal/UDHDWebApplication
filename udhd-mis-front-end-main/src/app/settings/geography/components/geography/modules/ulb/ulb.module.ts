import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SharedModule } from "src/app/shared/shared.module";
import { ReactiveFormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from '@angular/material/dialog';
import { MatCardModule } from "@angular/material/card";
import { RouterModule } from "@angular/router";
import { MatSelectModule } from "@angular/material/select";
import { SettingsGeoDivisionRoutingModule } from "./ulb-routing.module";
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CreateUlbComponent } from "./create-ulb/create-ulb.component";
import { EditUlbComponent } from "./edit-ulb/edit-ulb.component";
import { ListUlbComponent } from "./list-ulb/list-ulb.component";
import { ViewUlbComponent } from "./view-ulb/view-ulb.component";
import { ULBService } from "./services/ulb.service";
import { ULBComponent } from "./ulb.component";
import { GeoDistrictModule } from "../geo-district/geo-district.module";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from "@angular/material/tooltip";
@NgModule({
    declarations: [
        ViewUlbComponent,
        CreateUlbComponent,
        ListUlbComponent,
        EditUlbComponent,
        ULBComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatDialogModule,
        MatDatepickerModule,
        RouterModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSlideToggleModule,
        SettingsGeoDivisionRoutingModule,
        GeoDistrictModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatTooltipModule,
    ],
    exports: [
        GeoDistrictModule
    ],
    providers: [
        ULBService,
        MatDatepickerModule,
        MatNativeDateModule
    ]
})
export class ULBModule {

}
