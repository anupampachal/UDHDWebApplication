import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';
import { Principal } from 'src/app/auth/services/principal.service';

/**
 * @whatItDoes Conditionally includes an HTML element if current user has any
 * of the authorities passed as the `expression`.
 *
 * @howToUse
 * ```
 *     <some-element *scsHasAnyAuthority='['ROLE_ADMIN', 'ROLE_USER']'>...</some-element>
 * ```
 */
@Directive({
    // tslint:disable-next-line:directive-selector
    selector: '[scsHasAnyAuthority]'
})
export class HasAnyAuthorityDirective {
    private authorities!: string[];
    // private sub: Subscription;
    constructor(private principal: Principal, private templateRef: TemplateRef<any>, private viewContainerRef: ViewContainerRef) { }

    @Input()
    set scsHasAnyAuthority(value: string[]) {
        
        this.authorities = <string[]>value;
        this.updateView();

        
        // Get notified each time authentication state changes.
        this.principal.isAuthenticated().subscribe(() => this.updateView());
    }

    private updateView(): void {
        
        this.principal.getAuthenticationInfo().subscribe(usr=>{
            this.viewContainerRef.clear();
            if(this.authorities.indexOf(usr.authority)>-1){
                this.viewContainerRef.createEmbeddedView(this.templateRef);
            }
           
        }) 
       
     
    }

}
