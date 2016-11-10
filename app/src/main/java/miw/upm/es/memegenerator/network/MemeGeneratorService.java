package miw.upm.es.memegenerator.network;

import java.util.List;

import miw.upm.es.memegenerator.model.Meme;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Enrique on 10/11/2016.
 */

public interface MemeGeneratorService {

    static final String ENDPOINT = "https://ronreiter-meme-generator.p.mashape.com/";

    @GET("/meme")
    Call<Meme> generateMeme(@Query("bottom") String bottomText, @Query("font") String font, @Query("font_size") String fontSize, @Query("meme") String meme, @Query("top") String topText);

    @GET("/images")
    Call<List<String>> getImages();

    @GET("/fonts")
    Call<List<String>> getFonts();

    @FormUrlEncoded
    @POST("/images")
    void uploadImage(@Field("image") String image);

}
