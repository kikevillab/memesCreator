package miw.upm.es.memegenerator;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import miw.upm.es.memegenerator.model.Font;
import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.Meme;
import miw.upm.es.memegenerator.model.MemeContract;
import miw.upm.es.memegenerator.model.MemesManager;
import miw.upm.es.memegenerator.network.FontsCallback;
import miw.upm.es.memegenerator.network.ImagesCallback;
import miw.upm.es.memegenerator.network.MemeCallback;
import miw.upm.es.memegenerator.network.MemeGeneratorFactory;
import miw.upm.es.memegenerator.network.MemeGeneratorService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Enrique on 10/11/2016.
 */

public class MemesProvider  extends ContentProvider {

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final String AUTHORITY = MemesProvider.class.getPackage().getName() + ".provider";
    private static final String ENTITY = "meme";

    public static final Uri MEMES_URI = Uri.parse("content://" + AUTHORITY + "/" + ENTITY);
    public static final Uri IMAGES_URI = Uri.parse("content://" + AUTHORITY + "/" + ENTITY + "/images");
    public static final Uri FONTS_URI = Uri.parse("content://" + AUTHORITY + "/" + ENTITY + "/fonts");

    private static final int ID_URI_MEMES = 1;
    private static final int ID_URI_NEW_MEMES = 2;
    private static final int ID_URI_IMAGES = 3;
    private static final int ID_URI_FONTS = 4;

    static{
        sUriMatcher.addURI(AUTHORITY, ENTITY, ID_URI_MEMES);
        sUriMatcher.addURI(AUTHORITY, ENTITY + "/new", ID_URI_NEW_MEMES);
        sUriMatcher.addURI(AUTHORITY, ENTITY + "/images", ID_URI_IMAGES);
        sUriMatcher.addURI(AUTHORITY, ENTITY + "/fonts", ID_URI_FONTS);
    }

    private MemeGeneratorService memesApiService;
    private MemesManager memesManager;

    @Override
    public boolean onCreate() {
        memesApiService = MemeGeneratorFactory.create(getContext());
        memesManager = new MemesManager(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        Cursor rows = null;

        if(sUriMatcher.match(uri) == ID_URI_MEMES) {
            rows = memesManager.getMemesAsCursor(projection, selection, selectionArgs, sortOrder);
        }else if(sUriMatcher.match(uri) == ID_URI_IMAGES){
            rows = memesManager.getImagesAsCursor(projection, selection, selectionArgs, sortOrder);

            if(rows.getCount() == 0) {
                Call<List<String>> call = memesApiService.getImages();
                //ImagesCallback imagesCallback = new ImagesCallback(getContext());
                try {
                    Response<List<String>> response = call.execute();
                    if(response.isSuccessful()){

                        for(String imageName : response.body()){
                            Image img = new Image(imageName, Uri.parse("http://apimeme.com/meme?meme=" + imageName +"&top=&bottom="));
                            if(!memesManager.hasImage(img))
                                memesManager.addImage(img);
                        }
                    }
                    rows = memesManager.getImagesAsCursor(projection, selection, selectionArgs, sortOrder);
                   } catch (IOException e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        }else if(sUriMatcher.match(uri) == ID_URI_FONTS){
            rows = memesManager.getFontsAsCursor(projection, selection, selectionArgs, sortOrder);
            if(rows.getCount() == 0) {
                Call<List<String>> call = memesApiService.getFonts();
                //FontsCallback fontsCallback = new FontsCallback(getContext());
                try {
                    Response<List<String>> response = call.execute();
                    if(response.isSuccessful()){
                        for(String fontName : response.body()){
                            Font font = new Font(fontName);
                            if(!memesManager.hasFont(font))
                                memesManager.addFont(font);
                        }
                    }
                    rows = memesManager.getFontsAsCursor(projection, selection, selectionArgs, sortOrder);
                } catch (IOException e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        }

        return rows;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ID_URI_MEMES:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+"."+ENTITY;
            case ID_URI_NEW_MEMES:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+"."+ENTITY;
            case ID_URI_IMAGES:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+"."+ENTITY+".image";
            case ID_URI_FONTS:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+"."+ENTITY+".font";

        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if(sUriMatcher.match(uri) != ID_URI_NEW_MEMES)
            return null;

        Call<Meme> call = memesApiService.generateMeme((String) contentValues.get(MemeContract.MemeTable.COL_NAME_BOTTOM_TEXT), (String) contentValues.get(MemeContract.MemeTable.COL_NAME_FONT), (String) contentValues.get(MemeContract.MemeTable.COL_NAME_FONT_SIZE), (String) contentValues.get(MemeContract.MemeTable.COL_NAME_MEME),(String)  contentValues.get(MemeContract.MemeTable.COL_NAME_TOP_TEXT));
        MemeCallback memeCallback = new MemeCallback(getContext());
        call.enqueue(memeCallback);

        return uri;

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
