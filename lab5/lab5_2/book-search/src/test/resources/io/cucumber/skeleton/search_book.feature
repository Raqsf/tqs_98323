Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Background: 
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
      And a book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
      And a book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2021-01-01  
    
  Scenario: Search books by publication year
    When the customer searches for books published between 2013 and 2014
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'

  Scenario: Correct non-zero number of books found by title
    When I search for books by title 'How to cook a dino'
    Then I should find 1 book

  Scenario: Correct non-zero number of books found by author
    Given I have the following books in the store
      | title              | author          | published  |
      | One good book      | Anonymous       | 2013-03-14 |
      | Some other book    | John Tomson      | 2014-08-23 |
      | How to cook a dino | Fred Flintstone | 2021-01-01 |
    When I search for books by author 'John Tomson'
    Then I find 1 book