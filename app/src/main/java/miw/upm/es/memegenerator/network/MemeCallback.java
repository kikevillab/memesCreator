package miw.upm.es.memegenerator.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import miw.upm.es.memegenerator.MemesProvider;
import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.Meme;
import miw.upm.es.memegenerator.model.MemesManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enrique on 10/11/2016.
 */
public class MemeCallback implements Callback<Meme> {

    private Context context;
    private Meme meme;

    public MemeCallback(Context context){
        this.context = context;
    }

    @Override
    public void onResponse(Call<Meme> call, Response<Meme> response) {
        Log.i("RETROFIT", Integer.toString(response.code()));
        Log.i("RETROFIT", String.valueOf(response.raw()));

        this.meme =  response.body();
        MemesManager memesManager = new MemesManager(this.context);
        memesManager.addMeme(meme);



    }

    @Override
    public void onFailure(Call<Meme> call, Throwable t) {
        Log.e("RETROFIT", t.getMessage());




    }

    public Meme meme(){
        return this.meme;
    }
}
