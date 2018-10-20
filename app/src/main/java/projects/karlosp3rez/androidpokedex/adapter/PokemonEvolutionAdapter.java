package projects.karlosp3rez.androidpokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

import projects.karlosp3rez.androidpokedex.Interface.IItemClickListener;
import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.model.Evolution;
import projects.karlosp3rez.androidpokedex.utils.Common;

public class PokemonEvolutionAdapter extends RecyclerView.Adapter<PokemonEvolutionAdapter.EvolutionViewHolder> {

    Context context;
    List<Evolution> evolutions;

    public PokemonEvolutionAdapter(Context context, List<Evolution> evolutions) {
        this.context = context;
        if(evolutions != null)
            this.evolutions = evolutions;
        else
            this.evolutions = new ArrayList<>(); //fix crash if pokemon doesn't have prev or next evolution
    }

    @NonNull
    @Override
    public PokemonEvolutionAdapter.EvolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);
        return new PokemonEvolutionAdapter.EvolutionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonEvolutionAdapter.EvolutionViewHolder evolutionViewHolder, int position) {
        evolutionViewHolder.chip.setChipText(evolutions.get(position).getName());
        evolutionViewHolder.chip.changeBackgroundColor(Common.getColorByType(
                Common.findPokemonByNum(evolutions.get(position).getNum()).getType().get(0)));

        evolutionViewHolder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Click to evolve Pokemon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public class EvolutionViewHolder extends RecyclerView.ViewHolder {
        Chip chip;
        IItemClickListener itemClickListener;

        public void setItemClickListener(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public EvolutionViewHolder(@NonNull View itemView) {
            super(itemView);

            chip = (Chip) itemView.findViewById(R.id.chipView);

            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.KEY_NUM_EVOLUTION).
                                    putExtra("num", evolutions.get(getAdapterPosition()).getNum()));

                }
            });
        }
    }
}