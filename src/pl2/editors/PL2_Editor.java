package pl2.editors;


import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.Collator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import javax.swing.JTextPane;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import common.Mensaje;
import finalCode.FinalPagePL2;
import semantico.GuiSemantico;
import tests.TestPagePL2;

import org.eclipse.ui.ide.IDE;

/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class PL2_Editor extends MultiPageEditorPart implements IResourceChangeListener{

	/** The text editor used in page 0. */
	private static TextEditor editor;

	/** The font chosen in page 1. */
	private Font font;

	/** The text widget used in page 2. */
	private StyledText text;
	/**
	 * Creates a multi-page editor example.
	 */
	public PL2_Editor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	
	private String getFilePath() {
		IPath path = ((IFileEditorInput)getEditorInput()).getFile().getLocation();
		if (path != null && path.toString().endsWith("scanner.flex")){
			return path.toOSString().replace("doc"+File.separator+"specs"+File.separator+"scanner.flex", "");
		}
		if (path != null && path.toString().endsWith("parser.cup")) {
			return path.toOSString().replace("doc"+File.separator+"specs"+File.separator+"parser.cup", "");
		}		
		return null;		
	}
	
	void createPage0() {
		try {
			editor = new TextEditor();
			File file=new File(getFilePath()+"doc"+File.separator+"specs"+File.separator+"parser.cup");
			IWorkspace workspace= ResourcesPlugin.getWorkspace();   
			IPath location= Path.fromOSString(file.getAbsolutePath());
			IFile ifile= workspace.getRoot().getFileForLocation(location);
			IEditorInput ie = new FileEditorInput(ifile);
			int index = addPage(editor, ie);
			setPageText(index, editor.getTitle());			
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}
	}
	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
	void createPage1() {

		String path = getFilePath();
		GuiSemantico composite = new GuiSemantico(this, getContainer(),SWT.NONE,path);		      
	    int index = addPage(composite);
	    setPageText(index, "Semantico");
	}
	
	public static IProject getProj() {
		if(editor!=null)
		return ((FileEditorInput)editor.getEditorInput()).getFile().getProject();
		return null;
	}
	
	public static Class getSomeClass(String className,JTextPane jt) {
		Class parserClass=null;
		try {
			
			jt.setText(jt.getText()+"\nAntes de getProj");
			IProject proj = getProj();				

			jt.setText(jt.getText()+"\nDespues de getProj");
		String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(JavaCore.create(proj));
		jt.setText(jt.getText()+"\nDespues de JavaCore.create");
		jt.setText(jt.getText()+classPathEntries+"");
		jt.setText(jt.getText()+classPathEntries.length+"");
		List<URL> urlList = new ArrayList<URL>();
		for (int i = 0; i < classPathEntries.length; i++) {
		 String entry = classPathEntries[i];
		 jt.setText(jt.getText()+entry);
		 IPath path = new Path(entry); 
		 URL url = path.toFile().toURI().toURL();
		 urlList.add(url);
		}
		ClassLoader parentClassLoader = JavaCore.create(proj).getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
		jt.setText(jt.getText()+classLoader.toString());
		parserClass = classLoader.loadClass(className);	
		jt.setText(jt.getText()+parserClass.toString());
		} catch (Exception e) {
			jt.setText(jt.getText()+e.toString());
			e.printStackTrace();
		}
		return parserClass;
	}
	
	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
	void createPage2() {
			
			
			String path = getFilePath();
			FinalPagePL2 finalpage = new FinalPagePL2(getContainer(),SWT.NONE,path);		      
		    int index = addPage(finalpage);
		    setPageText(index, "Codigo final");
		}
	void createPage3() {		
		String path = getFilePath();
		TestPagePL2 testpage = new TestPagePL2(getContainer(),SWT.NONE,path);		      
	    int index = addPage(testpage);
	    setPageText(index, "Tests");
	}
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPage0();
		createPage1();
		createPage2();
		createPage3();
	}
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}
	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}
	/**
	 * Sets the font related data to be applied to the text in page 2.
	 */
	void setFont() {
		FontDialog fontDialog = new FontDialog(getSite().getShell());
		fontDialog.setFontList(text.getFont().getFontData());
		FontData fontData = fontDialog.open();
		if (fontData != null) {
			if (font != null)
				font.dispose();
			font = new Font(text.getDisplay(), fontData);
			text.setFont(font);
		}
	}
	/**
	 * Sorts the words in page 0, and shows them in page 2.
	 */
	void sortWords() {

		String editorText =editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();

		StringTokenizer tokenizer =
			new StringTokenizer(editorText, " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
		List<String> editorWords = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			editorWords.add(tokenizer.nextToken());
		}

		Collections.sort(editorWords, Collator.getInstance());
		StringWriter displayText = new StringWriter();
		for (int i = 0; i < editorWords.size(); i++) {
			displayText.write(((String) editorWords.get(i)));
			displayText.write(System.getProperty("line.separator"));
		}
		text.setText(displayText.toString());
	}
}
