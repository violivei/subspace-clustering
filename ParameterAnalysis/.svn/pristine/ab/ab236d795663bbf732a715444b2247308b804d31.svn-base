/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author hans
 */
public class ReadStream implements Runnable {
    InputStream is;
    Thread thread; 
    boolean print;
    
    public ReadStream(InputStream is, boolean print) {
        this.is = is;
        this.print = print;
    }       
    
    public void start () {
        thread = new Thread (this);
        thread.start ();
    }       
    
    public void run () {
        try {
            InputStreamReader isr = new InputStreamReader (is);
            BufferedReader br = new BufferedReader (isr);   
            while (true) {
                String s = br.readLine ();
                if (s == null) break;
                if (print) System.out.println (s);
            }
            is.close ();    
        } catch (Exception ex) {
            System.out.println ("Problem reading stream ... :" + ex);
            ex.printStackTrace ();
        }
    }
}

