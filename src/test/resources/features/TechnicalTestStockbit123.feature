Feature: Technical Test Stockbit

  Scenario Outline: Test1(Select Menu)
    Given User go to "<url>"
    When User in "<menu>" page
    And User choose select value "<selectValue>"
    And User choose select one "<selectOne>"
    And User choose old style select menu "<selectOldStyle>"
    And User choose multi select drop down all color
    Then User success input all select menu

    Examples:
      | url                              | menu          | selectValue         | selectOne | selectOldStyle |
      | https://demoqa.com/select-menu   | Select Menu   | Another root option | Other     | Aqua           |


  Scenario Outline: Test2(Search Books No Row Found)
    Given User go to "<url>"
    When User in "<menu>" page
    And User search book "<search>"
    Then User see "<expectedResult>"

    Examples:
      | url                              | menu          | search         | expectedResult |
      | https://demoqa.com/books   | Book Store   | qa engineer | No rows found     |

  Scenario Outline: Test3(Assert search book and detail book)
    Given User go to "<url>"
    When User in "<menu>" page
    And User search book "<search>"
    And User click book "<search>"
    Then User see detail book "<search>"

    Examples:
      | url                              | menu          | search         |
      | https://demoqa.com/books   | Book Store   | Git Pocket Guide |

