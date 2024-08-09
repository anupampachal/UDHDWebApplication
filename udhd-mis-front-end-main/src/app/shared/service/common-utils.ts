import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class CommonUtilsService {

    // add those methods here only whose multiple versions do not impact the code.

    enumSelector(definition:any) {
        return Object.keys(definition)
            .map(key => ({
                value: definition[key],
                title: key,
                formattedVal: key.replace(/_/g, ' ')
            }));
    }

}