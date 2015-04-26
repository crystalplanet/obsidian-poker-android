package com.crystalplanet.obsidianpoker.view.xml;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.view.DrawableFactory;
import com.crystalplanet.obsidianpoker.view.Layout;
import com.crystalplanet.obsidianpoker.view.Drawable;
import junit.framework.Assert;
import junit.framework.TestCase;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class LayoutHandlerTest extends TestCase {

    public void testTagClassNames() {
        LayoutHandler handler = parse();

        Layout layout = handler.getLayout();

        Assert.assertTrue(layout instanceof TestLayout);

        TestLayout tl = (TestLayout)layout;

        Assert.assertEquals(viewPrefix + "layout.PokerGameLayout", tl.name);
        Assert.assertEquals(viewPrefix + "layout.PotLayout", ((TestLayout) tl.children.get(0)).name);
        Assert.assertEquals(viewPrefix + "layout.CardsLayout", ((TestLayout) tl.children.get(1)).name);
        Assert.assertEquals(viewPrefix + "graphics.Card", ((TestLayout) ((TestLayout) tl.children.get(1)).children().get(0)).name);
        Assert.assertEquals(viewPrefix + "graphics.Card", ((TestLayout) ((TestLayout) tl.children.get(1)).children().get(1)).name);
        Assert.assertEquals(viewPrefix + "layout.PlayerLayout", ((TestLayout) tl.children.get(2)).name);
        Assert.assertEquals(viewPrefix + "layout.PlayerLayout", ((TestLayout) tl.children.get(3)).name);
    }

    public void testNestedLayouts() {
        LayoutHandler handler = parse();

        TestLayout tl = (TestLayout)handler.getLayout();

        Assert.assertTrue(tl.isLayout());

        Assert.assertEquals(4, tl.children().size());

        Assert.assertTrue(((TestLayout) tl.children.get(0)).isLayout());
        Assert.assertEquals(0, ((Layout) tl.children().get(0)).children().size());

        Assert.assertTrue(((TestLayout) tl.children.get(1)).isLayout());
        Assert.assertEquals(2, ((Layout) tl.children().get(1)).children().size());

        Assert.assertFalse(((TestLayout) ((TestLayout) tl.children.get(1)).children().get(0)).isLayout());

        Assert.assertFalse(((TestLayout) ((TestLayout) tl.children.get(1)).children().get(1)).isLayout());

        Assert.assertTrue(((TestLayout) tl.children.get(2)).isLayout());
        Assert.assertEquals(0, ((Layout) tl.children().get(2)).children().size());

        Assert.assertTrue(((TestLayout) tl.children.get(3)).isLayout());
        Assert.assertEquals(0, ((Layout) tl.children().get(3)).children().size());
    }

    public void testAttributes() {
        LayoutHandler handler = parse();

        TestLayout tl = (TestLayout)handler.getLayout();

        Assert.assertEquals("2000", tl.attr.get("width"));
        Assert.assertEquals("1000", tl.attr.get("height"));

        TestLayout pot = (TestLayout) tl.children().get(0);

        Assert.assertEquals("960", pot.attr.get("left"));
        Assert.assertEquals("350", pot.attr.get("top"));

        TestLayout card = (TestLayout)((TestLayout) tl.children.get(1)).children().get(0);

        Assert.assertEquals("700", card.attr.get("left"));
        Assert.assertEquals("410", card.attr.get("top"));

        TestLayout player = (TestLayout) tl.children.get(3);

        Assert.assertEquals("120", player.attr.get("width"));
        Assert.assertEquals("120", player.attr.get("height"));
        Assert.assertEquals("1574.81", player.attr.get("left"));
        Assert.assertEquals("125.19", player.attr.get("top"));
        Assert.assertEquals("true", player.attr.get("relative"));
    }

    private LayoutHandler parse() {
        TestLayoutFactory layoutFactory = new TestLayoutFactory();
        LayoutHandler handler = new LayoutHandler(layoutFactory);

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(getInput(), handler);
        } catch (Exception e) {

        }

        return handler;
    }

    private InputStream getInput() {
        return new ByteArrayInputStream(config.getBytes());
    }

    private String viewPrefix = "com.crystalplanet.obsidianpoker.view.";

    private String config = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<PokerGameLayout width=\"2000\" height=\"1000\">\n" +
            "    <PotLayout left=\"960\" top=\"350\" />\n" +
            "    <CardsLayout>\n" +
            "        <Card left=\"700\" top=\"410\" />\n" +
            "        <Card left=\"800\" top=\"410\" />\n" +
            "    </CardsLayout>\n" +
            "    <PlayerLayout width=\"120\" height=\"80\" left=\"1250\" top=\"40\" relative=\"true\">\n" +
            "    </PlayerLayout>\n" +
            "    <PlayerLayout width=\"120\" height=\"120\" left=\"1574.81\" top=\"125.19\" relative=\"true\">\n" +
            "    </PlayerLayout>\n" +
            "</PokerGameLayout>";

    private class TestLayout implements Layout {

        public String name;

        public Map<String, String> attr;

        private Layout parent;

        private List<Drawable> children = new ArrayList<Drawable>();

        public TestLayout(String name, Map<String, String> attr, Layout parent) {
            this.name = name;
            this.attr = attr;
            this.parent = parent;
        }

        public boolean isLayout() {
            return name.endsWith("Layout");
        }

        @Override
        public Layout parent() {
            return parent;
        }

        @Override
        public void onDraw(Canvas canvas) {

        }

        @Override
        public void addChild(Drawable drawable) {
            children.add(drawable);
        }

        @Override
        public List<Drawable> children() {
            return children;
        }
    }

    private class TestLayoutFactory extends DrawableFactory {
        @Override
        public Drawable newDrawable(String name, Map<String, String> attr, Layout parent) {
            TestLayout layout = new TestLayout(name, attr, parent);
            if (parent != null) parent.addChild(layout);
            return layout;
        }
    }
}
