package finalCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CargarOperaciones {
	public static void main(String[] args) {

		  String path="C:\\runtime-New_configuration\\PEDPL2\\";
		  ArrayList<Operacion> ci = cargar(path);
		  System.out.println("se ha realizado la carga");
		  for(Operacion s: ci) {
			  System.out.println(s);
		  }

		  ArrayList<Operacion> ciOp = getContentOp(path);
		  for(Operacion s: ciOp) {
			  System.out.println(s);
		  }
		  
	}

	public static ArrayList<Operacion> cargar(String path) {
		String fileName=path+"doc"+File.separator+"specs"+File.separator+"parser.cup";	
		ArrayList<String>ci=new ArrayList<String>();
		try {
			FileReader f = new FileReader(fileName);
			BufferedReader b = new BufferedReader(f);
			String cadena="";
			while((cadena = b.readLine())!=null) {
					if(cadena.contains("addQuadruple") && !cadena.contains("addQuadruples")) {
						String aux=cadena.split("addQuadruple")[1].split(",")[0].replaceAll("['(']", "").replaceAll("[')']", "").replaceAll(";", "").replaceAll("\"", "").trim();
						if(!ci.contains(aux) && isUpperCase(aux))ci.add(aux);
					}
			}
		}catch(Exception e) {}
		ArrayList<Operacion> ciOp=new ArrayList<Operacion>();
		for(String s:ci) {
			ciOp.add(new Operacion(s));
		}
		return ciOp;
	}

	//Comparar con el CI generado en el parser, no solo en el EXEC-ENV
	public static ArrayList<Operacion> getContentOp(String path) {
		String fileName=path+"src"+File.separator+"compiler"+File.separator+"code"+File.separator+"ExecutionEnvironmentEns2001.java";
		ArrayList<Operacion>ci=new ArrayList<Operacion>();
		try {
			FileReader f = new FileReader(fileName);
			BufferedReader b = new BufferedReader(f);
			String cadena="";
			String nameOp="";
			boolean caseProcess=false;
			boolean foundTranslate=false;
			String content="";
			while((cadena = b.readLine())!=null) {
				if(cadena.contains("translate") && !foundTranslate) {foundTranslate=true;}
				if(foundTranslate) {
					if(cadena.contains("case ")) {
						caseProcess=true;
						if(!nameOp.isEmpty() && nameOp.trim().length()>0) {
							ci.add(new Operacion(nameOp,content)); nameOp="";content="";
						}
						nameOp= cadena.split("case")[1].split(":")[0].replaceAll("\"", "").trim();
						content+=cadena+"\n";
					}
					else if(cadena.contains("default")) {
						ci.add(new Operacion(nameOp,content)); nameOp="";content=""; break;						
					}
					else if(caseProcess) {
						content+=cadena+"\n";
					}
				}
			}
		}catch(Exception e) {}
		return ci;
	}

	private static boolean isUpperCase(String texto) {
		char[] caracteres=texto.toCharArray();
		for(int i=0;i<texto.length();i++) {
			if(Character.isLowerCase(caracteres[i])) return false;
		}
		return true;
	}

}
