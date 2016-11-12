package miw.upm.es.memegenerator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tarea.cancel(true);
    }

    private class TareaCargarImagen extends AsyncTask<ImageView, Void, Bitmap> {

        private long horaInicio;

        private ImageView imageView = null;

        @Override
        protected void onPreExecute() {
            horaInicio = SystemClock.elapsedRealtime();
            crono.setBase(horaInicio);
            crono.start();
        }

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            Bitmap bmp = null;
            HttpURLConnection con = null;
            try {
                Thread.sleep(5000);
                @SuppressWarnings("ResourceType")
                URL mUrl = new URL(imageView.getTag().toString());
                Log.i(LOG_TAG, "URL=" + mUrl.toString());
                con = (HttpURLConnection) mUrl.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                is.close();
                Log.i(LOG_TAG, getString(R.string.txtImagenRecibida));
            } catch (Exception e) {
                Log.e("ERROR", getResources().getString(R.string.errorLoading) + e.getMessage());
            } finally {
                if (con != null) con.disconnect();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            crono.stop();
            long horaFin = SystemClock.elapsedRealtime();
            Log.i(LOG_TAG, String.format("Tiempo = %.1f s.",  ((horaFin - horaInicio) / 1000.0)));
            imageView.setImageBitmap(result);
            botonCancelar.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            crono.stop();
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.tareaCancelada),
                    Toast.LENGTH_LONG
            ).show();
            Log.i(LOG_TAG, getString(R.string.tareaCancelada));
        }
    } // TareaCargarImagen
}
