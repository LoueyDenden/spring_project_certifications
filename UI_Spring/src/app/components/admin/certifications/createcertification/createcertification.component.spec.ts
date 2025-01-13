import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatecertificationComponent } from './createcertification.component';

describe('CreatecertificationComponent', () => {
  let component: CreatecertificationComponent;
  let fixture: ComponentFixture<CreatecertificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatecertificationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatecertificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
