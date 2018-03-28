package com.tool.java2class;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaToClass {
	
	/**
     * compiles a java source file with the given <code>fileName</code>
     * @param fileName
     */
    @SuppressWarnings("unchecked")
	public static void main(String[] args){
    	 /*
         * the compiler will send its messages to this listener
         */
		
		@SuppressWarnings("rawtypes")
		DiagnosticListener listener = new DiagnosticListener() {

            public void report(Diagnostic diagnostic) {
                System.err.println("gond: " + diagnostic.getMessage(null));
                System.err.println("sor: " + diagnostic.getLineNumber());
                System.err.println(diagnostic.getSource());
            }
        };
        
        //getting the compiler object
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
      //  String path = System.getProperty("user.dir");
        String fileName = "D:\\Trains\\JavaProject\\AopProxy\\src\\com\\service\\impl\\PersonServiceImpl.java";
        //String fileName = "D:\\Trains\\JavaProject\\JavaToClass\\src\\com\\tool\\java2class\\JavaToClass.java";
        /*System.out.println(path);
        String fileName ="";*/
        Iterable<? extends JavaFileObject> files = manager.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, listener, null, null, files);
        
        // the compilation occures here
        task.call();
    }
}
