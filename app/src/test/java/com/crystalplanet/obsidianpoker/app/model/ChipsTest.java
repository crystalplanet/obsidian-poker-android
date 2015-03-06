package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ChipsTest extends TestCase {

    public void testEquals() {
        Assert.assertTrue(new Chips(100).equals(new Chips(100)));
        Assert.assertFalse(new Chips(0).equals(new Chips(1)));
        Assert.assertFalse(new Chips(1).equals(null));
        Assert.assertFalse(new Chips(1).equals(new Object()));
    }

    public void testCompare() {
        Assert.assertTrue(new Chips(100).compareTo(new Chips(80)) > 0);
        Assert.assertTrue(new Chips(20).compareTo(new Chips(20)) == 0);
        Assert.assertTrue(new Chips(98).compareTo(new Chips(101)) < 0);
    }

    public void testAddSubstract() {
        Assert.assertTrue(new Chips(100).add(new Chips(300)).equals(new Chips(400)));
        Assert.assertTrue(new Chips(100).add(new Chips(-50)).equals(new Chips(50)));
        Assert.assertTrue(new Chips(100).substract(new Chips(10)).equals(new Chips(90)));
    }

    public void testToString() {
        Assert.assertTrue(new Chips(100).toString().equals("$100"));
        Assert.assertTrue(new Chips(0).toString().equals("$0"));
        Assert.assertTrue(new Chips(-1).toString().equals("- $1"));
        Assert.assertTrue(new Chips(-30).toString().equals("- $30"));
    }
}
