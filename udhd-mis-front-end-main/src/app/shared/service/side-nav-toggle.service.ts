import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable()
export class SideNavToggleService{
  private isMinifiedSubject = new BehaviorSubject<boolean>(false);

  public setMinifiedSubjectState(){
    this.isMinifiedSubject.next(!this.isMinifiedSubject.value);
  }

  public getMinifiedState(): Observable<boolean>{
    return this.isMinifiedSubject.asObservable();
  }
}
