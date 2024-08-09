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
import { CreateDivisionComponent } from "./create-division/create-division.component";
import { EditDivisionComponent } from "./edit-division/edit-division.component";
import { ListDivisionComponent } from "./list-division/list-division.component";
import { ViewDivisionComponent } from "./view-division/view-division.component";
import { GeoDivisionComponent } from "./geo-division.component";
import { SettingsGeoDivisionRoutingModule } from "./geo-division-routing.module";
import { GeoDivisionService } from "./services/geo-division.service";
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatTooltipModule } from "@angular/material/tooltip";
@NgModule({
    declarations: [
        CreateDivisionComponent,
        ListDivisionComponent,
        EditDivisionComponent,
        ViewDivisionComponent,
        GeoDivisionComponent
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
        MatSnackBarModule,
        MatTooltipModule
    ],
    exports: [
    ],
    providers: [
        GeoDivisionService
    ]
})
export class GeoDivisionModule {

}
