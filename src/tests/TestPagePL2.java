package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.Permission;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import common.*;
import pl2.editors.PL2_Editor;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JRootPane;
import org.eclipse.swt.layout.FillLayout;
import javax.swing.DefaultListModel;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.eclipse.swt.widgets.Label;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class TestPagePL2 extends Composite {
	String ext="";
	String testpath="";
	String rutatests="";
	String ruta="";
	ArrayList<String> listaTests=new ArrayList<String>();
	JTextPane textAreaDebug ;
	ArrayList<String> listtypes=new ArrayList<String>();;
	ArrayList<String> listsyms=new ArrayList<String>();;;
	ArrayList<String> listOut=new ArrayList<String>();;;
	public static int index=0;
	public TestPagePL2(Composite parent, int style, String path) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		generateDebugParser.createSubparser(path);
		generateDebugParser.createSMWorker(path);
		
		Composite composite = new Composite(this, SWT.EMBEDDED);
		
		Frame frame = SWT_AWT.new_Frame(composite);
		
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JRootPane rootPane = new JRootPane();
		panel.add(rootPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{138, 0, 0, 76, 46, 0, 198, 0, 0, 53, 48, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		rootPane.getContentPane().setLayout(gridBagLayout);
		
		
		
		
		JLabel lblTests = new JLabel("Tests:");
		GridBagConstraints gbc_lblTests = new GridBagConstraints();
		gbc_lblTests.anchor = GridBagConstraints.WEST;
		gbc_lblTests.insets = new Insets(0, 0, 5, 5);
		gbc_lblTests.gridx = 0;
		gbc_lblTests.gridy = 1;
		rootPane.getContentPane().add(lblTests, gbc_lblTests);
		
		JLabel lblTablaDeSimbolos = new JLabel("Tabla de Simbolos");
		GridBagConstraints gbc_lblTablaDeSimbolos = new GridBagConstraints();
		gbc_lblTablaDeSimbolos.anchor = GridBagConstraints.WEST;
		gbc_lblTablaDeSimbolos.insets = new Insets(0, 0, 5, 5);
		gbc_lblTablaDeSimbolos.gridx = 3;
		gbc_lblTablaDeSimbolos.gridy = 1;
		rootPane.getContentPane().add(lblTablaDeSimbolos, gbc_lblTablaDeSimbolos);
		
		JLabel lblEjecucin = new JLabel("Ejecuci\u00F3n:");
		GridBagConstraints gbc_lblEjecucin = new GridBagConstraints();
		gbc_lblEjecucin.insets = new Insets(0, 0, 5, 5);
		gbc_lblEjecucin.gridx = 6;
		gbc_lblEjecucin.gridy = 1;
		rootPane.getContentPane().add(lblEjecucin, gbc_lblEjecucin);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		rootPane.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{140, 1, 0};
		gbl_panel_1.rowHeights = new int[]{1, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JList<String> list = new JList<String>(new DefaultListModel<String>());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		panel_1.add(list, gbc_list);
		//Rellenar lista con los distintos tests
		cargarTests(list,path);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 2;
		rootPane.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea textAreaSym = new JTextArea();
		scrollPane.setViewportView(textAreaSym);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.gridheight = 3;
		gbc_scrollPane_3.gridwidth = 5;
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 6;
		gbc_scrollPane_3.gridy = 2;
		rootPane.getContentPane().add(scrollPane_3, gbc_scrollPane_3);
		
		textAreaDebug = new JTextPane();
		scrollPane_3.setViewportView(textAreaDebug);
		
		
		JLabel lblContenidoDelTest = new JLabel("Contenido del test:");
		GridBagConstraints gbc_lblContenidoDelTest = new GridBagConstraints();
		gbc_lblContenidoDelTest.anchor = GridBagConstraints.WEST;
		gbc_lblContenidoDelTest.insets = new Insets(0, 0, 5, 5);
		gbc_lblContenidoDelTest.gridx = 0;
		gbc_lblContenidoDelTest.gridy = 3;
		rootPane.getContentPane().add(lblContenidoDelTest, gbc_lblContenidoDelTest);
		
		JLabel lblTablaDeTipos = new JLabel("Tabla de Tipos");
		GridBagConstraints gbc_lblTablaDeTipos = new GridBagConstraints();
		gbc_lblTablaDeTipos.anchor = GridBagConstraints.WEST;
		gbc_lblTablaDeTipos.insets = new Insets(0, 0, 5, 5);
		gbc_lblTablaDeTipos.gridx = 3;
		gbc_lblTablaDeTipos.gridy = 3;
		rootPane.getContentPane().add(lblTablaDeTipos, gbc_lblTablaDeTipos);
		
		JButton btnProbarTest = new JButton("Cargar Test");
		GridBagConstraints gbc_btnProbarTest = new GridBagConstraints();
		gbc_btnProbarTest.insets = new Insets(0, 0, 5, 5);
		gbc_btnProbarTest.gridx = 0;
		gbc_btnProbarTest.gridy = 0;
		rootPane.getContentPane().add(btnProbarTest, gbc_btnProbarTest);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 2;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 4;
		rootPane.getContentPane().add(scrollPane_2, gbc_scrollPane_2);
		
		JTextArea textAreaTestContent = new JTextArea();
		scrollPane_2.setViewportView(textAreaTestContent);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 3;
		gbc_scrollPane_1.gridy = 4;
		rootPane.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		JTextArea textAreaType = new JTextArea();
		scrollPane_1.setViewportView(textAreaType);
		

		JButton button_2 = new JButton(">");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(index<listOut.size()) {
					Color color=Color.BLACK;
					if(listOut.get(index).startsWith("#"))color=Color.RED;
					appendToPane(textAreaDebug, listOut.get(index)+"\n", color);
					try {
						textAreaSym.setText(listsyms.get(index));
						textAreaType.setText(listtypes.get(index));;
					}catch(IndexOutOfBoundsException ioobe) {}
					index++;
				}else {
					button_2.setEnabled(false);	
				}
				
				
			}
		});
		
		JButton btnFin = new JButton("FIN");
		btnFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaDebug.setText("");
				Color color=Color.BLACK;
				for(String s: listOut) {
					if(s.startsWith("#"))color=Color.RED;
					appendToPane(textAreaDebug, s+"\n", color);
				}
					textAreaSym.setText(listsyms.get(listsyms.size()-1));
					textAreaType.setText(listtypes.get(listtypes.size()-1));
				button_2.setEnabled(false);		
				btnFin.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnFin = new GridBagConstraints();
		gbc_btnFin.insets = new Insets(0, 0, 5, 5);
		gbc_btnFin.gridx = 1;
		gbc_btnFin.gridy = 0;
		rootPane.getContentPane().add(btnFin, gbc_btnFin);
		
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 0;
		rootPane.getContentPane().add(button_2, gbc_button_2);
		
		Label lblPleditor = new Label(composite, SWT.NONE);
		lblPleditor.setBounds(37, 554, 55, 15);
		lblPleditor.setText("PL2-Editor");
		//Realizamos la carga del test indicado
		
		btnProbarTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemExitControl sec = new SystemExitControl();
				sec.forbidSystemExitCall();
				try {
					textAreaDebug.setText(textAreaDebug.getText()+"Comienza la ejecucion");
					//button_2.setEnabled(true);
					//try {
					textAreaDebug.setText(textAreaDebug.getText()+"Comienza la ejecucion");
					String selected="";
					if(list.getSelectedValue()!=null) {
						selected=path+"doc"+File.separator+"test"+File.separator+list.getSelectedValue();
					}else {
						throw new NullPointerException();
					}
					//Creamos el lector del fichero test
					FileReader reader = new FileReader(selected);
					BufferedReader b = new BufferedReader(reader);
					String c="";
					String testText="";
					textAreaTestContent.setText("");
					while((c=b.readLine())!=null) {
						testText+=c+"\n";
					}
					textAreaDebug.setText(textAreaDebug.getText()+"Se lee el fichero del test");
					//Rellenamos el cuadro de texto del test
					textAreaTestContent.setText(testText.replaceAll("\t", " "));
					textAreaDebug.setText(textAreaDebug.getText()+"Rellena el cdt del test");
					
					String parserJava = path+"src"+File.separator+"compiler"+File.separator+"syntax";
					generateDebugParser.modificarParser(parserJava);
					textAreaDebug.setText(textAreaDebug.getText()+"Se modifica el parser "+parserJava);
					Class claseSubparser=PL2_Editor.getSomeClass("compiler.syntax.subparser",textAreaDebug);
					textAreaDebug.setText(textAreaDebug.getText()+"llamada a subparser");
					Constructor parCons = claseSubparser.getConstructor();
					textAreaDebug.setText(textAreaDebug.getText()+"Primera llamada al subparser");
					//Object instancia = parCons.newInstance();
					Object instancia = PL2_Editor.getSomeClass("compiler.syntax.subparser",textAreaDebug);
					textAreaDebug.setText(textAreaDebug.getText()+"Segunda llamada al subparser");
					//Limpieza de las listas y los cuadros de texto
					Method parseMethodClear = claseSubparser.getMethod("clear");
					parseMethodClear.invoke(instancia);
					textAreaDebug.setText(textAreaDebug.getText()+"CLEAR");
					listOut.clear();listtypes.clear();listsyms.clear();
					textAreaSym.setText("");textAreaType.setText("");textAreaDebug.setText("");
					//Ejecucion del test
					Method executeTest= claseSubparser.getMethod("executeTest",String.class);
					try {
						executeTest.invoke(instancia,selected);
					}
					catch(Exception ex) {}
					Method getListOut = claseSubparser.getMethod("getListOut");
					listOut = (ArrayList<String>) getListOut.invoke(instancia);
					textAreaDebug.setText(textAreaDebug.getText()+"listout");
					Method getListTypes = claseSubparser.getMethod("getListTypes");
					listtypes = (ArrayList<String>) getListTypes.invoke(instancia);
					textAreaDebug.setText(textAreaDebug.getText()+"types");
					Method getListSyms = claseSubparser.getMethod("getListSyms");
					listsyms = (ArrayList<String>) getListSyms.invoke(instancia);
					textAreaDebug.setText(textAreaDebug.getText()+"syms");
					Method getError = claseSubparser.getMethod("getError");
					listOut.add((String) getError.invoke(instancia));
					textAreaDebug.setText(textAreaDebug.getText()+"error");
					button_2.setEnabled(true);	
					btnFin.setEnabled(true);
					textAreaDebug.setText(textAreaDebug.getText()+"habilitar boton");
					textAreaDebug.setText("");
					index=0;
					//}catch(Exception err) {bw.write(err.toString());}
				} 
				catch (NullPointerException npe) {
					Mensaje.print("TEST","No se ha seleccionado ningun test.");
					npe.printStackTrace();
				}
				catch (Exception e1) {
					Mensaje.print("ERROR", e1.toString());
					e1.printStackTrace();
				}
				finally {sec.enableSystemExitCall();}
			}
		});
		
		//???
		ruta=path;
		getExtension(path);
		rutatests=path+"src"+File.separator+"compiler"+File.separator+"syntax"+File.separator+"hola.java";
		
		
		
		
		
	}
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
		private void cargarTests(JList<String> list,String path) {
			File folder = new File(path+"doc"+File.separator+"test"+File.separator);
			File[] listofFiles = folder.listFiles();
			String extension=getExtension(path);
			try {
			for(int i=0; i<listofFiles.length; i++) {
				if(listofFiles[i].getName().endsWith(extension)) {
					((DefaultListModel<String>)list.getModel()).addElement(listofFiles[i].getName());
					listaTests.add(listofFiles[i].getName());
				}
			}
			}catch(NullPointerException npe) {npe.printStackTrace();}
			
		}

		private String getExtension(String ruta) {
			//Leer fichero subparser del alumno y cargar la extension 
			//public static String extension=".cuned";
			File f = new File(ruta+File.separator+"src"+File.separator+"compiler"+File.separator+"syntax"+File.separator+"subparser.java");
			try {
				FileReader fr = new FileReader(f);
				@SuppressWarnings("resource")
				BufferedReader b = new BufferedReader(fr);
				String cadena="";
				while((cadena=b.readLine())!=null) {
					if(cadena.contains("public static String extension=")) {
						ext=cadena.replace("public static String extension=","").replaceAll("\"", "").replace(";","").trim();
						return ext;
					}
				}
				b.close();
			} catch (IOException e) {

				Mensaje.print("SubParser", "La clase subparser no ha sido generada todavía. Recuerde generarla.");
			}
			return null;
		}
		
		class SystemExitControl {
			 
		    public class FatalErrorException extends SecurityException {
		    }
		 
		    public void forbidSystemExitCall() {
		        final SecurityManager securityManager = new SecurityManager() {
		            @Override
		            public void checkPermission(Permission permission) {
		                if (permission.getName().contains("exitVM")) {
		                    throw new FatalErrorException();
		                }
		            }
		        };
		        System.setSecurityManager(securityManager);
		    }
		 
		    public void enableSystemExitCall() {
		        System.setSecurityManager(null);
		    }
		}
}