package Steps;

import Helpers.PokemonApiHelper;
import Helpers.SortingHelper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonSteps {

    private String baseUrl;
    private Response pokemonResponse;
    private Response speciesResponse;
    private Response evolutionChainResponse;
    private String speciesUrl;
    private String evolutionChainUrl;
    private List<String> pokemonNames;
    private List<String> sortedNames;

    @Given("the Pokemon API is available at {string}")
    public void thePokemonApiIsAvailableAt(String url) {
        baseUrl = url;
    }

    @When("I request the data of the Pokemon {string}")
    public void iRequestTheDataOfThePokemon(String pokemonName) {
        pokemonResponse = PokemonApiHelper.getPokemonData(baseUrl, pokemonName);
    }

    @Then("I get the URL of its species")
    public void iGetTheUrlOfItsSpecies() {
        speciesUrl = PokemonApiHelper.extractSpeciesUrl(pokemonResponse);
    }

    @Then("I get the URL of its evolution")
    public void iGetTheUrlOfItsEvolution() {
        speciesResponse = PokemonApiHelper.getSpeciesData(speciesUrl);
        evolutionChainUrl = PokemonApiHelper.extractEvolutionChainUrl(speciesResponse);
    }

    @Then("I extract the species names in the evolution chain")
    public void iExtractTheSpeciesNamesInTheEvolutionChain() {
        evolutionChainResponse = PokemonApiHelper.getEvolutionChainData(evolutionChainUrl);
        pokemonNames = PokemonApiHelper.extractEvolutionChainNames(evolutionChainResponse);
    }

    @Then("all response codes should be {int}")
    public void allResponseCodesShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, pokemonResponse.getStatusCode());
        assertEquals(expectedStatusCode, speciesResponse.getStatusCode());
        assertEquals(expectedStatusCode, evolutionChainResponse.getStatusCode());
    }

    @Then("I sort the names alphabetically")
    public void iSortTheNamesAlphabetically() {
        sortedNames = SortingHelper.bubbleSortNames(pokemonNames);
    }

    @Then("the list should contain Squirtle evolutions")
    public void theListShouldContainSquirtleEvolutions() {
        assertEquals(3, sortedNames.size());

        List<String> expectedNames = List.of("squirtle", "wartortle", "blastoise");
        for (String expected : expectedNames) {
            assertTrue(pokemonNames.contains(expected));
        }
    }

    @Then("the names should be sorted alphabetically")
    public void theNamesShouldBeSortedAlphabetically() {
        for (int i = 0; i < sortedNames.size() - 1; i++) {
            assertTrue(sortedNames.get(i).compareTo(sortedNames.get(i + 1)) <= 0);
        }
        System.out.println("Pokemon in the evolution chain sorted alphabetically:");
        for (String name : sortedNames) {
            System.out.println(name);
        }
    }
}
