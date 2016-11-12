package miw.upm.es.memegenerator.network;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import miw.upm.es.memegenerator.MemesProvider;
import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.Meme;
import miw.upm.es.memegenerator.model.MemeContract;
import miw.upm.es.memegenerator.model.MemesManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enrique on 10/11/2016.
 */
public class MemeCallback implements Callback<ResponseBody> {

    private Context context;
    private Meme meme;

    public MemeCallback(Context context, Meme meme){
        this.context = context;
        this.meme = meme;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.i("RETROFIT", Integer.toString(response.code()));
        Log.i("RETROFIT", String.valueOf(response.raw()));

        if(response.isSuccessful()){
            this.meme.setImage(BitmapFactory.decodeStream(response.body().byteStream()));
            new MemesManager(this.context).addMeme(this.meme);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("RETROFIT", t.getMessage());




    }

    public Meme meme(){
        return this.meme;
    }


}
