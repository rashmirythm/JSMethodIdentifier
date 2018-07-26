
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Name;


public class Mediate {

	private static Git git;
	private static Repository repository;

	public static GitDiffData[] getFunctionsAndLines(String commitHash) throws IOException, GitAPIException
	{

		//========================================================================================================

		//Commit Hash Id
		//commitHash = "625dd4a253f638914893b54d74d4b2e359f36840";
		//HashSet<String> changedMethods = new HashSet<String>();


		//getting the path names of all the Files in the above Commit
		String[] Pathnames=MethodIdentifier.FindGitCommitFiles(commitHash);
		System.out.println("\nList of files:\n---------------");
		for(String path:Pathnames) {
			System.out.println(path);
		}
		System.out.println("\n");

		//========================================================================================================



		//========================================================================================================

		//Get the methods for all files

		//copy the content in a File
		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		git = Git.open(gitWorkDir);
		repository = git.getRepository();
		//HashSet<String> changedMethods = new HashSet<String>();

		ObjectId treeId = ObjectId.fromString(commitHash);
		RevWalk revWalk = new RevWalk(repository);
		RevCommit commit = revWalk.parseCommit(treeId);
		RevTree tree = commit.getTree();

		//getting total count
		int totalFNcount=0;
		for (String filename:Pathnames) {

			String file1 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt";
			String file2 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSParentFile.txt";
			File testScript = new File("C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt");

			MethodIdentifier.createFile(tree,file1,filename);
			RevCommit[] Parents = commit.getParents();
			for(RevCommit parent:Parents) {
				RevCommit ParentCommit = revWalk.parseCommit(parent.getId());
				RevTree ParentTree = ParentCommit.getTree();
				MethodIdentifier.createFile(ParentTree,file2,filename);
			}

			//========================================================================================================


			//========================================================================================================
			//Get the CurrentASTRoot
			ScriptOrFnNode astRoot = MethodIdentifier.getASTnode(testScript);
			totalFNcount = totalFNcount + astRoot.getFunctionCount();
			//========================================================================================================

			
			

		}
		//========================================================================================================
		//get Function Details of all files with line numbers in Current Revision
		FunctionDetails fd[] = new FunctionDetails[totalFNcount];

		for (String filename:Pathnames) {

			String file1 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt";
			String file2 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSParentFile.txt";
			File testScript = new File("C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt");
			
			MethodIdentifier.createFile(tree,file1,filename);
			RevCommit[] Parents = commit.getParents();
			for(RevCommit parent:Parents) {
				RevCommit ParentCommit = revWalk.parseCommit(parent.getId());
				RevTree ParentTree = ParentCommit.getTree();
				MethodIdentifier.createFile(ParentTree,file2,filename);
			}

			ScriptOrFnNode astRoot = MethodIdentifier.getASTnode(testScript);

			fd = MethodIdentifier.GetFunctionAndLines(astRoot,totalFNcount,fd,filename);
		}
	
		System.out.println("Function names with Line numbers:\n----------------------------------");
		for(int i=0;i<totalFNcount;i++)
		{
			System.out.println("File Name= "+fd[i].getFileName()+":	Function "+fd[i].getFunctionName()+"() "+fd[i].getFunctionStartPos()+" - "+fd[i].getFunctionEndPos());
		}
		System.out.println("\n\n");


		//========================================================================================================

		
		
		
		
		GitDiffData gitdata[] = Difference.getDiffData(commitHash);
		
		return gitdata;
		
		
		//********************************************************************************************************

		//Get Methods changed by line number
		/*int linenumber = 10;
		String filename="JSsamplefunctions.js";
		MethodIdentifier.getMethodsChangedByLineNumber(fd,linenumber,filename);
*/
		//********************************************************************************************************

		

	}

}
