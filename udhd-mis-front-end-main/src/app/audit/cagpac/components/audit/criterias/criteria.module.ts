import { NgModule } from "@angular/core";
import { MatDialogModule } from "@angular/material/dialog";
import {MatExpansionModule} from '@angular/material/expansion';
import { MatSnackBarModule } from "@angular/material/snack-bar";
@NgModule({
    declarations: [
       // ListAgirAuditCriteriaComponent,
        //ViewAgirAuditCriteriaComponent,
        //EditAgirAuditCriteriaComponent,
        //CreateAgirAuditCriteriaComponent,
    ],
    imports: [
        MatExpansionModule,
        MatSnackBarModule,
        MatDialogModule
    ],
    providers: [
       // AGIRAuditCriteriaService AGIRAuditCriteriaService
    ]
})
export class AGIRAuditCriteriaModule { }