
public class AFD {

	private String[] lenguaje;
	private String[] estados;
	private String[] finales;
	private String[][] tabla;
	private String inicial;

	public AFD(String[] simbolos,String[] estados, String[] finales){
		this.lenguaje = simbolos;
		this.estados = estados;
		this.finales = finales;
		this.inicial = estados[0];
		
		this.tabla = new String[estados.length][lenguaje.length];
	}
	
	public AFD(String[] simbolos,String[] estados, String[] finales, String[][] transiciones){
		this.lenguaje = simbolos;
		this.estados = estados;
		this.finales = finales;
		this.inicial = estados[0];
		
		this.tabla = transiciones;
	}

	public void asignarDestino(String estado, String simbolo, String estadoSiguiente){ //Asigna el estado siguiente a un estadi y un simbolo
		this.tabla[posEstado(estado)][posSimbolo(simbolo)] = estadoSiguiente;	
	}
	
	public void asignarDestino(int posEstado, int posSimbolo, String estadoSiguiente){
		this.tabla[posEstado][posSimbolo] = estadoSiguiente;
	}

	public int posSimbolo(String simbolo){ //Regresa la posicion del simbolo en el alfabeto. Si no lo encuentra regresa -1

		for(int i = 0; i < lenguaje.length; i++){
			if(simbolo.compareTo(lenguaje[i]) == 0){
				return i;
			}
		}
		return -1;
	}

	public int posEstado(String estado){ //Regresa la posicion del estado en la lsita de estados. Si no lo encuentra regresa -1

		for(int i = 0; i < estados.length; i++){
			if(estado.compareTo(estados[i]) == 0){
				return i;
			}
		}
		return -1;
	}

	public String siguienteEstado(String simbolo, String estado){ //Regresa el estado que sigue basado en un simbolo y un estado
		return tabla[posEstado(estado)][posSimbolo(simbolo)];
	}
	
	public String siguienteEstado(int posSimbolo, int posEstado){
		return tabla[posEstado][posSimbolo];
	}
	
	public boolean esFinal(String estado){ //Revisa si el caracter esta en la lista de estados finales
		for(int i = 0; i < this.finales.length; i++){
			if(estado.compareTo(finales[i]) == 0){
				return true;
			}
		}
		
		return false;
	}
	
	public String[] getLenguaje() {
		return lenguaje;
	}

	public void setLenguaje(String[] lenguaje) {
		this.lenguaje = lenguaje;
	}

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public String[] getFinales() {
		return finales;
	}

	public void setFinales(String[] finales) {
		this.finales = finales;
	}

	public String[][] getTabla() {
		return tabla;
	}

	public void setTabla(String[][] tabla) {
		this.tabla = tabla;
	}

	public String getInicial() {
		return inicial;
	}

	public void setInicial(String inicial) {
		this.inicial = inicial;
	}

	public boolean probarPalabra(String palabra){ //Prueba si una palabra es aceptada por el AFD
		
		int simboloA, estadoA;
		String simbolo;
		
		estadoA = posEstado(inicial);
		
		for(int i = 0; i < palabra.length();i++){
			simbolo = Character.toString(palabra.charAt(i));
			
			simboloA = posSimbolo(simbolo);
			if(simboloA == -1){
				return false;
			}
			
			estadoA = posEstado(siguienteEstado(simboloA, estadoA));
			
		}
		
		String estado = estados[estadoA];
		
		if(esFinal(estado)){
			return true;
		}
		else{
			return false;
		}
		
	}

}
