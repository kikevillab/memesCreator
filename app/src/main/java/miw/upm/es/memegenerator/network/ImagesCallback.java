package miw.upm.es.memegenerator.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import miw.upm.es.memegenerator.MemesProvider;
import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.MemesManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enrique on 10/11/2016.
 */

public class ImagesCallback implements Callback<List<String>> {

    private Context context;
    private List<String> images;
    
    public ImagesCallback(Context context){
        this.context = context;
    }

    @Override
    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        Log.i("RETROFIT", Integer.toString(response.code()));
        Log.i("RETROFIT", String.valueOf(response.raw()));

        this.images =  response.body();
        MemesManager memesManager = new MemesManager(this.context);
        for(String imageName : images){
            Image img = new Image(imageName, Uri.parse("http://apimeme.com/meme?meme=" + imageName +"&top=&bottom="));
            if(!memesManager.hasImage(img))
                memesManager.addImage(img);

        }



    }

    @Override
    public void onFailure(Call<List<String>> call, Throwable t) {
        Log.e("RETROFIT", t.getMessage());




    }

    public List<String> images(){
        return this.images;
    }
}
