package com.tool.java2class;

import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class LinkCompiler {
	public void compiler(){
		
		//创建编译错误监听器
		@SuppressWarnings("rawtypes")
		DiagnosticListener listener = new DiagnosticListener() {
            public void report(Diagnostic diagnostic) {
                System.err.println("gond: " + diagnostic.getMessage(null));
                System.err.println("sor: " + diagnostic.getLineNumber());
                System.err.println(diagnostic.getSource());
            }
        };
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      //  Iterable<String> options = Arrays.asList("-d","d:");
        
        Iterable<String> options = Arrays.asList("-d","d:");
        
        //获得源文件管理器
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        String fileName = "D:\\Trains\\JavaProject\\AopProxy\\src\\com\\service\\impl\\PersonServiceImpl.java";     
        Iterable<? extends JavaFileObject> files = manager.getJavaFileObjects(fileName);
        
        @SuppressWarnings({ "unused", "unchecked" })
		JavaCompiler.CompilationTask task = 
        		compiler.getTask(null, manager, listener, options, null, files);
        
	/*JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
    String fileName = "D:\\Trains\\JavaProject\\AopProxy\\src\\com\\service\\impl\\PersonServiceImpl.java";
   
    Iterable<? extends JavaFileObject> files = manager.getJavaFileObjects(fileName);
    Iterable<String> options = Arrays.asList(fileName,"");
    @SuppressWarnings("unchecked")
	//JavaCompiler.CompilationTask task = compiler.getTask(null, manager, listener, null, null, files);   
    JavaCompiler.CompilationTask task = compiler.getTask(null, manager, listener, null, null, options); 
    task.call();*/
    }
}
