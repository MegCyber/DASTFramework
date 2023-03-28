Feature: OWASP ZAP DAST on Juice Shop

  Scenario: Perform DAST on Juice Shop using OWASP ZAP
    Given I have installed OWASP ZAP
    And Juice Shop is running locally on port 3000
    When I start a new ZAP session
    And I configure ZAP to use the Firefox webdriver
    And I set the ZAP target URL to "http://localhost:3000"
    And I set the ZAP spider options to "scan everything"
    And I start the ZAP spider
    And I wait for the spider to finish
    And I set the ZAP scan options to "full scan"
    And I start the ZAP scanner
    And I wait for the scanner to finish
    Then I should see a list of vulnerabilities in the ZAP report
    And I should see at least one high or medium severity vulnerability
    And   I close the browser
    And I shutdown ZAP
Feature: OWASP ZAP DAST on Juice Shop

  Scenario: Perform DAST on Juice Shop using OWASP ZAP
    Given I have installed OWASP ZAP
    And Juice Shop is running locally on port 3000
    When I start a new ZAP session
    And I configure ZAP to use the Firefox webdriver
    And I set the ZAP target URL to "http://localhost:3000"
    And I set the ZAP spider options to "scan everything"
    And I start the ZAP spider
    And I wait for the spider to finish
    And I set the ZAP scan options to "full scan"
    And I start the ZAP scanner
    And I wait for the scanner to finish
    Then I should see a list of vulnerabilities in the ZAP report
    And I should see at least one high or medium severity vulnerability


