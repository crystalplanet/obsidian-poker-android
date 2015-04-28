package com.crystalplanet.obsidianpoker.view;

import com.crystalplanet.obsidianpoker.mock.TestLayout;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class LayoutFactoryTest extends TestCase {

    public void testNewLayout() throws Exception {
        LayoutFactory factory = new LayoutFactory();

        Layout parent = new TestLayout(null, null);

        Map<String, String> attr = new HashMap<String, String>();

        Layout layout = factory.newLayout(
            "com.crystalplanet.obsidianpoker.mock.TestLayout",
            attr,
            parent
        );

        Assert.assertTrue(layout instanceof TestLayout);

        TestLayout testLayout = (TestLayout) layout;

        Assert.assertEquals(attr, testLayout.attr);
        Assert.assertEquals(parent, testLayout.parent());

        Assert.assertTrue(parent.children().contains(layout));
    }
}
