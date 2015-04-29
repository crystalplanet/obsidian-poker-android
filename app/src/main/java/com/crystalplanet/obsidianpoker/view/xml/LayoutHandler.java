package com.crystalplanet.obsidianpoker.view.xml;

import com.crystalplanet.obsidianpoker.view.Layout;
import com.crystalplanet.obsidianpoker.view.LayoutFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class LayoutHandler extends DefaultHandler {

    private LayoutFactory layoutFactory;

    private Layout current;

    public LayoutHandler(LayoutFactory layoutFactory) {
        this.layoutFactory = layoutFactory;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            current = layoutFactory.newLayout(layoutClass(qName), attributesMap(attributes), current);
        } catch (Exception e) {
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        current = current.parent() == null ? current : current.parent();
    }

    public Layout getLayout() {
        return current;
    }

    private Map<String, String> attributesMap(Attributes attributes) {
        HashMap<String, String> map = new HashMap<String, String>();

        for (int i=0; i<attributes.getLength(); ++i)
            map.put(attributes.getQName(i), attributes.getValue(i));

        return map;
    }

    private String layoutClass(String layout) {
        return "com.crystalplanet.obsidianpoker.view.layout." + layout;
    }
}
