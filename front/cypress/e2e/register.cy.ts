describe('Register', () => {
    it('Register form tets', () => {
      cy.visit('/register')
      
      cy.get('mat-form-field').eq(0).should('contain', 'First name');
      cy.get('mat-form-field').eq(1).should('contain', 'Last name');
      cy.get('mat-form-field').eq(2).should('contain', 'Email');
      cy.get('mat-form-field').eq(3).should('contain', 'Password');
    });
  
  });
  
  