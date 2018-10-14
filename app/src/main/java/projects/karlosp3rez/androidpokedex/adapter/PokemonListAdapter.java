package projects.karlosp3rez.androidpokedex.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.model.Pokemon;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>{

    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewHolder, int position) {
        Glide.with(context).load(pokemonList.get(position).getImg()).into(pokemonViewHolder.pokemon_image);
        //Set Name
        pokemonViewHolder.pokemon_name.setText(pokemonList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        ImageView pokemon_image;
        TextView pokemon_name;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image = (ImageView) itemView.findViewById(R.id.pokemon_image);
            pokemon_name = (TextView) itemView.findViewById(R.id.txt_pokemon_name);
        }
    }
}
