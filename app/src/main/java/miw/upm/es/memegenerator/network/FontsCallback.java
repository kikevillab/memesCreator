package miw.upm.es.memegenerator.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import miw.upm.es.memegenerator.MemesProvider;
import miw.upm.es.memegenerator.model.Font;
import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.MemesManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enrique on 10/11/2016.
 */
public class FontsCallback implements Callback<List<String>> {

    private Context context;
    private List<String> fonts;

    public FontsCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        Log.i("RETROFIT", Integer.toString(response.code()));
        Log.i("RETROFIT", String.valueOf(response.raw()));

        this.fonts =  response.body();
        MemesManager memesManager = new MemesManager(this.context);
        for(String fontName : fonts){
            Font font = new Font(fontName);
            if(!memesManager.hasFont(font))
                memesManager.addFont(font);

        }



    }

    @Override
    public void onFailure(Call<List<String>> call, Throwable t) {
        Log.e("RETROFIT", t.getMessage());




    }

    public List<String> fonts(){
        return this.fonts;
    }
}
