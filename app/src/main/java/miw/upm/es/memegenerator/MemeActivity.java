package miw.upm.es.memegenerator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Enrique on 12/11/2016.
 */
public class MemeActivity extends Activity{

    Uri memeUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_creator);

        Bundle extras = getIntent().getExtras();
        this.memeUri = Uri.parse(extras.getString("MEME_URI"));
    }
}
