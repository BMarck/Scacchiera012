package it.polito.tdp.scacchiera.model;

import javafx.concurrent.Task;

public class Ricerca {

	int conta;

	public int contaSoluzioni(int lato) {

		Scacchiera sc = new Scacchiera(lato);

		conta = 0;

		int fatto = 0; // quante posizioni sono già riempite

		cerca(sc, fatto);

		return conta;
	}

	private void cerca(Scacchiera sc, int fatto) {

		if (fatto == sc.size()) {
			// soluzione trovata!!!
			this.conta++;

			/*
			System.out.println("\nTrovata " + conta);

			for (Pos p : sc.getPosizioni())
				System.out.format("%d %d -> %d\n", p.getRiga(), p.getCol(), sc.get(p));
				*/

		} else {

			Pos p = sc.getPosizioni().get(fatto);

			
			if (tentativoValido(sc, p, 0)) {
				sc.set(p, 0);
				cerca(sc, fatto + 1);
				sc.delete(p);
			}

			if (tentativoValido(sc, p, 1)) {
				sc.set(p, 1);
				cerca(sc, fatto + 1);
				sc.delete(p);
			}

			if (tentativoValido(sc, p, 2)) {
				sc.set(p, 2);
				cerca(sc, fatto + 1);
				sc.delete(p);
			}

		}
	}
	
	private boolean tentativoValido(Scacchiera sc, Pos p, Integer i) {
		
		
		// alto
		Pos alto = new Pos(p.getRiga()-1, p.getCol()) ;
		Integer numAlto = sc.get(alto) ;
		if(numAlto !=  null && Math.abs(numAlto-i)!=1 )
			return false ;
		
		// sinistra
		Pos sx = new Pos(p.getRiga(), p.getCol()-1) ;
		Integer numSx = sc.get(sx) ;
		if(numSx !=  null && Math.abs(numSx-i)!=1 )
			return false ;

		return true ;
	}

	public static void main(String[] args) {
		Ricerca r = new Ricerca();

		r.contaSoluzioni(2);
		r.contaSoluzioni(3);
		
		r.contaSoluzioni(5);



	}

}
