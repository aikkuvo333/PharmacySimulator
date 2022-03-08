package controller;

import simu.model.Asiakas;

/**
 * Tämä rajapinta <code>IKontrolleri</code> tarjoaa <code>Kontrolleri</code>-luokalle metodit
 * @author Aivan Vo, Elsa Rajala, Olli Ruuskanen
 *
 */
public interface IKontrolleri {

	// Rajapinta, joka tarjotaan käyttöliittymälle:
	
	/**
	 * Käynnistää simuloinnin, luo moottorin konrollerille ja tyhjentää visuaalisen näytön
	 */
	public void kaynnistaSimulointi();
	/**
	 * Asettaa moottorille pienemmän viiveen, simulaatio nopeutuu
	 */
	public void nopeuta();
	/**
	 * Asettaa moottorille isomman viiveen, simulaatio hidastuu
	 */
	public void hidasta();
	/**
	 * palauttaa asiakkaiden viipymisaikojen keskiarvon
	 * @return asiakkaiden viipymisaikojen keskiarvo
	 */
	public double keskiarvo();
	/**
	 * Palauttaa apteekkisimulaattorin suoritustehon
	 * @return suoritusteho
	 */
	public double suoritusteho();

	// Rajapinta, joka tarjotaan moottorille:

	/**
	 * Metodi lisää käyttöliittymän visualisointiin uuden asiakkaan (pallon)
	 */
	public void visualisoiAsiakas();
	/**
	 * Antaa tulokset käyttöliittymälle kun simulaatio on suoritettu
	 */
	public void naytaTulokset();

	// itse lisätyt visualisoinnit
	/**
	 * Metodi poistaa käyttöliittymän visualisoinnista asiakkaan (pallon)
	 */
	public void visualisoiPoistuvaAsiakas();
	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	public void visualisoiPalvelupisteenAsiakas();
	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	public void visualisoiPalvelupisteen2Asiakas();
	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	public void visualisoiKassanAsiakas();
	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	public void visualisoiPalveltuAsiakas();
	/**
	 * Palauttaa käyttöliittymässä valitun jakauman indeksin kokonaislukuna
	 * @return valitun jakauman indeksi
	 */
	public int aktiivisenJakaumanIndex();
	/**
	 * Palauttaa taulukon asiakkaista, jonka asiakasdao on lukenut tietokannasta
	 * @return taulukko asiakas-olioita
	 */
	public Asiakas[] getAsiakkaat();
}
