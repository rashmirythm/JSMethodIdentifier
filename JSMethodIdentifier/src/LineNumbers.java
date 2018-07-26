import java.util.ArrayList;
import java.util.List;

public class LineNumbers {
	

	public List<Integer> oldLines = new ArrayList<Integer>();
	public List<Integer> newLines = new ArrayList<Integer>();
	public List<String> oldLineschangetype = new ArrayList<String>();
	public List<String> newLineschangetype = new ArrayList<String>();

	//public static List<String> Fulllist = new ArrayList<String>();
	
	
	public static void getLinesofBothRevision(List<String> Fulllist,GitDiffData gitdata)
	{
		//Fulllist.add("-15,6 +15");
		//Fulllist.add("-29 +24");
		//Fulllist.add("-76,0 +72,6");
		
		String[] FulllistString = new String[Fulllist.size()];
		FulllistString = Fulllist.toArray(FulllistString);
		/*for(int i=0;i<FulllistString.length;i++)
		{
			System.out.println(FulllistString[i]);
		}*/
		
		for(int i=0;i<FulllistString.length;i++)
		{
			String [] spacesplit = FulllistString[i].trim().split("\\s+");
			//System.out.println(spacesplit.length);
			//System.out.println(spacesplit[0]);
			//System.out.println(spacesplit[1]);
			
			//System.out.println(spacesplit[0]);
			//System.out.println(spacesplit[1]);
				String[] prevRevLines = spacesplit[0].trim().split(",");
				String[] newRevLines = spacesplit[1].trim().split(",");
			
				/*System.out.println("prevRevLines:");
				for(int p=0;p<prevRevLines.length;p++)
				{
					System.out.println(prevRevLines[p]);
				}
				System.out.println("newRevLines:");
				for(int q=0;q<newRevLines.length;q++)
				{
					System.out.println(newRevLines[q]);
				}*/
				
				
			int count = prevRevLines.length + newRevLines.length;
			

			if(count==2)
			{
				twopartlineinfo(prevRevLines,newRevLines,gitdata);
			}
			else if(count==3)
			{
				threepartlineinfo(prevRevLines,newRevLines,gitdata);
			}
			else if(count==4)
			{
				fourpartlineinfo(prevRevLines,newRevLines,gitdata);
			}
			else
			{
				System.out.println("Less than 2");
			}
			
			
		}
		//System.out.println("\n\n");
		System.out.println("OldFileLines:");
		System.out.println(gitdata.oldLines);
		System.out.println(gitdata.oldLineschangetype);
		/*for(int a=0;a<oldLines.size();a++)
		{
			System.out
		}*/
		System.out.println("NewFileLines:");
		System.out.println(gitdata.newLines);
		System.out.println(gitdata.newLineschangetype);
		/*for(int a=0;a<newLines.size();a++)
		{
			
		}*/
	}
	
	
	public static void twopartlineinfo(String[] prevRevLines, String[] newRevLines,GitDiffData gitdata)
	{
		gitdata.oldLines.add(Math.abs(Integer.parseInt(prevRevLines[0])));
		gitdata.oldLineschangetype.add("M");
		gitdata.newLines.add(Math.abs(Integer.parseInt(newRevLines[0])));
		gitdata.newLineschangetype.add("M");
	}
	
	public static void threepartlineinfo(String[] prevRevLines, String[] newRevLines,GitDiffData gitdata)
	{
		if(prevRevLines.length==2)
		{
			if(Math.abs(Integer.parseInt(prevRevLines[1]))==0)
			{
				gitdata.newLines.add(Math.abs(Integer.parseInt(newRevLines[0])));
				gitdata.newLineschangetype.add("A");
			}
			else
			{
				for(int i=0;i<Math.abs(Integer.parseInt(prevRevLines[1]));i++)
				{
					gitdata.oldLines.add((Math.abs(Integer.parseInt(prevRevLines[0])))+i);
					gitdata.oldLineschangetype.add("M");
				}
				gitdata.newLines.add(Math.abs(Integer.parseInt(newRevLines[0])));
				gitdata.newLineschangetype.add("M");
			}
			
		}
		else
		{
			if(Math.abs(Integer.parseInt(newRevLines[1]))==0)
			{
				gitdata.oldLines.add(Math.abs(Integer.parseInt(prevRevLines[0])));
				gitdata.oldLineschangetype.add("D");
			}
			else
			{
				gitdata.oldLines.add(Math.abs(Integer.parseInt(prevRevLines[0])));
				gitdata.oldLineschangetype.add("M");
				for(int i=0;i<Math.abs(Integer.parseInt(newRevLines[1]));i++)
				{
					gitdata.newLines.add((Math.abs(Integer.parseInt(newRevLines[0])))+i);
					gitdata.newLineschangetype.add("M");
				}
			}
			
		}
	}
	

	public static void fourpartlineinfo(String[] prevRevLines, String[] newRevLines,GitDiffData gitdata)
	{
		if(Math.abs(Integer.parseInt(prevRevLines[1]))==0)
		{
			for(int j=0;j<Math.abs(Integer.parseInt(newRevLines[1]));j++)
			{
				gitdata.newLines.add((Math.abs(Integer.parseInt(newRevLines[0])))+j);
				gitdata.newLineschangetype.add("A");
			}
		}
		else if(Math.abs(Integer.parseInt(newRevLines[1]))==0)
		{
			for(int i=0;i<Math.abs(Integer.parseInt(prevRevLines[1]));i++)
			{
				gitdata.oldLines.add((Math.abs(Integer.parseInt(prevRevLines[0])))+i);
				gitdata.oldLineschangetype.add("D");
			}
		}
		else
		{
			for(int i=0;i<Math.abs(Integer.parseInt(prevRevLines[1]));i++)
			{
				gitdata.oldLines.add((Math.abs(Integer.parseInt(prevRevLines[0])))+i);
				gitdata.oldLineschangetype.add("M");
			}
			for(int j=0;j<Math.abs(Integer.parseInt(newRevLines[1]));j++)
			{
				gitdata.newLines.add((Math.abs(Integer.parseInt(newRevLines[0])))+j);
				gitdata.newLineschangetype.add("M");
			}
		}
	
	}
	
}
