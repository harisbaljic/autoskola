package com.autoskola.instruktori.model;

public class Grad {
	private int GradId;
	private String Naziv;
	
	public Grad(int gradId, String naziv) {
		GradId = gradId;
		Naziv = naziv;
	}

	public int getGradID() {
		return GradId;
	}

	public void setGradID(int gradId) {
		GradId = gradId;
	}

	public String getNaziv() {
		return Naziv;
	}

	public void setNaziv(String naziv) {
		Naziv = naziv;
	}
	
	
	
}
