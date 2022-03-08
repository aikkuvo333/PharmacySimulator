package simu.model;

/**
 * Tätä rajapintaa käyttävät Kontrolleri tietokannan tietojen tarkastelua varten
 * ja OmaMoottori tiedon tallentamista tietokantaan varten.
 * 
 * @author Aivan Vo
 *
 */
public interface IAsiakasDAO {

	/**
	 * Luo annetun asiakkaan perusteella uuden <code>Asiakas</code>-rivin
	 * tietokantaan
	 *
	 * @param a parametrinä annetaan asiakas, joka halutaan luoda tietokantaan
	 * @return boolean palauttaa arvona true tai false, sen mukaan onnistuiko
	 *         asiakkaan luonti tietokantaan
	 */
	public boolean createAsiakas(Asiakas a); // Omamoottori kutsuu tätä metodia

	/**
	 * Hakee tietokannasta Asiakas -tablesta id:n perusteella asiakkaan tiedot (eli
	 * viipymisajan).
	 *
	 * @param id parametrinä annetaan asiakkaan id, jonka tiedot halutaan hakea
	 *          tietokannasta.
	 * @return Asiakas palauttaa Asiakas -olion, jonka id vastaa parametrina
	 *         annettua id:tä.
	 */
	public Asiakas readAsiakas(int id); // Omamoottori kutsuu tätä metodia

	/**
	 * Hakee tietokannasta Asiakas -tablesta olemassaolevat asiakkaat ja sijoittaa
	 * ne Arrayhin, joka annetaan metodin paluuarvona.
	 *
	 * @return Asiakas[] palauttaa <code>Asiakas</code>-olioita sisältävän Asrrayn.
	 */
	public Asiakas[] readAsiakkaat(); // Kontrolleri kutsuu tätä metodia
}