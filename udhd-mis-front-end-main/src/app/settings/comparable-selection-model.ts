import { SelectionModel } from "@angular/cdk/collections";
import {ULBDTO} from "../shared/model/ulb.model";

export class ComparableSelectionModel extends SelectionModel<ULBDTO>{
    private compareWith: (o1: ULBDTO, o2: ULBDTO) => boolean;
  
    constructor(
      _multiple?: boolean, 
      initial?: ULBDTO[], 
      _emitChanges?: boolean, 
      compareWith?: (o1: ULBDTO, o2: ULBDTO) => boolean) {
      super(_multiple, initial, _emitChanges);
  
      //this.compareWith = compareWith ? compareWith : (o1, o2) => o1==o2;
      this.compareWith = compareWith ? compareWith : (o1, o2) =>{
        if(o1.id==o2.id && o1.name==o2.name && o1.type==o2.type && o1.aliasName==o2.aliasName && o1.code==o2.code && o1.active==o2.active && o1.district.id==o2.district.id)
          return true;
        else 
          return false;
      }
    }
  
    override isSelected(value: ULBDTO): boolean {
      return this.selected.some((x) => this.compareWith(value, x));
    }
  
    /**
     * We also need to override deselect since you may have objects that 
     * meet the comparison criteria but are not the same instance.
     */
    override deselect(...values: ULBDTO[]): void {
      // using bracket notation here to work around private methods
      this['_verifyValueAssignment'](values);
  
      values.forEach((value) => {
        // need to find the exact object in the selection set so it 
        // actually gets deleted
        const found = this.selected.find((x) => this.compareWith(value, x));
        if (found) {
          this['_unmarkSelected'](found);
        }
      });
  
      this['_emitChangeEvent']();
    }
  }