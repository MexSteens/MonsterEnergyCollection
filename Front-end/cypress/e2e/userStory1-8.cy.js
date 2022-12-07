import Chance from 'chance'
const chance = new Chance();
const name = chance.name();
const username = chance.name();
const password = 'test1234';

describe('The register page', () => {
  
    it('user story 9', () => {
      //go to page and confirm it is the right page
      cy.visit('http://localhost:3000')
      cy.contains("Create an account")
  
      //fill in form
      cy.get('input[name=name]').type(name)
      cy.get('input[name=username]').type(username)
      cy.get('input[name=password1]').type(password)
      cy.get('input[name=password2]').type(password)
  
      //submit form
      cy.get('button[name=register-button]').click()
  
      //assert everything went okay
      cy.url().should('include', 'login')
      cy.get('h1[name=register-confirm]')
    })
  })


describe('User stories 1-8', () => {

  beforeEach(() => {
    //visit login
    cy.visit('http://localhost:3000/login')

    //fill in form
    cy.get('input[name=username]').type(username)
    cy.get('input[name=password]').type(password)

    //submit form
    cy.get('button[name=login-button]').click()

    //assert everything went okay
    cy.url().should('include', 'home')
    cy.contains("Hey there!")
  })

  it('user story 1', () => {
    //go to page and confirm it is the right page
    cy.visit('http://localhost:3000/result/1')
    cy.contains("Leave your review")
  })

  it('user story 4', () => {
    //confirm it is the right page
    cy.visit('http://localhost:3000/result/1')
    cy.contains("Leave your review")

    //leave review
    cy.get('input[name=comment').type("cypress review")
    cy.get('button[name=post]').click()

    //assert everything went okay
    cy.url().should('include', 'can/1')
    cy.contains("cypress review")
  })

  it('user story 3', () => {
    //confirm it is the right page
    cy.visit('http://localhost:3000/can/1')
    cy.contains("Average Rating")
  })

  it('user story 5', () => {
    //confirm it is the right page
    cy.visit('http://localhost:3000/can/1')
    cy.contains("Country")

    //edit comment
    cy.get('button[name=edit').click()
    cy.get('input[name=comment]').type(" edited")
    cy.get('button[name=submit-edit]').click()
    cy.contains("cypress review edited")

    //delete comment
    cy.get('button[name=delete').click()
    cy.get('button[name=confirm-delete').click()
    cy.contains("You haven't left a review yet...")
  })

  it('user story 6', () => {
    //confirm it is the right page
    cy.visit('http://localhost:3000/can/1')
    cy.contains("Country")

    //confirm other comments
    cy.contains("Other reviews")
    cy.get('p[name=review]')
    cy.get('span[name=review-stars]')
    cy.get('h1[name="account"]')
  })

  it('user story 7', () => {
    //confirm it is the right page
    cy.visit('http://localhost:3000/log')
    cy.contains("Recently scanned cans")

    //confirm logged can
    cy.get("div[name=logcard]")
  })

  it('user story 8', () => {
    //go to page and confirm it is the right page
    const random = chance.integer({min: 0, max: 100000})
    cy.visit(`http://localhost:3000/result/${random}`)
    cy.contains("This can does not seem to be in our database, please give it a name and we will possibly approve the new can!")

    //give name and submit
    cy.get('input[name=name]').type("asdfasdfasdf")
    cy.get('button[name=create-can]').click()

    //confirm submit
    cy.url().should('include', 'home')
    cy.contains("The new can has been succesfully requested!")
  })


})