package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.Asiakas;
import simu.model.AsiakasDAO;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

/**
 * Luokka <code>Kontrolleri</code> antaa mahdollisuuden luoda kontrolleri joka
 * yhdistää model- ja view-tasot
 * 
 * @author Aivan Vo, Elsa Rajala, Olli Ruuskanen
 *
 */
public class Kontrolleri implements IKontrolleri { // UUSI
	/**
	 * Muuttuja <code>asiakadao</code> antaa <code>AsiakasDAO</code>-luokan olion
	 * kontrollerin käyttöön
	 */
	private AsiakasDAO asiakasdao = new AsiakasDAO();
	/**
	 * Muuttuja <code>moottori</code>antaa <code>IMoottori</code>-rajapinnan
	 * toteuttavan olion kontrollerin käyttöön
	 */
	private IMoottori moottori;
	/**
	 * Muuttuja <code>ui</code> antaa <code>ISimulaattorinUI</code>-rajapinnan
	 * toteuttavan olion kontrollerin käyttöön
	 */
	private ISimulaattorinUI ui;

	/**
	 * Luo kontrollerin jonka käyttöliittymäksi asetetaan <code>ui</code>
	 * 
	 * @param ui kontrollerin käyttöliittymä
	 */
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	// Moottorin ohjausta:

	/**
	 * Käynnistää simuloinnin, luo moottorin konrollerille ja tyhjentää visuaalisen
	 * näytön
	 */
	@Override
	public void kaynnistaSimulointi() {

		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		Kello.getInstance().setAika(0);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(100);

		ui.getVisualisointi().tyhjennaNaytto();
		((Thread) moottori).start();

	}

	/**
	 * Asettaa moottorille isomman viiveen, simulaatio hidastuu
	 */
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	/**
	 * Asettaa moottorille pienemmän viiveen, simulaatio nopeutuu
	 */
	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}

	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata
	// JavaFX-säikeeseen:

	/**
	 * Antaa tulokset käyttöliittymälle kun simulaatio on suoritettu
	 */
	public void naytaTulokset() {
		Platform.runLater(() -> ui.tulokset());
	}

	/**
	 * palauttaa asiakkaiden viipymisaikojen keskiarvon
	 * 
	 * @return asiakkaiden viipymisaikojen keskiarvo
	 */
	public double keskiarvo() {
		return ((OmaMoottori) moottori).keskiarvo();
	}

	/**
	 * Palauttaa apteekkisimulaattorin suoritustehon
	 * 
	 * @return suoritusteho
	 */
	@Override
	public double suoritusteho() {
		return ((OmaMoottori) moottori).suoritusteho();
	}

	/**
	 * Palauttaa käyttöliittymässä valitun jakauman indeksin kokonaislukuna
	 * 
	 * @return valitun jakauman indeksi
	 */
	@Override
	public int aktiivisenJakaumanIndex() {
		return ui.getJakaumaIndex();
	}

	/**
	 * Palauttaa taulukon asiakkaista, jonka asiakasdao on lukenut tietokannasta
	 * 
	 * @return taulukko asiakas-olioita
	 */
	public Asiakas[] getAsiakkaat() {
		return asiakasdao.readAsiakkaat();
	}

	// Tässä kaikki asiakkaiden visualisoinnit eri tapahtumissa

	/**
	 * Metodi lisää käyttöliittymän visualisointiin uuden asiakkaan (pallon)
	 */
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().asiakasVuoronumeroon();
			}
		});
	}

	/**
	 * Metodi poistaa käyttöliittymän visualisoinnista asiakkaan (pallon)
	 */
	@Override
	public void visualisoiPoistuvaAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaAsiakasVuoronumerosta();
			}
		});
	}

	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	@Override
	public void visualisoiPalvelupisteenAsiakas() { // poistetaan pallo vuoronumerosta ja luodaan pallo pp:lle
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaAsiakasVuoronumerosta();
				ui.getVisualisointi().asiakasPalvelupisteeseen();
			}
		});
	}

	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	@Override
	public void visualisoiPalvelupisteen2Asiakas() { // poistetaan pallo vuoronumerosta ja luodaan pallo pp2:lle
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaAsiakasVuoronumerosta();
				ui.getVisualisointi().asiakasPalvelupisteeseen2();
			}
		});
	}

	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	@Override
	public void visualisoiKassanAsiakas() { // poistetaan pallo pp:stä ja luodaan pallo kassalle
		Platform.runLater(new Runnable() {
			public void run() {
				double pp = ui.getVisualisointi().getppLkm();
				double pp2 = ui.getVisualisointi().getpp2Lkm();
				if (pp < pp2) {
					ui.getVisualisointi().poistaAsiakasPalvelupisteelta();
				} else {
					ui.getVisualisointi().poistaAsiakasPalvelupisteelta2();
				}
				ui.getVisualisointi().asiakasKassalle();
			}
		});
	}

	/**
	 * Metodi siirtää käyttöliittymän visualisoinnissa asiakkaan (pallon) sijaintia
	 */
	@Override
	public void visualisoiPalveltuAsiakas() { // poistetaan pallo kassalta ja luodaan pallo palveltuihin
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaAsiakasKassalta();
				ui.getVisualisointi().asiakasPaveltuihin();
			}
		});
	}

}
