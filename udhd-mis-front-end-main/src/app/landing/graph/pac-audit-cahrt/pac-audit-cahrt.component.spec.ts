import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacAuditCahrtComponent } from './pac-audit-cahrt.component';

describe('PacAuditCahrtComponent', () => {
  let component: PacAuditCahrtComponent;
  let fixture: ComponentFixture<PacAuditCahrtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PacAuditCahrtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PacAuditCahrtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
