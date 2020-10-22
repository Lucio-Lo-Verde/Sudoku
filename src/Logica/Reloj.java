package Logica;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Reloj extends TimerTask {

	private Timer temporizador;

	private int horasPasadas;
	private int minutosPasados;
	private int segundosPasados;

	private JLabel decenaHoras;
	private JLabel unidadHoras;
	private JLabel decenaMinutos;
	private JLabel decenaSegundos;
	private JLabel unidadMinutos;
	private JLabel unidadSegundos;


	public Reloj(Timer t, JLabel decenaHoras, JLabel unidadHoras, JLabel decenaMinutos, JLabel unidadMinutos, JLabel decenaSegundos, JLabel unidadSegundos){
		this.temporizador = t;
		this.horasPasadas = 0;
		this.minutosPasados = 0;
		this.segundosPasados = 0;
		this.decenaHoras = decenaHoras;
		this.unidadHoras = unidadHoras;
		this.decenaMinutos = decenaMinutos;
		this.unidadMinutos = unidadMinutos;
		this.decenaSegundos = decenaSegundos;
		this.unidadSegundos = unidadSegundos;
	}

	@Override
	public void run() {

		ImageIcon imageIcon;

		//Cada vez que pasen un segundo
		if(segundosPasados<59){
			this.segundosPasados ++;

			//Cada vez que pasen 10 segundos
			if(segundosPasados>=10 && segundosPasados%10 == 0) {
				imageIcon = new ImageIcon(this.getClass().getResource("/img/" + segundosPasados/10 + ".png"));
				decenaSegundos.setIcon(imageIcon);
				reDimensionar(decenaSegundos, imageIcon);
			}
		}
		//Cada vez que pase un minuto
		else if(minutosPasados<59 && segundosPasados==59) {
			minutosPasados ++;
			segundosPasados = 0;

			//Cada vez que pasen 10 minutos
			if(minutosPasados>=10 && minutosPasados%10 == 0) {
				imageIcon = new ImageIcon(this.getClass().getResource("/img/" + minutosPasados/10 + ".png"));
				decenaMinutos.setIcon(imageIcon);
				reDimensionar(decenaMinutos, imageIcon);
			}

			imageIcon = new ImageIcon(this.getClass().getResource("/img/" + minutosPasados%10 + ".png"));
			unidadMinutos.setIcon(imageIcon);
			reDimensionar(unidadMinutos, imageIcon);

			imageIcon = new ImageIcon(this.getClass().getResource("/img/0.png"));
			decenaSegundos.setIcon(imageIcon);
			reDimensionar(decenaSegundos, imageIcon);
		}
		//Cada vez que pase una hora
		else if(minutosPasados == 59 && segundosPasados == 59) {
			horasPasadas ++;
			minutosPasados = 0;
			segundosPasados = 0;

			//Cada vez que pasen 10 horas
			if(horasPasadas>=10 && horasPasadas%10 == 0) {
				imageIcon = new ImageIcon(this.getClass().getResource("/img/" + horasPasadas/10 + ".png"));
				decenaHoras.setIcon(imageIcon);
				reDimensionar(decenaHoras, imageIcon);
			}

			imageIcon = new ImageIcon(this.getClass().getResource("/img/" + horasPasadas%10 + ".png"));
			unidadHoras.setIcon(imageIcon);
			reDimensionar(unidadHoras, imageIcon);

			imageIcon = new ImageIcon(this.getClass().getResource("/img/0.png"));
			decenaMinutos.setIcon(imageIcon);
			reDimensionar(decenaMinutos, imageIcon);

			unidadMinutos.setIcon(imageIcon);
			reDimensionar(unidadMinutos, imageIcon);

			decenaSegundos.setIcon(imageIcon);
			reDimensionar(decenaSegundos, imageIcon);
		}

		imageIcon = new ImageIcon(this.getClass().getResource("/img/" + segundosPasados%10 + ".png"));
		unidadSegundos.setIcon(imageIcon);
		reDimensionar(unidadSegundos, imageIcon);

		if(horasPasadas==23 && minutosPasados==59 && segundosPasados==59)
			temporizador.cancel();

	}

	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();

		}
	}

}
