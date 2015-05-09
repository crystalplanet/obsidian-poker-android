package com.crystalplanet.obsidianpoker.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class OffsetTest extends TestCase {

    public void testOffset() {
        Offset offset = new Offset(1, 10);

        Assert.assertEquals(1, offset.offsetLeft(0));
        Assert.assertEquals(10, offset.offsetTop(0));

        Assert.assertEquals(0, offset.offsetLeft(-1));
        Assert.assertEquals(0, offset.offsetTop(-10));

        Assert.assertEquals(20, offset.offsetLeft(19));
        Assert.assertEquals(20, offset.offsetTop(10));
    }

    public void testCreateCenteredOffset() {
        Offset o2 = Offset.createCenteredOffset(400, 200, 400, 100);

        Assert.assertEquals(0, o2.offsetLeft(0));
        Assert.assertEquals(50, o2.offsetTop(0));

        Offset o3 = Offset.createCenteredOffset(200, 400, 100, 400);

        Assert.assertEquals(50, o3.offsetLeft(0));
        Assert.assertEquals(0, o3.offsetTop(0));

        Offset o4 = Offset.createCenteredOffset(800, 300, 400, 200);

        Assert.assertEquals(66, o4.offsetLeft(0));
        Assert.assertEquals(0, o4.offsetTop(0));

        Offset o5 = Offset.createCenteredOffset(800, 300, 400, 400);

        Assert.assertEquals(333, o5.offsetLeft(0));
        Assert.assertEquals(0, o5.offsetTop(0));

        Offset o6 = Offset.createCenteredOffset(900, 400, 600, 100);

        Assert.assertEquals(0, o6.offsetLeft(0));
        Assert.assertEquals(83, o6.offsetTop(0));
    }
}
