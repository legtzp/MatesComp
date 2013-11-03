
public class StackLE<E> {

	private ListaEnlazada<E> stack;
	
	public StackLE(){
		this.stack = new ListaEnlazada<E>();
	}
	
	public E pop(){
		try{
			return this.stack.borrarInicio();
		}
		catch(ListaVaciaException e){
			throw new EmptyStackException("No se puede obtener un dato de una pila vacia");
		}
		
	}
	
	public void push(E dato){
		this.stack.insertarInicio(dato);
	}
	
	public E top(){
		try{
			return this.stack.inicio();
		}
		catch(ListaVaciaException e){
			throw new EmptyStackException("No se puede obtener un dato de una pila vacia");
		}
	}
	
	public boolean isEmpty(){
		return this.stack.isEmpty();
	}
	
	public int size(){
		return this.stack.size();
	}
	
	public void flush(){
		this.stack = new ListaEnlazada<E>();
		System.gc();
	}
	
	public String toString(){
		String string ="";
		Nodo temp = this.stack.nodoInicial();
		
		if(this.isEmpty() == true){
			return "Is empty";
		}
		
		for(int i = 0; i < this.stack.size() - 1; i++){
			string = ", "+ temp.toString() + string;
			temp = temp.getNext();	
		}
		
		string = temp.toString() + string;
			
		return string;
	}
	
	public static void main(String args[]){
		StackLE stack = new StackLE();
		
		stack.push('a');
		System.out.println(stack.pop());
	}

}
