package simu.model;

/**
 * Luokka <code>TapahtumanTyyppi</code> luo eri tapahtumien tyypit
 * 
 * @author Elsa Rajala
 *
 */
public enum TapahtumanTyyppi {
	/**
	 * Saapuminen järjestelmään
	 */
	ARR1, // Saapuminen
	/**
	 * Saapuminen vuoronumeroautomaatille
	 */
	VUORO, // Vuoronumeroautomaatti
	/**
	 * Saapuminen repestitiskille 1
	 */
	RES1, // Reseptitiski 1
	/**
	 * Saapuminen repestitiskille 2
	 */
	RES2, // Reseptitiski 2
	/**
	 * Saapuminen kassalle
	 */
	KASSA // Kassa

}
