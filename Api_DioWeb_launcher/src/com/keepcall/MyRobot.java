package com.keepcall;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class MyRobot {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	    try {
//	        Robot robot = new Robot();
//			openChrome(robot,WAIT);
//	        openDioWeb( robot,WAIT);
//	        newTabChrome(robot);
//	        openHubSpot(robot, WAIT);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}
	
	private static void enterText(String myText) throws AWTException {
			Robot robot = new Robot ();
			String text = myText ;
	        StringSelection stringSelection = new StringSelection(text);
	        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        clipboard.setContents(stringSelection, stringSelection);

//	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public static void openChrome(int wait, int WindowsButtonWAIT ) throws AWTException {
		Robot robot = new Robot ();
        /**Windows button**/
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_WINDOWS);  
        
        robot.delay(WindowsButtonWAIT);
        enterText("chrome");
        robot.delay(WindowsButtonWAIT);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(WindowsButtonWAIT);
        
	}
	public static void newTabChrome(int SMALLWAIT) throws AWTException {
		Robot robot = new Robot ();
		robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_T);
	    robot.keyRelease(KeyEvent.VK_T);	
	    robot.keyRelease(KeyEvent.VK_CONTROL);	
	    robot.delay(SMALLWAIT);
	}
	
	public static void openDioWeb(String[][] login,int WebSiteWait,int SMALLWAIT, String strUrlDioWeb) throws AWTException {
		
		Robot robot = new Robot ();
		String user_login = login[0][0];
		String mdp = login[0][1];
		
		enterText(strUrlDioWeb);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);	
	    robot.delay(WebSiteWait);
	    
	    enterText(user_login);
	    robot.delay(SMALLWAIT);
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);	
	    robot.delay(SMALLWAIT);
	    
	    enterText(mdp);
	    robot.delay(SMALLWAIT);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);	
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static void openHubSpot(String[][] login,int WebSiteWait ,int SMALLWAIT, String strUrlHubSpot) throws AWTException {
		Robot robot = new Robot ();
		String user_login = login[1][0];
		String mdp = login[1][1];
		
		enterText(strUrlHubSpot);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.delay(WebSiteWait);
	    
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_A);
	    robot.keyRelease(KeyEvent.VK_A);	
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.delay(SMALLWAIT);
	
	    robot.keyPress(KeyEvent.VK_DELETE);	
	    robot.keyRelease(KeyEvent.VK_DELETE);
	    robot.delay(SMALLWAIT);
	
	    enterText(user_login);
	    robot.delay(SMALLWAIT);
	
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    robot.delay(SMALLWAIT);
	
	    robot.keyPress(KeyEvent.VK_DELETE);	
	    robot.keyRelease(KeyEvent.VK_DELETE);
	    robot.delay(SMALLWAIT);
	
	    enterText(mdp);
	    robot.delay(SMALLWAIT);
	
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
}
