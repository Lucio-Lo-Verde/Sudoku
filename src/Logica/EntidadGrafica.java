package Logica;

import javax.swing.ImageIcon;

public class EntidadGrafica {

	private ImageIcon grafico;
	private String[] imagenes;
	
	public EntidadGrafica() {
		this.grafico = new ImageIcon();
		agregarImagenes();
	}
	
	public void actualizar(int indice) {
		if(indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setImagenes(String [] imagenes) {
		if(imagenes != null)
			this.imagenes = imagenes;
	}
	
	private void agregarImagenes(){
		imagenes = new String[9];
		for(int i=0 ; i<imagenes.length ; i++) {
			imagenes[i] = "/img/" + (i+1) + ".png";
		}
	}

	public void pintarDeAzul(int indice) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/lblue" + (indice+1) + ".png"));
		this.grafico.setImage(imageIcon.getImage());
	}

	public void pintarDeRojo(int indice) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/red" + (indice+1) + ".png"));
		this.grafico.setImage(imageIcon.getImage());
	}

	public void pintarDeBlanco(int indice) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/" + (indice+1) + ".png"));
		this.grafico.setImage(imageIcon.getImage());
	}
}
