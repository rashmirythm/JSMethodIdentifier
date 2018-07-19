
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

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


public class Client {
               public static void main(String Args[]) throws IOException{
            	   
            	   
                              String commitHash = "b52744f36374cab916b9fcd36f2033da8bf91fb9";
                              HashSet<String> changedMethods = new HashSet<String>();
                              changedMethods = MethodDiff.FindMethodsChanged(commitHash);
                              System.out.println("");
                              
                             CompilerEnvirons compilerEnv = new CompilerEnvirons();
                      	     ErrorReporter errorReporter = compilerEnv.getErrorReporter();
                      	     
                              File testScript = new File("C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt");
                              String sourceURI;
                              try {
                       	       sourceURI = testScript.getCanonicalPath();
                       	     } catch (IOException e) {
                       	       sourceURI = testScript.toString();
                       	     }
                              Reader reader = new FileReader(testScript);
                              
                              org.mozilla.javascript.Parser p = new org.mozilla.javascript.Parser(compilerEnv, null);
                              ScriptOrFnNode astRoot = p.parse(reader, sourceURI, 1);
                              
                              int count = astRoot.getFunctionCount();
                              System.out.println("Function Count="+count);
                              System.out.println("");
                              
                              FunctionDetails fd[] = new FunctionDetails[count];
                              
                              for(int i=0;i<count;i++)
                              {
                                  
                                  FunctionNode FN = astRoot.getFunctionNode(i);
                                  String funcName = FN.getFunctionName();
                                  int linenumber = FN.getLineno();
                                  int endlinenumber = FN.getEndLineno();
                                  /*if(FN.getType()== Token.FUNCTION)
                                  {
                                	  System.out.println("It is a Function");
                                  }*/
                                  
                                  fd[i] = new FunctionDetails();
                                  fd[i].setFunctionName(funcName);
                                  fd[i].setFunctionStartPos(linenumber);
                                  fd[i].setFunctionEndPos(endlinenumber);
                                  
                                  
                                  //FN.getNext();
                                  
                                  
                                  /*System.out.println("Function Name = " + funcName);
                                  System.out.println("Line number=" + linenumber);
                                  System.out.println("End Line number=" + endlinenumber);
                                  System.out.println("");
                                  System.out.println("");*/
                              }
                              
                              
                              for(int i=0;i<count;i++)
                              {
                            	  System.out.println("Function "+fd[i].getFunctionName()+"() "+fd[i].getFunctionStartPos()+" - "+fd[i].getFunctionEndPos());
                              }
                              
                              //boolean flag=true;
                              
                              int linenumber = 16;
                              if(fd[count-1].getFunctionEndPos()< linenumber)
                              {
                            	  //flag = false;
                            	  System.out.println("\nChange is not in any function");
                              }
                              else
                              {
                            	  for(int i=0;i<count;i++)
                            	  {
                            		  if((linenumber >= fd[i].getFunctionStartPos()) && (linenumber <= fd[i].getFunctionStartPos()))
                            		  {
                            			  //flag = true;
                            			  System.out.println("\nFunction that was Changed is : \nFunction "+fd[i].getFunctionName()+"() "+fd[i].getFunctionStartPos()+" - "+fd[i].getFunctionEndPos());
                            			  break;
                            		  }
                            		  else
                            		  {
                            			  //flag = false;
                            			  System.out.println("\nChange is not in any function");
                            		  }
                            	  }
                              }
                              
                              /*if(flag==false)
                              {
                            	  System.out.println("\nChange is not in any function");
                              }*/
                             
                              
                              
                              
                        	  
               }   
               
               
               
               
               
}
