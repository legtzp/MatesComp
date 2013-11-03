import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class lector {

	private String lenguaje[];
	private String estados[];
	private String finales[];
	private String transiciones[][];
	
	public lector(String archivo){
		try{
			FileReader a = new FileReader(archivo);
			BufferedReader lector = new BufferedReader(a);
			ArrayList caracteres = new ArrayList();
			ArrayList arrayFinales = new ArrayList();
			ArrayList arrayEstados = new ArrayList();
			ArrayList lineaTransicion = new ArrayList();
			
			String linea, lenguaje, estado;
			
			lenguaje = lector.readLine();
			
			StringTokenizer st = new StringTokenizer(lenguaje, ",");
			while(st.hasMoreTokens()){
				caracteres.add(st.nextToken());
			}
			
			this.lenguaje = new String[caracteres.size()];
			for(int i=0;i<caracteres.size();i++){
				this.lenguaje[i] = (String) caracteres.get(i);
			}
			
			
			while((linea = lector.readLine()) != null){
				st = new StringTokenizer(linea, "-");
				estado = st.nextToken();
				lineaTransicion.add(st.nextToken());
				if(Character.toString(estado.charAt(0)).equals("*")){
					arrayFinales.add(estado.replace("*", ""));
					arrayEstados.add(estado.replace("*", ""));
				}
				else{
					arrayEstados.add(estado.replace("*", ""));
				}
			}
			
			this.estados = new String[arrayEstados.size()];
			for(int i=0;i<arrayEstados.size();i++){
				this.estados[i] = (String) arrayEstados.get(i);
			}
			
			this.finales = new String[arrayFinales.size()];
			for(int i=0;i<arrayFinales.size();i++){
				this.finales[i] = (String) arrayFinales.get(i);
			}
			
			this.transiciones = new String[this.estados.length][this.lenguaje.length];
			for(int i=0;i<lineaTransicion.size();i++){
				st = new StringTokenizer((String) lineaTransicion.get(i), "&");
				for(int j=0;j<this.lenguaje.length;j++){
					this.transiciones[i][j] = st.nextToken();	
				}
			}
			lector.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String[] getLenguaje(){
		return this.lenguaje;
	}
	
	public String[] getEstados(){
		return this.estados;
	}
	
	public String[] getFinales(){
		return this.finales;
	}
	
	public String[][] getTransiciones(){
		return this.transiciones;
	}
	
	public static void main(String[] args) {
		lector prueba = new lector("prueba");
		String[] lenguaje = prueba.getLenguaje();
		System.out.println("Lenguaje");
		for(int i=0; i<lenguaje.length;i++){
			System.out.print("["+lenguaje[i]+"]");
		}
		System.out.println("\nEstados");
		for(int i=0; i<prueba.getEstados().length;i++){
			System.out.print("["+prueba.getEstados()[i]+"]");
		}
		System.out.println("\nFinales");
		for(int i=0; i<prueba.getFinales().length;i++){
			System.out.print("["+prueba.getFinales()[i]+"]");
		}
		System.out.println("\nTransiciones");
		for(int i=0; i<prueba.getTransiciones().length;i++){
			for(int j=0;j<prueba.getTransiciones()[0].length;j++){
				System.out.print("["+prueba.getTransiciones()[i][j]+"]");
			}
			System.out.println();
		}
	}
}
