import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { JeansService } from './jeans.service';

describe('JeansService', () => {
  let service: JeansService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JeansService]
    });
    service = TestBed.inject(JeansService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
