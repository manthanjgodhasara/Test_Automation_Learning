Feature: Sample Feature

  @tag1
  Scenario: Verify Google Title
    Given I open the Google website
    Then the title should be "Googly"

  @tag2
  Scenario: Verify Bing Title
    Given I open the Bing website
    Then the title should be "Bing"