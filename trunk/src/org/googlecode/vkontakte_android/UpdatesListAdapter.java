package org.googlecode.vkontakte_android;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import org.googlecode.vkontakte_android.database.StatusDao;

import java.text.SimpleDateFormat;



public class UpdatesListAdapter extends ResourceCursorAdapter {
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm ");//todo: get rid of extra space by using padding(?)
    public static final SimpleDateFormat weektimeFormat= new SimpleDateFormat("EEE, HH:mm ");

    public UpdatesListAdapter(Context context, int layout, Cursor cursor) {
        super(context, layout, cursor);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        StatusDao status = new StatusDao(cursor);
        TextView nameLine = (TextView) view.findViewById(R.id.name);
        nameLine.setText(status.getUserName());
        TextView statusLine = (TextView) view.findViewById(R.id.status);
        statusLine.setText(Html.fromHtml(status.getText()));
        TextView timeLine = (TextView) view.findViewById(R.id.time);
        timeLine.setText(weektimeFormat.format(status.getDate()));
        Bitmap bm = UserHelper.getPhotoByUserId(context, status.getUserId());
        if (bm != null &&  view.findViewById(R.id.photo)!=null ) {
        	ImageView photo = (ImageView) view.findViewById(R.id.photo);
            int maxSize = 90;//todo!
            int srcWidth = bm.getWidth();
            int srcHeight = bm.getHeight();
            int dstWidth = maxSize;
            int dstHeight = srcHeight * maxSize / srcWidth;
            //Bitmap scaledBitmap = Bitmap.createScaledBitmap(bm,dstWidth,dstHeight,true);
            Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, Math.min(maxSize, dstWidth), Math.min(maxSize, dstHeight));
            photo.setImageBitmap(croppedBitmap);
        }
    }
}