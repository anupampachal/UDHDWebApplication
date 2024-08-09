import { NgModule } from "@angular/core";
import { MatTabsModule } from '@angular/material/tabs';
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { SharedModule } from "src/app/shared/shared.module";

import { RouterModule } from "@angular/router";
import { SettingsGeographyRoutingModule } from "./geography-routing.module";
import { SettingsGeographyComponent } from "./geography/geography.component";

@NgModule({
  declarations: [
    SettingsGeographyComponent,
  ],
  imports: [

    SharedModule,
    CommonModule,
    MatButtonModule,
    MatTabsModule,
    RouterModule,
    SettingsGeographyRoutingModule
  ],
  providers: []
})
export class SettingsGeographyModule { }
