package com.crystalplanet.obsidianpoker.view.xml;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.view.LayoutFactory;
import com.crystalplanet.obsidianpoker.view.Layout;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;
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

        Assert.assertEquals(viewPrefix + "PokerGameLayout", tl.name);
        Assert.assertEquals(viewPrefix + "PotLayout", ((TestLayout) tl.childById("pot")).name);
        Assert.assertEquals(viewPrefix + "CardsLayout", ((TestLayout) tl.childById("shared_cards")).name);

        for (Layout l : tl.childById("shared_cards").children())
            Assert.assertEquals(viewPrefix + "Card", ((TestLayout) l).name);

        for (Layout l : tl.childrenOfType("PlayerLayout"))
            Assert.assertEquals(viewPrefix + "PlayerLayout", ((TestLayout) l).name);
    }

    public void testNestedLayouts() {
        LayoutHandler handler = parse();

        Layout tl = handler.getLayout();

        Assert.assertEquals(4, tl.children().size());

        Assert.assertEquals(0, tl.childById("pot").children().size());

        Assert.assertEquals(2, tl.childById("shared_cards").children().size());

        for (Layout l : tl.childById("shared_cards").children())
            Assert.assertEquals(0, l.children().size());

        for (Layout l : tl.childrenOfType("PlayerLayout"))
            Assert.assertEquals(0, l.children().size());
    }

    public void testAttributes() {
        LayoutHandler handler = parse();

        TestLayout tl = (TestLayout)handler.getLayout();

        Assert.assertEquals("2000", tl.attr.get("width"));
        Assert.assertEquals("1000", tl.attr.get("height"));

        TestLayout pot = (TestLayout) tl.childById("pot");

        Assert.assertEquals("960", pot.attr.get("left"));
        Assert.assertEquals("350", pot.attr.get("top"));

        TestLayout card = (TestLayout) tl.childById("shared_cards").children().get(0);

        Assert.assertEquals("700", card.attr.get("left"));
        Assert.assertEquals("410", card.attr.get("top"));

        TestLayout player = (TestLayout) tl.childById("player_2");

        Assert.assertEquals("player_2", player.id());

        Assert.assertEquals("player_2", player.attr.get("id"));
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

    private String viewPrefix = "com.crystalplanet.obsidianpoker.view.layout.";

    private String config = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<PokerGameLayout width=\"2000\" height=\"1000\">\n" +
            "    <PotLayout id=\"pot\" left=\"960\" top=\"350\" />\n" +
            "    <CardsLayout id=\"shared_cards\">\n" +
            "        <Card left=\"700\" top=\"410\" />\n" +
            "        <Card left=\"800\" top=\"410\" />\n" +
            "    </CardsLayout>\n" +
            "    <PlayerLayout id=\"player_1\" width=\"120\" height=\"80\" left=\"1250\" top=\"40\" relative=\"true\">\n" +
            "    </PlayerLayout>\n" +
            "    <PlayerLayout id=\"player_2\" width=\"120\" height=\"120\" left=\"1574.81\" top=\"125.19\" relative=\"true\">\n" +
            "    </PlayerLayout>\n" +
            "</PokerGameLayout>";

    private class TestLayout extends Layout {

        public String name;

        public Map<String, String> attr;

        public TestLayout(String name, Map<String, String> attr, Layout parent) {
            super(null, attr, parent);

            this.name = name;
            this.attr = attr;
        }

        @Override
        public void draw(Canvas canvas, Offset offset, Scale scale) {

        }
    }

    private class TestLayoutFactory extends LayoutFactory {

        public TestLayoutFactory() {
            super(null);
        }

        @Override
        public Layout newLayout(String name, Map<String, String> attr, Layout parent) {
            TestLayout layout = new TestLayout(name, attr, parent);
            if (parent != null) parent.addChild(layout);
            return layout;
        }
    }
}
