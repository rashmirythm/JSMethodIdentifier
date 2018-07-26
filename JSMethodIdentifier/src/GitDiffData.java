import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitDiffData {
		public String FileName;
		public List<String> GitLineinfo = new ArrayList<String>();
		public List<Integer> oldLines = new ArrayList<Integer>();
		public List<Integer> newLines = new ArrayList<Integer>();
		public List<String> oldLineschangetype = new ArrayList<String>();
		public List<String> newLineschangetype = new ArrayList<String>();
		//public List<String> seperatedLines = new ArrayList<String>();
		
		public List<Integer> seperatedLines = new ArrayList<Integer>();
		
		public String getFileName() {
			return FileName;
		}
		public List<String> getGitLineinfo() {
			return GitLineinfo;
		}
		public List<Integer> getSeperatedLines()
		{
			return seperatedLines;
		}
		
		public void setGitLineinfo(List<String> gitLineinfo) {
			GitLineinfo = gitLineinfo;
			String[] strArray = new String[GitLineinfo.size()];
			strArray = GitLineinfo.toArray(strArray);
			//System.out.println(Arrays.toString(strArray));
			int len = strArray.length;
			
			for(int i=0;i<len;i++)
			{
				String [] arrOfStrlinesplit = strArray[i].trim().split("\\s+", 3);
				for (String a : arrOfStrlinesplit)
	            {
	            	//System.out.println(a);
	            	String [] commasplit = a.trim().split(",", 2);
	            	for (String b : commasplit)
	                {
	            		if(b!=" ")
	            		{
	            			seperatedLines.add(Integer.parseInt(b));
	            		}
	            		
	                	//System.out.println(b);
	                }
	            }
			}
			/*for (String c : seperatedLines)
            {
        		//System.out.println(seperatedLines);
            	System.out.println(c);
            }*/
			
			//System.out.println(seperatedLines);
		}
		
		
		
		public void setFileName(String fileName) {
			FileName = fileName;
		}
		
}
