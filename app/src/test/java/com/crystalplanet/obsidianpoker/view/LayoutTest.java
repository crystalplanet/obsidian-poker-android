package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

public class LayoutTest extends TestCase {

    public void testChildrenByType() {
        Layout layout = new TestLayout();

        layout.addChild(new TestDrawable("1"));
        layout.addChild(new TestDrawable("2"));
        layout.addChild(new OtherTestDrawable("3"));
        layout.addChild(new TestDrawable("4"));
        layout.addChild(new OtherTestDrawable("5"));

        List<Drawable> testDrawables = layout.children("TestDrawable");
        List<Drawable> otherTestDrawables = layout.children("OtherTestDrawable");
        List<Drawable> empty = layout.children("NonExistantClass");

        Assert.assertEquals(3, testDrawables.size());
        Assert.assertEquals(2, otherTestDrawables.size());
        Assert.assertEquals(0, empty.size());
    }

    private class TestDrawable extends Drawable {

        private String id;

        public TestDrawable(String id) {
            super(null, null);
        }

        public String id() {
            return id;
        }

        @Override
        public void onDraw(Canvas canvas) {

        }
    }

    private class OtherTestDrawable extends TestDrawable {

        public OtherTestDrawable(String id) {
            super(id);
        }
    }

    private class TestLayout extends Layout {

        public TestLayout() {
            super(null, null);
        }

        @Override
        public void onDraw(Canvas canvas) {

        }
    }
}
