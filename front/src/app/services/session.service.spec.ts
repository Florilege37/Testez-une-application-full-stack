import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  const mockSessionInformation : SessionInformation = 
    {
      token: "token",
      type: "type",
      id: 17,
      username: "JulienDupre",
      firstName: "Julien",
      lastName: "Dupre",
      admin: true
    }
    
  

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in', () => {
    const loginSpy = jest.spyOn(service, 'logIn');

    service.logIn(mockSessionInformation);
    
    expect(loginSpy).toHaveBeenCalled();
    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toBe(mockSessionInformation);
  });

  it('should log out', () => {
    const logoutSpy = jest.spyOn(service, 'logOut');

    service.logOut();
    
    expect(logoutSpy).toHaveBeenCalled();
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBe(undefined);
  });

});
