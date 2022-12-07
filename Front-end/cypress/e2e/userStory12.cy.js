describe('The moderator page', () => {
    
    it('user story 12', () => {
        //visit login
        cy.visit('http://localhost:3000/login')
    
        //fill in form
        cy.get('input[name=username]').type("usernametest")
        cy.get('input[name=password]').type("test1234")
    
        //submit form
        cy.get('button[name=login-button]').click()
    
        //assert everything went okay
        cy.url().should('include', 'home')
        
        //leave comment
        cy.visit('http://localhost:3000/result/1')
        cy.contains("Leave your review")
        cy.get('input[name=comment').type("cypress review")
        cy.get('button[name=post]').click()

        

        //visit login
        cy.wait(500)
        cy.visit('http://localhost:3000/login')
    
        //fill in form
        cy.get('input[name=username]').type("tester")
        cy.get('input[name=password]').type("test123")
    
        //submit form
        cy.get('button[name=login-button]').click()
    
        //assert everything went okay
        cy.url().should('include', 'approval')
        cy.contains("The following comments / cans need your approval")

        //approve
        cy.get('div[name=usernametest]').within(() => {
            cy.get('button[name=approve-comment]').click()
        })

        //delete comment
        cy.wait(500)
        cy.visit('http://localhost:3000/login')
    
        //fill in form
        cy.get('input[name=username]').type("usernametest")
        cy.get('input[name=password]').type("test1234")
    
        //submit form
        cy.get('button[name=login-button]').click()
    
        //assert everything went okay
        cy.url().should('include', 'home')
        
        //leave comment
        cy.visit('http://localhost:3000/can/1')
        cy.get('button[name=delete').click()
        cy.get('button[name=confirm-delete').click()
        cy.contains("You haven't left a review yet...")
    })
})