import java.io.IOException;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

public class Client {

	public static String commitHash;
	
	public static void main(String args[]) throws IOException, GitAPIException
	{
		 Scanner sc=new Scanner(System.in);  
	     
		   System.out.println("Enter the commit id:");  
		   commitHash=sc.next(); 
		   sc.close();
		   
		   Mediate.getFunctionsAndLines(commitHash);
	}
}
