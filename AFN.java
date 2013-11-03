
public class AFN {

	private String[] lenguaje;
	private String[] estados;
	private String[] finales;
	private String[][][] tabla;
	private String inicial;

	public AFN(String[] simbolos,String[] estados, String[] finales){
		this.lenguaje = simbolos;
		this.estados = estados;
		this.finales = finales;
		this.inicial = estados[0];

		this.tabla = new String[estados.length][lenguaje.length][];
	}

	public AFN(String[] simbolos,String[] estados, String[] finales, String[][][] transiciones){
		this.lenguaje = simbolos;
		this.estados = estados;
		this.finales = finales;
		this.inicial = estados[0];

		this.tabla = transiciones;
	}

	public void asignarDestino(String estado, String simbolo, String[] estadoSiguiente){ //Asigna el estado siguiente a un estadi y un simbolo
		this.tabla[posEstado(estado)][posSimbolo(simbolo)] = estadoSiguiente;	
	}

	public void asignarDestino(int posEstado, int posSimbolo, String[] estadoSiguiente){
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

	public int posEstado(String estado){ //Regresa la posicion del estado en la lista de estados. Si no lo encuentra regresa -1

		for(int i = 0; i < estados.length; i++){
			if(estado.compareTo(estados[i]) == 0){
				return i;
			}
		}
		return -1;
	}

	public String[] siguienteEstado(String simbolo, String estado){ //Regresa el estado que sigue basado en un simbolo y un estado
		return tabla[posEstado(estado)][posSimbolo(simbolo)];
	}

	public String[] siguienteEstado(int posSimbolo, int posEstado){
		return tabla[posEstado][posSimbolo];
	}

	public boolean esFinal(String estado){ //Revisa si el estado esta en la lista de estados finales
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

	public String[][][] getTabla() {
		return tabla;
	}

	public void setTabla(String[][][] tabla) {
		this.tabla = tabla;
	}

	public String getInicial() {
		return inicial;
	}

	public void setInicial(String inicial) {
		this.inicial = inicial;
	}

	public boolean probarPalabra(String palabra){
		//Prueba si una palabra es aceptada por el AFD. Caso base del trato recursivo.
		
		if(palabra.length() == 0){
			return esFinal(inicial);
		}

		int simboloA;
		String[] estados;
		int largo = palabra.length();
		

		simboloA = posSimbolo(Character.toString(palabra.charAt(0)));
		
		if(simboloA == -1){ //Si el simbolo no se encuentra en el lenguaje el la palabra es rechazada
			return false;
		}

		estados = siguienteEstado(simboloA, posEstado(inicial));
		
		for(int i = 0; i < estados.length; i++){ //Prueba cada camino posible. Con que uno camino sea aceptado la palabra es aceptada.

			if(probarPalabra(estados[i], palabra, 1, largo)){
				return true;
			}
		}

		return false; //Ningun camino fue aceptado.

	}

	private boolean probarPalabra(String estado, String palabra, int posActual, int largoPalabra){ 
		//Llamada recursiva de probarPalabra, recibiendo el nuevo arreglo de estados, la posicion actual en la palabra y el estado del cual proviene.	
		
		if(posActual < largoPalabra){
			String carActual = Character.toString(palabra.charAt(posActual));
			
			if(posSimbolo(carActual) == -1){
				return false;
			}
			
			String estados[] = siguienteEstado(carActual, estado);
			
			System.out.println(estado);
			System.out.println("Revisar: "+carActual);
			
			/*if(estado.compareTo("%") == 0){ //Si se encuentra en el estado  vacio automaticamente regresa false
				System.out.println("No camino");
				return false;
			}*/
			
			for(int i = 0; i < estados.length; i++){ //Prueba cada camino.
				if(posEstado(estados[i]) == -1){
					return false;
				}
				
				if(probarPalabra(estados[i], palabra, posActual+1, largoPalabra)){
					return true;
				}
			}
			
		}
		if(esFinal(estado)){ //Si el ultimo estado es final, la palabra es aceptada.
			System.out.println("estado "+ estado + " es final");
			return true;
		}
		else{
			System.out.println("Estado "+ estado + " no es final");
			return false;
		}
	}
	
	public void imprimirAutomata(){
		for(int i = 0; i < tabla[i].length; i++){
			for(int j = 0; j < tabla[i][j].length; j++){
				for(int k = 0; k < tabla[i][j][k].length(); k++){
					System.out.println(tabla[i][j][k].length());
					System.out.print(tabla[i][j][k]+",");
				}
			}
			System.out.print(" ");
		}
		System.out.println();
	}


}
