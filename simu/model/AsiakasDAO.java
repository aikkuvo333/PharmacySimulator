package simu.model;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Luokka <code>AsiakasDAO</code> mahdollistaa yhteyden luomisen ulkoiseen
 * tietokantaan Hibernaten avulle
 * 
 * @author Aivan Vo, Elsa Rajala
 *
 */
public class AsiakasDAO implements IAsiakasDAO {
	/**
	 * Session factory, jonka avulla saadaan yhteys tietokantaan luotua
	 */
	private SessionFactory istuntotehdas = null;
	/**
	 * Transaktioon luodaan istunnot
	 */
	Transaction transaktio = null;

	/**
	 * Luetaan konfigurointitiedosto ja luodaan <code>SessionFactory</code> yhteys.
	 */
	public AsiakasDAO() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Luo annetun asiakkaan perusteella uuden <code>Asiakas</code>-rivin
	 * tietokantaan
	 *
	 * @param a parametrinä annetaan asiakas, joka halutaan luoda tietokantaan
	 * @return boolean palauttaa arvona true tai false, sen mukaan onnistuiko
	 *         asiakkaan luonti tietokantaan
	 */
	public boolean createAsiakas(Asiakas a) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			Asiakas as = new Asiakas(0, (a.getViipymisaika())); // id automaattisesti generoitu
			istunto.save(as);
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
				return false;
			}
		}
		return true;
	}

	/**
	 * Hakee tietokannasta Asiakas -tablesta id:n perusteella asiakkaan tiedot (eli
	 * viipymisajan).
	 *
	 * @param id parametrinä annetaan asiakkaan id, jonka tiedot halutaan hakea
	 *           tietokannasta.
	 * @return Asiakas palauttaa Asiakas -olion, jonka id vastaa parametrina
	 *         annettua id:tä.
	 */
	public Asiakas readAsiakas(int id) {

		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			Asiakas asi = new Asiakas();
			istunto.load(asi, id);
			transaktio.commit();
			return asi;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}
	}

	/**
	 * Hakee tietokannasta Asiakas -tablesta olemassaolevat asiakkaat ja sijoittaa
	 * ne Arrayhin, joka annetaan metodin paluuarvona.
	 *
	 * @return Asiakas[] palauttaa <code>Asiakas</code>-olioita sisältävän arrayn
	 */
	@Override
	public Asiakas[] readAsiakkaat() {
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Asiakas> result = istunto.createQuery("from Asiakas").list();
			for (Asiakas a : (List<Asiakas>) result) {
				System.out.println(a);
				asiakkaat.add(a); // Lisätään asiakas ArrayListiin
			}
			istunto.getTransaction().commit();
			istunto.close();

			Asiakas[] returnArray = new Asiakas[result.size()];
			return (Asiakas[]) asiakkaat.toArray(returnArray); // Muunnetaan Asiakas -oliot ArrayLististä Arrayhin
		} catch (Exception e) {
			if (transaktio != null)
				return null;
			throw e;
		}
	}

}