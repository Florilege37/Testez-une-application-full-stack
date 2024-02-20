import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from '../login/login.component';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let routerTest : Router;
  let authService: AuthService;
  let sessionService: SessionService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        RouterTestingModule.withRoutes([{ path: 'login', component: LoginComponent }]),
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    sessionService = TestBed.inject(SessionService);
    authService = TestBed.inject(AuthService);
    routerTest = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should indicate "An error occured"', () => {
    const message = 'An error occurred';
    jest.spyOn(authService, 'register').mockImplementation(() => throwError(message));

    component.submit();

    expect(component.onError).toBe(true);
  });

  it('should call authService.register and navigate to login on successful registration and route to /login', () => {
    const authService = TestBed.inject(AuthService);

    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);

    const authSpy = jest.spyOn(authService, 'register').mockImplementation(() => of(undefined));

    jest.spyOn(sessionService, 'logIn').mockImplementation(() => {});

    component.submit();
    
    expect(authSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['/login']);
  });

  it('should have valid form', async () => {
    expect(component).toBeTruthy();

    const nativeElement = fixture.nativeElement;

    const form = nativeElement.querySelector('.register-form');
    expect(form).toBeTruthy();

    const firstName = form.querySelector('input[formcontrolname=firstName]');
    const lastName = form.querySelector('input[formcontrolname=lastName]');
    const email = form.querySelector('input[formcontrolname=email]');
    const password = form.querySelector('input[formcontrolname=password]');

    expect(firstName).toBeTruthy();
    expect(lastName).toBeTruthy();
    expect(email).toBeTruthy();
    expect(password).toBeTruthy();
  });

});

