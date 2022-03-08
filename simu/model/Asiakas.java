package simu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Luokka <code>Asiakas</code> mahdollistaa asiakkaiden luonnin järjestelmään
 * sekä tietokantaan
 * 
 * @author Elsa Rajala, Aivan Vo
 *
 */

@Entity
@Table(name = "asiakas")
public class Asiakas {
	/**
	 * Muuttuja <code>saapumisaika</code> ilmaisee asiakkaan saapumisajan
	 */
	@Transient
	private double saapumisaika;
	/**
	 * Muuttuja <code>poistumisaika</code> ilmaisee asiakkaan poistumisajan
	 */
	@Transient
	private double poistumisaika;
	/**
	 * Muuttuja <code>viipymisaika</code> ilmaisee asiakkaan viipymisajan
	 */
	@Column(name = "viipymisaika")
	private double viipymisaika;
	/**
	 * Muuttuja <code>id</code> ilmaisee asiakkaan id:n
	 */
	@GeneratedValue
	@Id
	@Column(name = "id")
	private int id;
	/**
	 * kokonaisluku, jonka arvo ilmaisee asiakasolion id:n
	 */
	@Transient
	private static int i = 1;
	/**
	 * liukuluku, jonka arvo ilmaisee asiakasolioiden viipymisajan summan
	 */
	@Transient
	private static double sum = 0;
	/**
	 * liukuluku, jonka arvo ilmaisee palveltujen asiakaolioiden lukumäärän
	 */
	@Transient
	private static double palvellut = 0; // laskee palveltujen asiakkaiden määrän keskiarvon saamiseksi
	/**
	 * liukuluku, jonka arvo ilmaisee palveltujen asiakaolioiden viipymisajan
	 * keskiarvon
	 */
	@Transient
	private double keskiarvo = 0;
	/**
	 * liukuluku, jonka avo ilmaisee apteekkisimulaattorin suoritustehon
	 */
	@Transient
	private double suoritusteho = 0;

	/**
	 * Luo <code>Asiakas</code>-olion, jonka saapumisajaksi annetaan
	 * <code>saapumisaika</code> ja jonka <code>id</code> kasvaa aina yhdellä.
	 */
	public Asiakas() {

		saapumisaika = Kello.getInstance().getAika();
		if (saapumisaika > 0) {
			id = i++;
			Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
		}
	}

	/**
	 * Luo <code>Asiakas</code>-olion, jonka id:ksi annetaan <code>id</code> ja
	 * jonka viipymisajaksi asetetaan <code>viipymisaika</code>
	 * 
	 * @param id           ilmentymän id
	 * @param viipymisaika ilmentymän viipymisaika
	 */
	public Asiakas(int id, double viipymisaika) {
		this.id = id;
		this.viipymisaika = viipymisaika;
	}

	/**
	 * Luo <code>Asiakas</code>-olion, jonka id:ksi annetaan <code>id</code>
	 * 
	 * @param id ilmentymän id
	 */
	public Asiakas(int id) {// Testejä varten
		this.id = id;
	}

	// getterit ja setterit
	/**
	 * Palauttaa <code>Asiakas</code>-olion poistumisajan
	 * 
	 * @return ilmentymän poistumisaika
	 */
	public double getPoistumisaika() {

		return this.poistumisaika;
	}

	/**
	 * Asettaa <code>Asiakas</code>-oliolle poistumisajan
	 * 
	 * @param poistumisaika ilmentymän poistumisaika
	 */
	public void setPoistumisaika(double poistumisaika) {

		this.poistumisaika = poistumisaika;
	}

	/**
	 * Palauttaa <code>Asiakas</code>-olion saapumisajan
	 * 
	 * @return ilmentymän saapumisaika
	 */
	public double getSaapumisaika() {
		return this.saapumisaika;
	}

	/**
	 * Asettaa <code>Asiakas</code>-oliolle saapumisajan
	 * 
	 * @param saapumisaika ilmentymän saapumisaika
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Palauttaa <code>Asiakas</code>-olion id:n
	 * 
	 * @return ilmentymän id
	 */
	public int getId() {
		return id;
	}

	// Asiakkaiden läpimenoaikojen keskiarvo:
	/**
	 * Palauttaa <code>Asiakas</code>-olioiden viipymisaikojen keskiarvon
	 * 
	 * @return ilmentymien viipymisaikojen keskiarvo
	 */
	public double getKeskiarvo() {

		return keskiarvo;
	}

	/**
	 * Metodi, joka palauttaa muuttujat alkuarvoihinsa
	 */
	public void nollaa() {
		i = 1;
		this.suoritusteho = 0;
		this.keskiarvo = 0;
		palvellut = 0;
		sum = 0;

	}

	/**
	 * Palauttaa <code>Asiakas</code>-olion viipymiajan pyöristettynä
	 * 
	 * @return ilmentymän viipymisaika
	 */
	public double getViipymisaika() {
		double v = this.poistumisaika - this.saapumisaika;
		double roundOff = Math.round(v * 100.0) / 100.0;
		return roundOff;
	}

	/**
	 * Kasvattaa palveltujen asiakkaiden lukumäärää ja laskee viipymisaikojen summan
	 * sekä keskiarvon. Palauttaa raportin konsoliin
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		palvellut++;
		sum += (poistumisaika - saapumisaika);
		keskiarvo = sum / palvellut;

		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);

	}

	// Antaa palveltujen asiakkaiden määrän per aikayksikkö
	/**
	 * Palauttaa apteekki-simulaattorin suoritustehon
	 * 
	 * @return suoritusteho
	 */
	public double suoritusteho() {
		return palvellut / Kello.getInstance().getAika();
	}

	// METODIT TESTEJÄ VARTEN

	/**
	 * Palauttaa <code>Asiakas</code>-olioiden viipymisaikojen keskiarvon
	 * 
	 * @param sum       ilmentymien viipymisaikojen summa
	 * @param palvellut ilmentymien lukumäärä
	 * @return ilmentymien viipymisaikojen keskiarvo
	 */
	public double getKeskiarvoo(double sum, int palvellut) {
		return sum / palvellut;
	}

	/**
	 * palauttaa merkkijonon, joka ilmaisee olion id:n ja viipymisajan
	 * 
	 * @return olion id ja viipymisaika
	 */
	public String toString() {
		return "Asiakas numero: " + this.id + " Viipymisaika: " + this.viipymisaika;
	}

}
