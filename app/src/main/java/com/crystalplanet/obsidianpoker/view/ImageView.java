package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import com.crystalplanet.obsidianpoker.app.R;

public class ImageView extends ScaledView {

    protected Paint AA = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap image;

    private Matrix matrix;

    private int drawable;

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageView, 0, 0);

        try {
            drawable = a.getResourceId(R.styleable.ImageView_src, 0);
        } finally {
            a.recycle();
        }

        AA.setDither(true);
        AA.setFilterBitmap(true);
    }

    public void setImage(int drawable) {
        this.drawable = drawable;

        invalidate();
        requestLayout();
    }

    public int getImage() {
        return drawable;
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        image = getBitmap(getImage());
        matrix = getRotationMatrix(image.getWidth(), image.getHeight(), getRotation());
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(image, matrix, AA);
    }

    protected Bitmap getBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);

        return Bitmap.createScaledBitmap(
            bitmap,
            scale(baseWidth() > 0 ? baseWidth() : bitmap.getWidth()),
            scale(baseHeight() > 0 ? baseHeight() : bitmap.getHeight()),
            true
        );
    }

    protected Matrix getRotationMatrix(int width, int height, float rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation, width/2, height/2);
        matrix.postTranslate((scale(scaledWidth()) - width)/2, (scale(scaledHeight()) - height)/2);

        return matrix;
    }
}
