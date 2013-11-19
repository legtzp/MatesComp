import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class modulo4 {

	private Vector<String> cadenasAceptadas;
	
	public modulo4(AFN automata, String archivo) throws IOException{
		this.cadenasAceptadas = new Vector<String>();
		this.leer(automata, archivo);
	}
	
	public Vector<String> getCadenasAceptadas(){
		return this.cadenasAceptadas;
	}
	
	private void leer(AFN automata, String archivo) throws IOException{ //Enceuntra las cadenas aceptadas por la expresion regulas y las guarda
		
		String linea, sublinea;
		BufferedReader lector =  new BufferedReader(new FileReader(archivo));
		
		while((linea = lector.readLine()) != null){ //Lee cada linea individual
			linea = linea + '\n';
			for(int i = 0; i < linea.length();i++){ //Crea todas las subcadenas posibles de la linea y prueba si son aceptadas por el automata
				for(int j = 0; j <= linea.length(); j++){
					if(i<j){
						sublinea = linea.substring(i, j);
						//System.out.println("i: "+i+" j: "+j+" ->"+sublinea);
						if(automata.aceptado(automata.resolverCadena(sublinea, 0)) && !this.cadenasAceptadas.contains(sublinea)){
							this.cadenasAceptadas.add(sublinea);
						}
					}
					
				}
				
			}
		}
		
		lector.close();
	}
	
	
	
	public String toString(){ //Override del metodo substring que imprime todas las cadenas
		String string = "";
		for(int i = 0; i < this.cadenasAceptadas.size(); i++){
			System.out.println(this.cadenasAceptadas.elementAt(i));
			string.concat(this.cadenasAceptadas.elementAt(i)+"\n");
		}
		
		return string;
		
	}
	
	public static void main(String[] args) throws ExpresionPosfijaException, IOException{
		
		ConvertidorPosfijo posfijo = new ConvertidorPosfijo("S(.)*\n");
		AFNe afne = new AFNe(posfijo.getPosfijo());
		AFN afn = new AFN(afne);
		modulo4 prueba = new modulo4(afn,"prueba3.txt");
		System.out.println(prueba);
		
	}
	
}
