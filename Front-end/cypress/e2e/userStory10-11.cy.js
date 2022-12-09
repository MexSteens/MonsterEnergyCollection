describe('The admin page', () => {
    
    beforeEach(() => {
        //visit login
        cy.visit('http://localhost:3000/login')
    
        //fill in form
        cy.get('input[name=username]').type("")
        cy.get('input[name=password]').type("")
    
        //submit form
        cy.get('button[name=login-button]').click()
    
        //assert everything went okay
        cy.url().should('include', 'roles')
        cy.contains("You can change the permissions of the following users")
    })

    it('user story 10', () => {
      //confirm role is user
      cy.get('div[name=]').within(() => {
        cy.contains("")
        cy.contains("USER")
      })
  
      //change role to moderator
      cy.get('div[name=]').within(() => {
        cy.get('button[name=moderator-button]').click()
      })
  
      //assert everything went okay
      cy.get('div[name=]').within(() => {
        cy.contains("MODERATOR")
        cy.get('button[name=user-button]').click()
      })
    })

    it('user story 11', () => {
        //confirm  is present
        cy.get('div[name=]').within(() => {
          cy.contains("")
        })
    
        //search user
        cy.get('input[name=searchbar]').type("")
        cy.get('button[name=search-button]').click()
    
        //assert everything went okay
        cy.get('div[name=]').within(() => {
          cy.contains("")
        })

        //search partly
        cy.get('input[name=searchbar]').clear().type("")
        cy.get('button[name=search-button]').click()

        //assert everything went okay
        cy.get('div[name=]').within(() => {
          cy.contains("")
        })
      })
  })