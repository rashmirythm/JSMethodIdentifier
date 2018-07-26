import java.io.File;

import java.lang.Object;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.diff.RawText;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
 
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;



public class testUnifiedDiff {
	
	//File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
	
	public static void getUnifiedDiff() throws IOException
	{
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
	        Git git = Git.open(gitWorkDir);
	     
		    try
		    {
		      RawText oldFile = new RawText(new File("C:\\Users\\I338008\\Documents\\GIT Documents\\UnifiedDiff\\oldFile.txt"));
		      RawText newFile = new RawText(new File("C:\\Users\\I338008\\Documents\\GIT Documents\\UnifiedDiff\\newFile.txt"));
		      EditList diffList = new EditList();
		      diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, oldFile, newFile));
		      //diffList.addAll((Collection<? extends Edit>) git.diff());
		      
		      
		      new DiffFormatter(out).format(diffList, oldFile, newFile);
		      System.out.println(diffList.toString());
		    } catch (IOException e)
		    {
		      e.printStackTrace();
		    }
		    System.out.println(out.toString());
		    
		    
		  
		    
		    
	}
	
	
	/*File oldFile = new File("");
	File newFile = new File("");
	org.eclipse.jgit.diff.RawText a = RawText(oldFile);
	RawText b = RawText(newFile);*/
}



/*import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
 
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
 
public class RawDiff
{
  public static void main(String[] args) throws Exception
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
     
    try
    {
      RawText rt1 = new RawText(new File("C:\\temp\\twofiles\\file1.txt"));
      RawText rt2 = new RawText(new File("C:\\temp\\twofiles\\file2.txt"));
      EditList diffList = new EditList();
      diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2));
      new DiffFormatter(out).format(diffList, rt1, rt2);
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    System.out.println(out.toString());
  }
}*/