
public class EmptyStackException extends RuntimeException {

	public EmptyStackException(){
		super("Operacion invalida para una pila vacia");
	}
	
	public EmptyStackException(String msj){
		super(msj);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
