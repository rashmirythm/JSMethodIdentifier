
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;


public class Difference {
       
	
	public static GitDiffData[] getDiffData(String commitHash) throws IOException, GitAPIException
	{
		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
        Git git = Git.open(gitWorkDir);
        String oldHash = commitHash;
        //String oldHash = "625dd4a253f638914893b54d74d4b2e359f36840";
        ObjectId headId = git.getRepository().resolve(oldHash + "^{tree}");
        ObjectId oldId = git.getRepository().resolve(oldHash + "~^{tree}");
    
        ObjectReader reader = git.getRepository().newObjectReader();
         
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        oldTreeIter.reset(reader, oldId);
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, headId);
    
       
        
        List<DiffEntry> diffs= git.diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                .call();
         
        int noOfFiles = diffs.size();
        
        GitDiffData[] gitdata = new GitDiffData[noOfFiles];
        
        for(int i=0;i<noOfFiles;i++)
        {
     	   gitdata[i] = new GitDiffData();
        }
        
        //System.out.println("There are changes made in : "+noOfFiles+" files");
        
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DiffFormatter df = new DiffFormatter(out);
        df.setRepository(git.getRepository());
        
        GitDiffData gitData[] = new GitDiffData[noOfFiles];
        
        for(int i=0;i<noOfFiles;i++)
        {
     	   gitData[i] = new GitDiffData();
        }
        
        for(DiffEntry diff : diffs)
        {
        	
          df.format(diff);
          diff.getOldId();
          int iterator = diffs.lastIndexOf(diff);
          String diffText = out.toString("UTF-8");
          gitData[iterator]= getFileNameAndLinesChanged(diffText);
          //System.out.println(gitData[iterator].getFileName());
          /*int gData =gitData.length;
          for(int k=0;k<gData;k++)
          {
         	 System.out.println(gitData[k].getLinesChanges());
          }*/
          out.reset();
        }
        
        //List<String> separatedNumbers = new ArrayList<String>();
        
        //separatedNumbers = splitNumbers(gitData);
        
    return gitData;
	}
       
       
      /* public static List<String> splitNumbers(GitDiffData[] gitData) {
    	   
    	   List<String> linessplit = new ArrayList<String>();
    	   int count = gitData.length;
    	   for(int i=0;i<count;i++)
    	   {
    		   int size = gitData[i].GitLineinfo.size();
    		   for(int j=0;j<size;j++)
    		   {
    			   List<String> arrOfStrlinesplit = gitData[i].GitLineinfo[j];//.split("\\s+", 3);
    			   //gitData[i].GitLineinfo[j];
    			   
    		   }
    		  
    	   }
    	  // String [] arrOfStrlinesplit = lines.split("\\s+", 3);
    	   
    	   
    	   
		return linessplit;
	}*/
       
	public static GitDiffData getFileNameAndLinesChanged(String diffText)
       {
    	   
    	   GitDiffData gd = new GitDiffData();
    	   //System.out.println("\n\n");
    	   //System.out.println("---------------Both CHANGED----------------");
           Pattern pattern = Pattern.compile("@@(.*?)@@");
           Matcher matcher = pattern.matcher(diffText);
           
           Pattern pattern1 = Pattern.compile("\\---(.*?)\\.js");
           Matcher matcher1 = pattern1.matcher(diffText);
           
           int flag=0;
           List<String> GitLineinfo = new ArrayList<String>();
           while(matcher.find())
           {
            while (flag==0 && matcher1.find())
           {
            	String [] arrOfFilenamesplit = matcher1.group(0).split("/", 2);
               gd.setFileName(arrOfFilenamesplit[1]);
              
               flag=1;
           }
        	GitLineinfo.add(matcher.group(1));
            flag=0;
           }
           gd.setGitLineinfo(GitLineinfo);
           
         //System.out.println(gd.getFileName());
         //System.out.println(gd.getGitLineinfo());
         //System.out.println("\n");
           
           return gd;
       }
	
	
	public static GitDiffData[] readOutput() throws IOException {
		
		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		Process proc=Runtime.getRuntime().exec("git diff -U0 78641a9711a771d0dcc6a581981cacc8980ef19c e57f169e0bc0112718993a205cd3307c2188a4cc", null, gitWorkDir);

		//C:\Users\I338008\Documents\GIT Documents\Diff-U_output
		//final Path dst = Paths.get(C:\Users\I338008\Documents\GIT Documents\Diff-U_output\);
		
        System.out.println("read output:");

        InputStream is = (InputStream) proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
        String FullDiffData =null;// = br.toString();
        //System.out.println(FullDiffData);
        
        
        String line;
        while ((line = br.readLine()) != null) {
        	FullDiffData = FullDiffData + line + "\n";
            //System.out.println(line);
        }
        //System.out.println(FullDiffData);
        String[] fileDiffDivision = FullDiffData.split("---");
        /*for(int i=0;i<fileDiffDivision.length;i++)
        {
        	System.out.println(fileDiffDivision[i]);
        	System.out.println("**********************************************************************");
        }*/
        
        GitDiffData[] gitdata = new GitDiffData[fileDiffDivision.length-1];
        for(int i=0;i<fileDiffDivision.length-1;i++)
        {
        	gitdata[i] = new GitDiffData();
        	gitdata[i] =unifiedDiffHunck(fileDiffDivision[i+1]);
        	System.out.println(gitdata[i].FileName);
        	System.out.println(gitdata[i].GitLineinfo);
        	LineNumbers.getLinesofBothRevision(gitdata[i].GitLineinfo,gitdata[i]);
        	System.out.println("\n");
        }
        
        
        
        
        
        
       // List<String> filenames = new ArrayList<String>();
        //List<String> GitLineinfo = new ArrayList<String>();
        
        
        return gitdata;

        }
        
	public static GitDiffData unifiedDiffHunck(String fileDiffDivision)
	{
		GitDiffData gd = new GitDiffData();
        //for(int i=1; i<fileDiffDivision.length;i++)
        //{
        	
        	Pattern patternx = Pattern.compile("@@(.*?)@@");
            Matcher matcher = patternx.matcher(fileDiffDivision);
            
            Pattern pattern1 = Pattern.compile("\\+++(.*?)\\.js");
            Matcher matcher1 = pattern1.matcher(fileDiffDivision);
            
            int flag=0;
            List<String> Lineinfo = new ArrayList<String>();
            while(matcher.find())
            {
             while (flag==0 && matcher1.find())
            {
             	String [] arrOfFilenamesplit = matcher1.group(0).split("/", 2);
                //filenames.add(arrOfFilenamesplit[1]);
             	//System.out.println("FileName="+arrOfFilenamesplit[1]);
                gd.setFileName(arrOfFilenamesplit[1]);
                flag=1;
            }
            // Lineinfo.add(matcher.group(1));
             Lineinfo.add(matcher.group(1));
             flag=0;
            }
            //System.out.println(Lineinfo);
            gd.setGitLineinfo(Lineinfo);
           //}
        
        
		
		return gd;
	}
        
        

}

