
public class PosicionInexistenteException extends RuntimeException {
	public PosicionInexistenteException(){
		super("La posicion no existe");
	}
	
	public PosicionInexistenteException(String message){
		super(message);
	}

}
