import { Component, AfterViewInit, Injector } from "@angular/core";
import { MatFormFieldControl, MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";

@Component({
    selector: '[matErrorMessages]',
    template: '{{ error }}'
})
export class MatErrorMessagesDirective implements AfterViewInit {
    error = '';
    inputRef!: MatFormFieldControl<MatInput>;

    constructor(private _inj: Injector) { }

    // Setup all initial tooling
    ngAfterViewInit() {
        // grab reference to MatFormField directive, where form control is accessible.
        let container = this._inj.get(MatFormField);
        this.inputRef = container._control;

        // sub to the control's status stream
        this.inputRef?.ngControl?.statusChanges?.subscribe(this.updateErrors);
    }

    // This grabs a single active error instead of multiple.
    private updateErrors = (state: 'VALID' | 'INVALID') => {
        if (state === 'INVALID') {
            let controlErrors = this.inputRef?.ngControl?.errors;
            if (!!controlErrors) {
                const firstError = Object.keys(controlErrors)[0];
                if (firstError === 'required')
                    this.error = 'This field is required.';

                if (firstError === 'minlength') {
                    this.error = 'This field should be ' + controlErrors.minlength.requiredLength + ' or more characters long';
                }
                if (firstError === 'maxlength')
                    this.error = 'This field should be ' + controlErrors.maxlength.requiredLength + ' or less characters long';

                if (firstError === 'min') {
                    this.error = 'This field should be larger than ' + controlErrors.min.min;
                }
                if (firstError === 'max') {
                    this.error = 'This field should be smaller than ' + controlErrors.max.max;
                }
                if (firstError === 'pattern')
                    this.error = 'Numbers only!';

                if (firstError === 'error from my own custom validator')
                    this.error = 'You get the point.';
                // ..... 
            }

        }
    }

}