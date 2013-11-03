
public class ListaEnlazada<E> {
	private Nodo<E> inicio,
	fin;
	
	private int size;
	
	public ListaEnlazada(){
		this.inicio = this.fin = null;
		this.size = 0;
	}
	
	public ListaEnlazada(E[] datos){
		
		this();
		
		for(E elemento:datos){
			this.insertarFin(elemento);
		}
		
	}
	
	public int size(){
		return this.size;
	}
	
	public Nodo nodoInicial(){
		try{
			return this.inicio;
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede regresar el primer Nodo de una lista vacia");
		}
		
	}
	
	public E inicio(){
		try{
			return this.inicio.getDato();
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede regresar el primer elemento de una lista vacia");
		}
	}
	
	public E fin(){
		try{
			return this.fin.getDato();
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede regresar el ultimo elemento de una lista vacia");
		}
	}
	
	public void insertarInicio(E elemento){
		Nodo<E> nuevo = new Nodo<E>(elemento, this.inicio);
		this.inicio = nuevo;
		if(this.size==0){
			this.fin = nuevo;
		}
		this.size++;
	}
	
	public boolean isEmpty(){
		if(this.size() > 0){
			return false;
		}
		
		return true;
	}
	
	public void insertarFin(E elemento){
		
		Nodo<E> nuevo = new Nodo<E>(elemento);
		if(this.size == 0){
			this.insertarInicio(elemento);
		}
		else{
			this.fin.setNext(nuevo);
			this.fin = nuevo;
			size++;
		}

	}
	
	public void insertarEn(int pos, E dato){
		
		if(pos < 0 || pos > this.size){
			throw new PosicionInexistenteException("No se puede insertat un elemenento en la posicion "+pos+"en una lista de tamano "+size);
		}
			Nodo nElemento = new Nodo(dato);
			
			if(pos == 0){
				this.insertarInicio(dato);
			}
			else if(pos == this.size){
				this.insertarFin(dato);
			}
			else{
				Nodo temp = this.inicio;
				int ctr = 1;
				while(ctr < pos){
					temp = temp.getNext();
					ctr++;
				}
				nElemento.setNext(temp.getNext());
				temp.setNext(nElemento);
				this.size++;
			}
	}
	
	public E borrarInicio(){
		
		try{
			E dato = this.inicio.getDato();
			
			if(size == 1){
				this.inicio = this.fin = null;
				size--;
				return dato;
			}
			
			this.inicio = this.inicio.getNext();
			size--;
			return dato;
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede borrar el inicio de una lista vacia");
		}
		
		
	}
	
	public E borrarFin(){
		
		try{
			
			E dato = this.fin.getDato();
			
			if(size == 1){
				this.inicio = this.fin = null;
				size--;
				return dato;
			}
			
			Nodo temp = this.inicio;
			
			for(int pos = 0; pos < this.size - 2; pos++){
				temp = temp.next;
			}
			
			temp.setNext(null);
			this.fin = temp;
			size--;
			return dato;
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede borrar el inicio de una lista vacia");
		}
		
	}
	
	public E borrarEn(int pos, E dato){
		
		if(pos < 0 || pos >= this.size){
			throw new PosicionInexistenteException("No se puede borrar un elemenento en la posicion "+pos+"en una lista de tamano "+size);
		}
		else if(pos == 0){
				return this.borrarInicio();
			}
		else if(pos == this.size - 1){
				return this.borrarFin();
			}
		else{
			Nodo<E> temp = this.inicio;
			int ctr = 1;
			while(ctr < pos){
				temp = temp.next;
				ctr++;
			}
			E rdato = temp.getNext().getDato();
			temp.setNext(temp.getNext().getNext());
			this.size--;
			return rdato;
			}
	}
	
	public E elementoEn(int pos){
		
		
		if(pos >= this.size){
			throw new IndexOutOfBoundsException("No se puede obtener el elemento "+pos+" de una lista de tamaño "+size);
		}
		
		if(pos == size -1){
			return this.fin();
		}
		else{
			Nodo<E> temp = this.inicio;
			for(int i = 0; i < pos; i++){
				temp = temp.getNext();
			}
			return temp.getDato();
		}	
			
	}

	public String toString(){
		
		String lista="";
		Nodo temp = this.inicio;
		int ctr = 0;
		
		while(temp != null){
			if(ctr == size-1){
				lista = lista.concat(temp+"");
			}
			else{
				lista=lista.concat(temp+ ", ");
				
			}
			temp = temp.getNext();
			ctr++;
		}
		
		return lista;
		
	}
	
	public static void main(String[] args) {
		
		Integer[] lista = {1,2,3,4,5,6,7,8,9};
		ListaEnlazada test = new ListaEnlazada(lista);
		System.out.println(test);
		test.insertarEn(1, 19);
		System.out.println(test);
		test.borrarEn(1, 19);
		System.out.println(test);
		test.elementoEn(5);

	}

}

class Nodo<E>{
	
	private E dato;
	Nodo<E> next;
	
	public Nodo(E dato){
		this(dato,null);
	}
	
	public Nodo(E dato, Nodo<E> next){
		this.dato = dato;
		this.next = next;
	}
	
	public E getDato() {
		return dato;
	}
	
	public void setDato(E dato) {
		this.dato = dato;
	}
	
	public Nodo<E> getNext() {
		return next;
	}
	
	public void setNext(Nodo<E> next) {
		this.next = next;
	}
	
	public String toString(){
		return this.dato.toString();
	}

}