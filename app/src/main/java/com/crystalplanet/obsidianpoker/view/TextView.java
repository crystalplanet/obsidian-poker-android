package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.crystalplanet.obsidianpoker.app.R;

public class TextView extends ScaledView {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int boxWidth;

    private int textSize;

    private int align;

    private String text = "";

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextView, 0, 0);

        try {
            textSize = a.getInt(R.styleable.TextView_text_size, 20);
            boxWidth = a.getInt(R.styleable.TextView_box_width, 300);

            paint.setColor(a.getColor(R.styleable.TextView_text_color, 0));

            align = a.getInt(R.styleable.TextView_align, 1);

            paint.setTextAlign(
                align < 0 ? Paint.Align.RIGHT : (align > 0 ? Paint.Align.LEFT : Paint.Align.CENTER)
            );
        } finally {
            a.recycle();
        }

        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/gothic.ttf"));
    }

    public void setText(String text) {
        this.text = text;

        invalidate();
        requestLayout();
    }

    public String text() {
        return text;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

        paint.setTextSize(scale(textSize));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(text(), getTextPosition(), paint.getTextSize(), paint);
    }

    private int getTextPosition() {
        return align < 0 ? scale(boxWidth) : (align > 0 ? 0 : scale(boxWidth / 2));
    }
}
