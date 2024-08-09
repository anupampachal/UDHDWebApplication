import { Component } from '@angular/core';

@Component({
  selector: 'app-uc',
  template: `
  
    <h4>Kindly note that a separate portal exists for maintaing UC. Click below link to move to the other app.</h4>
    <a target="_blank" href="https://nagarseva.bihar.gov.in/MIS/Login.aspx">Nagarseva portal</a>
  `,
  styleUrls:['./uc-app.component.css']
  
})
export class UCAppComponent {}