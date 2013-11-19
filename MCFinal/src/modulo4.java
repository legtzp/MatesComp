import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class modulo4 {

	private Vector<String> cadenasAceptadas;
	
	public modulo4(AFN automata, String archivo) throws IOException{
		this.leer(automata, archivo);
	}
	
	public Vector<String> getCadenasAceptadas(){
		return this.cadenasAceptadas;
	}
	
	private void leer(AFN automata, String archivo) throws IOException{ //Enceuntra las cadenas aceptadas por la expresion regulas y las guarda
		
		String linea, sublinea;
		BufferedReader lector =  new BufferedReader(new FileReader(archivo));
		
		while((linea = lector.readLine()) != null){ //Lee cada linea individual
			for(int i = 0; i < linea.length();i++){ //Crea todas las subcadenas posibles de la linea y prueba si son aceptadas por el automata
				for(int j = 0; j < linea.length(); j++){
					sublinea = linea.substring(j, i);
					if(automata.aceptado(automata.resolverCadena(sublinea, 0))){
						this.cadenasAceptadas.add(sublinea);
					}
				}
			}
		}
		
	}
	
	
	
	public String toString(){ //Override del metodo substring que imprime todas las cadenas
		String string = "";
		for(int i = 0; i < this.cadenasAceptadas.size(); i++){
			string.concat(this.cadenasAceptadas.elementAt(i)+"\n");
		}
		
		return string;
		
	}
	
	public static void main(String[] args){
		
	}
	
}
