
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class InterfacePrincipal extends JFrame implements ActionListener {
	
	private String palabra;
	private boolean aceptacion;
	private AFN automata;
	private String[] simbolos;
	private String[] estados, finales;
	private String[][] tablaTransiciones;
	private Image logo, bien, mal;
	private lector lector;
	
	public InterfacePrincipal(){
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(700, 700);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.aceptacion = false;
		this.logo = new ImageIcon("src/logo.jpg").getImage();
		this.bien = new ImageIcon("src/confirm.png").getImage();
		this.mal = new ImageIcon("src/bad.png").getImage();
	}
	
	public void cargarAutomata(){//Metodo para inicializar el automata//
		this.automata = new AFN(this.simbolos, this.estados, this.finales, this.tablaTransiciones);
	}
	
	public void cargarLector(){//MŽtodo para inicializar el lector
		//Codigo del File Chooser//
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos txt", "txt", ".txt");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(this);
		File selectedFile = chooser.getSelectedFile();
		String dir = selectedFile.getAbsolutePath();
		System.out.println(dir);
		
		//C—digo del lector//
		this.lector = new lector(dir);
		this.simbolos = lector.getLenguaje();
		this.estados = lector.getEstados();
		this.finales = lector.getFinales();
		this.tablaTransiciones = lector.getTransiciones();
	}
	
	public void validarPalabra(){//MŽtodo que da la salida si la palabra es o no  aceptada//
		if(this.automata.probarPalabra(this.palabra) == true){
			this.aceptacion = true;
			this.repaint();
		}
		else{
			this.aceptacion = false;
			this.repaint();
		}
	}
	
	public void cargarPalabra(){//MŽtodo que recibe la palabra//
		this.palabra = new JOptionPane("Favor de introducir la palabra").showInputDialog(rootPane, "Favor de introducir la palabra", "palabra");
		System.out.println(this.palabra);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.setBackground(Color.WHITE);
        Font fuente = new Font("Monospaced", Font.BOLD, 20);
        g.setFont(fuente);
		g.setColor(Color.BLUE);
		g.drawImage(this.logo, 30, 5, null);
		if(this.palabra == null){
			g.drawString("Favor de ingresar una palabra", 150, 300);
		}
		else{
			if(this.aceptacion == false){
				g.drawString("La palabra ingresada no es aceptada", 150, 300);
				g.drawImage(this.mal, 200, 350, null);
			}
			else{
				g.drawString("La palabra ingresada es aceptada", 150, 300);
				g.drawImage(this.bien, 270, 350, null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		while(true){
			InterfacePrincipal principal = new InterfacePrincipal();
			principal.cargarLector();
			principal.cargarAutomata();
			principal.cargarPalabra();
			principal.validarPalabra();
		}
		
	}

}
