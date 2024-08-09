import { NgModule } from "@angular/core";
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatMenuModule } from "@angular/material/menu";
import { MatCardModule } from "@angular/material/card";
@NgModule({
  declarations: [
  ],
  imports: [
    MatIconModule,
    MatButtonModule,
    MatListModule,
    MatFormFieldModule,
    MatMenuModule,
    MatCardModule
  ],
  providers: [],
  exports:[
    MatIconModule,
    MatButtonModule,
    MatListModule,
    MatFormFieldModule,
    MatMenuModule,
    MatCardModule
  ]
})
export class AngularMaterialModule { }
