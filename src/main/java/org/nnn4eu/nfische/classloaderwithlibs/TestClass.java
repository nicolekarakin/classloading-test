package org.nnn4eu.nfische.classloaderwithlibs;

import java.util.Random;
import java.util.Vector;
public class TestClass implements LocalModule {
    /*
     * This is an example of a class reference that will be resolved
     * at load time.
     */
    Vector v = new Vector();
    /** This is our start function */
    public void start(String opt) {
        /* This reference will be resolved at run time. */
        Random r;
        System.out.println("Running the Test class, option was                   '"+opt+"'");
        System.out.println("Now initializing a Random object.");
        r = new Random();
        for (int i = 0; i < 5; i++) {
            v.addElement(new Integer(r.nextInt()));
        }
        /* This reference should get the cached copy of random. */
        r = new Random();
        System.out.println("A series of 5 random numbers: ");
        for (int i = 0; i < v.size(); i++) {
            Integer z = (Integer)v.elementAt(i);
            System.out.println(i+": "+z);
        }
    }
}