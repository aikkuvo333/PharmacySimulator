package view;

/**
 * Rajapinta, joka antaa metodit SimulaattorinGUI:lle
 * 
 * @author Aivan Vo, Elsa Rajala, Olli Ruuskanen
 *
 */
public interface ISimulaattorinUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	/**
	 * Palauttaa käyttäjän syöttämän ajan käyttöliittymästä. Palautettu arvo vastaa
	 * simuloinnin kestoa.
	 * 
	 * @return käyttäjän syöttämä aika doublena, jolla määrätään simuloinnin kesto.
	 */
	public double getAika();

	/**
	 * Palauttaa simulaattorille kontrollerissa asetetun viiveen
	 * 
	 * @return simulaattorin viive
	 */
	public long getViive();

	// Kontrolleri tarvitsee
	/**
	 * Palauttaa naytto-olion, joka kuvaa simulaattorin visualisointia
	 * 
	 * @return näytto-olio
	 */
	public IVisualisointi getVisualisointi();

	/**
	 * Suorituskykysuureiden tulostus dialogiin
	 */
	public void tulokset();

	/**
	 * Palauttaa valitun jakauman lista-indeksin
	 * 
	 * @return valitun jakauman lista-indeksi
	 */
	public int getJakaumaIndex();

}
