package it.polito.tdp.scacchiera.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scacchiera {
	
	private int N ;
	
	private Map<Pos,Integer> caselle ;
	private List<Pos> posizioni ;

	/** 
	 * Crea una scacchiera vuota di lato N
	 * @param n
	 */
	public Scacchiera(int n) {
		N = n;
		caselle = new HashMap<Pos,Integer>() ;
		
		// definisci le posizioni valide
		posizioni = new ArrayList<Pos>() ;
		for( int riga = 1 ; riga <= N; riga++ ) {
			for( int col = 1; col <= N; col++ ) {
				posizioni.add(new Pos(riga, col)) ;
			}
		}
		
	}
	
	public Integer get(Pos p) {
		return caselle.get(p) ;
	}
	
	public void set(Pos p, Integer i) {
		if( caselle.get(p)==null )
			caselle.put(p, i) ;
		else
			throw new RuntimeException("Casella già occupata") ;
	}
	
	public void delete(Pos p) {
		if( caselle.get(p)!=null ) 
			caselle.put(p, null) ;
		else
			throw new RuntimeException("Casella già vuota") ;
	}
	
	public boolean valid(Pos p) {
		return posizioni.contains(p) ;
	}
	
	public int size() {
		return posizioni.size() ;
	}
	
	public List<Pos> getPosizioni() {
		return posizioni ;
	}
	
	

	
}
