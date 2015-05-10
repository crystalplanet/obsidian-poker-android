package com.crystalplanet.obsidianpoker.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ScaleTest extends TestCase {

    public void testScaleSameProportions() {
        Scale lower = new Scale(1, 3, 2, 6);
        Scale higher = new Scale(16, 8, 4, 2);

        Assert.assertEquals(5, lower.scale(9));
        Assert.assertEquals(36, higher.scale(9));
    }

    public void testScaleDifferentProportions() {
        Scale wide = new Scale(2, 2, 4, 2);
        Scale high = new Scale(6, 6, 3, 6);

        Assert.assertEquals(4, wide.scale(7));
        Assert.assertEquals(10, high.scale(10));
    }

    public void testNegative() {
        Assert.assertEquals(-10, new Scale(10, 10, 1, 1).scale(-1));
    }

    public void testInvert() {
        Scale s1 = new Scale(2, 4, 3, 2);

        Assert.assertEquals(100, s1.scale(s1.invert().scale(100)));
    }
}
