package Helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PokemonApiHelper {

    public static Response getPokemonData(String baseUrl, String pokemonName) {
        RestAssured.baseURI = baseUrl;
        return given().when().get("/pokemon/" + pokemonName).then().extract().response();
    }

    public static String extractSpeciesUrl(Response response) {
        return response.jsonPath().getString("species.url");
    }

    public static Response getSpeciesData(String speciesUrl) {
        return given().when().get(speciesUrl).then().extract().response();
    }

    public static String extractEvolutionChainUrl(Response response) {
        return response.jsonPath().getString("evolution_chain.url");
    }

    public static Response getEvolutionChainData(String evolutionChainUrl) {
        return given().when().get(evolutionChainUrl).then().extract().response();
    }

    public static List<String> extractEvolutionChainNames(Response response) {
        return extractEvolutionChainNames(response.asString());
    }

    private static List<String> extractEvolutionChainNames(String evolutionChainJson) {
        List<String> names = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(evolutionChainJson);
        JSONObject chain = jsonObject.getJSONObject("chain");

        names.add(chain.getJSONObject("species").getString("name"));
        extractEvolvesTo(chain, names);

        return names;
    }

    private static void extractEvolvesTo(JSONObject chainNode, List<String> names) {
        JSONArray evolvesTo = chainNode.getJSONArray("evolves_to");
        for (int i = 0; i < evolvesTo.length(); i++) {
            JSONObject evolution = evolvesTo.getJSONObject(i);
            names.add(evolution.getJSONObject("species").getString("name"));
            extractEvolvesTo(evolution, names);
        }
    }
}
