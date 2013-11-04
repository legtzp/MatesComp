import java.util.Vector;


public class AFNe {
	
	char lenguaje[] = {'ε',' ','\n',
			'0','1','2','3','4','5','6','7','8','8',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	int inicial;
	
	Vector<Vector<Vector<Integer>>> automata; //Orden: Estados - SimboloEntrada - EstadosPosibles
	Vector<Integer> finales; //Guarda los estados finales aceptados por el AFN
	
	public AFNe(String posfijo){
		
		this.automata = new Vector<Vector<Vector<Integer>>>();
		this.inicial = 0;
		this.convertir(posfijo);
	}
	
	public int posCaracter(char simbolo){
		
		for(int i = 0; i < lenguaje.length; i++){
			if(lenguaje[i]==simbolo){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public boolean esOperador(char simbolo){
		
		if(simbolo == '+' || simbolo == '*' || simbolo == ',' ||simbolo == '#'){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public Vector<Vector<Integer>> crearEstados(){
		Vector<Vector<Integer>> vEstados = new Vector<Vector<Integer>>();
		Vector<Integer> estadosInit = new Vector<Integer>();
		
		for(int i = 0; i < lenguaje.length;i++){
			vEstados.add(estadosInit);
		}
		
		return vEstados;
	}
	
	public void convertir(String posfijo){
		int nEstado, inicial1, inicial2, final1, final2;
		nEstado = inicial1 = inicial2 = final1 = final2 = 0;
		char simbolo=' ';
		
		this.automata.add(crearEstados()); //Solo aplica para el primer caracter de la expresion
		inicial1 = nEstado;
		this.automata.add(crearEstados());
		final1 = nEstado+1;
		this.automata.elementAt(nEstado).elementAt(posCaracter(simbolo)).add((Integer)nEstado+1);
		nEstado = nEstado + 2;
		
		for(int i = 1;i < posfijo.length(); i++){//Recorre los simbolos de la expresion posfija
			//Aqui adentro va toda la creacion del AFN
			simbolo = posfijo.charAt(i);
			
			if(!this.esOperador(simbolo)){
				this.automata.add(crearEstados());
				inicial2 = nEstado;
				this.automata.add(crearEstados());
				final2 = nEstado+1;
				
				this.automata.elementAt(nEstado).elementAt(posCaracter(simbolo)).add((Integer)nEstado+1);
				
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
					final1 = nEstado + 1;
					nEstado = nEstado + 2;
				}
				else if(simbolo == '#'){ //Concatenacion
					this.automata.elementAt(final1).elementAt(0).add((Integer)inicial2);
					final1 = final2;
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
				else{
					this.automata.add(crearEstados());
					this.automata.add(crearEstados());
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)inicial2);
					this.automata.elementAt(nEstado).elementAt(0).add((Integer)nEstado+1);
					this.automata.elementAt(final2).elementAt(0).add((Integer)nEstado+1);
					inicial2 = nEstado;
					final2 = nEstado+1;
					nEstado = nEstado + 2;
				}
			}
		}
		
	}

}
