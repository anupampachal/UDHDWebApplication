import { NgModule } from "@angular/core";
import { SettingsComponent } from "./settings.component";
import { SettingsRoutingModule } from "./settings.routing-module";

import { CommonModule } from "@angular/common";
@NgModule({
    declarations: [
        SettingsComponent
    ],
    imports: [
        SettingsRoutingModule,
        CommonModule
    ],
    providers: [],
})
export class SettingsModule { }
