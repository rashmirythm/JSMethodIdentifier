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
		   
		   GitDiffData gitdata[] = Mediate.getFunctionsAndLines(commitHash);
		   
		   for(int i=0;i<gitdata.length;i++)
		   {
			   System.out.println("File Name: "+gitdata[i].getFileName());
			   System.out.println("Changes: "+gitdata[i].getGitLineinfo());
			   System.out.println("Lines separated: "+gitdata[i].getSeperatedLines());
			   System.out.println("\n");
		   }
	}
}
