package Logica;

public class Celda {

	private Integer valor;
	private EntidadGrafica entidadGrafica;
	
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new EntidadGrafica();
	}
	
	public void actualizar(int valor) {
		
		if(valor>=0 && valor<=8)
			this.valor = valor;
		
		entidadGrafica.actualizar(this.valor);
	}
	
	public int getCantElementos() {
		return this.entidadGrafica.getImagenes().length;
	}
	
	public Integer getValor() {
		return this.valor;
	}
	
	public void setValor(Integer valor) {
		if(valor != null && valor < this.getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizar(valor);
		}
		else
			this.valor = null;
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	public void setGrafica(EntidadGrafica g) {
		if(g != null)
			this.entidadGrafica = g;
	}
}
