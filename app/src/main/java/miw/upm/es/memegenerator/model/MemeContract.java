package miw.upm.es.memegenerator.model;

import android.provider.BaseColumns;

/**
 * Created by Enrique on 10/11/2016.
 */

public class MemeContract {

    private MemeContract() {}

    public static class MemeTable implements BaseColumns
    {
        public final static String TABLE_NAME = "MEMES";

        public final static String COL_NAME_ID = _ID;
        public final static String COL_NAME_BOTTOM_TEXT = "BOTTOM_TEXT";
        public final static String COL_NAME_TOP_TEXT = "TOP_TEXT";
        public final static String COL_NAME_FONT = "FONT";
        public final static String COL_NAME_FONT_SIZE = "FONT_SIZE";
        public final static String COL_NAME_MEME = "BASE_IMG";
        public final static String COL_NAME_MEME_IMG = "MEME_IMG";

        public final static String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_NAME_BOTTOM_TEXT + " TEXT, " +
            COL_NAME_TOP_TEXT + " TEXT, " +
            COL_NAME_FONT + " TEXT, " +
            COL_NAME_FONT_SIZE + " INTEGER, " +
            COL_NAME_MEME + " TEXT, " +
            COL_NAME_MEME_IMG + " BLOB " +
                ");";

        public final static String DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    }
}
