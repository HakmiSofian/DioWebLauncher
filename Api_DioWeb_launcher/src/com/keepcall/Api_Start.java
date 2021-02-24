package com.keepcall;


import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;

public class Api_Start {	
    private static String strdebutPause;
	private static String strurl ;
    private static String strdbuser; 
    private static String strdbpassword;
    private static String strUrlDioWeb;
    private static String strUrlHubSpot;
    private static String strPathOut;
    private static String strPathErr;
    private static String strPathConfigFile="c:\\config.xml";
	private static int SMALLWAIT ;
	private static int WindowsButtonWAIT ;
	private static int WebSiteWait ;
	
	

    
    public static void main(String[] args)  {	
//    	String[][] loginC= {{"CALLCENTER","3QG36R8B"},{"keepcallgroup@gmail.com","Reynog2m"}};
//    	openApi(loginC);
//    	String[][] test =testlogin("KEEPCALL", "a");
    	
    	final JFrame frame = new JFrame("Dioweb Login");
    	frame.setSize(600, 400); 
    	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	
    	readConfigFile();

    	try {
            // Save original out stream.
    		PrintStream originalOut = System.out;
            // Save original err stream.
            PrintStream originalErr = System.err;
            
			PrintStream fileOut = new PrintStream(new FileOutputStream(strPathOut,true));
			PrintStream fileErr = new PrintStream(new FileOutputStream(strPathErr,true));

			System.setOut(fileOut);
			System.setErr(fileErr);


			
    		System.out.println("*** Start application.");
    	
	        JLabel lblUser = new JLabel("User :");
	        JTextField tfUser = new JTextField(20);
	        lblUser.setLabelFor(tfUser);
	 
	        JLabel lblPassword = new JLabel("Mot de passe :");
	        final JPasswordField pfPassword = new JPasswordField(20);
	        lblPassword.setLabelFor(pfPassword);
	        JButton btnDioWeb = new JButton("DioWeb");
	        JButton btnLogout = new JButton("Logout");
	        JButton btnPause = new JButton("Début de Pause");
	        JButton btnFinPause = new JButton("Fin de Pause");
	        JButton btnOpenHubSpot = new JButton("HubSpot");
	        JButton btnNewWindow = new JButton("Nouvelle Page");

	        // FOR TEST
//    		driver = initChromedriver(strFirefoxWebDriver, strPathFirefoxDriver) ;
	        
	        JButton btnGet = new JButton("Afficher Mot de passe");
	        btnGet.addActionListener(
	                new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        String password = new String(pfPassword.getPassword());
	                        JOptionPane.showMessageDialog(frame,
	                                "Votre mot de passe : " + password);
//	                        logger.info("Affichage du mot de passe");
	                        System.out.println("Affichage du mot de passe");
	                    }
	                });
	 
	        
	        
	        JButton btnLogin = new JButton("Login");
	        btnLogin.addActionListener(
	                new ActionListener() {
	                	
	                    public void actionPerformed(ActionEvent e) {
//	                    	logger.info("Login verification ...");
	                    	System.out.println("Login verification ...");
	                        String[][] resultlogin = testlogin(tfUser.getText(),pfPassword.getText() );
	                        
	                    	if(resultlogin[0][0].equals("Erreur")) {
	                    		System.out.println("Login FAILED.  "+resultlogin[0][1]);
//	                    		logger.warning("Login FAILED.  "+resultlogin[0][1]);
	                    		JOptionPane.showMessageDialog(frame,
	                                    "Erreur : " + resultlogin[0][1] );
	                    	}else {
//	                    		logger.info("Login success.");
	                    		loggerDB("Loggin Dioweb",tfUser.getText());
	                    		System.out.println("Login success.");
	                    		
	                    		btnDioWeb.setEnabled(true);
	                    		btnOpenHubSpot.setEnabled(true);
	                    		btnPause.setEnabled(true);
	                    		btnLogout.setEnabled(true);
	                	        btnNewWindow.setEnabled(true);
	                    		tfUser.setEnabled(false);
	                    		pfPassword.setEnabled(false);
	                    		btnLogin.setEnabled(false);
	                    		btnGet.setEnabled(false);

	                    		
	                    		try {
		                    		System.out.println("Open Chrome ...");
		                    		MyRobot.openChrome(WindowsButtonWAIT,WindowsButtonWAIT);
		                    		System.out.println("Open DioWeb ...");
									MyRobot.openDioWeb(resultlogin,WebSiteWait,SMALLWAIT,strUrlDioWeb);
		                    		System.out.println("Open New Tab ...");
									MyRobot.newTabChrome(SMALLWAIT);
		                    		System.out.println("Open HubSpot ...");
									MyRobot.openHubSpot(resultlogin,WebSiteWait,SMALLWAIT,strUrlHubSpot);
								} catch (AWTException e1) {
									e1.printStackTrace();
								}
	                    		
	                    		System.out.println("All success.");
	                    		

	                    	}
	                    		
	                    }
	                });
	        //listener for ENTER KEY
	        pfPassword.addKeyListener(new KeyListener(){
	            @Override
	            public void keyTyped(KeyEvent e) {
	                if(e.getKeyChar()==KeyEvent.VK_ENTER){
	                	btnLogin.doClick();
	                }
	            }
				@Override
				public void keyPressed(KeyEvent e) {
					
				}
	
				@Override
				public void keyReleased(KeyEvent e) {
					
				}   
	        });
	        
	        btnOpenHubSpot.addActionListener(
	                new ActionListener() {
	                	
	                	 public void actionPerformed(ActionEvent e) {
//	                		 logger.info("Login verification ...");
		                    	System.out.println("Login verification ...");
		                        String[][] resultlogin = testlogin(tfUser.getText(),pfPassword.getText() );
		                        
		                    	if(resultlogin[0][0].equals("Erreur")) {
		                    		System.out.println("Login FAILED.  "+resultlogin[0][1]);
//		                    		logger.warning("Login FAILED.  "+resultlogin[0][1]);
		                    		JOptionPane.showMessageDialog(frame,
		                                    "Erreur : " + resultlogin[0][1] );
		                    	}else {
		                    		System.out.println("Login success.");
		                    		System.out.println("Ouvrir HubSpot.");
									try {
			                    		MyRobot.openChrome(WindowsButtonWAIT,WindowsButtonWAIT);
										MyRobot.openHubSpot(resultlogin,WebSiteWait,SMALLWAIT,strUrlHubSpot);
									} catch (AWTException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
		                    		System.out.println("Ouverture HubSpot success.");
		                    	}
	                     }
	                });
	        btnOpenHubSpot.setEnabled(false);
	        
	        btnDioWeb.addActionListener(
	                new ActionListener() {
	                	
	                	 public void actionPerformed(ActionEvent e) {
	                		 System.out.println("Refresh.");
	                 		loggerDB("Refresh Dioweb",tfUser.getText());
	                 		String[][] resultlogin = testlogin(tfUser.getText(),pfPassword.getText() );
                    		System.out.println("Open Chrome ...");
                    		try {
	                    		MyRobot.openChrome(WindowsButtonWAIT,WindowsButtonWAIT);
	                    		System.out.println("Open DioWeb ...");
								MyRobot.openDioWeb(resultlogin,WebSiteWait,SMALLWAIT,strUrlDioWeb);
							} catch (AWTException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                  }
	                });
	        btnDioWeb.setEnabled(false);
	
	        btnLogout.addActionListener(
	                new ActionListener() {
	                	 public void actionPerformed(ActionEvent e) {
	                		 System.out.println("Logout.");
	                 		loggerDB("Logout",tfUser.getText());
	                 		System.exit(0);
	                     }
	                });
	        btnLogout.setEnabled(false);
	
	        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
	
	        
	        btnPause.addActionListener(
	                new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	System.out.println("Début de pause.");
	                    		loggerDB("Début de pause",tfUser.getText());
	                    		strdebutPause = formatter.format(new Date(System.currentTimeMillis() + 3600 * 1000));
	                    		JOptionPane.showMessageDialog(frame,
	                                    "Début de pause : " + strdebutPause);
	                    		btnFinPause.setEnabled(true);
	                    		btnPause.setEnabled(false);
	                    }
	                });
	        btnPause.setEnabled(false);        
	        
	        btnFinPause.addActionListener(
	                new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	System.out.println("Fin de pause.");
	                    		loggerDB("Fin de pause",tfUser.getText());
	                    		String strFinPause = formatter.format(new Date(System.currentTimeMillis() + 3600 * 1000));
								JOptionPane.showMessageDialog(frame,
								        "Début de pause : " + strdebutPause + "\n"+
								        "Fin de pause : "+ strFinPause +"\n" +
								        "Durée : "+ getHoursDiff( strFinPause,strdebutPause));
	                    		btnFinPause.setEnabled(false);
	                    		btnPause.setEnabled(true);
	                    }
	                });
	        btnFinPause.setEnabled(false);
	

	        
	        JPanel panel = new JPanel();
	        

	        
	//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	 
	        panel.add(lblUser);
	        panel.add(tfUser);
	        panel.add(lblPassword);
	        panel.add(pfPassword);
	        panel.add(btnLogin);
	        panel.add(btnGet);
	        panel.add(btnOpenHubSpot);
	        panel.add(btnDioWeb);
	        panel.add(btnPause);
	        panel.add(btnFinPause);
	        panel.add(btnLogout);
	
	        
	        List<String> result =new ArrayList<String>();
	        result.add(tfUser.getText());
	        result.add( new String(pfPassword.getPassword()));
	 
	//        SpringUtilities.makeCompactGrid(panel,
	//                3, 2, //rows, cols
	//                6, 6, //initX, initY
	//                6, 6); //xPad, yPad
	 
	        frame.getContentPane().add(panel);
	        frame.setVisible(true);
	        
	     // Do not forget set original output and error stream back again.
            System.setOut(originalOut);
            System.setErr(originalErr);
	        
		} catch (IOException e) {
//			logger.severe("An exception occurred." + e.getMessage());
			e.printStackTrace();
		}
    }
    
    
    /**
     * 
     * Test si le login et le mot de passe sont correct et renvoie le resultat.
     * 
     * 
     * @param login
     * @param mdp
     * @return
     */
    public static String[][] testlogin(String login, String mdp) {


        	String[][] result= {{"Erreur","Erreur d'authentification"}};

            try {
            	Connection con = DriverManager.getConnection(strurl, strdbuser, strdbpassword);
//                Statement st = con.createStatement();
//                st.executeQuery("INSERT INTO public.logtime (logtime, logtype) VALUES(now(),'"+ type +"') "); 
            	Statement statement = con.createStatement();
            	ResultSet rs = statement.executeQuery("select login ,mdp from users where login='"+login+"' and mdp ='"+mdp+"';");
            	while(rs.next()) {
            		ResultSet tmpRes = statement.executeQuery("select h.login loginh,h.mdp mdph,c.login loginc,c.mdp mdpc from couple u" + 
            		  		"	left join users h on h.login = u.loginh" + 
            		  		"	left join users c on c.login =u.loginc " + 
            		  		"	where u.logink='"+login+"'");
            		while(tmpRes.next()) {
                    	con.close();
            		  return new String[][]{
			          			  	{tmpRes.getString("loginc"),tmpRes.getString("mdpc")},
			        			  	{tmpRes.getString("loginH"),tmpRes.getString("mdpH")}
		  						};
            		}
//            		  System.out.println(lastName + "\n");
        		}
            	con.close();

            } catch (SQLException ex) {
            	System.out.println("Login Connection failure.");
            	result[0][1]= "Login Connection failure";
//            	logger.severe("Login Connection failure." + ex.getMessage());
                ex.printStackTrace();
            }
			return result;
        
    }
    
    /**
     * 
     * 		Read config file and generate all variable.
     *      
     */
    public static void readConfigFile() {

    	 try {

	 	        File fXmlFile = new File(strPathConfigFile);
	 	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	 	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	 	        Document doc = dBuilder.parse(fXmlFile);
	
	 	        doc.getDocumentElement().normalize();
		
	 	        strurl = doc.getElementsByTagName("DriverUrl").item(0).getTextContent();
	 	        strdbuser = doc.getElementsByTagName("DriverUser").item(0).getTextContent();
	 	        strdbpassword = doc.getElementsByTagName("DriverPassword").item(0).getTextContent();
	 	        strUrlDioWeb = doc.getElementsByTagName("UrlDioweb").item(0).getTextContent();
	 	        strUrlHubSpot = doc.getElementsByTagName("UrlHubSpot").item(0).getTextContent();
	 	        strPathOut = doc.getElementsByTagName("PathOutLog").item(0).getTextContent();
	 	        strPathErr = doc.getElementsByTagName("PathErrLog").item(0).getTextContent();
	 	        WebSiteWait = Integer.parseInt(doc.getElementsByTagName("WebSiteWait").item(0).getTextContent());
	 	        SMALLWAIT = Integer.parseInt(doc.getElementsByTagName("SMALLWAIT").item(0).getTextContent());
	 	        WindowsButtonWAIT = Integer.parseInt(doc.getElementsByTagName("WindowsButtonWAIT").item(0).getTextContent());
	 	       

 	        } catch (Exception e) {
 	        e.printStackTrace();
 	        }
    	
    	
    }
    



    
    /**
     * 
     * Ecriture sur la base de donnée des différents timeslog.
     * (loggin, début pause, fin pause, refresh ...)
     * 
     * 
     * @param type
     * @param user
     */
    public static void loggerDB(String type,String user) {


        try {
        	Connection con = DriverManager.getConnection(strurl, strdbuser, strdbpassword);
//            Statement st = con.createStatement();
//            st.executeQuery("INSERT INTO public.logtime (logtime, logtype) VALUES(now(),'"+ type +"') "); 
            PreparedStatement statement = con.prepareStatement("INSERT INTO public.logtime (logtime, logtype,loguser) VALUES(now(), ?,?)");
            statement.setString(1, type);
            statement.setString(2, user);
            statement.execute();
            con.close();

        } catch (SQLException ex) {
        	System.out.println("LogTime Connection failure.");
//        	logger.severe("LogTime Connection failure." + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static String getHoursDiff(String date1, String date2) {
    	  

    	   String[] fractions1=date1.split(":");
    	   String[] fractions2=date2.split(":");
    	   Integer hours1=Integer.parseInt(fractions1[0]);
    	   Integer hours2=Integer.parseInt(fractions2[0]);
    	   Integer minutes1=Integer.parseInt(fractions1[1]);
    	   Integer minutes2=Integer.parseInt(fractions2[1]);      
    	   Integer secondes1=Integer.parseInt(fractions1[2]);
    	   Integer secondes2=Integer.parseInt(fractions2[2]);      
    	   int hourDiff=hours1-hours2;
    	   int minutesDiff=minutes1-minutes2;
    	   int secondesDiff=secondes1-secondes2;
    	   if (secondesDiff < 0) {
    		   secondesDiff = 60 + secondesDiff;
    		   minutesDiff--;
    	   }    	   
    	   if (minutesDiff < 0) {
    	       minutesDiff = 60 + minutesDiff;
    	       hourDiff--;
    	   }
    	   if (hourDiff < 0) {
    	       hourDiff = 24 + hourDiff ;
    	   }
    	   return hourDiff+":"+ minutesDiff+":"+secondesDiff;
    }
    



	

}
