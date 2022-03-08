package view;

/**
 * Rajapinta määrittelee pakolliset metodit käyttöliittymän visualiaaliselle
 * toteutukselle.
 * 
 * @author Aivan Vo, Elsa Rajala
 *
 */
public interface IVisualisointi {

	/**
	 * Tyhjennetään näyttö (toteutetaan aina uuden Visualisointi -olion yhteydessä)
	 * ja asetetaan alkuarvot
	 */
	public void tyhjennaNaytto();

	/**
	 * Uuden asiakkaan=pallon luonti ensimmäiselle palvelupisteelle
	 * (Vuoronumeroautomaatti)
	 */
	public void asiakasVuoronumeroon();

	/**
	 * Pallon poisto ensimmäiseltä palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasVuoronumerosta();

	/**
	 * Uuden asiakkaan=pallon luonti toiselle palvelupisteelle (Palvelupiste nro 1)
	 */
	public void asiakasPalvelupisteeseen();

	/**
	 * Pallon poisto toiselta palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasPalvelupisteelta();

	/**
	 * Uuden asiakkaan=pallon luonti kolmannelle palvelupisteelle (Palvelupiste nro
	 * 2)
	 */
	public void asiakasPalvelupisteeseen2();

	/**
	 * Pallon poisto kolmannelta palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasPalvelupisteelta2();

	/**
	 * Uuden asiakkaan=pallon luonti neljännelle palvelupisteelle (Kassa)
	 */
	public void asiakasKassalle();

	/**
	 * Pallon poisto neljänneltä palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasKassalta();

	/**
	 * Pallon luonti sivupalkkiin (Palvellut asiakkaat)
	 */
	public void asiakasPaveltuihin();

	/**
	 * Kertoo viimeisimmän Palvelupiste nro 1:llä olevan pallon x-koordinaatin
	 * 
	 * @return palauttaa Pavelupiste nro 1:llä olevan viimeisemmän pallon
	 *         x-koordinaatin arvon
	 */
	public double getppLkm();

	/**
	 * Kertoo viimeisimmän Palvelupiste nro 2:llä olevan pallon x-koordinaatin
	 * 
	 * @return palauttaa Pavelupiste nro 2:llä olevan viimeisemmän pallon
	 *         x-koordinaatin arvon
	 */
	public double getpp2Lkm();
}
