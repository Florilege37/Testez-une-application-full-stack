describe('Login And create session', () => {
    it('Login, check good create form', () => {
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
          url: '/api/teacher'
          ,
        },
        {
          statusCode: 200,
          body: [
            {
                "id": 1,
                "lastName": "DELAHAYE",
                "firstName": "Margot",
                "createdAt": "2024-02-08T12:38:25",
                "updatedAt": "2024-02-08T12:38:25"
            },
            {
                "id": 2,
                "lastName": "THIERCELIN",
                "firstName": "Hélène",
                "createdAt": "2024-02-08T12:38:25",
                "updatedAt": "2024-02-08T12:38:25"
            }
        ],
        }
      ).as('teacher');
      

      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

      cy.get('button[routerLink=create]').click({onclick})
  
      cy.url().should('include', '/sessions/create') 

      cy.get('mat-form-field').eq(0).should('contain', 'Name');
      cy.get('mat-form-field').eq(1).should('contain', 'Date');
      cy.get('mat-form-field').eq(2).should('contain', 'Teacher');
      cy.get('mat-form-field').eq(3).should('contain', 'Description');

    })
  });