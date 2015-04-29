package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutTest extends TestCase {

    public void testChildrenOfType() {
        Layout layout = new TestLayout(null, null);

        layout.addChild(new TestLayout(idAttr("1"), layout));
        layout.addChild(new TestLayout(idAttr("2"), layout));
        layout.addChild(new OtherTestLayout(idAttr("3"), layout));
        layout.addChild(new TestLayout(idAttr("4"), layout));
        layout.addChild(new OtherTestLayout(idAttr("5"), layout));

        List<Layout> testLayouts = layout.childrenOfType("TestLayout");
        List<Layout> otherTestLayouts = layout.childrenOfType("OtherTestLayout");
        List<Layout> empty = layout.childrenOfType("NonExistantClass");

        Assert.assertEquals(3, testLayouts.size());
        Assert.assertEquals(2, otherTestLayouts.size());
        Assert.assertEquals(0, empty.size());
    }

    public void testChildById() {
        Layout layout = new TestLayout(null, null);

        Layout test = new TestLayout(idAttr("test"), null);

        layout.addChild(new TestLayout(idAttr("1"), null));
        layout.addChild(new TestLayout(idAttr("2"), null));
        layout.addChild(new OtherTestLayout(idAttr("3"), null));
        layout.addChild(test);
        layout.addChild(new TestLayout(idAttr("4"), null));
        layout.addChild(new OtherTestLayout(idAttr("5"), null));

        Assert.assertEquals(test, layout.childById("test"));
        Assert.assertEquals(null, layout.childById("nonexistant"));
    }

    public void testPosition() {
        Assert.assertEquals(new Float(0), new TestLayout(null, null).position().first);
        Assert.assertEquals(new Float(0), new TestLayout(null, null).position().second);

        Map<String, String> attr = new HashMap<String, String>();

        Assert.assertEquals(new Float(0), new TestLayout(attr, null).position().first);
        Assert.assertEquals(new Float(0), new TestLayout(attr, null).position().second);

        attr.put("left", "120");
        attr.put("top", "90");

        Assert.assertEquals(new Float(120), new TestLayout(attr, null).position().first);
        Assert.assertEquals(new Float(90), new TestLayout(attr, null).position().second);
    }

    public void testIsRelative() {
        Map<String, String> attr = new HashMap<String, String>();

        Assert.assertTrue(new TestLayout(null, null).isRelative());

        TestLayout parent = new TestLayout(null, null);

        Assert.assertFalse(new TestLayout(null, parent).isRelative());
        Assert.assertFalse(new TestLayout(attr, parent).isRelative());

        attr.put("relative", "false");

        Assert.assertFalse(new TestLayout(attr, parent).isRelative());

        attr.put("relative", "true");
        attr.put("left", "80");
        attr.put("top", "120");

        TestLayout relative = new TestLayout(attr, parent);

        Assert.assertTrue(relative.isRelative());

        TestLayout child = new TestLayout(null, relative);

        relative.addChild(child);

        relative.draw(null, new Offset(0, 0), new Scale(1, 1, 1, 1));

        Assert.assertEquals(0, relative.offset.offsetLeft(0));
        Assert.assertEquals(0, relative.offset.offsetTop(0));

        Assert.assertEquals(80, child.offset.offsetLeft(0));
        Assert.assertEquals(120, child.offset.offsetTop(0));
    }

    private Map<String, String> idAttr(String id) {
        Map<String, String> attr = new HashMap<String, String>();
        attr.put("id", id);
        return attr;
    }

    private class TestLayout extends Layout {

        public Offset offset;

        public TestLayout(Map<String, String> attr, Layout parent) {
            super(attr, parent);
        }

        @Override
        public void draw(Canvas canvas, Offset offset, Scale scale) {
            this.offset = offset;
            super.draw(canvas, offset, scale);
        }
    }

    private class OtherTestLayout extends TestLayout {

        public OtherTestLayout(Map<String, String> attr, Layout parent) {
            super(attr, parent);
        }
    }
}
