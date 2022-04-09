Feature: Purchase flight from BlazeDemo
 
  Scenario: Successful purchase
    Given I navigate to 'https://blazedemo.com'
      And I select flight from 'Boston' to 'London'
      And I click Find Flights
      And I click Choose This Flight
      And I click Purchase Flight
      Then I should be shown the 'BlazeDemo Confirmation' page