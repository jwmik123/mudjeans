import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { FileUploadService } from './file-upload.service';
import { skipWhile } from 'rxjs/operators';

const file = new File([], 'testing-file.xlsx', { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })

describe('FileUploadService', () => {
  let service: FileUploadService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FileUploadService]
    });
    service = TestBed.inject(FileUploadService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should return a observable', () => {
    const form = new FormData();
    form.append('file', file)
    service.uploadWithProgress(form).pipe(
      skipWhile(event => {
        if (event.type === HttpEventType.UploadProgress) return (Math.round(100 * event.loaded / event.total) === 0);
        return true;
      })
    ).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        expect(event.loaded).toBeGreaterThan(0);
      } else if (event instanceof HttpResponse) {
        expect(event.body.fileName).toBe('testing-file.xlsx');
        expect(event.body.fileType).toBe('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
      }
    })

  })
});
