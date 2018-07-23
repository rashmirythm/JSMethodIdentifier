import java.util.ArrayList;
import java.util.List;

public class GitDiffData {
		public String FileName;
		public List<String> GitLineinfo = new ArrayList<String>();
		
		public String getFileName() {
			return FileName;
		}
		public List<String> getGitLineinfo() {
			return GitLineinfo;
		}
		public void setGitLineinfo(List<String> gitLineinfo) {
			GitLineinfo = gitLineinfo;
		}
		public void setFileName(String fileName) {
			FileName = fileName;
		}
		
}
