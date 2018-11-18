package projects.karlosp3rez.androidpokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import projects.karlosp3rez.androidpokedex.Interface.IItemClickListener;
import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.model.Pokemon;
import projects.karlosp3rez.androidpokedex.utils.Common;

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
        pokemonViewHolder.pokemon_number.setText("#" + pokemonList.get(position).getNum());

        //Event
        pokemonViewHolder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
               // Toast.makeText(context, "Clic on pokemon: "+ pokemonList.get(position).getName(), Toast.LENGTH_SHORT).show();
                LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("position", position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemon_image;
        TextView pokemon_name;
        TextView pokemon_number;

        IItemClickListener iItemClickListener;

        public void setiItemClickListener(IItemClickListener iItemClickListener) {
            this.iItemClickListener = iItemClickListener;
        }

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image = (ImageView) itemView.findViewById(R.id.pokemon_image);
            pokemon_name = (TextView) itemView.findViewById(R.id.txt_pokemon_name);
            pokemon_number = (TextView) itemView.findViewById(R.id.txt_pokemon_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
