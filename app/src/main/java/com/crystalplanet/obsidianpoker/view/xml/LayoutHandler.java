package com.crystalplanet.obsidianpoker.view.xml;

import com.crystalplanet.obsidianpoker.view.Drawable;
import com.crystalplanet.obsidianpoker.view.DrawableFactory;
import com.crystalplanet.obsidianpoker.view.Layout;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class LayoutHandler extends DefaultHandler {

    private DrawableFactory drawableFactory;

    private Layout current;

    public LayoutHandler(DrawableFactory drawableFactory) {
        this.drawableFactory = drawableFactory;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Drawable drawable = drawableFactory.newDrawable(drawableClass(qName), attributesMap(attributes), current);

        if (isLayout(qName)) current = (Layout)drawable;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isLayout(qName)) current = current.parent() == null ? current : current.parent();
    }

    public Layout getLayout() {
        while (current.parent() != null) current = current.parent();

        return current;
    }

    private boolean isLayout(String qName) {
        return qName.endsWith("Layout");
    }

    private Map<String, String> attributesMap(Attributes attributes) {
        HashMap<String, String> map = new HashMap<String, String>();

        for (int i=0; i<attributes.getLength(); ++i)
            map.put(attributes.getQName(i), attributes.getValue(i));

        return map;
    }

    private String drawableClass(String drawable) {
        return "com.crystalplanet.obsidianpoker.view." + (isLayout(drawable) ? "layout" : "graphics") + "." + drawable;
    }
}
