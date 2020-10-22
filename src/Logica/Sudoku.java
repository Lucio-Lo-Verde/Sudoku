package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sudoku {

	private Celda [][] tablero;
	private int cantFilas;
	private int cantNumInsertados;
	private boolean juegoTerminado;
	private int [][] numIniciales;
	private static Logger logger;

	public Sudoku() {
		this.cantFilas = 9;
		this.tablero = new Celda[this.cantFilas][this.cantFilas];		
		this.cantNumInsertados = 0;
		this.juegoTerminado = false;

		if(logger == null) {

			logger = Logger.getLogger(this.getClass().getName());

			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.FINE);
			logger.addHandler(hnd);

			logger.setLevel(Level.INFO);
		}

		File file = new File("C:\\Users\\Lucio\\Desktop\\workspace\\Sudoku3\\src\\solucion.txt"); //Introduzca la ruta del archivo tablero.txt
		Scanner sc;
		int [][] entrada = new int [this.cantFilas][this.cantFilas];
		int cantLineasFile = 0;
		boolean sePaso = false; //Es verdadero si el archivo de texto no respeta el formato dado

		//Leo el archivo de texto linea por linea y luego con cada linea decido si el formato del archivo es valido
		try {
			String cadena;
			int num = 0;
			char c;
			int filaFile = 0;
			int colaFile = 0;

			sc = new Scanner(file);

			while (sc.hasNextLine() && !sePaso) {

				cadena = sc.nextLine();
				cantLineasFile ++;

				if(cadena.length() != (this.cantFilas*2)-1)
					sePaso = true;
				else {
					for(int i=0 ; i<cadena.length() && !sePaso ; i++) {

						c = cadena.charAt(i);

						if(i%2 == 0) {
							switch (c) {
							case '1':
								num = 1;
								break;
							case '2':
								num = 2;
								break;
							case '3':
								num = 3;
								break;
							case '4':
								num = 4;
								break;
							case '5':
								num = 5;
								break;
							case '6':
								num = 6;
								break;
							case '7':
								num = 7;
								break;
							case '8':
								num = 8;
								break;
							case '9':
								num = 9;
								break;
							default:
								sePaso = true;
								break;
							}
							if(!sePaso) {

								entrada[filaFile][colaFile] = num-1;

								if(colaFile<this.cantFilas-1)
									colaFile ++;
								else {
									filaFile ++;
									colaFile = 0;

								}
							}
						}
						else if(c != ' ')
							sePaso = true;

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if(!sePaso && cantLineasFile == this.cantFilas && verificarEntrada(entrada)) {

			this.numIniciales = new int[this.cantFilas][this.cantFilas];
			Random rand = new Random();

			for (int i=0 ; i<this.cantFilas ; i++) {
				for(int j=0 ; j<this.cantFilas ; j++) {

					this.tablero[i][j] = new Celda();

					int value = rand.nextInt(3);

					if(this.cantNumInsertados<32 && value==0) {
						this.tablero[i][j].setValor(entrada[i][j]);
						this.tablero[i][j].getEntidadGrafica().pintarDeAzul(this.tablero[i][j].getValor().intValue());
						numIniciales[i][j] = entrada[i][j];
						this.cantNumInsertados ++;
					}
					else
						numIniciales[i][j] = -1;
				}
			}
			
		}
		else {
			//Mensaje del logger
			logger.severe("EL ARCHIVO '" + file.getName() + "' NO RESPETA EL FORMATO DADO. TERMINANDO EJECUCION...");

			//Cierra la ejecucion del programa
			System.exit(1);
		}
	}

	public void accionar(Celda c, int elem) {
		if(c != null) {
			if(c.getValor() == null)
				cantNumInsertados++;
			c.actualizar(elem);
		}
	}

	public Celda getCelda(int i, int j) {
		Celda aRetornar = null;
		if(i<this.cantFilas && j<this.cantFilas)
			aRetornar = this.tablero[i][j];

		return aRetornar;
	}

	public int getCantFilas() {
		return this.cantFilas;
	}

	public int getCantNumInsertados() {
		return cantNumInsertados;
	}

	public boolean juegoTerminado() {
		return juegoTerminado;
	}

	private boolean estaRepetido(int elem, int fil, int col) {
		//Busca si el numero elem esta en la fila fil, en la columna col o dentro de su respectivo panel de 3x3
		//Si se da al menos una de las 3 condiciones anteriores,
		//quiere decir que el numero elem que quiero insertar en el tablero ya a romper con almenos una de las reglas del juego

		boolean encontre = false;

		//Busca si el numero elem esta en la fila fil
		for(int i=0 ; i<this.cantFilas && !encontre ; i++) 
			if(tablero[fil][i] != null)
				if(tablero[fil][i].getValor() != null)
					if(tablero[fil][i].getValor() == elem)
						encontre = true;

		//Busca si el numero elem esta en la columna col
		for(int i=0 ; i<this.cantFilas && !encontre ; i++) 
			if(tablero[i][col] != null)
				if(tablero[i][col].getValor() != null)
					if(tablero[i][col].getValor() == elem)
						encontre = true;

		//Busca si el numero elem se repite en su respectivo panel de 3x3 
		if(!encontre) {
			int filaInicial = (Math.floorDiv(fil,3))*3;
			int colInicial = (Math.floorDiv(col,3))*3;

			for(int i=filaInicial ; i<filaInicial+3 && !encontre ; i++) 
				for(int j=colInicial ; j<colInicial+3 && !encontre ; j++) 
					if(tablero[i][j] != null)
						if(tablero[i][j].getValor() != null)
							if(tablero[i][j].getValor() == elem)
								encontre = true;
		}

		return encontre;
	}

	public void verificar() {
		//Este metodo se invoca una vez que se pulso el boton de verificar
		//verifica si el jugador gano la partida

		if(this.cantNumInsertados == this.cantFilas*this.cantFilas) {

			this.juegoTerminado = true; //Asumo que el jugador gano la partida hasta demostrar lo contrario

			for(int i=0 ; i<this.cantFilas; i++)
				for(int j=0 ; j<this.cantFilas; j++)
					if(numIniciales[i][j] == -1)
						this.tablero[i][j].getEntidadGrafica().pintarDeBlanco(this.tablero[i][j].getValor().intValue());

			//Verifico si se repiten los numeros entre las filas y entre las columnas del tablero
			for(int i=0 ; i<this.cantFilas; i++) {

				for(int j=0 ; j<this.cantFilas-1 ; j++) {

					for(int k=j+1 ; k<this.cantFilas ; k++) {

						//Verificacion por filas
						if(this.tablero[i][j].getValor().intValue() == this.tablero[i][k].getValor().intValue()) {
							this.juegoTerminado = false;
							if(numIniciales[i][j] == -1) //Verifico si el numero que se repite no es de los originales (los pintados en azul)
								this.tablero[i][j].getEntidadGrafica().pintarDeRojo(this.tablero[i][j].getValor().intValue()); //Pinto de rojo los numeros que esten repetidos
							if(numIniciales[i][k] == -1)
								this.tablero[i][k].getEntidadGrafica().pintarDeRojo(this.tablero[i][k].getValor().intValue());
						}

						//Verificacion por columnas
						if(this.tablero[j][i].getValor().intValue() == this.tablero[k][i].getValor().intValue()) {
							this.juegoTerminado = false;
							if(numIniciales[j][i] == -1)
								this.tablero[j][i].getEntidadGrafica().pintarDeRojo(this.tablero[j][i].getValor().intValue());
							if(numIniciales[k][i] == -1)
								this.tablero[k][i].getEntidadGrafica().pintarDeRojo(this.tablero[k][i].getValor().intValue());
						}

					}//Fin bucle for(int k=j+1 ; k<this.cantFilas ; k++)

				}//Fin bucle for(int j=0 ; j<this.cantFilas-1 ; j++)

			}//Fin bucle for(int i=0 ; i<this.cantFilas; i++)

			int filaInicial = 0;
			int colInicial = 0;
			int primFil;
			int primCol;

			//Verifico si se repiten los numeros entre los paneles
			while(filaInicial<this.cantFilas) {
				while(colInicial<this.cantFilas) {

					for(int i=filaInicial ; i<filaInicial+3; i++) {
						for(int j=colInicial ; j<colInicial+3 ; j++) {

							if(j<colInicial+2) {
								primFil = i;
								primCol = j+1;
							}
							else {
								primFil = i+1;
								primCol = colInicial;
							}

							for(int k=primFil ; k<filaInicial+3 ; k++) {
								for(int l=primCol ; l<colInicial+3 ; l++) {

									if(this.tablero[i][j].getValor().intValue() == this.tablero[k][l].getValor().intValue()) {
										this.juegoTerminado = false;
										if(numIniciales[i][j] == -1)
											this.tablero[i][j].getEntidadGrafica().pintarDeRojo(this.tablero[i][j].getValor().intValue());
										if(numIniciales[k][l] == -1)
											this.tablero[k][l].getEntidadGrafica().pintarDeRojo(this.tablero[k][l].getValor().intValue());
									}
								}
								primCol = colInicial;
							}
						}
					}
					colInicial += 3;
				}
				filaInicial += 3;
				colInicial = 0;
			}

		}//Fin condidicional(this.cantNumInsertados == this.cantFilas*this.cantFilas)

	}//Fin metodo verificar()

	private boolean verificarEntrada(int[][] entrada) {
		//Este metodo se invoca en el constructor para verificar que el archivo de texto posee una solucion valida

		boolean esValida = true; //Asumo que el archivo de texto posee una solucion valida hasta demostrar lo contrario

		//Verifico si se repiten los numeros entre las filas y entre las columnas en el tablero del archivo de entrada
		for(int i=0 ; i<this.cantFilas && esValida ; i++)
			for(int j=0 ; j<this.cantFilas-1 && esValida ; j++)
				for(int k=j+1 ; k<this.cantFilas && esValida ; k++)
					if(entrada[i][j] == entrada[i][k] || entrada[j][i] == entrada[k][i])
						esValida = false;


		if(esValida) {

			int filaInicial = 0;
			int colInicial = 0;
			int primFil;
			int primCol;

			//Verifico si se repiten los numeros entre los paneles
			while(filaInicial<this.cantFilas && esValida) {
				while(colInicial<this.cantFilas && esValida) {

					for(int i=filaInicial ; i<filaInicial+3 && esValida ; i++) {
						for(int j=colInicial ; j<colInicial+3 && esValida ; j++) {

							if(j<colInicial+2) {
								primFil = i;
								primCol = j+1;
							}
							else {
								primFil = i+1;
								primCol = colInicial;
							}

							for(int k=primFil ; k<filaInicial+3 && esValida ; k++) {
								for(int l=primCol ; l<colInicial+3 && esValida ; l++) {

									if(entrada[i][j] == entrada[k][l])
										esValida = false;
								}
								primCol = colInicial;
							}
						}
					}
					colInicial += 3;
				}
				filaInicial += 3;
				colInicial = 0;
			}
		}

		return esValida;
	}//Fin metodo verificarEntrada(int[][])
}
