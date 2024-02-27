describe('Login Error', () => {
    it('Login error', () => {
      cy.visit('/login')
  
      cy.get('input[formControlName=email]').type("yogaa@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
  
      cy.get('.error' ).should('have.text', 'An error occurred');
    })
    
  });