package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

/**
 * Luokka <code>Palvelupiste</code> mahdollistaa palvelupisteen luomisen ja
 * antaa metodeita jonon käsittelyyn
 * 
 * @author Elsa Rajala
 *
 */
public class Palvelupiste {

	/**
	 * Muuttuja <code>jono</code> on lista johon asiakkaat lisätään "jonoon"
	 */
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	/**
	 * Muuttuja <code>generator</code> antaa satunnaislukugeneraattorin
	 * palvelupisteen käyttöön
	 */
	private ContinuousGenerator generator;
	/**
	 * Muuttuja <code>tapahtumalista</code> antaa Tapahtumalistan palvelupisteen
	 * käyttöön
	 */
	private Tapahtumalista tapahtumalista;
	/**
	 * Muuttuja <code>skeduloitavanTapahtumanTyyppi</code> antaa TapahtumanTyypin
	 * palvelupisteen käyttöön
	 */
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	/**
	 * Muuttuja <code>kesto</code> ilmaisee palvelupisteen palvelun keston
	 */
	private double kesto; // Tehdään toinen konstruktori, jolla voidaan luoda palvelupiste jonka palvelun
							// kesto voidaan määrittää sitä luodessa

	// JonoStartegia strategia; //optio: asiakkaiden järjestys

	/**
	 * Muuttuja <code>varattu</code> ilmaisee onko palvelpisteellä asiakas vai ei
	 */
	private boolean varattu = false;

	/**
	 * Luo palvelupisteen, jolla on parametrina satunnaislukugeneraattori,
	 * tapahtumalista sekä tapahtumantyyppi
	 * 
	 * @param generator      satunnaislukugeneraattori
	 * @param tapahtumalista ilmentymän tapahtumalista
	 * @param tyyppi         ilmentymän tapahtumien tyyppi
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}

	// Tässä toinen konstruktori (käytetään vuoronumeroautomaatissa)
	/**
	 * Luo palvelupisteen, jolla on parametrina palveluajan kesto, tapahtumalista
	 * sekä tapahtumantyyppi
	 * 
	 * @param kesto          ilmentymän palveluajan kesto
	 * @param tapahtumalista ilmentymän tapahtumalista
	 * @param tyyppi         ilmentymän tapahtumien tyyppi
	 */
	public Palvelupiste(double kesto, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.kesto = kesto;
		this.tapahtumalista = tapahtumalista;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	/**
	 * Lisää asiakkaan jonoon
	 * 
	 * @param a jonoon lisättävä asiakas
	 */
	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		jono.add(a);

	}

	/**
	 * Ottaa palvelussa olleen asiakkaan pois jonosta, palauttaa jonossa olleen
	 * asiakkaan
	 * 
	 * @return jonosta poistettu asiakas
	 */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	/**
	 * Aloittaa palvelun jonossa ensimmäisenä olevalle asiakkaalle
	 */
	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika;
		if (this.kesto > 0) {
			palveluaika = this.kesto;
		} else {
			palveluaika = generator.sample();
		}

		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	/**
	 * Palauttaa boolean-arvon siitä että onko palvelupiste varattu vai ei
	 * 
	 * @return onko palvelupiste varattu
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Palauttaa boolean-arvon siitä että onko palvelupisteellä jonoa vai ei
	 * 
	 * @return onko palvelupisteelle jonoa
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	// Oma metodi, jolla tarkistetaan jonon pituudet ja tämän perusteella valitaan
	// kumpaan jonoon asiakas laitetaan vuoronumeroautomaatilta
	/**
	 * Palauttaa jonon pituuden
	 * 
	 * @return jonon pituus
	 */
	public int getJono() {
		return jono.size();
	}

}
