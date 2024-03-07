describe('Login And LogOut', () => {  
    it('should be able to log out the user after logging in', () => {
      cy.visit('/session')

      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'yoga@studio.com',
          firstName: 'Michel',
          lastName: 'Blanc',
          admin: true,
        },
      });
    
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []
      ).as('session');
    
      cy.url().should('include', '/session');
    
      cy.get('.link').contains('Logout').click();
    
      cy.url().should('include', '/');
    });
  
});