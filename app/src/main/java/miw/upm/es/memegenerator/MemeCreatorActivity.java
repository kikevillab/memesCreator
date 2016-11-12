package miw.upm.es.memegenerator;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import miw.upm.es.memegenerator.model.FontContract;
import miw.upm.es.memegenerator.model.ImageContract;
import miw.upm.es.memegenerator.model.MemeContract;

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

        Button createMemeButton = (Button) findViewById(R.id.createMemeButton);
        createMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topText = ((EditText) findViewById(R.id.topText)).getText().toString();
                String bottomText = ((EditText) findViewById(R.id.bottomText)).getText().toString();
                String fontSize = ((EditText) findViewById(R.id.selectedFontSize)).getText().toString();
                Cursor fontFamilySelectedCursor = ((Cursor) ((Spinner) findViewById(R.id.selectedFont)).getSelectedItem());
                String fontFamily = fontFamilySelectedCursor.getString(fontFamilySelectedCursor.getColumnIndexOrThrow(FontContract.FontTable.COL_NAME_FONT_NAME));
                ContentValues values = new ContentValues();
                values.put(MemeContract.MemeTable.COL_NAME_TOP_TEXT, topText);
                values.put(MemeContract.MemeTable.COL_NAME_BOTTOM_TEXT, bottomText);
                values.put(MemeContract.MemeTable.COL_NAME_FONT_SIZE, fontSize);
                values.put(MemeContract.MemeTable.COL_NAME_FONT, fontFamily);
                values.put(MemeContract.MemeTable.COL_NAME_MEME, image);

                Toast.makeText(getApplicationContext(), fontFamily, Toast.LENGTH_SHORT);

                Uri newMemeUri = getContentResolver().insert(MemesProvider.NEW_MEMES_URI, values);



                Intent intent = new Intent(getApplicationContext(), MemeActivity.class);
                intent.putExtra("MEME_URI", newMemeUri);
                startActivity(intent);


            }
        });

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
