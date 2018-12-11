package common;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class generateDebugParser {
	
	  public static void createSubparser(String path) {
		  String subparser="package compiler.syntax;\r\n" + 
		  		"\r\n" + 
		  		"import java.io.FileReader;\r\n" + 
		  		"import java.io.Reader;\r\n" + 
		  		"import java.lang.reflect.Constructor;\r\n" + 
		  		"import es.uned.lsi.compiler.lexical.ScannerIF;\r\n" +
		  		"public class subparser extends parser{\r\n" + 
		  		"	//Insertar aqui la extensión de los ficheros de tests\r\n" + 
		  		"	public static String extension=\".cuned\";	\r\n" + 
		  		"	public subparser() {}\r\n" + 
		  		"	public subparser(ScannerIF s) {super(s);}\r\n" + 
		  		"	public static String error=\"\";\r\n" + 
		  		"	public static String getError() {return error;}\r\n" + 
		  		"	public static void executeTest(String fileName) {\r\n" + 
		  		"		  try {\r\n" + 
		  		"			  FileReader aFileReader = new FileReader (fileName);\r\n" + 
		  		"			  Class scannerClass = Class.forName (\"compiler.lexical.Scanner\"); \r\n" + 
		  		"			  Constructor scannerConstructor = scannerClass.getConstructor(Reader.class);\r\n" + 
		  		"			  ScannerIF aScanner = (ScannerIF) scannerConstructor.newInstance(aFileReader);\r\n" + 
		  		"			  subparser s=new subparser(aScanner);\r\n" + 
		  		"			  try {\r\n" + 
		  		"			  s.parse();}\r\n" + 
		  		"			  catch(Exception npe) {\r\n" + 
		  		"				  error=\"#ERROR: \"+npe.toString();\r\n" + 
		  		"			  }		  \r\n" + 
		  		"		  }catch(Exception e) {}\r\n" + 
		  		"	}	 \r\n" + 
		  		"}";
		  	File file = new File(path+"src"+File.separator+"compiler"+File.separator+"syntax"+File.separator+"subparser.java");
		  	if(!file.exists())
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(subparser);
				fileWriter.close();
			} catch (IOException e) {
				Mensaje.print("ScopeManagerWorker", "No se ha podido generar la clase ScopeManagerWorker");
				e.printStackTrace();
			}
	  }
	  
	  public static void createSMWorker(String path) {
		  String worker="package compiler.syntax;\r\n" + 
		  		"\r\n" + 
		  		"import java.util.ArrayList;\r\n" + 
		  		"import java.util.Iterator;\r\n" + 
		  		"import java.util.List;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.Scope;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.ScopeIF;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.ScopeManagerIF;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.symbol.SymbolIF;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.type.TypeIF;\r\n" + 
		  		"import es.uned.lsi.compiler.semantic.type.TypeTableIF;\r\n" + 
		  		"\r\n" + 
		  		"public class ScopeManagerWorker {\r\n" + 
		  		"    public static void logSemantics (ScopeManagerIF scopeManager,ArrayList<String>tipos, ArrayList<String>syms)\r\n" + 
		  		"    {\r\n" + 
		  		"        tipos.add(logTypeTables (scopeManager));\r\n" + 
		  		"        syms.add(logSymbolTables (scopeManager));\r\n" + 
		  		"    }\r\n" + 
		  		"    private static String logTypeTables (ScopeManagerIF scopeManager)\r\n" + 
		  		"    {\r\n" + 
		  		"        Iterator<ScopeIF> scopesIt = scopeManager.getOpenScopes().iterator ();\r\n" + 
		  		"        //Que sentido tiene la lista dumped?\r\n" + 
		  		"        List<TypeTableIF> dumpedTypeTables = new ArrayList<TypeTableIF> ();\r\n" + 
		  		"        String cadena=\"\";\r\n" + 
		  		"        while (scopesIt.hasNext ())\r\n" + 
		  		"        {\r\n" + 
		  		"            Scope aScope = (Scope) scopesIt.next ();\r\n" + 
		  		"            TypeTableIF aTypeTable = aScope.getTypeTable ();\r\n" + 
		  		"            if (!dumpedTypeTables.contains (aTypeTable)) {\r\n" + 
		  		"            	cadena+=logTypeTable (aTypeTable)+\"\\n\";\r\n" + 
		  		"            	\r\n" + 
		  		"            }\r\n" + 
		  		"        }\r\n" + 
		  		"        return cadena;\r\n" + 
		  		"    }\r\n" + 
		  		"    private static String logTypeTable(TypeTableIF typeTable)\r\n" + 
		  		"    {	\r\n" + 
		  		"        Iterator<TypeIF> typesIt = typeTable.getTypes ().iterator ();\r\n" + 
		  		"        String cadena=\"\";\r\n" + 
		  		"        while (typesIt.hasNext ())\r\n" + 
		  		"        {\r\n" + 
		  		"            TypeIF aType = (TypeIF) typesIt.next ();\r\n" + 
		  		"            cadena+=aType.toString()+\"\\n\";\r\n" + 
		  		"        }\r\n" + 
		  		"        return cadena;\r\n" + 
		  		"    }\r\n" + 
		  		"    private static String logSymbolTables (ScopeManagerIF scopeManager)\r\n" + 
		  		"    {\r\n" + 
		  		"        Iterator<ScopeIF> scopesIt = scopeManager.getOpenScopes().iterator ();\r\n" + 
		  		"        String cadena=\"\";\r\n" + 
		  		"        while (scopesIt.hasNext ())\r\n" + 
		  		"        {\r\n" + 
		  		"            ScopeIF scope = (ScopeIF) scopesIt.next ();\r\n" + 
		  		"            cadena+=logSymbolTable (scope.getSymbolTable ());\r\n" + 
		  		"        }\r\n" + 
		  		"        return cadena;\r\n" + 
		  		"    }\r\n" + 
		  		"    private static String logSymbolTable (SymbolTableIF symbolTable)\r\n" + 
		  		"    {\r\n" + 
		  		"        Iterator<SymbolIF> symbolsIt = symbolTable.getSymbols ().iterator ();\r\n" + 
		  		"        String cadena=\"\";\r\n" + 
		  		"        while (symbolsIt.hasNext ())\r\n" + 
		  		"        {\r\n" + 
		  		"            SymbolIF aSymbol = (SymbolIF) symbolsIt.next ();\r\n" + 
		  		"            cadena+=aSymbol.toString()+\"\\n\";\r\n" + 
		  		"        }\r\n" + 
		  		"        return cadena;\r\n" + 
		  		"    }\r\n" + 
		  		"}\r\n" + 
		  		"";
		  	File file = new File(path+"src"+File.separator+"compiler"+File.separator+"syntax"+File.separator+"ScopeManagerWorker.java");
		  	if(!file.exists())
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(worker);
				fileWriter.close();
			} catch (IOException e) {
				Mensaje.print("ScopeManagerWorker", "No se ha podido generar la clase ScopeManagerWorker");
				e.printStackTrace();
			}
	  }
	  
	  public static void modificarParser(String inputFile) {
		  File original=new File(inputFile+File.separator+"parser.java");
		  File backup=new File(inputFile+File.separator+"parser.java.backup");
		  original.renameTo(backup);
		  File nuevo=new File(inputFile+File.separator+"parser.java");
		  
		  try {
			writeParser(nuevo,backup);
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		  backup.renameTo(original);
	  }
	private static void writeParser(File original, File backup) throws IOException {
		ArrayList<String> listaProducciones = new ArrayList<String>();
		FileReader fr = new FileReader(backup);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fileWriter = new FileWriter(original);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write("//BUILDED by MV");
		String cadena="";
		boolean incase=false;
		boolean inSwitch=false;
		boolean printedProd=false;
		String produccion="";
		try {
		while((cadena=br.readLine()) != null) {
			if(cadena.trim().equals("//BUILDED by MV")) {
				backup.renameTo(original);break;
			}
			if(cadena.contains("public void syntax_error(Symbol symbol)")) {
				bw.write("	public static void addOutput(String s) {System.out.println(s);listaOut.add(s);ScopeManagerWorker.logSemantics(CompilerContext.getScopeManager(), listaTypes, listaSyms);}\r\n" + 
						"	public static ArrayList<String> listaTypes=new ArrayList<String>();\r\n" + 
						"	public static ArrayList<String> listaSyms=new ArrayList<String>();\r\n" + 
						"	public static ArrayList<String> listaOut=new ArrayList<String>();\r\n" + 
						"	public static ArrayList<String> getListOut(){return listaOut;}\r\n" + 
						"	public static ArrayList<String> getListTypes(){return listaTypes;}\r\n" + 
						"	public static ArrayList<String> getListSyms(){return listaSyms;}\r\n" + 
						"	public static void clear() {listaTypes.clear();listaSyms.clear();listaOut.clear();CompilerContext.getScopeManager().closeScope();}\r\n" + 
						"	public void printTrace(String s) {\r\n" + 
						"		addOutput(s);\r\n" + 
						"	}\r\n");
			}
			//Inicio lectura casos
			if(cadena.contains("switch (CUP$parser$act_num)")) {
				inSwitch=true;				
			}
			if(inSwitch && cadena.contains("case") && !incase) {
				incase=true;			
				produccion=cadena.split("//")[1];
				listaProducciones.add(produccion);
			}
			if(inSwitch && cadena.contains("return CUP$parser$result;")) {
				incase=false;	
				printedProd=false;
				produccion="";
			}
			if(inSwitch && incase && cadena.contains(";") && !cadena.contains("break;") && !cadena.contains("Object RESULT =null;") && !cadena.contains("CUP$parser")  && !cadena.contains("(java_cup.runtime.Symbol)") && cadena.trim().length()>2 && !cadena.trim().startsWith("//") && !(cadena.trim().startsWith("/*") && cadena.trim().endsWith("*/"))) {
				if(!printedProd) {
					produccion = buscarProduccion(produccion.trim(),listaProducciones);
					bw.write(cadena+"parser.printTrace(\"\\n"+produccion+"\");\n"); printedProd=true;
				}else {
					bw.write(cadena+"parser.printTrace(\"   ---   "+cadena.trim().replaceAll("\"", "").replace("\\", "")+"\");\n");
				}
			}
			else{
				bw.write(cadena+"\n");
			}
		}
		}catch (Exception e) {e.printStackTrace();}
		finally {
			br.close();bw.close();
		}
		
	}
	private static String buscarProduccion(String produccion, ArrayList<String> listaProducciones) {
		produccion=produccion.replace(" ::=","");
		if(produccion.startsWith("NT$")) {
			for(String s: listaProducciones) {
				//System.out.println("buscando: "+produccion+ " -> en -> "+s);
				if(s.contains(produccion+" ") && !s.contains(produccion+" ::=")) {
					//System.out.println("Encontrado "+s);
					return s;
				}
			}
		}
		return produccion;
	}
}
