package projects.karlosp3rez.androidpokedex.model;

import java.util.List;

public class Pokedex {

    private List<Pokemon> pokemon;

    public Pokedex() {

    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
