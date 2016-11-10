package miw.upm.es.memegenerator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import miw.upm.es.memegenerator.model.MemeContract.MemeTable;
import miw.upm.es.memegenerator.model.FontContract.FontTable;
import miw.upm.es.memegenerator.model.ImageContract.ImageTable;

/**
 * Created by Enrique on 10/11/2016.
 */

public class MemesManager extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = MemeTable.TABLE_NAME + ".db";

    private final static int VERSION = 2;

    public MemesManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MemeTable.CREATE_SQL);
        sqLiteDatabase.execSQL(ImageTable.CREATE_SQL);
        sqLiteDatabase.execSQL(FontTable.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MemeTable.DROP_SQL);
        sqLiteDatabase.execSQL(ImageTable.DROP_SQL);
        sqLiteDatabase.execSQL(FontTable.DROP_SQL);

        sqLiteDatabase.execSQL(MemeTable.CREATE_SQL);
        sqLiteDatabase.execSQL(ImageTable.CREATE_SQL);
        sqLiteDatabase.execSQL(FontTable.CREATE_SQL);

    }

    public List<Font> getFonts(){
        List<Font> fontsList = new ArrayList<Font>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor rows = db.rawQuery("SELECT *  FROM "+ FontTable.TABLE_NAME, null);
        if(rows.moveToFirst()){
            while(!rows.isAfterLast()){
                fontsList.add(new Font(
                        rows.getColumnIndex(FontTable.COL_NAME_ID),
                        rows.getString(rows.getColumnIndex(FontTable.COL_NAME_FONT_NAME))
                ));

                rows.moveToNext();
            }
        }

        db.close();

        return fontsList;
    }

    public void addFont(Font font){
        ContentValues values = new ContentValues();
        values.put(FontTable.COL_NAME_FONT_NAME, font.getName());

        font.setId(getWritableDatabase().insert(ImageTable.TABLE_NAME, null, values));
    }

    public boolean hasFont(Font font){
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, FontTable.TABLE_NAME, FontTable.COL_NAME_FONT_NAME + "= ?", new String[]{font.getName()});
        db.close();
        return cnt > 0;
    }


    public List<Image> getImages(){
        List<Image> imagesList = new ArrayList<Image>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor rows = db.rawQuery("SELECT *  FROM "+ ImageTable.TABLE_NAME, null);
        if(rows.moveToFirst()){
            while(!rows.isAfterLast()){
                imagesList.add(new Image(
                        rows.getColumnIndex(ImageTable.COL_NAME_ID),
                        rows.getString(rows.getColumnIndex(ImageTable.COL_NAME_IMAGE_NAME)),
                        Uri.parse(rows.getString(rows.getColumnIndex(ImageTable.COL_NAME_IMAGE_URI)))
                ));

                rows.moveToNext();
            }
        }

        db.close();

        return imagesList;
    }

    public void addImage(Image img){
        ContentValues values = new ContentValues();
        values.put(ImageTable.COL_NAME_IMAGE_NAME, img.getName());
        values.put(ImageTable.COL_NAME_IMAGE_URI, String.valueOf(img.getImage()));

        img.setId(getWritableDatabase().insert(ImageTable.TABLE_NAME, null, values));
    }

    public boolean hasImage(Image img){
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, ImageTable.TABLE_NAME, ImageTable.COL_NAME_IMAGE_NAME + "= ?", new String[]{img.getName()});
        db.close();
        return cnt > 0;
    }

    public List<Meme> getMemes(){
        List<Meme> memesList = new ArrayList<Meme>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor rows = db.rawQuery("SELECT *  FROM "+ MemeTable.TABLE_NAME, null);
        if(rows.moveToFirst()){
            while(!rows.isAfterLast()){
                memesList.add(new Meme(
                        rows.getColumnIndex(MemeTable.COL_NAME_ID),
                        rows.getString(rows.getColumnIndex(MemeTable.COL_NAME_BOTTOM_TEXT)),
                        rows.getString(rows.getColumnIndex(MemeTable.COL_NAME_TOP_TEXT)),
                        rows.getString(rows.getColumnIndex(MemeTable.COL_NAME_FONT)),
                        rows.getColumnIndex(MemeTable.COL_NAME_FONT_SIZE),
                        rows.getString(rows.getColumnIndex(MemeTable.COL_NAME_MEME)),
                        getBitmapImage(rows.getBlob(rows.getColumnIndex(MemeTable.COL_NAME_MEME_IMG)))
                        ));

                rows.moveToNext();
            }
        }

        db.close();

        return memesList;
    }

    public void addMeme(Meme meme){
        ContentValues values = new ContentValues();
        values.put(MemeTable.COL_NAME_BOTTOM_TEXT, meme.getBottomText());
        values.put(MemeTable.COL_NAME_TOP_TEXT, meme.getTopText());
        values.put(MemeTable.COL_NAME_FONT, meme.getFont());
        values.put(MemeTable.COL_NAME_FONT_SIZE, meme.getFontSize());
        values.put(MemeTable.COL_NAME_MEME, meme.getBaseImage());
        if(meme.getImage() != null)
            values.put(MemeTable.COL_NAME_MEME_IMG, getBitmapAsByteArray(meme.getImage()));

        meme.setId(getWritableDatabase().insert(ImageTable.TABLE_NAME, null, values));
    }


    private Bitmap getBitmapImage( byte[] imgByte) {
        return null;
        //return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        return null;
        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();*/
    }


    public Cursor getMemesAsCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(sortOrder == null)
            sortOrder = MemeTable.COL_NAME_ID + " ASC";

        return getReadableDatabase().query(MemeTable.TABLE_NAME,projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor getImagesAsCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(sortOrder == null)
            sortOrder = ImageTable.COL_NAME_ID + " ASC";

        return getReadableDatabase().query(ImageTable.TABLE_NAME,projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor getFontsAsCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(sortOrder == null)
            sortOrder = FontTable.COL_NAME_ID + " ASC";

        return getReadableDatabase().query(FontTable.TABLE_NAME,projection, selection, selectionArgs, null, null, sortOrder);
    }
}
