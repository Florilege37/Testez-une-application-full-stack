import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Session } from '../interfaces/session.interface';
import { User } from '../../../interfaces/user.interface';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const mockUser : User  = {
    id: 10,
    email: "mail",
    lastName: "Thierry",
    firstName: "Blanc",
    admin: false,
    password: "pwdWow",
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  const mockSession1 : Session = {
    id: 1,
    name: "name1",
    description: "desc1",
    date: new Date(),
    teacher_id: 1,
    users: [1,2,3],
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  const mockSession2 : Session = {
    id: 2,
    name: "name2",
    description: "desc2",
    date: new Date(),
    teacher_id: 2,
    users: [10,20,30],
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  const mockSession3 : Session = {
    id: 3,
    name: "name3",
    description: "desc3",
    date: new Date(),
    teacher_id: 3,
    users: [100,200,300],
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  let mockSessionArray : Session[];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,HttpClientTestingModule
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return all Sessions', () => {
    mockSessionArray = [mockSession1, mockSession2, mockSession3]
    const observable = service.all();

    let sessionSubscribe;

    observable.subscribe(sessions => {
      sessionSubscribe = sessions;
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessionArray);
    expect(sessionSubscribe).toEqual(mockSessionArray); 
  });

  it('should return detail session', () => {
    const observable = service.detail("1");

    let sessionSubscribe;

    observable.subscribe(sessions => {
      sessionSubscribe = sessions;
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession1);
    expect(sessionSubscribe).toEqual(mockSession1); 
  });

  it('should return delete session', () => {
    const observable = service.delete("1");

    let sessionSubscribe;

    observable.subscribe(sessions => {
      sessionSubscribe = sessions;
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(mockSession1);
    expect(sessionSubscribe).toEqual(mockSession1); 
  });

  it('should return create session', () => {
    const observable = service.create(mockSession1);

    let sessionSubscribe;

    observable.subscribe(sessions => {
      sessionSubscribe = sessions;
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    req.flush(mockSession1);
    expect(sessionSubscribe).toEqual(mockSession1); 
  });

  it('should return update session', () => {
    const observable = service.update("1", mockSession1);

    let sessionSubscribe;

    observable.subscribe(sessions => {
      sessionSubscribe = sessions;
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockSession1);
    expect(sessionSubscribe).toEqual(mockSession1); 
  });

  it('should return participate to session', () => {

    service.participate('1', mockUser.id.toString()).subscribe((session) => {
      expect(session).toBeUndefined();
    });
    

    const req = httpMock.expectOne('api/session/1/participate/'+mockUser.id.toString());
    expect(req.request.method).toBe('POST');
  });

  it('should return unparticipate to session', () => {

    service.unParticipate('1', mockUser.id.toString()).subscribe((session) => {
      expect(session).toBeUndefined();
    });
    

    const req = httpMock.expectOne('api/session/1/participate/'+mockUser.id.toString());
    expect(req.request.method).toBe('DELETE');
  });
});
