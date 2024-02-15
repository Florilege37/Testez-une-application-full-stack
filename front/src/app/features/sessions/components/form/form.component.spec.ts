import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: SessionApiService;
  let routerTest : Router;
  let bar : MatSnackBar;

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
      admin: true
    }
  } 

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule 
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    sessionApiService = TestBed.inject(SessionApiService);
    routerTest = TestBed.inject(Router);
    bar = TestBed.inject(MatSnackBar);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit and create', () => {
    component.onUpdate = false;

    const sessionApiCreateSpy = jest.spyOn(sessionApiService, 'create').mockReturnValue(of(sessionTest));
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);
    const barSpy = jest.spyOn(bar, 'open');

    component.submit();

    expect(sessionApiCreateSpy).toHaveBeenCalled();
    expect(barSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['sessions']);
  });

  it('should submit and update', () => {
    component.onUpdate = true;

    const sessionApiCreateSpy = jest.spyOn(sessionApiService, 'update').mockReturnValue(of(sessionTest));
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);
    const barSpy = jest.spyOn(bar, 'open');

    component.submit();

    expect(sessionApiCreateSpy).toHaveBeenCalled();
    expect(barSpy).toHaveBeenCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(['sessions']);
  });

});
