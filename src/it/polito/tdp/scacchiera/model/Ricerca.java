package it.polito.tdp.scacchiera.model;

import it.polito.tdp.scacchiera.ScacchieraController;

public class Ricerca {

	int conta;

	// il Task a cui comunicare gli aggiornamenti nello stato di avanzamento
	private ScacchieraController.TaskRicerca task;

	// costruttore utilizzato dal Main di prova (non collegato ad un Task)
	public Ricerca() {
		this.task = null;
	}

	// costruttore utilizzato dal Controller JavaFX (con la specifica del Task
	// asincrono)
	public Ricerca(ScacchieraController.TaskRicerca t) {
		this.task = t;
	}

	/**
	 * Calcola il numero di scacchiere {012} valide, di un determinato {@code lato}.
	 * 
	 * Complessità dell'algoritmo: O( 3 ^ (lato^2) )
	 * 
	 * @param lato Lato della scacchiera
	 * @return Numero di scacchiere valide
	 */
	public int contaSoluzioni(int lato) {

		Scacchiera sc = new Scacchiera(lato);

		conta = 0;

		int fatto = 0; // quante posizioni sono già riempite

		cerca(sc, fatto);

		return conta;
	}

	/**
	 * Procedura ricorsiva per il calcolo delle scacchiere {012} valide. Quanto
	 * trova una nuova soluzione, incremente la variabile {@code conta}
	 * 
	 * @param sc
	 *            La {@link Scacchiera} su cui effettuare la ricerca
	 * @param fatto
	 *            Il livello della ricorsione, che corrisponde al numero di
	 *            caselle già occupate.
	 */
	private void cerca(Scacchiera sc, int fatto) {

		// se la scacchiera è già tutta piena
		if (fatto == sc.size()) {
			// soluzione trovata!!!
			this.conta++;

			/*
			 * System.out.println("\nTrovata " + conta);
			 * 
			 * for (Pos p : sc.getPosizioni()) System.out.format("%d %d -> %d\n"
			 * , p.getRiga(), p.getCol(), sc.get(p));
			 */

		} else {

			// sono piene le posizioni 0..(fatto-1). Devo riempire la posizione
			// (fatto)

			Pos p = sc.getPosizioni().get(fatto);

			// progresso 0%
			if (fatto == 0 && task != null)
				task.myUpdateProgress(0, 3);

			// provo a mettere 0
			if (tentativoValido(sc, p, 0)) {
				// metto 0 nella (fatto)-esima casella
				sc.set(p, 0);
				// ricorsione: chiedo di riempire dalla casella (fatto+1) in
				// avanti
				cerca(sc, fatto + 1);
				// ripristino la scacchiera, rimuovendo lo 0 che avevo provato a
				// mettere
				sc.delete(p);
			}

			// progresso 33%
			if (fatto == 0 && task != null)
				task.myUpdateProgress(1, 3);

			// provo a mettere 1
			if (tentativoValido(sc, p, 1)) {
				sc.set(p, 1);
				cerca(sc, fatto + 1);
				sc.delete(p);
			}

			// progresso 66%
			if (fatto == 0 && task != null)
				task.myUpdateProgress(2, 3);

			// provo a mettere 2
			if (tentativoValido(sc, p, 2)) {
				sc.set(p, 2);
				cerca(sc, fatto + 1);
				sc.delete(p);
			}

			// progresso 100%
			if (fatto == 0 && task != null)
				task.myUpdateProgress(3, 3);
		}
	}

	/**
	 * Verifica se è lecito posizionare un valore in una certa casella della
	 * scacchiera, controllando che non venga mai violata la regola per cui due
	 * caselle hanno valori che differiscono sempre per 1. In pratica, il
	 * tentativo non è valido se esiste almeno una casella vicina che sia: (1)
	 * interna alla scacchiera, (2) già contenente un valore, (3) quel valore
	 * abbia differenza != 1.
	 * 
	 * @param sc
	 *            La scacchiera di gioco
	 * @param p
	 *            La posizione in cui intendo valutare la validità della mossa
	 * @param i
	 *            Il valore da provare (0, 1, 2)
	 * @return {@code true} se il tentativo è lecito, {@code false} se invece
	 *         viola il vincolo
	 */
	private boolean tentativoValido(Scacchiera sc, Pos p, Integer i) {

		// alto
		Pos alto = new Pos(p.getRiga() - 1, p.getCol());
		Integer numAlto = sc.get(alto);
		// nota: numAlto può essere null se (1) la casella è vuota, oppure (2)
		// la casella è fuori dalla scacchiera
		if (numAlto != null && Math.abs(numAlto - i) != 1)
			return false;

		// sinistra
		Pos sx = new Pos(p.getRiga(), p.getCol() - 1);
		Integer numSx = sc.get(sx);
		if (numSx != null && Math.abs(numSx - i) != 1)
			return false;

		// non verifico a destra e in basso perché sono sicuro che sono celle
		// vuote
		// (per come ho costruito l'elenco delle posizioni)

		return true;
	}

	public static void main(String[] args) {
		Ricerca r = new Ricerca();

		int sol;

		sol = r.contaSoluzioni(2);
		System.out.format("Lato %d, soluzioni %d\n", 2, sol);

		sol = r.contaSoluzioni(3);
		System.out.format("Lato %d, soluzioni %d\n", 3, sol);

		sol = r.contaSoluzioni(5);
		System.out.format("Lato %d, soluzioni %d\n", 5, sol);

	}

}
