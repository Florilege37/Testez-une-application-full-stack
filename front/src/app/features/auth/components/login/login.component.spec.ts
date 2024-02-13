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

class AuthServiceMock {
  public onError = false;

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return of({
      token: "token",
      type: "type",
      id: 123456,
      username: "username",
      firstName: "name",
      lastName: "lastname",
      admin: false,
    });
  }
  
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let service : AuthServiceMock;
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
    service = new AuthServiceMock();
  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('should not log in and catch error', () => {
    let loginRequest : LoginRequest = { email: "test@gmail.copmm",
    password: "superpwd",}
    const loginMock = jest.fn().mockReturnValue(throwError('Unauthorized'));

    service.login = loginMock;

    service.login(loginRequest).subscribe({
      next:(response: SessionInformation) =>{
        routerTest.navigate(['/sessions']);
      },
      error: error => service.onError = true,
    })
    expect(service.onError).toBe(true);
  });

  it('should should log and route to /sessions ', () => {
    const loginRequest : LoginRequest = { email: "test@gmail.copm m",
        password: "superpwd",};
    const sessionInformation: SessionInformation ={
          token: "token",
          type: "type",
          id: 123456,
          username: "username",
          firstName: "name",
          lastName: "lastname",
          admin: false,
    }
    jest.spyOn(service, 'login').mockReturnValue(of(sessionInformation));

    component.submit();
    
  });

});

