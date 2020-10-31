package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import Logica.Celda;
import Logica.Reloj;
import Logica.Sudoku;
import javax.swing.JButton;

import java.util.Timer;
import java.util.TimerTask;


public class Gui extends JFrame {

	private FondoPanel contentPane;
	private JLabel [][] labelsNumeros;
	private Sudoku sudoku;
	private static final int ancho = 600;
	private static final int largo = 700;
	private int numSeleccionado;
	private Timer temporizador;
	private Reloj tarea;
	private JLabel inicio;
	private ImageIcon start;
	private ImageIcon pause;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {

		this.setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, ancho, largo);
		contentPane = new FondoPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		sudoku = new Sudoku();
		contentPane.setLayout(new GridLayout(sudoku.getCantFilas()+4, sudoku.getCantFilas()+4, 0, 0));

		ImageIcon intersec = new ImageIcon(this.getClass().getResource("/img/intersec2.png"));
		ImageIcon linea = new ImageIcon(this.getClass().getResource("/img/linHor.png"));
		ImageIcon esquina = new ImageIcon(this.getClass().getResource("/img/esquinaAD.png"));

		agregarSeparador();

		JLabel texto = new JLabel();
		contentPane.add(texto);
		texto.setText("Time:");
		texto.setForeground(Color.RED);

		ImageIcon icono0 = new ImageIcon(this.getClass().getResource("/img/0.png"));
		ImageIcon iconoDosPuntos = new ImageIcon(this.getClass().getResource("/img/dosPuntos.png"));

		JLabel decenaHoras = new JLabel();
		agregarImagen(decenaHoras, icono0);

		JLabel unidadHoras = new JLabel();
		agregarImagen(unidadHoras, icono0);

		JLabel dosPuntosH = new JLabel();
		agregarImagen(dosPuntosH, iconoDosPuntos);

		JLabel decenaMinutos = new JLabel();
		agregarImagen(decenaMinutos, icono0);

		JLabel unidadMinutos = new JLabel();
		agregarImagen(unidadMinutos, icono0);

		JLabel dosPuntos = new JLabel();
		agregarImagen(dosPuntos, iconoDosPuntos);

		JLabel decenaSegundos = new JLabel();
		agregarImagen(decenaSegundos, icono0);

		JLabel unidadSegundos = new JLabel();
		agregarImagen(unidadSegundos, icono0);

		agregarSeparador();

		this.inicio = new JLabel();
		contentPane.add(this.inicio);
		this.inicio.setText("START");
		this.inicio.setForeground(Color.GREEN);

		JButton btnGo = new JButton();
		btnGo.setActionCommand("START");
		btnGo.addActionListener(new buttonListener());
		contentPane.add(btnGo);
		this.start = new ImageIcon(this.getClass().getResource("/img/go.png"));
		ajustarIcono(btnGo, this.start);
		this.pause = new ImageIcon(this.getClass().getResource("/img/pause.png"));
		ajustarIcono(btnGo, this.pause);
		btnGo.setIcon(this.start);

		for(int i=0 ; i<sudoku.getCantFilas()+3 ; i++) {

			if(i==3 || i==7)
				agregarSeparador(intersec);
			else if(i==11)
				agregarSeparador(esquina);
			else
				agregarSeparador(linea);

		}

		texto = new JLabel();
		contentPane.add(texto);
		texto.setText("Select:");
		texto.setForeground(Color.RED);

		this.numSeleccionado = 1;
		this.labelsNumeros = new JLabel[sudoku.getCantFilas()][sudoku.getCantFilas()];
		int boton = 1;

		for (int i = 0; i <sudoku.getCantFilas(); i++) {
			for(int j =0; j<sudoku.getCantFilas(); j++) {
				Celda c = sudoku.getCelda(i,j);
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				JLabel label = new JLabel();

				this.labelsNumeros[i][j] = label;
				contentPane.add(label);
				label.setVisible(false);

				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});

				if(sudoku.getCelda(i, j).getValor() == null) {

					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							sudoku.accionar(c, numSeleccionado-1);

							reDimensionar(label,grafico);
						}
					});
				}

				if((j+1)%3 == 0 && j<sudoku.getCantFilas()-1) {
					linea = new ImageIcon(this.getClass().getResource("/img/linVert.png"));
					agregarSeparador(linea);
				}

			}

			linea = new ImageIcon(this.getClass().getResource("/img/linVert.png"));

			if(boton<=9) {
				agregarSeparador(linea);
				agregarBotonNumerico(boton);
			}
			else if(boton==11) {
				agregarSeparador(linea);

				JButton btnVerificar = new JButton();
				btnVerificar.setActionCommand("Verificar");
				btnVerificar.addActionListener(new buttonListener());
				contentPane.add(btnVerificar);
				agregarIcono(btnVerificar, new ImageIcon(this.getClass().getResource("/img/check.png")));
			}
			else {
				agregarSeparador(linea);
				texto = new JLabel();
				contentPane.add(texto);
				texto.setText("Check:");
				texto.setForeground(Color.RED);
			}
			boton ++;

			if((i+1)%3 == 0 && i<sudoku.getCantFilas()-1) {
				linea = new ImageIcon(this.getClass().getResource("/img/linHor.png"));
				ImageIcon cruz = new ImageIcon(this.getClass().getResource("/img/cruz.png"));
				for(int k=0 ; k<sudoku.getCantFilas()+2 ; k++) {
					if(k==3 || k==7)
						agregarSeparador(cruz);
					else
						agregarSeparador(linea);
				}

				intersec = new ImageIcon(this.getClass().getResource("/img/intersec.png"));

				agregarSeparador(intersec);
				agregarBotonNumerico(boton);
				boton ++;
			}

		}

		this.temporizador = new Timer();
		this.tarea = new Reloj(this.temporizador, decenaHoras, unidadHoras, decenaMinutos, unidadMinutos, decenaSegundos, unidadSegundos);
		this.temporizador.schedule(tarea,0,1000);
	}

	private void agregarBotonNumerico(int indice) {

		JButton btn = new JButton();
		btn.setActionCommand(indice + "");
		btn.addActionListener(new buttonListener());
		contentPane.add(btn);

		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/" + indice + ".png"));
		agregarIcono(btn, icon);
	}

	private void agregarIcono(JButton btn, ImageIcon icon) {

		btn.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				btn.setIcon(icon);
				Image image = icon.getImage();
				if (image != null) {
					Image newImg = image.getScaledInstance((int) btn.getSize().getWidth(), (int) btn.getSize().getHeight(),  java.awt.Image.SCALE_SMOOTH);
					btn.setVerticalTextPosition(SwingConstants.CENTER);
					btn.setHorizontalTextPosition(SwingConstants.CENTER);
					icon.setImage(newImg);
					btn.repaint();
				}
			}
		});
	}

	private void ajustarIcono(JButton btn, ImageIcon icon) {

		btn.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				Image image = icon.getImage();
				if (image != null) {
					Image newImg = image.getScaledInstance((int) btn.getSize().getWidth(), (int) btn.getSize().getHeight(),  java.awt.Image.SCALE_SMOOTH);
					btn.setVerticalTextPosition(SwingConstants.CENTER);
					btn.setHorizontalTextPosition(SwingConstants.CENTER);
					icon.setImage(newImg);
				}
			}
		});
	}

	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();

		}
	}

	private void agregarImagen(JLabel label, ImageIcon imagen) {

		contentPane.add(label);
		label.setSize(50,55);

		label.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				label.setIcon(imagen);
				reDimensionar(label, imagen);
			}
		});

	}

	private void agregarSeparador(ImageIcon linea) {
		JLabel separador = new JLabel();
		contentPane.add(separador);

		separador.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				reDimensionar(separador, linea);
				separador.setIcon(linea);
			}
		});

	}

	private void agregarSeparador() {
		JLabel separador = new JLabel();
		contentPane.add(separador);
	}


	private class buttonListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {

			JButton boton = new JButton();

			try {
				boton = (JButton) e.getSource();

			}catch(ClassCastException exc) {
				exc.printStackTrace();
			}

			String src = boton.getActionCommand();

			if(src == "Verificar") {
				if(sudoku.getCantNumInsertados() == sudoku.getCantFilas()*sudoku.getCantFilas()) {

					sudoku.verificar();

					if(sudoku.juegoTerminado()) {

						temporizador.cancel();
						temporizador.purge();
						int opcion = JOptionPane.YES_NO_OPTION;
						int respuesta = JOptionPane.showConfirmDialog (null, "¡Gano la partida! ¿Desea comenzar otro juego?","Nuevo juego", opcion);
						setVisible(false);
						dispose();
						if(respuesta == JOptionPane.YES_OPTION)
							main(null);
					}
					else {
						for(int i=0 ; i<sudoku.getCantFilas() ; i++)
							for(int j=0 ; j<sudoku.getCantFilas() ; j++)
								reDimensionar(labelsNumeros[i][j], sudoku.getCelda(i,j).getEntidadGrafica().getGrafico());
						JOptionPane.showMessageDialog(null, "Hay algunos errores en el tablero. Ayuda: Los numeros que no cumplen con las reglas del juego estan marcados en rojo", "Verificacion del resultado", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Por favor complete todas las casillas antes de verificar", "Verificacion del resultado", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if(src == "START") {

				for(int i=0 ; i<sudoku.getCantFilas() ; i++)
					for(int j=0 ; j<sudoku.getCantFilas() ; j++)
						labelsNumeros[i][j].setVisible(true);

				//temporizador = new Timer();
				//temporizador.schedule(tarea,0,1000);
				tarea.empezar();
				
				boton.setIcon(pause);
				boton.setActionCommand("PAUSE");
				inicio.setText("Pause");
				inicio.setForeground(Color.ORANGE);
				
			}
			else if(src == "PAUSE") {
				for(int i=0 ; i<sudoku.getCantFilas() ; i++)
					for(int j=0 ; j<sudoku.getCantFilas() ; j++)
						labelsNumeros[i][j].setVisible(false);

				//temporizador.cancel();
				//temporizador.purge();
				tarea.pausar();

				boton.setIcon(start);
				boton.setActionCommand("START");
				inicio.setText(" Start");
				inicio.setForeground(Color.GREEN);

			}
			else if(src.equals("1") || src.equals("2") || src.equals("3") || src.equals("4") || src.equals("5") || src.equals("6") || src.equals("7") || src.equals("8") || src.equals("9")){

				char c = src.charAt(0);

				switch (c) {
				case '1':
					numSeleccionado = 1;
					break;
				case '2':
					numSeleccionado = 2;
					break;
				case '3':
					numSeleccionado = 3;
					break;
				case '4':
					numSeleccionado = 4;
					break;
				case '5':
					numSeleccionado = 5;
					break;
				case '6':
					numSeleccionado = 6;
					break;
				case '7':
					numSeleccionado = 7;
					break;
				case '8':
					numSeleccionado = 8;
					break;
				case '9':
					numSeleccionado = 9;
					break;
				default:
					System.out.println("JavaButton: Fuente desconocida");
					break;
				}
			}
		}
	}
	
	private class FondoPanel extends JPanel{
		
		private Image imagen;
		
		@Override
		public void paint(Graphics grafico) {
			
			imagen = new ImageIcon(this.getClass().getResource("/img/space.png")).getImage();
			grafico.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
			super.paint(grafico);
			setVisible(true);
		}
		
	}
	
}
