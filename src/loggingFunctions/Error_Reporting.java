package loggingFunctions;

import java.io.*;
import java.util.ArrayList;



/*
 TODO:
 	expand functions as new functunality comes in to main program
 */


public class Error_Reporting {
	
	private static String errorlogfilename = "errorlog.txt";
	private static String actionlogfilename = "history.txt";

	
	private int filecreatesuccess = 0;
	
	
	
	public boolean createLogFiles()
	{
		
		try 
		{
			File errorlogfile= new File(errorlogfilename);
			
			if(errorlogfile.createNewFile())
			{
				filecreatesuccess++;
				System.out.println("error log file created succesfully");
			}
			else 
			{
				System.out.println("error log file already exists");
			}
		}
		catch (IOException e)
		{
			System.out.println("something went wrong in error log creation");
			e.printStackTrace();
		}
		
		try 
		{
			File actionslogfile= new File(actionlogfilename);
			
			if(actionslogfile.createNewFile())
			{
				filecreatesuccess++;
				System.out.println("actions log file created succesfully");
			}
			else 
			{
				System.out.println("actions log file already exists");
			}
		}
		catch (IOException e)
		{
			System.out.println("something went wrong in actions log creation");
			e.printStackTrace();
		}
		
		if(filecreatesuccess ==2)
			return true;
		else
			return false;
	}
	

	public void writeActionlog(String action)
	{
		try
		{
			FileWriter actionWriter = new FileWriter(actionlogfilename, true);
			actionWriter.write(action+"\n");
			actionWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public void writeErrorLog(String error)
	{
		try
		{
			FileWriter errorWriter = new FileWriter(errorlogfilename, true);
			errorWriter.write(error+"\n");
			errorWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	
		
	}
	
	public void writeErrorStack(Throwable throwable)
	{
		try (StringWriter sw = new StringWriter(); 
	               PrintWriter pw = new PrintWriter(sw)) 
	        {
	            throwable.printStackTrace(pw);
	            try
	    		{
	    			FileWriter errorWriter = new FileWriter(errorlogfilename, true);
	    			errorWriter.write(sw.toString()+"\n");
	    			errorWriter.close();
	    			sw.close();
	    		}
	            
	    		catch (IOException e)
	    		{
	    			System.out.println("An error occurred.");
	    		    e.printStackTrace();
	    		
	    		} 
	  
	       } catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	
}
