import java.util.Vector;


public class AFNe {
	
	char lenguaje[] = {'ε','\n',' ',
			'0','1','2','3','4','5','6','7','8','9',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	private int inicial;
	private int estadoFinal;
	private int nEstado;
	
	Vector<Vector<Vector<Integer>>> automata; //Orden: Estados - SimboloEntrada - EstadosPosibles
	
	public AFNe(String posfijo){
		
		this.automata = new Vector<Vector<Vector<Integer>>>();
		this.inicial = 0;
		this.estadoFinal = 0;
		this.nEstado = 0;
		this.convertir(posfijo);
	}
	
	public int posCaracter(char simbolo){ //Obtiene la posicion del caracter en el alfabeto
		
		for(int i = 0; i < lenguaje.length; i++){
			if(lenguaje[i]==simbolo){
				return i;
			}
		}
		return -1;
	}
	
	private char caracterAt(int pos){
		return this.lenguaje[pos];
	}
	
	private boolean esOperador(char simbolo){
		
		if(simbolo == '+' || simbolo == '*' || simbolo == ',' ||simbolo == '#'){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	private Vector<Vector<Integer>> crearEstados(){ //Cada que se crea un estado, incializa un espacio para guardar la transiciones por caracter
		Vector<Vector<Integer>> vEstados = new Vector<Vector<Integer>>();
		
		for(int i = 0; i < lenguaje.length;i++){
			vEstados.add(new Vector<Integer>());
		}
		
		return vEstados;
	}
	
	private Vector<Boolean> crearInicial(){//Crea un estado que es inicial
		
		Vector<Boolean> vInicial= new Vector<Boolean>();
		vInicial.add(true);
		vInicial.add(false);
		
		return vInicial;
		
	}
	
	
	private Vector<Boolean> crearFinal(){//Crea un estado que es final
		
		Vector<Boolean> vFinal= new Vector<Boolean>();
		vFinal.add(false);
		vFinal.add(true);
		
		return vFinal;
		
	}
	
	private int getInicial2(Vector<Vector<Boolean>> inicialFinal){
		for(int i= inicialFinal.size() -1; i>=0; i--){ //Busca cual es el inicial del segundo automata
			if(inicialFinal.elementAt(i).elementAt(0) == true){
				return i;
			}
		}
		
		return -1;
	}
	
	private int getInicial1(Vector<Vector<Boolean>> inicialFinal, int inicial2){
		for(int i= inicial2; i>=0; i--){ //Busca cual es el inicial del primer automata
			if(inicialFinal.elementAt(i).elementAt(0) == true && i != inicial2){
				return i;
			}
		}
		
		return -1;
	}
	
	private int getFinal2(Vector<Vector<Boolean>> inicialFinal){
		for(int i= inicialFinal.size()-1; i>0; i--){ //Busca cual es el final del segundo automata
			if(inicialFinal.elementAt(i).elementAt(1) == true){
				return i;
				
			}
		}
		
		return -1;
	}
	
	private int getFinal1(Vector<Vector<Boolean>> inicialFinal, int final2){
		for(int i= final2; i>0; i--){ //Busca cual es el inicial del segundo automata
			if(inicialFinal.elementAt(i).elementAt(1) == true && i != final2){
				return i;
				
			}
		}
		
		return -1;
	}
	
	private void concatenar(Vector<Vector<Boolean>> inicialFinal){
		Integer inicial1, inicial2, final1, final2;
		inicial1 = inicial2 = final1 = final2 = 0;
		
		inicial2 = this.getInicial2(inicialFinal);
		inicial1 = this.getInicial1(inicialFinal, inicial2);
		final2 = this.getFinal2(inicialFinal);
		final1 = this.getFinal1(inicialFinal, final2);
		
		this.automata.elementAt(final1).elementAt(0).add(inicial2);
		inicialFinal.elementAt(final1).set(1, false);
		inicialFinal.elementAt(inicial2).set(0,false);
		
	}
	
	private void union(Vector<Vector<Boolean>> inicialFinal){
		Integer inicial1, inicial2, final1, final2;
		inicial1 = inicial2 = final1 = final2 = 0;
		
		inicial2 = this.getInicial2(inicialFinal);
		inicial1 = this.getInicial1(inicialFinal, inicial2);
		final2 = this.getFinal2(inicialFinal);
		final1 = this.getFinal1(inicialFinal, final2);
		
		this.automata.add(crearEstados()); //Inicial del nuevo automata
		inicialFinal.add(this.crearInicial());
		this.automata.add(crearEstados()); //Final del nuevo automata
		inicialFinal.add(this.crearFinal());
		this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial1); //Une el nuevo estado con las dos expresiones
		this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial2);
		this.automata.elementAt(final1).elementAt(0).add((Integer)nEstado+1);//Une los dos automatas a un mismo estado final
		this.automata.elementAt(final2).elementAt(0).add((Integer)nEstado+1);
		
		inicialFinal.elementAt(inicial1).set(0, false); //En la listado de iniciales/finales de los estados, cambia los iniciales para el nuevo automata
		inicialFinal.elementAt(inicial2).set(0, false);
		
		inicialFinal.elementAt(final1).set(1, false);
		inicialFinal.elementAt(final2).set(1, false);
		
		this.nEstado = this.nEstado+2;
		
		
	}
	
	private void cerraduraEstrella(Vector<Vector<Boolean>> inicialFinal){
		int inicial, eFinal;
		
		inicial = this.getInicial2(inicialFinal);
		eFinal = this.getFinal2(inicialFinal);
		
		this.automata.add(crearEstados());
		inicialFinal.add(this.crearInicial());
		this.automata.add(crearEstados());
		inicialFinal.add(this.crearFinal());
		
		this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial);
		this.automata.elementAt(nEstado).elementAt(0).add((Integer)nEstado+1);
		this.automata.elementAt(eFinal).elementAt(0).add((Integer)nEstado+1);
		this.automata.elementAt(eFinal).elementAt(0).add((Integer)inicial);
		
		inicialFinal.elementAt(inicial).set(0, false);
		inicialFinal.elementAt(eFinal).set(1, false);
		
		this.nEstado = this.nEstado + 2;
		
	}
	
	private void cerraduraPositiva(Vector<Vector<Boolean>> inicialFinal){
		int inicial, eFinal;
		
		inicial = this.getInicial2(inicialFinal);
		eFinal = this.getFinal2(inicialFinal);
		
		this.automata.add(crearEstados());
		inicialFinal.add(this.crearInicial());
		this.automata.add(crearEstados());
		inicialFinal.add(this.crearFinal());
		
		this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial);
		this.automata.elementAt(eFinal).elementAt(0).add((Integer)nEstado+1);
		this.automata.elementAt(eFinal).elementAt(0).add((Integer)inicial);
		
		inicialFinal.elementAt(inicial).set(0, false);
		inicialFinal.elementAt(eFinal).set(1, false);
		
		this.nEstado = this.nEstado + 2;
	}
	
	public void convertir(String posfijo){ //Convierte el posfijo a AFNe
		this.nEstado = 0;
		char simbolo;
		Vector<Vector<Boolean>> inicialFinal = new Vector<Vector<Boolean>>();
		
		for(int i =0 ; i < posfijo.length(); i++){
			simbolo = posfijo.charAt(i);
			
			if(!this.esOperador(simbolo)){ //Crear automatas para los caracteres
				this.automata.add(crearEstados());
				inicialFinal.add(this.crearInicial());
				this.automata.add(crearEstados());
				inicialFinal.add(this.crearFinal());
				
				if(simbolo == '.'){ 
					for(int j = 2; j < this.lenguaje.length; j++){
						this.automata.elementAt(nEstado).elementAt(j).add((Integer)nEstado+1);
					}
				}
				else{
					this.automata.elementAt(nEstado).elementAt(this.posCaracter(simbolo)).add((Integer)nEstado+1);
				}
				
				nEstado = nEstado+2;
				
			}
			else{ //Aplicar operaciones
				if(simbolo == '#'){
					this.concatenar(inicialFinal);
				}
				else if(simbolo == ','){
					this.union(inicialFinal);
				}
				else if(simbolo == '*'){
					this.cerraduraEstrella(inicialFinal);
				}
				else if(simbolo == '+'){
					this.cerraduraPositiva(inicialFinal);
				}
			}
		}
		
		
		for(int i = 0; i < inicialFinal.size(); i++){
			if(inicialFinal.elementAt(i).elementAt(0) == true){
				this.inicial = i;
				break;
			}
		}
		
		for(int i = 0; i < inicialFinal.size(); i++){
			if(inicialFinal.elementAt(i).elementAt(1) == true){
				this.estadoFinal = i;
				break;
			}
		}
	}

	public void imprimirAFNe(){
		
		System.out.println("Numero de Estados: "+automata.size());
		System.out.println("Estado inicial: q"+this.inicial+" Estado Final: q"+this.estadoFinal);
		
		for(int i = 0; i < automata.size(); i++){
			System.out.print("\nEstado q"+i);
			for(int j = 0; j < automata.elementAt(i).size(); j++){
				
				for(int k = 0; k < automata.elementAt(i).elementAt(j).size(); k++){
					if(automata.elementAt(i).elementAt(j).size() > 0){
						System.out.print(" simbolo : "+ this.caracterAt(j)+ " estados:  ");
						System.out.print(this.automata.elementAt(i).elementAt(j));
					}
				}
				
			}
		}
		
		System.out.println();
		
	}

	
	//getters
	public int getInicial(){
		return this.inicial;
	}
	
	public int getFinal(){
		return this.estadoFinal;
	}
	
	public Vector<Vector<Vector<Integer>>> getAutomata(){
		return this.automata;
	}
	
	public int numeroEstados(){
		return this.nEstado + 1;
	}
	
	public static void main(String[] args) throws Exception{
		ConvertidorPosfijo posfijo = new ConvertidorPosfijo("(arbol,casa)");
		AFNe automata = new AFNe(posfijo.getPosfijo());
		System.out.println(posfijo.getPosfijo());
		automata.imprimirAFNe();

	}
}

