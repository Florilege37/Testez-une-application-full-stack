describe('Login And check /me', () => {
  it('Login and /me succesfull data', () => {
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
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')


    cy.intercept(
        {
          method: 'GET',
          url: '/api/user/1',
        },
        {
          statusCode: 200,
          body: {
            id: 1,
            email: 'yoga@studio.com',
            lastName: 'Blanc',
            firstName: 'Michel',
            admin: true,
            password: 'pwdTest',
            createdAt: new Date(),
            updatedAt: new Date(),
          },
        }
      ).as('me');

    cy.get('span[routerLink=me]').click({onclick});

    cy.url().should('include', '/me')

    cy.get('p').contains('Name: Michel BLANC').should('exist');
    cy.get('p').contains('Email: yoga@studio.com').should('exist');
    cy.get('p').contains('You are admin').should('exist');
    
  })
});

