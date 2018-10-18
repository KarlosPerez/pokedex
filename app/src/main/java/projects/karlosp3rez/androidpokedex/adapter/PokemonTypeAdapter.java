package projects.karlosp3rez.androidpokedex.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.List;

import projects.karlosp3rez.androidpokedex.Interface.IItemClickListener;
import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.utils.Common;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeAdapter.TypeViewHolder> {

    Context context;
    List<String> typeList;

    public PokemonTypeAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);
        return new TypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder typeViewHolder, int position) {
        typeViewHolder.chip.setChipText(typeList.get(position));
        typeViewHolder.chip.changeBackgroundColor(Common.getColorByType(typeList.get(position)));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        Chip chip;
        IItemClickListener itemClickListener;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);

            chip = (Chip) itemView.findViewById(R.id.chipView);

            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    itemClickListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
