import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { Router } from '@angular/router';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let service: SessionService;
  let routerTest : Router;
  let bar : MatSnackBar;
  let sessionApiService: SessionApiService;

  const sessionTest = {
    id: 1,
    name: 'test',
    description: 'test',
    date: new Date(),
    teacher_id: 1,
    users: [1, 2, 3, 4, 5],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent], 
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    })
      .compileComponents();
      service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    routerTest = TestBed.inject(Router);
    service  = TestBed.inject(SessionService);
    bar = TestBed.inject(MatSnackBar);
    sessionApiService = TestBed.inject(SessionApiService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should delete ', () => {
    
    const sessionApiSpy = jest.spyOn(sessionApiService, 'delete').mockImplementation(() => of(true));
    const barSpy  = jest.spyOn(bar, 'open');
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);
    

    component.delete();

    expect(sessionApiSpy).toHaveBeenCalled();
    expect(barSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['sessions']);
  });

  it('should participate and get session', () => {
    jest.spyOn(sessionApiService, 'participate').mockImplementation(() => of(undefined));
    const detailSpy = jest.spyOn(sessionApiService, 'detail').mockImplementation(() => of(sessionTest));

    component.participate();

    expect(detailSpy).toHaveBeenCalled();
    expect(component.session).toEqual(sessionTest);

  })

  it('should unparticipate and get session', () => {
    jest.spyOn(sessionApiService, 'unParticipate').mockImplementation(() => of(undefined));
    const detailSpy = jest.spyOn(sessionApiService, 'detail').mockImplementation(() => of(sessionTest));

    component.unParticipate();

    expect(detailSpy).toHaveBeenCalled();
    expect(component.session).toEqual(sessionTest);

  })
});

