import java.util.Vector;


public class AFNe {
	
	char lenguaje[] = {'ε',' ','\n',
			'0','1','2','3','4','5','6','7','8','8',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	int inicial = 0;
	
	Vector<Vector<Vector<Integer>>> automata;
	
	public AFNe(String posfijo){
		
		this.automata = Vector<Vector<Vector<Integer>>>;
		
		this.convertir(posfijo);
	}
	
	public int posEstado(char simbolo){
		
		for(int i = 0; i < lenguaje.length; i++){
			if(lenguaje[i]==simbolo){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public void convertir(String posfijo){
		int nEstado, inicial1, inicial2, final1, final2;
		nEstado = 0;
		char simbolo=' ';
		
		for(int i = 0;i < posfijo.length(); i++){//Recorre los simbolos de la expresion posfija
			//Aqui adentro va toda la creacion del AFN
		}
		
	}

}
