import java.util.Vector;


public class AFNe {
	
	char lenguaje[] = {'ε',' ','\n',
			'0','1','2','3','4','5','6','7','8','8',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'á','é','í','ó','ú','Á','É','Í','Ó','Ú'};
	
	Vector<Vector<String>> automata;
	
	public AFNe(String posfijo){
		this.convertir(posfijo);
	}
	
	public void convertir(String posfijo){
		int nEstado, inicial1, inicial2, final1, final2;
		
	}

}
