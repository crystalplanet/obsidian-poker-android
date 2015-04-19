package com.crystalplanet.obsidianpoker.view.util;

import junit.framework.Assert;
import org.junit.Test;

public class ScaleTest {

    @Test
    public void testScaleSameProportions() {
        Scale lower = new Scale(1, 3, 2, 6);
        Scale higher = new Scale(16, 8, 4, 2);

        Assert.assertEquals((float)4.5, lower.scale(9));
        Assert.assertEquals((float)36, higher.scale(9));
    }

    @Test
    public void testScaleDifferentProportions() {
        Scale wide = new Scale(2, 2, 4, 2);
        Scale high = new Scale(6, 6, 3, 6);

        Assert.assertEquals((float)3.5, wide.scale(7));
        Assert.assertEquals((float)10, high.scale(10));
    }

    @Test
    public void testNegative() {
        Assert.assertEquals((float)-10, new Scale(10, 10, 1, 1).scale(-1));
    }
}
