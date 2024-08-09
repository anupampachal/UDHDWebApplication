import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatGridListModule } from "@angular/material/grid-list"
import { SharedModule } from "src/app/shared/shared.module";
import { UserMgtComponent } from "./user-mgt.component";
import { ListUserComponent } from './component/list-user/list-user.component';
import { ViewUserComponent } from './component/view-user/view-user.component';
import { CreateUserComponent } from './component/create-user/create-user.component';
import { EditUserComponent } from './component/edit-user/edit-user.component';
import { UserMgtSettingsRouteModule } from "./user-mgt-routing.module";
import { UserMgtService } from "./services/user-mgt.service";
import {MatMenuModule} from '@angular/material/menu';
import { CreateUdhdUserComponent } from './component/create-user/create-udhd-user/create-udhd-user.component';
import { CreateSlpmuUserComponent } from './component/create-user/create-slpmu-user/create-slpmu-user.component';
import { CreateUlbUserComponent } from './component/create-user/create-ulb-user/create-ulb-user.component';
import { CreateOtherUserComponent } from './component/create-user/create-other-user/create-other-user.component';
import { EditOtherUserComponent } from './component/edit-user/edit-other-user/edit-other-user.component';
import { EditUlbUserComponent } from './component/edit-user/edit-ulb-user/edit-ulb-user.component';
import { EditUdhdUserComponent } from './component/edit-user/edit-udhd-user/edit-udhd-user.component';
import { EditSlpmuUserComponent } from './component/edit-user/edit-slpmu-user/edit-slpmu-user.component';
import { CommonComponent } from './component/common/common.component';
import { SharedFormService } from "./services/shared-form.service";
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatListModule } from "@angular/material/list";
@NgModule({
    declarations: [
        UserMgtComponent,
        ListUserComponent,
        ViewUserComponent,
        CreateUserComponent,
        EditUserComponent,
        CreateUdhdUserComponent,
        CreateSlpmuUserComponent,
        CreateUlbUserComponent,
        CreateOtherUserComponent,
        EditOtherUserComponent,
        EditUlbUserComponent,
        EditUdhdUserComponent,
        EditSlpmuUserComponent,
        CommonComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatGridListModule,
        MatSelectModule,
        MatDialogModule,
        RouterModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSlideToggleModule,
        UserMgtSettingsRouteModule,
        MatMenuModule,
        MatTooltipModule,
        MatCheckboxModule,
        MatListModule
    ],
    providers: [
        UserMgtService,
        SharedFormService
    ]
})
export class UserMgtModule { }
