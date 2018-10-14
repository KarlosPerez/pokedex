package projects.karlosp3rez.androidpokedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import projects.karlosp3rez.androidpokedex.R;
import projects.karlosp3rez.androidpokedex.adapter.PokemonListAdapter;
import projects.karlosp3rez.androidpokedex.model.Pokedex;
import projects.karlosp3rez.androidpokedex.retrofit.IPodedex;
import projects.karlosp3rez.androidpokedex.retrofit.RetrofitClient;
import projects.karlosp3rez.androidpokedex.utils.Common;
import projects.karlosp3rez.androidpokedex.utils.ItemOffsetDecorator;
import retrofit2.Retrofit;


public class PokemonList extends Fragment {

    IPodedex iPodedex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recycler;

    static PokemonList instance;

    public static PokemonList getInstance() {
        if(instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iPodedex = retrofit.create(IPodedex.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        pokemon_list_recycler = (RecyclerView) view.findViewById(R.id.pokemon_list_recycler);
        pokemon_list_recycler.setHasFixedSize(true);
        pokemon_list_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecorator itemOffsetDecorator = new ItemOffsetDecorator(getActivity(), R.dimen.spacing);
        pokemon_list_recycler.addItemDecoration(itemOffsetDecorator);

        loadPokemonList();

        return view;
    }

    private void loadPokemonList() {
        compositeDisposable.add(iPodedex.getListaPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Pokedex>() {
            @Override
            public void accept(Pokedex pokedex) throws Exception {
                Common.pokemonList = pokedex.getPokemon();
                showPokemonList();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }

    private void showPokemonList() {
        PokemonListAdapter adapter = new PokemonListAdapter(getActivity(), Common.pokemonList);
        pokemon_list_recycler.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
