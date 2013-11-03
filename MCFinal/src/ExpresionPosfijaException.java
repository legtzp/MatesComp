
public class ExpresionPosfijaException extends Exception {

	public ExpresionPosfijaException(){
		super("Error en la evaluacion de la expresion");
	}
	
	public ExpresionPosfijaException(String message){
		super(message);
	}
	
}
