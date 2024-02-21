import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { MeComponent } from './me.component';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { of } from 'rxjs';
import { SessionService } from '../../services/session.service';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let routerTest : Router;
  let sessionService : SessionService;
  let userService : UserService;
  let bar : MatSnackBar;

  const date = new Date();

  const userTest = {
    id: 1,
    email: 'test@gmail.com',
    lastName: 'Tremelo',
    firstName: 'Florian',
    admin: false,
    password: 'pwdTest',
    createdAt: date,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [{
        provide: SessionService, 
        useValue: {
          sessionInformation: {
            id: 1,
          },
          logOut: () => {
          },
        },
      },
        {
          provide: UserService,
          useValue: {
            getById: () => of(userTest),
            delete: (id : string) => of({}),
          },
      }],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    routerTest = TestBed.inject(Router);
    sessionService  = TestBed.inject(SessionService);
    userService = TestBed.inject(UserService);
    bar = TestBed.inject(MatSnackBar);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should delete ', () => {
    
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);
    const sessionSpy = jest.spyOn(sessionService, 'logOut');
    const barSpy  = jest.spyOn(bar, 'open').mockImplementation();;
    

    component.delete();

    expect(barSpy).toHaveBeenCalled();
    expect(sessionSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['/']);
  });

  it('getById should return right user', () => {
    expect(component).toBeTruthy();
    expect(component.user).toEqual({
      id: 1,
      email: 'test@gmail.com',
      lastName: 'Tremelo',
      firstName: 'Florian',
      admin: false,
      password: 'pwdTest',
      createdAt: date,
    });
  });

  /*it('should display right user info', async () => {

    const nativeElement = fixture.nativeElement;

    const userTest = {
      id: 1,
      email: 'test@gmail.com',
      lastName: 'Tremelo',
      firstName: 'Florian',
      admin: true,
      password: 'pwdTest',
      createdAt: date,
      updatedAt: date,
    };

    component.user = userTest;

    fixture.detectChanges();

    const [
      userName,
      userMail,
      userIsAdmin,
      userCreatedAt,
      userUpdatedAt,
    ] = nativeElement.querySelectorAll('p');

    expect(userName.textContent).toEqual(
      `Name: ${userTest.firstName} ${userTest.lastName.toUpperCase()}`
    );

    expect(userMail.textContent).toEqual(`Email: ${userTest.email}`);

    expect(userIsAdmin.textContent).toEqual('You are admin');

    let dateComparaison = userTest.createdAt;
    const options = {month: "long"};
    const dateBonFormat = new Intl.DateTimeFormat("en-US", options).format(dateComparaison);
    let jour = date.getDay().toString();
    let mois = date.getMonth.

    expect(userCreatedAt.textContent).toEqual('Create at:');

    expect(userUpdatedAt.textContent).toEqual(date);

  });*/
  
});
