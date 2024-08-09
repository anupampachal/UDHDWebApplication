import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternalAuditCahrtComponent } from './internal-audit-cahrt.component';

describe('InternalAuditCahrtComponent', () => {
  let component: InternalAuditCahrtComponent;
  let fixture: ComponentFixture<InternalAuditCahrtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InternalAuditCahrtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalAuditCahrtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
