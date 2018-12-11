package finalCode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import common.Mensaje;

import org.eclipse.swt.layout.FillLayout;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JRootPane;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FinalPagePL2 extends Composite{
	private int currentFontSize=14;
	String lastCISelected="";
	ArrayList<Operacion> listCIOperaciones= new ArrayList<Operacion>();

	public FinalPagePL2(Composite parent, int none, String path) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(this, SWT.EMBEDDED);
		
		Frame frame = SWT_AWT.new_Frame(composite);
		
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JRootPane rootPane = new JRootPane();
		panel.add(rootPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		rootPane.getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		rootPane.getContentPane().add(scrollPane, gbc_scrollPane);
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		
		JButton btnGenerarData = new JButton("Generar DATA");
		GridBagConstraints gbc_btnGenerarData = new GridBagConstraints();
		gbc_btnGenerarData.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarData.gridx = 2;
		gbc_btnGenerarData.gridy = 0;
		rootPane.getContentPane().add(btnGenerarData, gbc_btnGenerarData);
		btnGenerarData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenerarClasesFinal.createData(path);				
			}
		});
		
		JButton btnGenerarMemory = new JButton("Generar MEMORY");
		GridBagConstraints gbc_btnGenerarMemory = new GridBagConstraints();
		gbc_btnGenerarMemory.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarMemory.gridx = 3;
		gbc_btnGenerarMemory.gridy = 0;
		rootPane.getContentPane().add(btnGenerarMemory, gbc_btnGenerarMemory);
		btnGenerarMemory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenerarClasesFinal.createMemory(path);				
			}
		});
		
		JButton button = new JButton("+");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 4;
		gbc_button.gridy = 0;
		rootPane.getContentPane().add(button, gbc_button);
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentFontSize+=2;
	    		textPane.setFont(new Font("Segoe UI", 0, currentFontSize));	    		
	    	}
	    });
		
		JButton button_1 = new JButton("-");
	    button_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentFontSize-=2;
	    		textPane.setFont(new Font("Segoe UI", 0, currentFontSize));	
	    	}
	    });
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 5;
		gbc_button_1.gridy = 0;
		rootPane.getContentPane().add(button_1, gbc_button_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 6;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		rootPane.getContentPane().add(scrollPane_1, gbc_scrollPane_1);

		
		listCIOperaciones=CargarOperaciones.getContentOp(path);
		JList<String> listCI = new JList<String>(new DefaultListModel<String>());
		scrollPane_1.setViewportView(listCI);
		cargarTests(listCI,listCIOperaciones);
		listCI.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selected=listCI.getSelectedValue();
				if(!lastCISelected.trim().isEmpty())
				for(Operacion op:listCIOperaciones) {
					if(op.getName().equals(lastCISelected)) {
						op.setContent(textPane.getText());
					}
				}		
				lastCISelected=selected;
				//Aqui habría que guardar el anterior
				guardarCF(path,listCIOperaciones);
				
				textPane.setText("");
				for(Operacion op:listCIOperaciones) {
					if(op.getName().equals(selected)) {
						textPane.setText(op.getContent());
					}
				}
			}			
		});	

		JButton btnCapturarCi = new JButton("Capturar CI");
		GridBagConstraints gbc_btnCapturarCi = new GridBagConstraints();
		gbc_btnCapturarCi.insets = new Insets(0, 0, 5, 5);
		gbc_btnCapturarCi.gridx = 1;
		gbc_btnCapturarCi.gridy = 0;
		rootPane.getContentPane().add(btnCapturarCi, gbc_btnCapturarCi);
		btnCapturarCi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listCIOperaciones=CargarOperaciones.getContentOp(path);
				cargarTests(listCI,listCIOperaciones);				
			}
		});
		
	}
	
	private void guardarCF(String path, ArrayList<Operacion> listCIOperaciones) {
			String fileName=path+"src"+File.separator+"compiler"+File.separator+"code"+File.separator+"ExecutionEnvironmentEns2001.java";		
			try {
				FileReader f = new FileReader(fileName);
				BufferedReader b = new BufferedReader(f);
				String cadena="";
				String originaltext="";
				while((cadena = b.readLine())!=null) {
					originaltext+=cadena+"\n";
						if(cadena.contains("switch (quadruple.getOperation()){")) break;
				}
				b.close();
	            String fin="\r\n        default: return \"\\n;\"+quadruple.getOperation()+\" \"+quadruple.getResult()+\r\n" + 
	            		"        			\" \"+quadruple.getFirstOperand()+\" \"+quadruple.getSecondOperand();\r\n" + 
	            		"       //Aqui se acaban los casos	\r\n" + 
	            		"    	}   \r\n" + 
	            		"    	//Se capturan los posibles errores y se muestran en el fichero generado *.ens\r\n" + 
	            		"    	//para su depuracion en caso de ser necesario\r\n" + 
	            		"    	}catch(Exception e){return \".\\n.....ERROR.....\\n\"\r\n" + 
	            		"    	+quadruple.getOperation()+\" = \"+quadruple.getResult()+\"=\"+quadruple.getFirstOperand()+\"=\"+\r\n" + 
	            		"    			quadruple.getSecondOperand()+\"\\n.\\n\";} 	\r\n" + 
	            		"    }\r\n" + 
	            		"}";
				FileWriter fileWriter = new FileWriter(fileName);

	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	            bufferedWriter.write(originaltext);
	            for(Operacion op: listCIOperaciones) {
	            	bufferedWriter.write(op.getContent());
	            }
	            bufferedWriter.write(fin);
	            
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            Mensaje.print("ERROR","Error al escribir el fichero '"+ fileName + "'");
	        }
			
		
		
	}
	
	private void cargarTests(JList<String> list,ArrayList<Operacion> listaCI) {
		for(Operacion ci: listaCI) {
			((DefaultListModel<String>)list.getModel()).addElement(ci.getName());
		}		
	}
}
