package projects.karlosp3rez.androidpokedex.retrofit;

import io.reactivex.Observable;
import projects.karlosp3rez.androidpokedex.model.Pokedex;
import retrofit2.http.GET;

public interface IPodedex {
    @GET("pokedex.json")
    Observable<Pokedex> getListaPokemon();
}
