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
		
		String linea;
		BufferedReader lector =  new BufferedReader(new FileReader(archivo));
		
		while((linea = lector.readLine()) != null){
			
		}
		
	}
	
	
	
	public String toString(){
		String string = "";
		for(int i = 0; i < this.cadenasAceptadas.size(); i++){
			string.concat(this.cadenasAceptadas.elementAt(i)+"\n");
		}
		
		return string;
		
	}
	
}
