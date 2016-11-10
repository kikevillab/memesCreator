package miw.upm.es.memegenerator;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int URL_LOADER_IMAGES = 0;
    private static final int URL_LOADER_FONTS = 1;
    private static final int URL_LOADER_MEMES = 2;

    ImageAdapter imageListAdapter;
    SimpleCursorAdapter fontListAdapter;
    SimpleCursorAdapter memeListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(URL_LOADER_IMAGES, null, this);

        ListView lvItems = (ListView) findViewById(R.id.imagesList);
        imageListAdapter = new ImageAdapter(this, R.layout.image_list, null);
        lvItems.setAdapter(imageListAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID) {
            case URL_LOADER_IMAGES:

                // Returns a new CursorLoader
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        MemesProvider.IMAGES_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case URL_LOADER_FONTS:
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        MemesProvider.FONTS_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case URL_LOADER_MEMES:
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        MemesProvider.MEMES_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i("LOADER", "FINISH");
        switch (loader.getId()) {
            case URL_LOADER_IMAGES:
                imageListAdapter.changeCursor(cursor);
                break;
            case URL_LOADER_MEMES:
                memeListAdapter.changeCursor(cursor);
                break;
            case URL_LOADER_FONTS:
                fontListAdapter.changeCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case URL_LOADER_IMAGES:
                imageListAdapter.changeCursor(null);
                break;
            case URL_LOADER_MEMES:
                memeListAdapter.changeCursor(null);
                break;
            case URL_LOADER_FONTS:
                fontListAdapter.changeCursor(null);
                break;
        }
    }

}
