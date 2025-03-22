Feature: Pokémon Evolution Chain Integration Test

  Scenario: Retrieve Squirtle evolution chain and sort the names
    Given the Pokemon API is available at "https://pokeapi.co/api/v2"
    When I request the data of the Pokemon "squirtle"
    Then I get the URL of its species
    Then I get the URL of its evolution
    Then I extract the species names in the evolution chain
    Then all response codes should be 200
    Then I sort the names alphabetically
    Then the list should contain Squirtle evolutions
    Then the names should be sorted alphabetically
