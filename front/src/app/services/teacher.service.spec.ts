import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  const mockTeacher1 = {
    id: 1,
    lastName: "1",
    firstName: "1",
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  const mockTeacher2 : Teacher = {
    id: 2,
    lastName: "2",
    firstName: "2",
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  const mockTeacher3 = {
    id: 3,
    lastName: "3",
    firstName: "3",
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  let mockTeacherArray : Teacher[];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return all', () => {
    mockTeacherArray = [mockTeacher1, mockTeacher2, mockTeacher3];
    const observable = service.all();

    let teacherList;

    observable.subscribe(teachers => {
      teacherList = teachers;
    });

    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET'); 
    req.flush(mockTeacherArray);
    expect(teacherList).toEqual(mockTeacherArray);
  });

  it('should return detail', () => {
    const observable = service.detail(mockTeacher1.id.toString());
    let id;
    observable.subscribe(teacher => {
      id = teacher.id;
    });
    
    const req = httpMock.expectOne('api/teacher/'+mockTeacher1.id.toString());
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher1);
    expect(id).toStrictEqual(mockTeacher1.id); 
  });

});


