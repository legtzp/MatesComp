
public class ListaVaciaException extends RuntimeException {
	public ListaVaciaException(){
		super("La lista esta vacia");
	}
	
	public ListaVaciaException(String message){
		super(message);
	}
}
