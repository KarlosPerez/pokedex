package projects.karlosp3rez.androidpokedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.adapter.PokemonEvolutionAdapter;
import projects.karlosp3rez.androidpokedex.adapter.PokemonTypeAdapter;
import projects.karlosp3rez.androidpokedex.model.Pokemon;
import projects.karlosp3rez.androidpokedex.utils.Common;

public class PokemonDetail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name, pokemon_height, pokemon_weight;
    RecyclerView recycler_type, recycler_weakness, recycler_nextEvolution, recycler_prevEvolution;

    static PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if(instance == null)
            instance = new PokemonDetail();
        return instance;
    }

    public PokemonDetail() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Pokemon pokemon;
        View itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        //Get position from Argument
        if (getArguments().get("num") == null) {
            pokemon = Common.pokemonList.get(getArguments().getInt("position"));
        }else {
            pokemon = Common.findPokemonByNum(getArguments().getString("num"));
        }
        pokemon_img = (ImageView) itemView.findViewById(R.id.pokemon_image);
        pokemon_name = (TextView) itemView.findViewById(R.id.pokeName);
        pokemon_height = (TextView) itemView.findViewById(R.id.pokeHeight);
        pokemon_weight = (TextView) itemView.findViewById(R.id.pokeWeight);

        recycler_type = (RecyclerView) itemView.findViewById(R.id.recycler_pokeType);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_weakness = (RecyclerView) itemView.findViewById(R.id.recycler_pokeWeakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_prevEvolution = (RecyclerView) itemView.findViewById(R.id.recycler_pokePrevEvolution);
        recycler_prevEvolution.setHasFixedSize(true);
        recycler_prevEvolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_nextEvolution = (RecyclerView) itemView.findViewById(R.id.recycler_pokeNextEvolution);
        recycler_nextEvolution.setHasFixedSize(true);
        recycler_nextEvolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        setDetailPokemon(pokemon);

        return itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //Load image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);
        pokemon_name.setText(pokemon.getName());
        pokemon_weight.setText(getResources().getString(R.string.dummy_pokemon_weight)+ " " + pokemon.getWeight());
        pokemon_height.setText(getResources().getString(R.string.dummy_pokemon_height)+ " " + pokemon.getHeight());

        //Set type
        PokemonTypeAdapter typeAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getType());
        recycler_type.setAdapter(typeAdapter);

        //Set weakness
        PokemonTypeAdapter weaknessAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getWeaknesses());
        recycler_weakness.setAdapter(weaknessAdapter);

        //Set Evolution
        PokemonEvolutionAdapter prevEvoAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getPrev_evolution());
        recycler_prevEvolution.setAdapter(prevEvoAdapter);

        PokemonEvolutionAdapter nextEvoAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getNext_evolution());
        recycler_nextEvolution.setAdapter(nextEvoAdapter);
    }
}
