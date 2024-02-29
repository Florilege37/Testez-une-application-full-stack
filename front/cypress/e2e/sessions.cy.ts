describe('Login And check /sessions', () => {
    it('Login with classic user and /sessions succesfull data with Edit Button hidden', () => {
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
  
      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
  
      cy.url().should('include', '/sessions') 

      cy.get('mat-card-title').eq(1).should('contain', 'Session 1');
      cy.get('mat-card-content').eq(0).should('contain', 'Description 1');

      cy.get('mat-card-actions button').eq(0).should('contain', 'Detail');
      cy.get('mat-card-actions button').eq(1).should('not.contain', 'Edit');

    })

    it('Login with admin and /sessions succesfull data', () => {
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
    
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    
        cy.url().should('include', '/sessions') 
  
        cy.get('mat-card-title').eq(1).should('contain', 'Session 1');
        cy.get('mat-card-content').eq(0).should('contain', 'Description 1');
  
        cy.get('mat-card-actions button').eq(0).should('contain', 'Detail');
        cy.get('mat-card-actions button').eq(1).should('contain', 'Edit');
  
      })
  });