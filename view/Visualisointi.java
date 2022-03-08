package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Luokka antaa käyttöliittymälle visualiaalisen toteutuksen asiakkaiden
 * liikkumisesta palvelupisteissä.
 * 
 * @author Aivan Vo, Elsa Rajala
 *
 */
public class Visualisointi extends Canvas implements IVisualisointi {

	/**
	 * GraphicsContext-oliolla voidaan piirtää Canvas -oliolle
	 */
	private GraphicsContext gc;

	/**
	 * Ensimmäisen palvelupisteen x-koordinaatti
	 */
	// Ensimmäisen palvelupisteen (vuoronumeroautomaatti) pallojen x ja
	// y-koordinaatit
	double vuoronro_x = this.getWidth() / 6 - 3;
	/**
	 * Ensimmäisen palvelupisteen y-koordinaatti
	 */
	double vuoronro_y = this.getHeight() / 2 - 5; // ..ja y sille mistä kohin pallot alkaa näkymään

	/**
	 * Toisen palvelupisteen x-koordinaatti
	 */
	// Toisen palvelupisteen (palvelupiste1) pallojen x ja y-koordinaatit
	double pp1_x = this.getWidth() / 3 + 7;
	/**
	 * Toisen palvelupisteen y-koordinaatti
	 */
	double pp1_y = this.getHeight() / 5;

	/**
	 * Kolmannen palvelupisteen x-koordinaatti
	 */
	// Kolmannen palvelupisteen (palvelupiste2) pallojen x ja y-koordinaatit
	double pp2_x = this.getWidth() / 3 + 7;
	/**
	 * Kolmannen palvelupisteen y-koordinaatti
	 */
	double pp2_y = (this.getHeight() / 6) * 4.7;

	/**
	 * Neljännen palvelupisteen x-koordinaatti
	 */
	// Neljännen palvelupisteen (kassa) pallojen x ja y-koordinaatit
	double kassa_x = this.getWidth() / 6 * 3.7;
	/**
	 * Neljännen palvelupisteen y-koordinaatti
	 */
	double kassa_y = this.getHeight() / 2 - 5;

	/**
	 * Sivupalkin (palvellut asiakkaat) pallojen x-koordinaatti
	 */
	// Sivupalkin (palvellut asiakkaat) pallot
	double palvellut_x = this.getWidth() / 4 * 3 + 3;
	/**
	 * Sivupalkin (palvellut asiakkaat) pallojen y-koordinaatti
	 */
	double palvellut_y = this.getHeight() / 5;

	/**
	 * Luodaan <code>Visualisointi</code> olio, jonka koko määritetään luonnin
	 * yhteydessä.
	 * 
	 * @param width,heighth arvot määrittävät luotavan Visualisointi -olion koon
	 *                      leveyden ja korkeuden.
	 */
	public Visualisointi(int width, int heighth) {
		super(width, heighth);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}

	/**
	 * Tyhjennetään näyttö (toteutetaan aina uuden Visualisointi -olion yhteydessä)
	 * ja asetetaan alkuarvot
	 */
	public void tyhjennaNaytto() {
		gc.setFill(Color.TRANSPARENT); // tausta muokattu läpinäkyväksi, jotta saadaan oma kuva taustalle
		gc.fillRect(0, 0, this.getWidth() / 4, this.getHeight());

		// muuttujat alkuarvoon
		vuoronro_x = this.getWidth() / 6 - 3;
		vuoronro_y = this.getHeight() / 2 - 5;
		pp1_x = this.getWidth() / 3 + 7;
		pp1_y = this.getHeight() / 5;
		pp2_x = this.getWidth() / 3 + 7;
		pp2_y = (this.getHeight() / 6) * 4.7;
		kassa_x = this.getWidth() / 6 * 3.7;
		kassa_y = this.getHeight() / 2 - 5;
		palvellut_x = this.getWidth() / 4 * 3 + 3;
		palvellut_y = this.getHeight() / 5;

		for (int i = 0; i < 10000; i++) {
			gc.setFill(Color.web("#326986"));
			gc.fillOval(vuoronro_x, vuoronro_y, 10, 10);
			vuoronro_x = (vuoronro_x - 15) % this.getWidth();
			i += 15;
		}
		for (int i = 0; i < 10000; i++) {
			gc.setFill(Color.web("#326986"));
			gc.fillOval(pp1_x, pp1_y, 10, 10);
			pp1_x = (pp1_x - 15) % (this.getWidth());
			i += 15;
		}
		for (int i = 0; i < 10000; i++) {
			gc.setFill(Color.web("#326986"));
			gc.fillOval(pp2_x, pp2_y, 10, 10);
			pp2_x = (pp2_x - 15) % (this.getWidth());
			i += 15;
		}
		for (int i = 0; i < 10000; i++) {
			gc.setFill(Color.web("#326986"));
			gc.fillOval(kassa_x, kassa_y, 10, 10);
			kassa_x = (kassa_x - 15) % this.getWidth();
			i += 15;
		}
		for (int i = 0; i < 10000; i++) {
			gc.setFill(Color.web("#4ca880"));
			gc.fillOval(palvellut_x, palvellut_y, 10, 10);
			palvellut_x = (palvellut_x + 15) % this.getWidth();
			i += 15;
			if (palvellut_x == 3) {
				palvellut_x = this.getWidth() / 4 * 3 + 3;
				palvellut_y += 15;
			}
		}
		// muuttujat takaisin alkuarvoihinsa
		vuoronro_x = this.getWidth() / 6 - 3;
		vuoronro_y = this.getHeight() / 2 - 5;
		pp1_x = this.getWidth() / 3 + 7;
		pp1_y = this.getHeight() / 5;
		pp2_x = this.getWidth() / 3 + 7;
		pp2_y = (this.getHeight() / 6) * 4.7;
		kassa_x = this.getWidth() / 6 * 3.7;
		kassa_y = this.getHeight() / 2 - 5;
		palvellut_x = this.getWidth() / 4 * 3 + 3;
		palvellut_y = this.getHeight() / 5;
	}

	/**
	 * Uuden asiakkaan=pallon luonti ensimmäiselle palvelupisteelle
	 * (Vuoronumeroautomaatti)
	 */
	public void asiakasVuoronumeroon() {
		gc.setFill(Color.RED);
		gc.fillOval(vuoronro_x, vuoronro_y, 10, 10);

		vuoronro_x = (vuoronro_x - 15) % this.getWidth();
		if (vuoronro_x == 0)
			vuoronro_y += 10;
	}

	/**
	 * Pallon poisto ensimmäiseltä palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasVuoronumerosta() {
		gc.setFill(Color.web("#326986")); // "poistetaan pallo" eli luodaan oval jonka väri vastaa taustan väriä
		gc.fillOval(vuoronro_x + 15, vuoronro_y, 10, 10);
		vuoronro_x = (vuoronro_x + 15) % this.getWidth();
	}

	/**
	 * Uuden asiakkaan=pallon luonti toiselle palvelupisteelle (Palvelupiste nro 1)
	 */
	public void asiakasPalvelupisteeseen() {
		gc.setFill(Color.WHITE);
		gc.fillOval(pp1_x, pp1_y, 10, 10);

		pp1_x = (pp1_x - 15) % (this.getWidth());
		if (pp1_x == 0)
			pp1_y += 10;
	}

	/**
	 * Pallon poisto toiselta palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasPalvelupisteelta() {
		gc.setFill(Color.web("#326986"));
		gc.fillOval(pp1_x + 15, pp1_y, 10, 10);
		pp1_x = (pp1_x + 15) % this.getWidth();
	}

	/**
	 * Uuden asiakkaan=pallon luonti kolmannelle palvelupisteelle (Palvelupiste nro
	 * 2)
	 */
	public void asiakasPalvelupisteeseen2() {
		gc.setFill(Color.WHITE);
		gc.fillOval(pp2_x, pp2_y, 10, 10);

		pp2_x = (pp2_x - 15) % (this.getWidth());
		if (pp2_x == 0)
			pp2_y += 10;
	}

	/**
	 * Pallon poisto kolmannelta palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasPalvelupisteelta2() {
		gc.setFill(Color.web("#326986"));
		gc.fillOval(pp2_x + 15, pp2_y, 10, 10);
		pp2_x = (pp2_x + 15) % this.getWidth();
	}

	/**
	 * Uuden asiakkaan=pallon luonti neljännelle palvelupisteelle (Kassa)
	 */
	public void asiakasKassalle() {
		gc.setFill(Color.PINK);
		gc.fillOval(kassa_x, kassa_y, 10, 10);

		kassa_x = (kassa_x - 15) % this.getWidth();
		if (kassa_x == 0)
			kassa_y += 10;
	}

	/**
	 * Pallon poisto neljänneltä palvelupisteeltä, muuttamalla pallon väri taustaa
	 * vastaavaksi
	 */
	public void poistaAsiakasKassalta() {
		gc.setFill(Color.web("#326986"));
		gc.fillOval(kassa_x + 15, kassa_y, 10, 10);
		kassa_x = (kassa_x + 15) % this.getWidth();
	}

	/**
	 * Pallon luonti sivupalkkiin (Palvellut asiakkaat)
	 */
	public void asiakasPaveltuihin() {
		gc.setFill(Color.PURPLE);
		gc.fillOval(palvellut_x, palvellut_y, 10, 10);

		palvellut_x = (palvellut_x + 15) % this.getWidth();
		if (palvellut_x == 3) {
			palvellut_x = this.getWidth() / 4 * 3 + 3;
			palvellut_y += 15;
		}
	}

	/**
	 * Kertoo viimeisimmän Palvelupiste nro 1:llä olevan pallon x-koordinaatin
	 * 
	 * @return palauttaa Pavelupiste nro 1:llä olevan viimeisemmän pallon
	 *         x-koordinaatin arvon
	 */
	// Palauttaa viimeisimmän Palvelupiste nro 1:llä olevan pallon x-koordinaatin
	public double getppLkm() {
		return pp1_x;
	}

	/**
	 * Kertoo viimeisimmän Palvelupiste nro 2:llä olevan pallon x-koordinaatin
	 * 
	 * @return palauttaa Pavelupiste nro 2:llä olevan viimeisemmän pallon
	 *         x-koordinaatin arvon
	 */
	// Palauttaa viimeisimmän Palvelupiste nro 2:llä olevan pallon x-koordinaatin
	public double getpp2Lkm() {
		return pp2_x;
	}
}
