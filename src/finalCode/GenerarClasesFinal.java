package finalCode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import common.Mensaje;

public class GenerarClasesFinal {
	  public static void createData(String path) {
		  String subparser="package compiler.code;\r\n"
				  +"import java.util.HashMap;\r\n" + 
				  "/**\r\n" + 
				  " * Esta clase es la encargada de mantener las cadenas de texto\r\n" + 
				  " */\r\n" + 
				  "public class Data {\r\n" + 
				  "	protected static HashMap<String, String> cadenas= new HashMap<>();\r\n" + 
				  "	protected static int i=0;\r\n" + 
				  "	public HashMap<String, String> getCadenas() {\r\n" + 
				  "		//Devolvemos la lista\r\n" + 
				  "		return cadenas;\r\n" + 
				  "	}	\r\n" + 
				  "	public void setCadenas(HashMap<String, String> cadenas) {\r\n" + 
				  "		//Asignamos la lista de cadenas\r\n" + 
				  "		Data.cadenas = cadenas;\r\n" + 
				  "	}\r\n" + 
				  "	public static void setCadena(String cadena,String key){\r\n" + 
				  "		//Añadimos una nueva cadena \r\n" + 
				  "		cadenas.put(key,cadena);\r\n" + 
				  "	}\r\n" + 
				  "	public static String  getKey() {\r\n" + 
				  "		//Devolvemos el primer nombre de cadena que se encuentra libre y aumentamos el contador\r\n" + 
				  "		return (\"data\"+ i++);\r\n" + 
				  "	}	\r\n" + 
				  "	public static void setKey(int i) {\r\n" + 
				  "		//Asignamos el numero del contador\r\n" + 
				  "		Data.i = i;\r\n" + 
				  "	}\r\n" + 
				  "}";
		  	File file = new File(path+"src"+File.separator+"compiler"+File.separator+"code"+File.separator+"Data.java");
		  	if(!file.exists()) {
				try {
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(subparser);
					fileWriter.close();
					Mensaje.print("OK", "Se ha generado la clase DATA en el paquete compiler.code");
				} catch (IOException e) {
					Mensaje.print("Data", "No se ha podido generar la clase Data");
					e.printStackTrace();
				}
		  	}
		  	else {
		  		Mensaje.print("DATA", "La clase DATA ya existe");
		  	}
	  }
	  
	  public static void createMemory(String path) {
		  String subparser="package compiler.code;\r\n"+
				  "import java.util.ArrayList;\r\n" + 
				  "import java.util.HashMap;\r\n" + 
				  "import java.util.Iterator;\r\n" + 
				  "import java.util.List;\r\n" + 
				  "\r\n" + 
				  "import compiler.CompilerContext;\r\n" + 
				  "import compiler.intermediate.Temporal;\r\n" + 
				  "import compiler.semantic.symbol.SymbolVariable;\r\n" + 
				  "import es.uned.lsi.compiler.intermediate.TemporalIF;\r\n" + 
				  "import es.uned.lsi.compiler.semantic.ScopeIF;\r\n" + 
				  "import es.uned.lsi.compiler.semantic.symbol.SymbolIF;\r\n" + 
				  "/**\r\n" + 
				  " * Esta clase asigna los espacios de memoria para los simbolos y temporales generados *\r\n" + 
				  " */\r\n" + 
				  "public class Memory {\r\n" + 
				  "	private static int gAddress = 2;\r\n" + 
				  "	private static int dataAddress=0;\r\n" + 
				  "	private static List<Integer> tamanoS = new ArrayList<Integer>();\r\n" + 
				  "	public static void asignarDirecciones(){\r\n" + 
				  "		try{\r\n" + 
				  "			int lOffset = 2;\r\n" + 
				  "			//Obtenemos todos los ambitos creados\r\n" + 
				  "			List<ScopeIF> scopes = CompilerContext.getScopeManager().getAllScopes ();\r\n" + 
				  "			for (ScopeIF scope: scopes) { //Sacamos los simbolos de todos los ambitos\r\n" + 
				  "				List<SymbolIF> symbols = scope.getSymbolTable().getSymbols();\r\n" + 
				  "				for (SymbolIF s: symbols) {\r\n" + 
				  "					//Por cada simbolo variable, le asignamos una direccion y aumentamos el contador\r\n" + 
				  "					if (s instanceof SymbolVariable){\r\n" + 
				  "						if (scope.getLevel () == 0){\r\n" + 
				  "							//La variable es global\r\n" + 
				  "							SymbolVariable u= (SymbolVariable) s;\r\n" + 
				  "							u.setAddress (gAddress);\r\n" + 
				  "							gAddress += u.getType().getSize();\r\n" + 
				  "						 \r\n" + 
				  "						}else{ \r\n" + 
				  "							//La variable no es global\r\n" + 
				  "							SymbolVariable u= (SymbolVariable) s;\r\n" + 
				  "							u.setAddress (lOffset);\r\n" + 
				  "							lOffset=lOffset + u.getType().getSize ();\r\n" + 
				  "						}\r\n" + 
				  "					}\r\n" + 
				  "				}//Asignamos espacio de memoria para los temporales del ambito\r\n" + 
				  "				List<TemporalIF> temporals = scope.getTemporalTable ().getTemporals();\r\n" + 
				  "				for (TemporalIF t: temporals){\r\n" + 
				  "					Temporal u= (Temporal) t;\r\n" + 
				  "					u.setAddress (lOffset + u.getSize()); \r\n" + 
				  "					lOffset=lOffset + u.getSize();\r\n" + 
				  "				}\r\n" + 
				  "				tamanoS.add(lOffset);\r\n" + 
				  "				lOffset=2;\r\n" + 
				  "			}\r\n" + 
				  "			dataAddress=gAddress+100;\r\n" + 
				  "			reservaparaData(); //Reservamos espacio para las cadenas de texto\r\n" + 
				  "		}catch(NullPointerException e){\r\n" + 
				  "			e.printStackTrace();\r\n" + 
				  "		}\r\n" + 
				  "		\r\n" + 
				  "	}\r\n" + 
				  "	\r\n" + 
				  "	private static void reservaparaData(){\r\n" + 
				  "		HashMap<String, String> texts = Data.cadenas;\r\n" + 
				  "	 	gAddress+=gAddress+300;\r\n" + 
				  "	 	Iterator<String> it = texts.values().iterator();\r\n" + 
				  "	 	while (it.hasNext()){\r\n" + 
				  "	 		gAddress += it.next().length()+5;\r\n" + 
				  "	 	}\r\n" + 
				  "	}\r\n" + 
				  "\r\n" + 
				  "	public static int getTamanoS(int ins) {\r\n" + 
				  "		if(ins >= tamanoS.size()){\r\n" + 
				  "			try{\r\n" + 
				  "				return tamanoS.get(tamanoS.size()-1);\r\n" + 
				  "			}catch(ArrayIndexOutOfBoundsException e){}\r\n" + 
				  "		}\r\n" + 
				  "		return tamanoS.get(ins);	\r\n" + 
				  "	}\r\n" + 
				  "	public static int getgAddress() {return gAddress;}\r\n" + 
				  "	public static void setgAddress(int gAddress) {Memory.gAddress = gAddress;}\r\n" + 
				  "	public static void setTamanoS(List<Integer> tamanoS) {Memory.tamanoS = tamanoS;} //Asignamos una lista de enteros\r\n" + 
				  "	public static int getDataAddress() {return dataAddress;} //Devolvemos la direccion actual\r\n" + 
				  "	public static void setDataAddress(int dataAddress) {Memory.dataAddress = dataAddress;} //Asignamos la direccion\r\n" + 
				  "}";
		  	File file = new File(path+"src"+File.separator+"compiler"+File.separator+"code"+File.separator+"Memory.java");
		  	if(!file.exists()) {
				try {
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(subparser);
					fileWriter.close();
					Mensaje.print("OK", "Se ha generado la clase Memory en el paquete compiler.code");
				} catch (IOException e) {
					Mensaje.print("Memory", "No se ha podido generar la clase Memory");
					e.printStackTrace();
				}
		  	}
		  	else {
		  		Mensaje.print("Memory", "La clase Memory ya existe");
		  	}
	  }
}
