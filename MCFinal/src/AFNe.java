import java.util.Vector;


public class AFNe {
	
	char lenguaje[] = {'ε',' ','\n',
			'0','1','2','3','4','5','6','7','8','8',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	int inicial;
	int estadoFinal;
	
	Vector<Vector<Vector<Integer>>> automata; //Orden: Estados - SimboloEntrada - EstadosPosibles
	
	public AFNe(String posfijo){
		
		this.automata = new Vector<Vector<Vector<Integer>>>();
		this.inicial = 0;
		this.convertir(posfijo);
	}
	
	private int posCaracter(char simbolo){ //Obtiene la posicion del caracter en el alfabeto
		
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
	
	public void convertir(String posfijo){ //Convierte el posfijo a AFNe
		int nEstado, inicial1, inicial2, final1, final2;
		nEstado = inicial1 = inicial2 = final1 = final2 = 0;
		char simbolo=posfijo.charAt(0);
		
		this.automata.add(crearEstados()); //Solo aplica para el primer caracter de la expresion, creando el automata R, que se sobreescribe en las operaciones binarias
		inicial1 = inicial2 = nEstado;
		this.automata.add(crearEstados());
		this.estadoFinal = final1 = final2 = nEstado+1;
		
		if(simbolo == '.'){
			for(int k = 1; k < lenguaje.length; k++){
				this.automata.elementAt(nEstado).elementAt(k).add((Integer)nEstado+1);
			}
		}
		else{
			this.automata.elementAt(nEstado).elementAt(posCaracter(simbolo)).add((Integer)nEstado+1);
		}
		
		nEstado = nEstado + 2;
		
		for(int i = 1;i < posfijo.length(); i++){//Recorre los simbolos de la expresion posfija
			//Aqui adentro va toda la creacion del AFN
			simbolo = posfijo.charAt(i);
			
			if(!this.esOperador(simbolo)){ //Cada caracter nuevo crea un nuevo automata 'S'.
				
				this.automata.add(crearEstados());
				inicial2 = nEstado;
				this.automata.add(crearEstados());
				final2 = nEstado+1;
				
				if(simbolo == '.'){
					for(int k = 1; k < lenguaje.length; k++){
						this.automata.elementAt(nEstado).elementAt(k).add((Integer)nEstado+1);
					}
				}
				else{
					this.automata.elementAt(nEstado).elementAt(posCaracter(simbolo)).add((Integer)nEstado+1);
				}
				
				nEstado = nEstado + 2;
			}
			else{
				if(simbolo == ','){ //Union
					this.automata.add(crearEstados()); //Inicial del nuevo automata
					this.automata.add(crearEstados()); //Final del nuevo automata
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial1); //Une el nuevo estado con los das dos expresiones
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial2);
					this.automata.elementAt(final1).elementAt(0).add((Integer)nEstado+1);//Une los dos automatas a un mismo estado final
					this.automata.elementAt(final2).elementAt(0).add((Integer)nEstado+1);
					
					inicial1 = nEstado;
					this.inicial = inicial1;
					inicial2 = inicial1;
					final1 = nEstado + 1;
					this.estadoFinal = final1;
					final2 = final1;
					nEstado = nEstado + 2;
				}
				else if(simbolo == '#'){ //Concatenacion
					this.automata.elementAt(final1).elementAt(0).add((Integer)inicial2);
					inicial2 = inicial1;
					final1 = final2;
					this.estadoFinal = final1;
					
					
				}
				else if(simbolo == '*'){ //Cerradura Estrella
					this.automata.add(crearEstados());
					this.automata.add(crearEstados());
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial2);
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)nEstado+1);
					this.automata.elementAt(final2).elementAt(0).add((Integer)nEstado+1);
					inicial2 = nEstado;
					final2 = nEstado+1;
					nEstado = nEstado + 2;
				}
				else{ //Cerradura positiva
					this.automata.add(crearEstados());
					this.automata.add(crearEstados());
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial2);
					this.automata.elementAt(final2).elementAt(0).add((Integer)nEstado+1);
					inicial2 = nEstado;
					final2 = nEstado+1;
					nEstado = nEstado + 2;
				}
			}
		}
		
	}

	public void imprimirAFNe(){
		
		System.out.println("Numero de Estados: "+automata.size());
		System.out.println("Estado inicial: q"+this.inicial+" Estado Final: q"+this.estadoFinal);
		
		for(int i = 0; i < automata.size(); i++){
			System.out.print("Estado q"+i+" ");
			for(int j = 0; j < automata.elementAt(i).size(); j++){
				
				for(int k = 0; k < automata.elementAt(i).elementAt(j).size(); k++){
					if(automata.elementAt(i).elementAt(j).size() > 0){
						System.out.print("simbolo : "+ this.caracterAt(j)+ " estados:  ");
						System.out.print(this.automata.elementAt(i).elementAt(j));
					}
					System.out.println();
				}
				
			}
		}
		
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
	
	public static void main(String[] args) throws Exception{
		ConvertidorPosfijo posfijo = new ConvertidorPosfijo();
		AFNe automata = new AFNe(posfijo.convertir("(01)*(ε,0)+(10)*(ε,1)"));
		automata.imprimirAFNe();

	}
}
