describe('Login And check /sessions', () => {
    it('Login with admin and session detail with delete button', () => {
        cy.visit('/login')
    
        cy.intercept('POST', '/api/auth/login', {
          body: {
            id: 1,
            username: 'yoga@studio.com',
            firstName: 'Michel',
            lastName: 'Blanc',
            admin: true
          },
        })
    
        cy.intercept(
          {
            method: 'GET',
            url: '/api/session',
          },
          {
            statusCode: 200,
            body: 
                [{
                    id: 1,
                    name: 'Session 1',
                    description: "Description 1",
                    date: new Date(),
                    teacher_id: 1,
                    users: [1,2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 2,
                    name: 'Session 2',
                    description: "Description 2",
                    date: new Date(),
                    teacher_id: 2,
                    users: [1,2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 3,
                    name: 'Session 3',
                    description: "Description 3",
                    date: new Date(),
                    teacher_id: 3,
                    users: [1,2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },],
          }
        ).as('sessions'); 

        cy.intercept(
            {
              method: 'GET',
              url: '/api/session/1',
            },
            {
              statusCode: 200,
              body: 
                  {
                      id: 1,
                      name: 'Session 1',
                      description: "Description 1",
                      date: new Date(),
                      teacher_id: 1,
                      users: [1,2,3,4,5],
                      createdAt: new Date(),
                      updatedAt: new Date(),    
                   },
            }
          ).as('session'); 

    
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    
        cy.url().should('include', '/sessions') 

        cy.get('mat-card-actions button').eq(0).click({onclick});

        cy.url().should('include', '/sessions/detail/1') 
  
        cy.get('button[color="warn"]').should('contain', 'Delete');
        cy.get('span[class="ml1"]').should('contain', '5 attendees');

        cy.get('button[color="warn"]').click({onclick});

        cy.intercept('DELETE', '/api/sessions/1');

        cy.url().should('include', '/sessions') 
      })

      it('Login with classic user and session detail with Participate', () => {
        cy.visit('/login')
    
        cy.intercept('POST', '/api/auth/login', {
          body: {
            id: 1,
            username: 'yoga@studio.com',
            firstName: 'Michel',
            lastName: 'Blanc',
            admin: false
          },
        })
    
        cy.intercept(
          {
            method: 'GET',
            url: '/api/session',
          },
          {
            statusCode: 200,
            body: 
                [{
                    id: 1,
                    name: 'Session 1',
                    description: "Description 1",
                    date: new Date(),
                    teacher_id: 1,
                    users: [2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 2,
                    name: 'Session 2',
                    description: "Description 2",
                    date: new Date(),
                    teacher_id: 2,
                    users: [2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 3,
                    name: 'Session 3',
                    description: "Description 3",
                    date: new Date(),
                    teacher_id: 3,
                    users: [2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },],
          }
        ).as('sessions'); 

        cy.intercept(
            {
              method: 'GET',
              url: '/api/session/1',
            },
            {
              statusCode: 200,
              body: 
                  {
                      id: 1,
                      name: 'Session 1',
                      description: "Description 1",
                      date: new Date(),
                      teacher_id: 1,
                      users: [2,3,4,5],
                      createdAt: new Date(),
                      updatedAt: new Date(),    
                   },
            }
          ).as('session'); 

    
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    
        cy.url().should('include', '/sessions') 

        cy.get('mat-card-actions button').eq(0).click({onclick});

        cy.url().should('include', '/sessions/detail/1') 
  
        cy.get('span[class="ml1"]').should('contain', 'Participate');
        cy.get('span[class="ml1"]').should('contain', '4 attendees');
      })

      it('Login with classic user and session detail with Do not participate', () => {
        cy.visit('/login')
    
        cy.intercept('POST', '/api/auth/login', {
          body: {
            id: 1,
            username: 'yoga@studio.com',
            firstName: 'Michel',
            lastName: 'Blanc',
            admin: false
          },
        })
    
        cy.intercept(
          {
            method: 'GET',
            url: '/api/session',
          },
          {
            statusCode: 200,
            body: 
                [{
                    id: 1,
                    name: 'Session 1',
                    description: "Description 1",
                    date: new Date(),
                    teacher_id: 1,
                    users: [1,2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 2,
                    name: 'Session 2',
                    description: "Description 2",
                    date: new Date(),
                    teacher_id: 2,
                    users: [2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },
                 {
                    id: 3,
                    name: 'Session 3',
                    description: "Description 3",
                    date: new Date(),
                    teacher_id: 3,
                    users: [2,3,4,5],
                    createdAt: new Date(),
                    updatedAt: new Date(),    
                 },],
          }
        ).as('sessions'); 

        cy.intercept(
            {
              method: 'GET',
              url: '/api/session/1',
            },
            {
              statusCode: 200,
              body: 
                  {
                      id: 1,
                      name: 'Session 1',
                      description: "Description 1",
                      date: new Date(),
                      teacher_id: 1,
                      users: [1,2,3,4,5],
                      createdAt: new Date(),
                      updatedAt: new Date(),    
                   },
            }
          ).as('session'); 

    
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    
        cy.url().should('include', '/sessions') 

        cy.get('mat-card-actions button').eq(0).click({onclick});

        cy.url().should('include', '/sessions/detail/1') 
  
        cy.get('span[class="ml1"]').should('contain', 'Do not participate');
        cy.get('span[class="ml1"]').should('contain', '5 attendees');
      })
  });