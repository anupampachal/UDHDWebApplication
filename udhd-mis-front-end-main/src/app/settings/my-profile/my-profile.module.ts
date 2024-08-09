import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SharedModule } from "src/app/shared/shared.module";
import { ReactiveFormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from '@angular/material/dialog';
import { MyProfileMgtService } from "./services/my-profile-mgt.service";
import { MyProfileComponent } from "./my-profile.component";
import { MyProfileRoutingModule } from "./my-profile-routing.module";
import { ViewProfileComponent } from './view-profile/view-profile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
@NgModule({
  declarations: [
    MyProfileComponent,
    ViewProfileComponent,
    EditProfileComponent
  ],
  imports: [
    SharedModule,
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MyProfileRoutingModule
  ],
  exports: [
  ],
  providers: [
    MyProfileMgtService
  ]
})
export class MyProfileMgtModule {

}
