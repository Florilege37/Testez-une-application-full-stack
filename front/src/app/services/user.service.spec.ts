import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  const mockUser : User  = {
    id: 1,
    email: "mail",
    lastName: "Thierry",
    firstName: "Blanc",
    admin: false,
    password: "pwdWow",
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,HttpClientTestingModule
      ],
      providers:[UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a User', () => {
    const observable = service.getById(mockUser.id.toString());

    let userSubscribe;

    observable.subscribe(user => {
      userSubscribe = user;
    });

    const req = httpMock.expectOne('api/user/'+mockUser.id.toString());
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
    expect(userSubscribe).toEqual(mockUser); 
  });

  it('should delete a User', () => {
    const observable = service.delete(mockUser.id.toString());

    let userSubscribe;

    observable.subscribe(user => {
      userSubscribe = user;
    });

    const req = httpMock.expectOne('api/user/'+mockUser.id.toString());
    expect(req.request.method).toBe('DELETE');
    req.flush(mockUser);
    expect(userSubscribe).toEqual(mockUser); 
  });
});
