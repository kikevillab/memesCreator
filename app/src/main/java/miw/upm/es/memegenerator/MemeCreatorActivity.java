package miw.upm.es.memegenerator;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import miw.upm.es.memegenerator.model.FontContract;
import miw.upm.es.memegenerator.model.ImageContract;

/**
 * Created by Enrique on 11/11/2016.
 */
public class MemeCreatorActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int URL_LOADER_FONTS = 1;

    private String image;
    private SimpleCursorAdapter fontAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_creator);

        Bundle extras = getIntent().getExtras();
        this.image = extras.getString("IMAGEN");

        byte[] imgByte = extras.getByteArray("IMAGEN_URL");
        ImageView ivImageSrc = (ImageView) findViewById(R.id.imageSelected);
        ivImageSrc.setImageBitmap(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));

        Toast.makeText(this, this.image, Toast.LENGTH_SHORT).show();

        getLoaderManager().initLoader(URL_LOADER_FONTS, null, this);

        Spinner fontSelector = (Spinner) findViewById(R.id.selectedFont);
        fontAdapter = new SimpleCursorAdapter(this, R.layout.support_simple_spinner_dropdown_item, null, new String[] {FontContract.FontTable.COL_NAME_FONT_NAME}, new int[] {android.R.id.text1}, 0);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSelector.setAdapter(fontAdapter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID) {
            case URL_LOADER_FONTS:
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        MemesProvider.FONTS_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i("LOADER", "FINISH");
        switch (loader.getId()) {
            case URL_LOADER_FONTS:
                fontAdapter.changeCursor(cursor);
                Log.i("CURSOR", String.valueOf(cursor.getCount()));
                fontAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i("LOADER", "RESET");
        switch (loader.getId()) {
            case URL_LOADER_FONTS:
                fontAdapter.changeCursor(null);
                //fontAdapter.notifyDataSetChanged();
                break;
        }
    }
}
