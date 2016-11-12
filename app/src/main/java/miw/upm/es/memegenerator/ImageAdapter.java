package miw.upm.es.memegenerator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import miw.upm.es.memegenerator.model.Image;
import miw.upm.es.memegenerator.model.ImageContract;

/**
 * Created by Enrique on 10/11/2016.
 */

public class ImageAdapter extends CursorAdapter {

    private Context context;
    private int resourceId;

    public ImageAdapter(Context context, int resource, Cursor cursor) {
        super(context, cursor, 0);

        this.context = context;
        this.resourceId = resource;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(resourceId, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvImageName = (TextView) view.findViewById(R.id.imageName);
        ImageView ivImageSrc = (ImageView) view.findViewById(R.id.poster);

        // Extract properties from cursor
        String imageName = cursor.getString(cursor.getColumnIndexOrThrow(ImageContract.ImageTable.COL_NAME_IMAGE_NAME));
        Uri imageSrc = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(ImageContract.ImageTable.COL_NAME_IMAGE_URI)));

        // Populate fields with extracted properties
        tvImageName.setText(imageName);
        if (null != imageSrc) {
            Picasso.with(context)
                    .load(imageSrc)
                    .into(ivImageSrc);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvImageName = (TextView) view.findViewById(R.id.imageName);
                ImageView ivImageSrc = (ImageView) view.findViewById(R.id.poster);

                Intent intent = new Intent(context, MemeCreatorActivity.class);
                intent.putExtra("IMAGEN", tvImageName.getText().toString());
                Bitmap bitmap = ((BitmapDrawable)ivImageSrc.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
                intent.putExtra("IMAGEN_URL", outputStream.toByteArray());
                context.startActivity(intent);
            }
        });

    }


}
