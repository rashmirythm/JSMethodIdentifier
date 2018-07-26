import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

import main.java.io.reflectoring.diffparser.api.UnifiedDiffParser;


public class Client {

	public static String commitHash;
	//public static String parentcommitHash;
	
	public static void main(String args[]) throws IOException, GitAPIException
	{
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		 Scanner sc=new Scanner(System.in);  
	     
		   System.out.println("Enter the commit id:");  
		   commitHash=sc.next(); 
		   sc.close();
		   //parentcommitHash = "0ee8e206edae2bc36a79640d24af19ba2ef9581f";
		   
		   GitDiffData gitdata[] = Mediate.getFunctionsAndLines(commitHash);
		   
		   
		   
		   for(int i=0;i<gitdata.length;i++)
		   {
			   System.out.println("File Name: "+gitdata[i].getFileName());
			   System.out.println("Changes: "+gitdata[i].getGitLineinfo());
			   System.out.println("Lines separated: "+gitdata[i].getSeperatedLines());
			   System.out.println("\n");
		   }
		   
		   
/* GitDiffData gitdata1[] = Mediate.getFunctionsAndLines(parentcommitHash);
		   
		   for(int i=0;i<gitdata.length;i++)
		   {
			   System.out.println("File Name: "+gitdata1[i].getFileName());
			   System.out.println("Changes: "+gitdata1[i].getGitLineinfo());
			   System.out.println("Lines separated: "+gitdata1[i].getSeperatedLines());
			   System.out.println("\n");
		   }*/
		   
		   
		   
		   
		   
		   
		/*
		//File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		
		//(NEW) command is "git difftool -y -x \"diff -c\" HEAD^1 HEAD "
		//git difftool -x "diff -c" HEAD^1 HEAD
		//Process p1=Runtime.getRuntime().exec("git diff -U0 3054ff31104134f1060c3bf8903513d4df818d71 1faaad0f4067fdea926f7a5f89a7ef23f48a781b", null, gitWorkDir);
		
		//Process p1=Runtime.getRuntime().exec("git difftool -y -x \"diff -c\" 0ee8e206edae2bc36a79640d24af19ba2ef9581f 625dd4a253f638914893b54d74d4b2e359f36840", null, gitWorkDir);
		//Process p1=Runtime.getRuntime().exec("git diff -U0 0ee8e206edae2bc36a79640d24af19ba2ef9581f 625dd4a253f638914893b54d74d4b2e359f36840", null, gitWorkDir);

		
		//readOutput(p1);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
		   
		   

		//LineNumbers.getLinesofBothRevision();
		System.out.println("\n\n\n");
		System.out.println("*******************UNIFIED DIFF DATA**************************");
		GitDiffData gData[] = Difference.readOutput();
		
		
		//********************************************************************************************************

				//Get Methods changed by line number
				/*int linenumber = 10;
				String filename="JSsamplefunctions.js";
				MethodIdentifier.getMethodsChangedByLineNumber(fd,linenumber,filename);
		*/
				//********************************************************************************************************
//=================================================================================================================
		/*FunctionDetails fd[]=Mediate.getFunctionDetails(commitHash);
		
		for(int i=0;i<gData.length;i++)
		{
			String filename = gData[i].FileName;
			List<Integer> newRevLines = gData[i].newLines;
			
			System.out.println(newRevLines);
			
			for(int j=0;j<newRevLines.size();j++)
			{
				int lines[] = newRevLines[j];
				MethodIdentifier.getMethodsChangedByLineNumber(fd,newRevLines[j],filename);
			}
		
		}*/
		
	}
	
	

}
