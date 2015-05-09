package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.crystalplanet.obsidianpoker.util.Offset;
import com.crystalplanet.obsidianpoker.util.Scale;

public class CenteredScaledLayout extends ScaledLayout {

    private Offset offset;

    public CenteredScaledLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getOffsetLeft() {
        return offset.offsetLeft(0);
    }

    @Override
    public int getOffsetTop() {
        return offset.offsetTop(0);
    }

    @Override
    public void setScale(Scale scale) {
        super.setScale(scale);

        View parent = (View) getParent();

        offset = Offset.createCenteredOffset(
            parent.getWidth(),
            parent.getHeight(),
            scaledWidth(),
            scaledHeight()
        );
    }

    @Override
    protected void setChildPosition(Scalable child, int l, int t, int r, int b) {
        child.layout(
                scale(child.getOffsetLeft()) - (isRelative() ? 0 : l),
                scale(child.getOffsetTop()) - (isRelative() ? 0 : t),
                scale(child.getOffsetLeft() + child.scaledWidth()) - (isRelative() ? 0 : l),
                scale(child.getOffsetTop() + child.scaledHeight()) - (isRelative() ? 0 : t)
        );
    }
}
