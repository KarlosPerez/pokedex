package projects.karlosp3rez.androidpokedex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import projects.karlosp3rez.androidpokedex.fragments.PokemonDetail;
import projects.karlosp3rez.androidpokedex.model.Pokemon;
import projects.karlosp3rez.androidpokedex.utils.Common;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable back button on toolbar
                getSupportActionBar().setDisplayShowHomeEnabled(true); //too

                //replace fragment
                Fragment detailFragment = PokemonDetail.getInstance();
                int position = intent.getIntExtra("position", -1);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.listPokemonFragment, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set Pokemon Name for Toolbar
                Pokemon pokemon = Common.pokemonList.get(position);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Common.KEY_NUM_EVOLUTION)) {

                //replace fragment
                Fragment detailFragment = PokemonDetail.getInstance();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num", num);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailFragment); //remove current fragment
                fragmentTransaction.replace(R.id.listPokemonFragment, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set Pokemon Name for Toolbar
                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //Register broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, new IntentFilter(Common.KEY_ENABLE_HOME));
        //Register broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution, new IntentFilter(Common.KEY_NUM_EVOLUTION));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toolbar.setTitle("POKEMON LIST");

                //Clear all fragment detail and pop to list fragment
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
                default:
                    break;
        }
        return true;
    }
}
