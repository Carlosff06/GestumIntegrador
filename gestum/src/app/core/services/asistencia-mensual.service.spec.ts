import { TestBed } from '@angular/core/testing';

import { AsistenciaMensualService } from './asistencia-mensual.service';

describe('AsistenciaMensualService', () => {
  let service: AsistenciaMensualService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AsistenciaMensualService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
