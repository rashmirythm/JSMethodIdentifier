import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.mozilla.javascript.*;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.ast.*;
import org.mozilla.javascript.ast.AstRoot;


public class MethodIdentifier{

	public static int currentObjArrayPosition=0;
	private static final String currentfile = null;
	private static Git git;
	private static Repository repository;
	private static FileOutputStream fop = null;
	private static byte[] contentInBytes;
	
	
	//========================================================================================================
	//Getting the path names/file names
	public static String[] FindGitCommitFiles( String commitHash ) throws IOException {

		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		git = Git.open(gitWorkDir);
		repository = git.getRepository();

		ObjectId treeId = ObjectId.fromString(commitHash);
		RevWalk revWalk = new RevWalk(repository);
		RevCommit commit = revWalk.parseCommit(treeId);
		RevTree tree = commit.getTree();


		Collection<String> pathNames = new ArrayList<>();
		TreeWalk treeWalk = new TreeWalk( repository );
		treeWalk.setRecursive( true );
		treeWalk.setPostOrderTraversal( true );
		treeWalk.addTree( tree );
		while( treeWalk.next() ) {
			pathNames.add( treeWalk.getPathString() );
		}

		String[] Pathnames= ( String[] )pathNames.toArray( new String[ pathNames.size() ] );

		return Pathnames;
	}
	//========================================================================================================

	
	
	
	//========================================================================================================
	//Creating and copying the contents into a file
	public static void createFile(ObjectId tree, String filename, String filechanged) throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {

		File file = new File(filename);
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		treeWalk.setFilter(PathFilter.create(filechanged));
		if (!treeWalk.next()) 
		{
			System.out.println("Nothing found!");
			return;
		}
		ObjectId objectId = treeWalk.getObjectId(0);
		ObjectLoader loader = repository.open(objectId);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		loader.copyTo(out);

		if (!file.exists()) {
			file.createNewFile();
		}
		fop = new FileOutputStream(file);
		contentInBytes = out.toString().getBytes();
		fop.write(contentInBytes);
	}
	//========================================================================================================

	
	
	
	
	//========================================================================================================
	//getting the ASTRoot 
	public static ScriptOrFnNode getASTnode(File ts) throws IOException
	{
		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		ErrorReporter errorReporter = compilerEnv.getErrorReporter();

		File testScript = ts;
		String sourceURI;
		try {
			sourceURI = testScript.getCanonicalPath();
		} catch (IOException e) {
			sourceURI = testScript.toString();
		}
		Reader reader = new FileReader(testScript);

		org.mozilla.javascript.Parser p = new org.mozilla.javascript.Parser(compilerEnv, null);
		ScriptOrFnNode astRoot = p.parse(reader, sourceURI, 1);

		return astRoot;
	}
	//========================================================================================================

	
	
	
	

	//========================================================================================================
	//getting the function names and its start line and end lines
	public static FunctionDetails[] GetFunctionAndLines(ScriptOrFnNode astRoot,int totalFNcount,FunctionDetails[] fd,String filename) throws IOException
	{
		int count=astRoot.getFunctionCount();
		for(int i=0;i<count;i++)
		{

			FunctionNode FN = astRoot.getFunctionNode(i);
			String funcName = FN.getFunctionName();
			int linenumber = FN.getLineno();
			int endlinenumber = FN.getEndLineno();


			//int c = FN.getParamAndVarCount();
			//System.out.println("Param count="+c);

			//String[] params = FN.getParamAndVarNames();
			//System.out.println(params[0]);

			/*if(FN.getType()== Token.FUNCTION)
	                                  {
	                                	  System.out.println("It is a Function");
	                                  }*/

			fd[currentObjArrayPosition] = new FunctionDetails();
			fd[currentObjArrayPosition].setFileName(filename);
			fd[currentObjArrayPosition].setFunctionName(funcName);
			fd[currentObjArrayPosition].setFunctionStartPos(linenumber);
			fd[currentObjArrayPosition].setFunctionEndPos(endlinenumber);

			//				String[] sn = FN.getParamAndVarNames();
			//				
			//				
			//				int paramcount = sn.length;
			//				
			//				for(int j=0;j<paramcount;j++)
			//				{
			//					System.out.println(sn[j]);
			//				}
			//				



			currentObjArrayPosition++;

			//FN.getNext();
		}

		return (fd);

	}   
	//========================================================================================================
	
	
	
	
	
	
	//========================================================================================================
	//getting the method names for the lines 
	public static void getMethodsChangedByLineNumber(FunctionDetails fd[],int linenumber,String filename)
	{
		int flag=0;
		int count = fd.length;
		for(int i=0;i<count;i++)
		{

			if(fd[i].getFileName().equals(filename))
			{
				if((linenumber >= fd[i].getFunctionStartPos()) && (linenumber <= fd[i].getFunctionEndPos()))
				{
					flag = 1;
					System.out.println("\nFunction that was Changed is : \nFunction "+fd[i].getFunctionName()+"() "+fd[i].getFunctionStartPos()+" - "+fd[i].getFunctionEndPos());
				}
			}
		}
		if(flag==0)
		{
			System.out.println("\nChange is not inside any function");
		}

	}

	//========================================================================================================



}
