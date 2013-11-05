
public class ConvertidorPosfijo {

	private StackLE<Character> pila;
	
	/*
	 -constructor(String exp) que imprime en pantalla la solucion
	 -parsing()
	 -evalua()
	 */
	
	/*Excepciones
	 -Encontrar caracter que ni al caso
	 -Al vaciar la pila encontraste un parentesis que no cerro
	 -Si encuentras un parentesis que cierra y no encuentras el que abrio
	  
	 */
	
	public ConvertidorPosfijo(String expresion) throws ExpresionPosfijaException{
		this.pila = new StackLE<Character>();
		System.out.println(this.convertir(expresion));
	}
	
	public ConvertidorPosfijo() throws ExpresionPosfijaException{
		this.pila = new StackLE<Character>();
	}
	
	public StackLE getPila(){
		return this.pila;
	}
	
	private int prioridadOperador(char operador){
		
		if(operador == '*'){ //estrella
			return 3;
		}
		else if(operador == '+'){ 
			return 2;
		}
		else if(operador == '#'){
			return 1;
		}
		else if(operador == ','){ 
			return 0;
		}
		else{
			return -1;
		}
	}
	
	
	public String convertir(String expresionPre) throws ExpresionPosfijaException{
		
		System.out.println(expresionPre);
		
		String expresion = ""; //Agrega simbolos de concatenacion a la expresion
		char act, act1;
		for(int i = 0; i < expresionPre.length()-1; i++){
			act = expresionPre.charAt(i);
			act1 = expresionPre.charAt(i+1);
			if((act != ',' && act != '(') && (act1 != '+' && act1 != '*' && act1 != ',' && act1 != ')')){
				expresion = expresion + act + '#';
			}
			else{
				expresion = expresion + act;
			}
			
		}
		expresion = expresion + expresionPre.charAt(expresionPre.length() - 1);
		System.out.println(expresion);
		
		
		//Conversion de la expresion a posfijo
		String posfijo = "";
		char simbolo;
		
		for(int i = 0; i < expresion.length();i++){
			simbolo = expresion.charAt(i);
			
			if(/*simbolo == '+'|| simbolo == '*' ||*/ simbolo == '(' || simbolo == ')' || simbolo == ',' || simbolo == '#'){ //Revisa que el simbolo sea un operador
				if(this.pila.isEmpty() || ( simbolo == '(' || (this.prioridadOperador(simbolo) > this.prioridadOperador(this.pila.top()) )) ){ //Si la pila esta vacia o el operador actual tiene mayor prioridad que el top de la pila actual automaticamente agrega el operador
					
					this.pila.push(simbolo);
				}
				else if(simbolo == ')'){
					
					try{ //Revisa si esta el parentesis que lo abrio
						while(this.pila.top() != '('){ //Saca todos los simbolos que hay hasta que llega a '('
							posfijo = posfijo + this.pila.pop();
						}
						this.pila.pop();
					}
					catch(Exception e){
							throw new ExpresionPosfijaException("No se encontro el parentesis que abria");
					};
				}
				else{
					while(!this.pila.isEmpty() && (prioridadOperador(simbolo) <= prioridadOperador(this.pila.top()))){ //El simbolo es de menor prioridad al top actual y se repita hasta que sea mayor, donde se agrega a la pila
						posfijo = posfijo + this.pila.pop();
					}
					this.pila.push(simbolo);
				}	
			}
			else if(Character.isAlphabetic(simbolo) || simbolo == '\n' || simbolo == ' ' || Character.isDigit(simbolo) || simbolo == '.' || simbolo == '+'|| simbolo == '*'){ //Agrega el operando a la expresion final
				posfijo = posfijo + simbolo;
			}
			else{
				throw new ExpresionPosfijaException("La expresion contiene un simbolo invalido");
			}
		}
		
		while(!this.pila.isEmpty()){ //Agrega a la expresion final los operadores restantes en la pila
			if(this.pila.top() == '('){
				throw new ExpresionPosfijaException("Falto un parentesis de cerrar");
			}
			posfijo = posfijo + pila.pop();
		}
		
		return posfijo;
	}
	
	public static void main(String[] args) throws ExpresionPosfijaException {
		
		ConvertidorPosfijo convertir = new ConvertidorPosfijo("(padre,ε)(.)+www(.)+com");

	}

}
