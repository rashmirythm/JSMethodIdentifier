
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.*;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.ast.*;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;



public class MethodDiff {
	
               private static final String currentfile = null;
               private static Git git;
               private static Repository repository;
               private static FileOutputStream fop = null;
               private static byte[] contentInBytes;
               
               private static String[] FindGitCommitFiles( ObjectId treeId ) throws IOException {
                   Collection<String> pathNames = new ArrayList<>();
                   TreeWalk treeWalk = new TreeWalk( repository );
                   treeWalk.setRecursive( true );
                   treeWalk.setPostOrderTraversal( true );
                   treeWalk.addTree( treeId );
                   while( treeWalk.next() ) {
                     pathNames.add( treeWalk.getPathString() );
                   }
                   
                   return ( String[] )pathNames.toArray( new String[ pathNames.size() ] );
                 }
               
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

    public static HashSet<String> FindMethodsChangedofFiles(String filechanged, ObjectId treeId) throws IOException {
        HashSet<String> changedMethods = new HashSet<>();
        HashMap<String, String> methods = new HashMap<>();
        
        String file1 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt";
                              String file2 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSParentFile.txt";
        
                   RevWalk revWalk = new RevWalk(repository);
                   RevCommit commit = revWalk.parseCommit(treeId);
                   RevTree tree = commit.getTree();
        createFile(tree,file1,filechanged);
                                 RevCommit[] Parents = commit.getParents();
                                                for(RevCommit parent:Parents) {
                                                RevCommit ParentCommit = revWalk.parseCommit(parent.getId());
                                                RevTree ParentTree = ParentCommit.getTree();
                                                createFile(ParentTree,file2,filechanged);
                                                }
               
        MethodDiff md = new MethodDiff();

        return changedMethods;
    }


    public static HashSet<String> FindMethodsChanged(String commitHash) throws IOException {
               File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
                   git = Git.open(gitWorkDir);
                   repository = git.getRepository();
                   HashSet<String> changedMethods = new HashSet<String>();
                   
                   ObjectId treeId = ObjectId.fromString(commitHash);
                   RevWalk revWalk = new RevWalk(repository);
                   RevCommit commit = revWalk.parseCommit(treeId);
                   RevTree tree = commit.getTree();
                   String[] Pathnames=FindGitCommitFiles(tree);
                   System.out.println("List of files:");
                   for(String path:Pathnames) {
                              System.out.println(path);
                   }
                   
                   for (String filename:Pathnames) {
                              changedMethods.addAll(FindMethodsChangedofFiles(filename,treeId));
                   }
                   return changedMethods;
                   
                   
    }
    
    
    
}
