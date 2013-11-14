import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;


public class AFN {
	
	char lenguaje[] = {'ε',' ','\n',
			'0','1','2','3','4','5','6','7','8','8',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	int inicial;
	int estadoFinal;
	
	Vector<Integer[]> automata;
	Vector<Vector<Integer>> estadostmp;
	int[][] AFN;
	Vector<Character> alfabetoTmp;
	char[] alfabeto;

	public AFN(AFNeOLD afne){
		this.estadostmp = new Vector<Vector<Integer>>();
		this.automata = new Vector<Integer[]>();
		this.addEstado(this.calcularEdo0(afne));
		calcularTabla(afne);
		//depurarAutomata();
	}
	
	public void imprimirAutomata(){
		for(int i=0; i<this.automata.size(); i++){
			for(int j=0; j<this.lenguaje.length;j++){
				
				if(this.automata.elementAt(i)[j] != -1){
					System.out.println("Estado "+i+"["+this.lenguaje[j]+"] = "+this.automata.elementAt(i)[j]);
				}
				
			}
		}
	}
	
	public void imprimirAFN(){
		System.out.print("[ ]");
		for (int i = 0; i < this.alfabeto.length; i++) {
			System.out.print("["+this.alfabeto[i]+"]");
		}
		System.out.println();
		for(int i=0; i<this.AFN.length;i++){
			System.out.print("["+i+"]");
			for (int j = 0; j < this.AFN[0].length; j++) {
				System.out.print("["+this.AFN[i][j]+"]");
			}
			System.out.println();
		}
	}
	
	public void imprimirEstados(){
		for(int i=0; i<this.estadostmp.size(); i++){
			System.out.println("{"+this.estadostmp.elementAt(i)+"}");
		}
	}
	
	public Vector<Integer> calcularEdo0(AFNeOLD afne){
		Vector<Integer> edoInicial = clausura(afne, afne.getInicial());;
		return edoInicial;
	}
	
	public Vector<Integer> clausura(AFNeOLD afne, Integer estado){
		Vector<Integer> respuesta = new Vector<Integer>();
		if(afne.automata.elementAt(estado).elementAt(0).size() == 0){
			respuesta.add(estado);
		}
		else{
			for (int i = 0; i < afne.automata.elementAt(estado).elementAt(0).size(); i++) {
				if(!respuesta.contains(estado)) respuesta.add(estado);
				if(!respuesta.contains(afne.automata.elementAt(estado).elementAt(0).elementAt(i)) && afne.automata.elementAt(estado).elementAt(0).elementAt(i) != estado ){
					respuesta.addAll(this.clausura(afne, afne.automata.elementAt(estado).elementAt(0).elementAt(i)));
				}
			}
		}
		respuesta = this.quitarRepeticiones(respuesta);
		return respuesta;
	}
	
	public Vector<Integer> quitarRepeticiones(Vector<Integer> a){//Metodo para quitar repeticiones de las tranciciones
		HashSet<Integer> hs = new HashSet<Integer>();
		hs.addAll(a);
		a.clear();
		a.addAll(hs);
		return a;
	}
	
	public Vector<Integer> adondeva(AFNeOLD afne, Vector<Integer> estado, char simbolo){
		int posicion = afne.posCaracter(simbolo);
		Vector<Integer> respuesta = new Vector<Integer>();
		Integer estadotmp;
		for(int i=0; i<estado.size();i++){
			for(int j=0; j<afne.automata.elementAt(estado.elementAt(i)).elementAt(posicion).size();j++){
				estadotmp = afne.automata.elementAt(estado.elementAt(i)).elementAt(posicion).elementAt(j);
				Vector<Integer> destino = this.clausura(afne, estadotmp);
				if(destino.size()>0) respuesta.addAll(destino);
			}
		}
		this.quitarRepeticiones(respuesta);
		if(respuesta.size() > 0) Collections.sort(respuesta);
		return respuesta;
	}
	
	public void addEstado(Vector<Integer> nvoEstado){
		this.estadostmp.add(nvoEstado);
		this.automata.add(new Integer[this.lenguaje.length]);
	}
	
	public int existeEstado(Vector<Integer> nvoEstado){
		for(int i=0; i<this.estadostmp.size();i++){
			if(this.equalVector(nvoEstado, this.estadostmp.elementAt(i))) return i;
		}
		return -1;
	}
	
	public boolean equalVector(Vector<Integer> vector1, Vector<Integer> vector2){
		if(vector1.size()!=vector2.size()) return false;
		else{
			for(int i=0; i<vector1.size();i++){
				if(vector1.elementAt(i)!=vector2.elementAt(i)) return false;
			}
		}
		return true;
	}
	
	public void calculaTransiciones(Vector<Integer> estado, AFNeOLD afne){
		int estadoActual = this.existeEstado(estado);
		int estadoTransicion;
		
		Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();
		Vector<Integer> transicion;
		this.automata.elementAt(estadoActual)[0] = -1;
		for (int i = 1; i < afne.lenguaje.length; i++) {
			transicion = this.adondeva(afne, estado, afne.lenguaje[i]);
			vec.add(transicion);
			if(transicion.size() > 0){
				estadoTransicion = this.existeEstado(transicion);
				if(estadoTransicion == -1){
					
					this.addEstado(transicion);
					estadoTransicion = this.existeEstado(transicion);
				}
				this.automata.elementAt(estadoActual)[i] = estadoTransicion;
				
			}
			else this.automata.elementAt(estadoActual)[i] = -1;
		}
	}
	
	public void calcularTabla(AFNeOLD afne){
		int estadosSize = this.estadostmp.size();
		int contador = 0;
		while(contador<estadosSize){
			this.calculaTransiciones(this.estadostmp.elementAt(contador), afne);
			estadosSize = this.estadostmp.size();
			contador++;
		}
	}
	
	public int posAlfabeto(char simbolo){
		for (int i = 0; i < this.alfabeto.length; i++) {
			if(simbolo == this.alfabeto[i]) return i;
		}
		return -1;
	}
	
	public void ordenarAlfabeto(){
		char tmp;
		for (int i = 0; i < this.alfabeto.length-1; i++) {
			if(this.alfabeto[i]>this.alfabeto[i+1]){
				tmp = this.alfabeto[i];
				this.alfabeto[i] = this.alfabeto[i+1];
				this.alfabeto[i+1] = tmp;
			}
		}
	}
	
	public void depurarAutomata(){
		int max = 0;
		int maxtmp = 0;
		for(int i=0; i<this.automata.size(); i++){
			maxtmp = 0;
			for(int j=0; j<this.lenguaje.length;j++){
				if(this.automata.elementAt(i)[j] != -1) maxtmp++;
			}
			if(maxtmp>max) max = maxtmp;
		}
		
		this.AFN = new int[this.automata.size()][max];
		for (int i = 0; i < this.AFN.length; i++) {
			for (int j = 0; j < this.AFN[0].length; j++) {
				this.AFN[i][j] = -1;
			}
		}
		
		this.alfabetoTmp = new Vector<Character>();
		
		for(int i=0; i<this.automata.size(); i++){
			for(int j=0; j<this.lenguaje.length;j++){
				if(this.automata.elementAt(i)[j] != -1){
					if(!this.alfabetoTmp.contains(this.lenguaje[j])) this.alfabetoTmp.add(this.lenguaje[j]);
				}
			}
		}
		this.alfabeto = new char[max];
		for (int i = 0; i < this.alfabeto.length; i++) {
			this.alfabeto[i] = this.alfabetoTmp.elementAt(i);
		}
		ordenarAlfabeto();
		
		for(int i=0; i<this.automata.size(); i++){
			for(int j=0; j<this.lenguaje.length;j++){
				if(this.automata.elementAt(i)[j] != -1){
					System.out.println(this.automata.elementAt(i)[j]);
					this.AFN[i][posAlfabeto(this.lenguaje[j])] = this.automata.elementAt(i)[j];
				}
			}
		}
	}
	
	public static void main(String[] args) throws ExpresionPosfijaException {
		ConvertidorPosfijo posfijo = new ConvertidorPosfijo();
		AFNeOLD e = new AFNeOLD(posfijo.convertir("(ab,cd)"));
		e.imprimirAFNe();
		AFN automata = new AFN(e);
		automata.imprimirAutomata();
		
	}

}