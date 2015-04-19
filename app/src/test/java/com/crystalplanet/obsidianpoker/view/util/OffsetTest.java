package com.crystalplanet.obsidianpoker.view.util;

import junit.framework.Assert;
import org.junit.Test;

public class OffsetTest {

    @Test
    public void testOffsetSameProportions() {
        Offset offset = new Offset(2, 2, 4, 4);

        Assert.assertEquals(0, offset.offsetTop(0));
        Assert.assertEquals(0, offset.offsetLeft(0));

        Assert.assertEquals(2, offset.offsetTop(2));
        Assert.assertEquals(-10, offset.offsetLeft(-10));
    }

    @Test
    public void testOffsetLeft() {
        Offset offset = new Offset(8, 8, 4, 8);

        Assert.assertEquals(0, offset.offsetTop(0));
        Assert.assertEquals(2, offset.offsetLeft(0));
    }

    @Test
    public void testOffsetTop() {
        Offset offset = new Offset(8, 8, 8, 4);

        Assert.assertEquals(2, offset.offsetTop(0));
        Assert.assertEquals(0, offset.offsetLeft(0));
    }
}
