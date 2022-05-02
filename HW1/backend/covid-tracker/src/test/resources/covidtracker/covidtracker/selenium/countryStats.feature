Feature: Search COVID-19 statistics

    Scenario: See current global statistics
        When user navigates to the covid tracker application
        And orders alphabetically the countries
        Then the recovered cases should be greater than or equal to 467514901
        Then the first country should be 'Afghanistan'
    
    Scenario: See current Andorra's statistics and history at october 6th of 2021
        When user navigates to the covid tracker application
        And user selects 'Andorra'
        And selects 06/10/2021
        Then the continent should be 'Europe'
        Then the total cases should be greater than or equal to 41349
        Then the new cases at 19:15:04 should be 4