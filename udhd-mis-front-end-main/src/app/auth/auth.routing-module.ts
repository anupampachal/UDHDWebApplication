import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthComponent } from "./auth.component";
import { LoginComponent } from "./components/login/login.component";
const authRoutes: Routes = [
    {
        path:'',
        redirectTo:'auth',
        pathMatch:'full'
    },{
        path:'auth',
        component:AuthComponent,
        children:[
            {
                path:'',
                redirectTo:'login',
                pathMatch:'full'
            },
            {
                path:'login',
                component: LoginComponent
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(authRoutes)],
    exports:[RouterModule]
})
export class AuthRoutingModule { }