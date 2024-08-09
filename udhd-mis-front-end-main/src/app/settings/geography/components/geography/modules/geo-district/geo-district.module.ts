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
import { SettingsGeoDivisionRoutingModule } from "./geo-district-routing.module";
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { CreateDistrictComponent } from "./create-district/create-district.component";
import { EditDistrictComponent } from "./edit-district/edit-district.component";
import { ListDistrictComponent } from "./list-district/list-district.component";
import { ViewDistrictComponent } from "./view-district/view-district.component";
import { GeoDistrictService } from "./services/geo-district.service";
import { GeoDistrictComponent } from "./geo-district.component";
import { GeoDivisionModule } from "../geo-division/geo-division.module";
import { GeoDivisionService } from "../geo-division/services/geo-division.service";
import { MatTableExporterModule } from "mat-table-exporter";
import { MatTooltipModule } from "@angular/material/tooltip";
@NgModule({
    declarations: [
        CreateDistrictComponent,
        EditDistrictComponent,
        ListDistrictComponent,
        ViewDistrictComponent,
        GeoDistrictComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatDialogModule,
        RouterModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSlideToggleModule,
        SettingsGeoDivisionRoutingModule,
        GeoDivisionModule,
        MatTableExporterModule,
        MatTooltipModule
    ],
    exports: [
    ],
    providers: [
        GeoDistrictService,
        GeoDivisionService
    ]
})
export class GeoDistrictModule {

}
