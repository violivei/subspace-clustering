/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class OSInfo {
 
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	public static String getOsStr() {
  
		if (isWindows()) {
			return "winows";
		} else if (isMac()) {
			return "osx";
		} else if (isUnix()) {
			return "linux";
		} else if (isSolaris()) {
			return "solaris";
		} else {
			return "unknown";
		}
	}
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
 
}
