package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import com.crystalplanet.obsidianpoker.app.R;

public class ImageView extends ScaledView {

    private Paint AA = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Matrix matrix;

    private int resource;

    private Bitmap image;

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageView, 0, 0);

        try {
            resource = a.getResourceId(R.styleable.ImageView_src, 0);
        } finally {
            a.recycle();
        }

        AA.setDither(true);
        AA.setFilterBitmap(true);

//        matrix = getRotationMatrix(image, getRotation());
    }

    @Override
    public void onDraw(Canvas canvas) {
        image = getBitmap(resource);
        matrix = getRotationMatrix(image, getRotation());
        canvas.drawBitmap(image, matrix, AA);
    }

    private Bitmap getBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);

        return Bitmap.createScaledBitmap(
            bitmap,
            scale(scaledWidth() > 0 ? scaledWidth() : bitmap.getWidth()),
            scale(scaledHeight() > 0 ? scaledHeight() : bitmap.getHeight()),
            true
        );
    }

    private Matrix getRotationMatrix(Bitmap bitmap, float rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation, bitmap.getWidth()/2, bitmap.getHeight()/2);
        matrix.postTranslate(0, 0);

        return matrix;
    }
}
