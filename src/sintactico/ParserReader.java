package sintactico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ParserReader {
	private String fileName = "parser.cup";
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private ArrayList<NonTerminal> nonterminal = new ArrayList<NonTerminal>();


	public ParserReader(String path) throws IOException{
		fileName=path+"doc"+File.separator+"specs"+File.separator+fileName;
		String cadena;
			FileReader f = new FileReader(fileName);
		    BufferedReader b = new BufferedReader(f);
			while((cadena = b.readLine())!=null) {
				if(cadena.toLowerCase().startsWith("start with ")) {
					break;
				}
			}
			String text="";
			while((cadena = b.readLine())!=null) {
				text+=cadena+"\n";				
			}					
			nonterminal = creteStructure(text);			
			b.close();
	}

	private ArrayList<NonTerminal> creteStructure(String text) {
		ArrayList<NonTerminal> ntlist = new ArrayList<NonTerminal>();
		text=text.replaceAll(";\n//","; \n//");
		String[] lines = text.split("\n");
		for(int i=0;i<lines.length;i++) {
			boolean endNT=false;
			String ntText="";
			String coms="";
			boolean isOut=true;
			boolean comsEnd=false;
			while(!endNT) {
				if(i==lines.length)break;
				if((lines[i].startsWith("//") || lines[i].trim().isEmpty()) && !comsEnd) {
					coms+=lines[i]+"\n";
					i++;
				}
				else {
					comsEnd=true;
					char[] chars=lines[i].toCharArray();
					for(int ind=0;ind<chars.length-1;ind++) {
						if(chars[ind]=='{' && chars[ind+1]==':') isOut=false;
						if(chars[ind]==':' && chars[ind+1]=='}') isOut=true;
					}
					ntText+=lines[i]+"\n";
					if(isOut) {
						if(ntText.replaceAll("\r", " ").replaceAll("\t"," ").replaceAll("\\s+", " ").replaceAll("\n"," ").replaceAll("['{:'].*[':}']", " ").contains(";")) {
							endNT=true;
						}
					}
					i++;
				}
			}
			NonTerminal n = createNT(coms,ntText);
			if(n!=null)	ntlist.add(n);
		}
		
		return ntlist;
	}

	private NonTerminal createNT(String coms,String ntText) {
		//ntText=espaciar(ntText); // Revisar
		//System.out.println("...\n\n"+ntText+"\n\n...");
		String[] words = ntText.split(" ");
		NonTerminal nt = new NonTerminal(words[0]);
		if(nt!=null)nt.setContent(coms+"\n"+ntText);
		/*Produccion p = new Produccion();
		for(int i=2; i<words.length;i++) {
			if(words[i].trim().isEmpty()) {}
			else if(words[i].trim().equals("{:")) {
				i++;
				String semContent="";
				//Comienzo de regla semantica:
				while(!words[i].trim().equals(":}") && i<words.length) {
					semContent+=words[i];i++;
				}
				p.add(new SemanticProduction(semContent));
			}
			else if(words[i].contains(":")) {
				String name=words[i].split(":")[0];
				String subname=words[i].split(":")[1];
				if(isUpperCase(name)) p.add(searchToken(name));
				else p.add(searchNT(name));
				p.add(new Subname(subname));
			}
			else if(words[i].contains("|")) {
				nt.addProduccion(p);
				p=new Produccion();
			}
			else if(words[i].trim().equals(";")) {
				nt.addProduccion(p);
				p=null;
			}
			else if(p!=null){
				if(isUpperCase(words[i])){ 
					Token t=searchToken(words[i]);
					if(t!=null)p.add(t);
				}
				else {
					NonTerminal nont=searchNT(words[i]);
					if(nont!=null)p.add(nt);
				}
			}
		}
		if(nt!=null && p!=null)nt.addProduccion(p);*/
		return nt;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}


	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}


	public ArrayList<NonTerminal> getNonterminal() {
		
		return nonterminal;
	}


	public void setNonterminal(ArrayList<NonTerminal> nonterminal) {
		this.nonterminal = nonterminal;
	}


}
