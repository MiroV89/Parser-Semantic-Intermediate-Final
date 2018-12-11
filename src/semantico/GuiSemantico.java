package semantico;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.TransferHandler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pl2.editors.PL2_Editor;
import sintactico.*;

import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;

import common.Mensaje;

import org.eclipse.swt.layout.GridData;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JRootPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import org.eclipse.swt.widgets.Label;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiSemantico extends Composite{
	private Text text_1;
	private ExpandBar expandBar;
	private ArrayList<JEditorPane> editorPaneList=new ArrayList<JEditorPane>();
	private ArrayList<ExpandItem> expandItemList=new ArrayList<ExpandItem>();
	private int currentFontSize=14;
	private String path="";

	public GuiSemantico(PL2_Editor pl2_Editor, Composite parent, int none, String path) {
		super(parent, SWT.NONE);
		this.path=path;
		    setLayout(new FillLayout(SWT.HORIZONTAL));
		    
		    expandBar = new ExpandBar(this, SWT.V_SCROLL);

			createControls(expandBar);
		    try {
				ParserReader pr = new ParserReader(path);
			    for(NonTerminal nt: pr.getNonterminal())
					createExp(expandBar,nt.getName()+" ::= ",nt.getContent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	}
	private void createControls(ExpandBar bar) {
		ExpandItem xpndtmMain3 = new ExpandItem(bar, SWT.NONE);
	    xpndtmMain3.setText("CONTROL");
	    
	    Composite composite = new Composite(expandBar, SWT.NONE);
	    xpndtmMain3.setControl(composite);
	    
	    Composite composite_1 = new Composite(expandBar, SWT.EMBEDDED);
	    xpndtmMain3.setControl(composite_1);
	    
	    Frame frame = SWT_AWT.new_Frame(composite_1);
	    
	    Panel panel = new Panel();
	    frame.add(panel);
	    panel.setLayout(new BorderLayout(0, 0));
	    
	    JRootPane rootPane = new JRootPane();
	    panel.add(rootPane);
	    GridBagLayout gridBagLayout = new GridBagLayout();
	    gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
	    gridBagLayout.rowHeights = new int[]{0, 0, 0};
	    gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
	    rootPane.getContentPane().setLayout(gridBagLayout);
	    
	    JLabel lblTextSize = new JLabel("Text Size:");
	    GridBagConstraints gbc_lblTextSize = new GridBagConstraints();
	    gbc_lblTextSize.insets = new Insets(0, 0, 5, 5);
	    gbc_lblTextSize.gridx = 0;
	    gbc_lblTextSize.gridy = 0;
	    rootPane.getContentPane().add(lblTextSize, gbc_lblTextSize);
	    
	    JButton button = new JButton("+");
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentFontSize+=2;
	    		for(JEditorPane jpe:editorPaneList) {
	    		    jpe.setFont(new Font("Segoe UI", 0, currentFontSize));
	    		}
	    	}
	    });
	    GridBagConstraints gbc_button = new GridBagConstraints();
	    gbc_button.insets = new Insets(0, 0, 5, 5);
	    gbc_button.gridx = 1;
	    gbc_button.gridy = 0;
	    rootPane.getContentPane().add(button, gbc_button);
	    
	    JButton button_1 = new JButton("-");
	    button_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentFontSize-=2;
	    		for(JEditorPane jpe:editorPaneList) {
	    		    jpe.setFont(new Font("Segoe UI", 0, currentFontSize));
	    		}
	    	}
	    });
	    GridBagConstraints gbc_button_1 = new GridBagConstraints();
	    gbc_button_1.insets = new Insets(0, 0, 5, 5);
	    gbc_button_1.gridx = 2;
	    gbc_button_1.gridy = 0;
	    rootPane.getContentPane().add(button_1, gbc_button_1);
	    
	    JButton btnSaveFile = new JButton("Save");
	    btnSaveFile.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String text="";
	    		for(JEditorPane jpe:editorPaneList) {
	    			text+=jpe.getText().trim()+"\n\r\n";
	    		}
	    		write(text);
	    	}
	    });
	    GridBagConstraints gbc_btnSaveFile = new GridBagConstraints();
	    gbc_btnSaveFile.fill = GridBagConstraints.HORIZONTAL;
	    gbc_btnSaveFile.insets = new Insets(0, 0, 5, 5);
	    gbc_btnSaveFile.gridx = 4;
	    gbc_btnSaveFile.gridy = 0;
	    rootPane.getContentPane().add(btnSaveFile, gbc_btnSaveFile);
	    
	    xpndtmMain3.setHeight(xpndtmMain3.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		
	}
	protected void write(String semantic) {
		String fileName=path+"doc"+File.separator+"specs"+File.separator+"parser.cup";		
		try {
			FileReader f = new FileReader(fileName);
			BufferedReader b = new BufferedReader(f);
			String cadena="";
			String originaltext="";
			while((cadena = b.readLine())!=null) {
				originaltext+=cadena+"\n";
					if(cadena.contains("start with ")) break;
			}
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(originaltext+semantic);
            bufferedWriter.close();
        }
        catch(IOException ex) {
            Mensaje.print("ERROR","Error al escribir el fichero '"+ fileName + "'");
        }
		
	}
	private static boolean isUpperCase(String texto) {
		char[] caracteres=texto.toCharArray();
		for(int i=0;i<texto.length();i++) {
			if(Character.isLowerCase(caracteres[i])) return false;
		}
		return true;
	}
	private void createExp(ExpandBar bar,String name,String content) {
			ExpandItem xpndtmMain3 = new ExpandItem(bar, SWT.NONE);
			xpndtmMain3.setExpanded(false);
		    xpndtmMain3.setText(name);
		    expandItemList.add(xpndtmMain3);
		    Composite composite = new Composite(bar, SWT.NONE);
		    composite.setLayout(new GridLayout(2, false));
		    
		    
		    //Composite size:
		    text_1 = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		    text_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 14));
		    Button btnGetscope = new Button(composite, SWT.NONE);
		    btnGetscope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btnGetsymtable = new Button(composite, SWT.NONE);
		    btnGetsymtable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		    Button btnGettypetable = new Button(composite, SWT.NONE);
		    btnGettypetable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btnGetfactory = new Button(composite, SWT.NONE);
		    btnGetfactory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btnCodebuilder = new Button(composite, SWT.NONE);
		    btnCodebuilder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btnLabelfactory = new Button(composite, SWT.NONE);
		    btnLabelfactory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btnGetsymbol = new Button(composite, SWT.NONE);
		    btnGetsymbol.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		    Button btn1 = new Button(composite, SWT.NONE);
		    btn1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btn2 = new Button(composite, SWT.NONE);
		    btn2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		    Button btn3 = new Button(composite, SWT.NONE);
		    btn3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btn4 = new Button(composite, SWT.NONE);
		    btn4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btn5 = new Button(composite, SWT.NONE);
		    btn5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btn6 = new Button(composite, SWT.NONE);
		    btn6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		    
		    Button btn7 = new Button(composite, SWT.NONE);
		    btn7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		    //Composite size end.
		    
		    Composite composite_1 = new Composite(expandBar, SWT.EMBEDDED);
		    xpndtmMain3.setControl(composite_1);
		    
		    Frame frame = SWT_AWT.new_Frame(composite_1);
		    
		    Panel panel = new Panel();
		    frame.add(panel);
		    panel.setLayout(new BorderLayout(0, 0));
		    
		    JRootPane rootPane = new JRootPane();
		    panel.add(rootPane);
		    GridBagLayout gridBagLayout = new GridBagLayout();
		    gridBagLayout.columnWidths = new int[]{95, 103, 0};
		    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		    gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		    gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		    rootPane.getContentPane().setLayout(gridBagLayout);

		    JScrollPane scrollPane = new JScrollPane();
		    GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		    gbc_scrollPane.gridheight = 14;
		    gbc_scrollPane.fill = GridBagConstraints.BOTH;
		    gbc_scrollPane.gridx = 1;
		    gbc_scrollPane.gridy = 0;
		    rootPane.getContentPane().add(scrollPane, gbc_scrollPane);	
		    //EDITOR PANEL
		    JEditorPane editorPane = new JEditorPane();
		    scrollPane.setViewportView(editorPane);
		    editorPane.setText(content);
		    editorPane.setFont(new Font("Segoe UI", 0, 14));
			editorPaneList.add(editorPane);
		    
		    JButton btnGetscope_1 = new JButton("getScope");
		    GridBagConstraints gbc_btnGetscope_1 = new GridBagConstraints();
		    gbc_btnGetscope_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGetscope_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGetscope_1.gridx = 0;
		    gbc_btnGetscope_1.gridy = 0;
		    rootPane.getContentPane().add(btnGetscope_1, gbc_btnGetscope_1);
		    String getScopeContent="\nScopeIF scope = scopeManager.getCurrentScope();\n";
		    btnGetscope_1.setTransferHandler(new ValueExportTransferHandler(getScopeContent));
		    btnGetscope_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnCreatetype = new JButton("createType");
		    GridBagConstraints gbc_btnCreatetype = new GridBagConstraints();
		    gbc_btnCreatetype.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnCreatetype.insets = new Insets(0, 0, 5, 5);
		    gbc_btnCreatetype.gridx = 0;
		    gbc_btnCreatetype.gridy = 7;
		    rootPane.getContentPane().add(btnCreatetype, gbc_btnCreatetype);
		    String getCreateTypeContent="\nTypeSimple tsType = new TypeSimple(scopeManager.getCurrentScope(), \"Nombre\");\r\n" + 
		    		"scopeManager.getCurrentScope().getTypeTable().addType(\"Nombre\",tsType);\n";
		    btnCreatetype.setTransferHandler(new ValueExportTransferHandler(getCreateTypeContent));
		    btnCreatetype.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    
		    JButton btnGetsymbol_1 = new JButton("getSymbol");
		    GridBagConstraints gbc_btnGetsymbol_1 = new GridBagConstraints();
		    gbc_btnGetsymbol_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGetsymbol_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGetsymbol_1.gridx = 0;
		    gbc_btnGetsymbol_1.gridy = 1;
		    rootPane.getContentPane().add(btnGetsymbol_1, gbc_btnGetsymbol_1);
		    String getSymbolContent="\nSymbolIF symbol = scopeManager.searchSymbol(nombre);\n";
		    btnGetsymbol_1.setTransferHandler(new ValueExportTransferHandler(getSymbolContent));
		    btnGetsymbol_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnLabelfactory_1 = new JButton("LabelFactory");
		    GridBagConstraints gbc_btnLabelfactory_1 = new GridBagConstraints();
		    gbc_btnLabelfactory_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnLabelfactory_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnLabelfactory_1.gridx = 0;
		    gbc_btnLabelfactory_1.gridy = 8;
		    rootPane.getContentPane().add(btnLabelfactory_1, gbc_btnLabelfactory_1);
		    String getLabelFactoryContent="\nLabelFactory labelFactory = new LabelFactory();\n";
		    btnLabelfactory_1.setTransferHandler(new ValueExportTransferHandler(getLabelFactoryContent));
		    btnLabelfactory_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnGettype = new JButton("getType");
		    GridBagConstraints gbc_btnGettype = new GridBagConstraints();
		    gbc_btnGettype.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGettype.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGettype.gridx = 0;
		    gbc_btnGettype.gridy = 2;
		    rootPane.getContentPane().add(btnGettype, gbc_btnGettype);
		    String getTypeContent="\nTypeIF tipo = scopeManager.searchType(\"Nombre\");\n";
		    btnGettype.setTransferHandler(new ValueExportTransferHandler(getTypeContent));
		    btnGettype.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnGetfactory_1 = new JButton("TemporalFactory");
		    GridBagConstraints gbc_btnGetfactory_1 = new GridBagConstraints();
		    gbc_btnGetfactory_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGetfactory_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGetfactory_1.gridx = 0;
		    gbc_btnGetfactory_1.gridy = 9;
		    rootPane.getContentPane().add(btnGetfactory_1, gbc_btnGetfactory_1);
		    String getTempFactoryContent="\nTemporalFactory temporalFactory= new TemporalFactory(scope);\n";
		    btnGetfactory_1.setTransferHandler(new ValueExportTransferHandler(getTempFactoryContent));
		    btnGetfactory_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnGetsymtable_1 = new JButton("getSymTable");
		    GridBagConstraints gbc_btnGetsymtable_1 = new GridBagConstraints();
		    gbc_btnGetsymtable_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGetsymtable_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGetsymtable_1.gridx = 0;
		    gbc_btnGetsymtable_1.gridy = 3;
		    rootPane.getContentPane().add(btnGetsymtable_1, gbc_btnGetsymtable_1);
		    String getSymTableContent="\nSymbolTableIF symTable = scope.getSymbolTable();\n";
		    btnGetsymtable_1.setTransferHandler(new ValueExportTransferHandler(getSymTableContent));
		    btnGetsymtable_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnCodebuilder_1 = new JButton("CodeBuilder");
		    GridBagConstraints gbc_btnCodebuilder_1 = new GridBagConstraints();
		    gbc_btnCodebuilder_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnCodebuilder_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnCodebuilder_1.gridx = 0;
		    gbc_btnCodebuilder_1.gridy = 10;
		    rootPane.getContentPane().add(btnCodebuilder_1, gbc_btnCodebuilder_1);
		    String getCodeBuilderContent="\nIntermediateCodeBuilder codeBuilder = new IntermediateCodeBuilder(scope);\n";
		    btnCodebuilder_1.setTransferHandler(new ValueExportTransferHandler(getCodeBuilderContent));
		    btnCodebuilder_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnGettypetable_1 = new JButton("getTypeTable");
		    GridBagConstraints gbc_btnGettypetable_1 = new GridBagConstraints();
		    gbc_btnGettypetable_1.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnGettypetable_1.insets = new Insets(0, 0, 5, 5);
		    gbc_btnGettypetable_1.gridx = 0;
		    gbc_btnGettypetable_1.gridy = 4;
		    rootPane.getContentPane().add(btnGettypetable_1, gbc_btnGettypetable_1);
		    String getTypeTableContent="\nTypeTableIF typeTable = scope.getTypeTable();\n";
		    btnGettypetable_1.setTransferHandler(new ValueExportTransferHandler(getTypeTableContent));
		    btnGettypetable_1.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnPrintTs = new JButton("Print TS");
		    GridBagConstraints gbc_btnPrintTs = new GridBagConstraints();
		    gbc_btnPrintTs.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnPrintTs.insets = new Insets(0, 0, 5, 5);
		    gbc_btnPrintTs.gridx = 0;
		    gbc_btnPrintTs.gridy = 11;
		    rootPane.getContentPane().add(btnPrintTs, gbc_btnPrintTs);
		    String printTSContent="\nSymbolTableIF symTable = scope.getSymbolTable();\r\n"
		    		+ "List<SymbolIF> symList = symTable.getSymbols();\n"
		    		+ "for(SymbolIF sym:symList){System.out.println(\"Simbolo: \"+sym+\"\\n\");}\n";
		    btnPrintTs.setTransferHandler(new ValueExportTransferHandler(printTSContent));
		    btnPrintTs.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnContainssymbol = new JButton("containsSymbol");
		    GridBagConstraints gbc_btnContainssymbol = new GridBagConstraints();
		    gbc_btnContainssymbol.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnContainssymbol.insets = new Insets(0, 0, 5, 5);
		    gbc_btnContainssymbol.gridx = 0;
		    gbc_btnContainssymbol.gridy = 5;
		    rootPane.getContentPane().add(btnContainssymbol, gbc_btnContainssymbol);
		    String containsSymContent="\nboolean contains=scopeManager.containsSymbol(\"Nombre\");\r\n";
		    btnContainssymbol.setTransferHandler(new ValueExportTransferHandler(containsSymContent));
		    btnContainssymbol.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnPrintTt = new JButton("Print TT");
		    GridBagConstraints gbc_btnPrintTt = new GridBagConstraints();
		    gbc_btnPrintTt.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnPrintTt.insets = new Insets(0, 0, 5, 5);
		    gbc_btnPrintTt.gridx = 0;
		    gbc_btnPrintTt.gridy = 12;
		    rootPane.getContentPane().add(btnPrintTt, gbc_btnPrintTt);
		    String printTTContent="\nTypeTableIF typeTable = scope.getTypeTable();\r\n"
		    		+ "List<TypeIF> typeList = typeTable.getTypes();\n"
		    		+ "for(TypeIF type:typeList){System.out.println(\"Tipo: \"+type+\"\\n\");}\n";
		    btnPrintTt.setTransferHandler(new ValueExportTransferHandler(printTTContent));
		    btnPrintTt.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnContainsType = new JButton("contains Type");
		    GridBagConstraints gbc_btnContainsType = new GridBagConstraints();
		    gbc_btnContainsType.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnContainsType.insets = new Insets(0, 0, 5, 5);
		    gbc_btnContainsType.gridx = 0;
		    gbc_btnContainsType.gridy = 6;
		    rootPane.getContentPane().add(btnContainsType, gbc_btnContainsType);
		    String containsTypeContent="\nboolean contains=scopeManager.containsSymbol(\"Nombre\");\r\n";
		    btnContainsType.setTransferHandler(new ValueExportTransferHandler(containsTypeContent));
		    btnContainsType.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    
		    JButton btnPrintCi = new JButton("Print CI");
		    GridBagConstraints gbc_btnPrintCi = new GridBagConstraints();
		    gbc_btnPrintCi.fill = GridBagConstraints.HORIZONTAL;
		    gbc_btnPrintCi.insets = new Insets(0, 0, 0, 5);
		    gbc_btnPrintCi.gridx = 0;
		    gbc_btnPrintCi.gridy = 13;
		    rootPane.getContentPane().add(btnPrintCi, gbc_btnPrintCi);
		    String printCIContent="\nScopeIF scope = scopeManager.getCurrentScope();\r\n" + 
		    		"	IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);\r\n" +
		    		"	List<QuadrupleIF> intermediateCode = cb.create();\r\n" + 
		    		"	String  cuadruplas=\"\";\r\n" + 
		    		"	for(QuadrupleIF q: intermediateCode){\r\n" + 
		    		"		cuadruplas+= q.toString() + \"\\n\";\r\n" + 
		    		"	}		\r\n" + 
		    		"	System.out.println(cuadruplas);\n";
		    btnPrintCi.setTransferHandler(new ValueExportTransferHandler(printCIContent));
		    btnPrintCi.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {}
                public void mouseDragged(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handle = button.getTransferHandler();
                    handle.exportAsDrag(button, e, TransferHandler.COPY);
                }
            });
		    xpndtmMain3.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		    new Label(composite, SWT.NONE);
		    new Label(composite, SWT.NONE);
		    
	}
}
