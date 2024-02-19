import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { Observable } from 'rxjs/internal/Observable';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let sessionService: SessionService;
  let routerTest : Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    routerTest = TestBed.inject(Router);
    fixture.detectChanges();
    sessionService = TestBed.inject(SessionService);
  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('should not log in and catch error', () => {
    const authService = TestBed.inject(AuthService);

    const loginSpy = jest.spyOn(authService, 'login').mockImplementation(() => throwError(() => new Error('err')));

    component.submit();
    
    expect(loginSpy).toHaveBeenCalled();
    expect(component.onError).toBe(true);
  });

  it('should should log and route to /sessions ', () => {
    const authService = TestBed.inject(AuthService);

    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);

    const authSpy = jest.spyOn(authService, 'login').mockImplementation(() => of({} as SessionInformation));

    jest.spyOn(sessionService, 'logIn').mockImplementation(() => {});

    component.submit();
    
    expect(authSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['/sessions']);
  });

});

