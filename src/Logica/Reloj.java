package Logica;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

	private ImageIcon [] iconos;


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

		this.iconos = new ImageIcon[10];

		for(int i=0 ; i<10 ; i++) {
			this.iconos[i] = new ImageIcon(this.getClass().getResource("/img/" + i + ".png"));
			reDimensionar(unidadSegundos, this.iconos[i]);
		}

	}

	@Override
	public void run() {

		//Cada vez que pase un segundo
		this.segundosPasados ++;
		unidadSegundos.setIcon(iconos[segundosPasados%10]);

		//Cada vez que pasen 10 segundos
		if(segundosPasados%10 == 0 && segundosPasados<60 && segundosPasados>=10) {
			decenaSegundos.setIcon(iconos[segundosPasados/10]);
		}
		//Cada vez que pase un minuto
		else if(minutosPasados<59 && segundosPasados==60) {
			minutosPasados ++;
			segundosPasados = 0;

			//Cada vez que pasen 10 minutos
			if(minutosPasados>=10 && minutosPasados%10 == 0) {
				decenaMinutos.setIcon(iconos[minutosPasados/10]);
			}

			unidadMinutos.setIcon(iconos[minutosPasados%10]);

			decenaSegundos.setIcon(iconos[0]);
		}
		//Cada vez que pase una hora
		else if(minutosPasados==59 && segundosPasados==60) {
			horasPasadas ++;
			minutosPasados = 0;
			segundosPasados = 0;

			//Cada vez que pasen 10 horas
			if(horasPasadas>=10 && horasPasadas%10 == 0) {
				decenaHoras.setIcon(iconos[horasPasadas/10]);
			}

			unidadHoras.setIcon(iconos[horasPasadas%10]);

			decenaMinutos.setIcon(iconos[0]);

			unidadMinutos.setIcon(iconos[0]);

			decenaSegundos.setIcon(iconos[0]);
		}

		if(horasPasadas==23 && minutosPasados==59 && segundosPasados==59)
			temporizador.cancel();

	}

	private void reDimensionar(JLabel label, ImageIcon grafico) {

		label.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				Image image = grafico.getImage();
				if (image != null) {
					Image newImg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
					grafico.setImage(newImg);
				}
			}
		});

	}

}
