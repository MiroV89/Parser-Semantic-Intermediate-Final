package sintactico;

import java.util.ArrayList;

public class NonTerminal extends Element{
	private String coms="";
	public boolean raiz=false;
	private String clase="";
	private String semantico="";
	private String semanticName="";
	private String content="";
	public int i=0;
	public int prodSize=0;

	public boolean reduced() {
		if(i<prodSize && prodSize!=0) {
			i++;
			return false;
		}
		else return true;
	}
	public int geti() {return i;}
	public int getsize() {return prodSize;}
	public String getSemanticName() {
		return semanticName;
	}
	public void setSemanticName(String semanticName) {
		this.semanticName = semanticName;
	}
	public String getSemantico() {
		return semantico;
	}
	public void setSemantico(String semantico) {
		this.semantico = semantico;
	}
	public NonTerminal(String name) {
		super();
		this.setName(name);
	}
	public NonTerminal(String clase, String name) {
		super();
		this.clase = clase;
		this.setName(name);
	}
	
	
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getComs() {
		return coms;
	}
	public void setComs(String coms) {
		this.coms = coms;
	}
	public void setContent(String ntText) {
		this.content=ntText;		
	}
	public String getContent() {
		return content;
	}
	
}
