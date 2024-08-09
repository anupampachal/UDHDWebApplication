import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinanceAuditCahrtComponent } from './finance-audit-cahrt.component';

describe('FinanceAuditCahrtComponent', () => {
  let component: FinanceAuditCahrtComponent;
  let fixture: ComponentFixture<FinanceAuditCahrtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FinanceAuditCahrtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FinanceAuditCahrtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
