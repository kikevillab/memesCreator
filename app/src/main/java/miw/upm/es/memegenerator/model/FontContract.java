package miw.upm.es.memegenerator.model;

import android.provider.BaseColumns;

/**
 * Created by Enrique on 10/11/2016.
 */

public class FontContract {

    private FontContract() {}

    public static class FontTable implements BaseColumns
    {
        public final static String TABLE_NAME = "FONTS";

        public final static String COL_NAME_ID = _ID;
        public final static String COL_NAME_FONT_NAME = "NAME";

        public final static String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " +
                COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_NAME_FONT_NAME + " TEXT " +
                ");";

        public final static String DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
