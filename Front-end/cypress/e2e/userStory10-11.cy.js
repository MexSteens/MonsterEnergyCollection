describe('The admin page', () => {
    
    beforeEach(() => {
        //visit login
        cy.visit('http://localhost:3000/login')
    
        //fill in form
        cy.get('input[name=username]').type("testaccount")
        cy.get('input[name=password]').type("test1234")
    
        //submit form
        cy.get('button[name=login-button]').click()
    
        //assert everything went okay
        cy.url().should('include', 'roles')
        cy.contains("You can change the permissions of the following users")
    })

    it('user story 10', () => {
      //confirm role is user
      cy.get('div[name=usernametest]').within(() => {
        cy.contains("usernametest")
        cy.contains("USER")
      })
  
      //change role to moderator
      cy.get('div[name=usernametest]').within(() => {
        cy.get('button[name=moderator-button]').click()
      })
  
      //assert everything went okay
      cy.get('div[name=usernametest]').within(() => {
        cy.contains("MODERATOR")
        cy.get('button[name=user-button]').click()
      })
    })

    it('user story 11', () => {
        //confirm usernametest is present
        cy.get('div[name=usernametest]').within(() => {
          cy.contains("usernametest")
        })
    
        //search user
        cy.get('input[name=searchbar]').type("usernametest")
        cy.get('button[name=search-button]').click()
    
        //assert everything went okay
        cy.get('div[name=usernametest]').within(() => {
          cy.contains("usernametest")
        })

        //search partly
        cy.get('input[name=searchbar]').clear().type("username")
        cy.get('button[name=search-button]').click()

        //assert everything went okay
        cy.get('div[name=usernametest]').within(() => {
          cy.contains("usernametest")
        })
      })
  })