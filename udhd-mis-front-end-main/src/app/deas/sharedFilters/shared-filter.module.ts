import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatCardModule } from "@angular/material/card";
import { MatOptionModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatRadioModule } from "@angular/material/radio";
import { MatSelectModule } from "@angular/material/select";
import { RouterModule } from "@angular/router";
import { SettingsModule } from "src/app/settings/settings.module";
import { UserMgtService } from "src/app/settings/user-mgt/services/user-mgt.service";
import { DateFilterComponent } from "./date-filter/date-filter.component";
import { UlbFilterComponent } from "./ulb-filter/ulb-filter.component";
import { HierarchyDataComponent } from './hierarchy-data/hierarchy-data.component';

@NgModule({

    declarations:[
        UlbFilterComponent,
        DateFilterComponent,
        HierarchyDataComponent
    ],
    imports:[
        MatIconModule,
        MatOptionModule,
        MatSelectModule,
        ReactiveFormsModule,
        FormsModule,
        MatRadioModule,
        CommonModule,
        MatDatepickerModule,
        SettingsModule,
        MatInputModule,
        MatFormFieldModule,
        MatCardModule,
        RouterModule
    ],
    providers:[
        UserMgtService
    ],

    exports:[
        UlbFilterComponent,
        DateFilterComponent,
        HierarchyDataComponent
    ]
})
export class SharedFiltersModule{
}
