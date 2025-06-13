import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Vacaciones } from './vacaciones';

describe('Vacaciones', () => {
  let component: Vacaciones;
  let fixture: ComponentFixture<Vacaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Vacaciones]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Vacaciones);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
