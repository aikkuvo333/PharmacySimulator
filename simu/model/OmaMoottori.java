package simu.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

/**
 * Luokka <code>OmaMottori</code> antaa mahdollisuuden luoda moottorin
 * simulaattorille, perii <code>Mottori</code>-luokan
 * 
 * @author Elsa Rajala, Olli Ruuskanen
 */
public class OmaMoottori extends Moottori {

	/**
	 * Muuttuja <code>saapumisprosessi</code> antaa
	 * <code>Saapumisprosessi</code>-luokan olion moottorin käyttöön
	 */
	private Saapumisprosessi saapumisprosessi;
	/**
	 * Muuttuja <code>a</code> antaa <code>Asiakas</code>-luokan olion moottorin
	 * käyttöön
	 */
	private Asiakas a = new Asiakas();
	/**
	 * Muuttuja <code>keski</code> ilmaisee palveltujen asiakkaiden viipymisajan
	 * keskiarvon
	 */
	private double keski;
	/**
	 * Muuttuja <code>asiakasdao</code> antaa <code>AsiakasDAO</code>-luokan olion
	 * moottorin käyttöön
	 */
	private AsiakasDAO asiakasdao = new AsiakasDAO();

	/**
	 * Luo OmaMoottori-olion joka saa parametrina
	 * <code>IKontrolleri</code>-rajapinnan olion kontrolleri. Luonnin yhteydessä
	 * järjestelmään luodaan neljä palvelupistettä.
	 * 
	 * @param kontrolleri ilmentymän kontrolleri
	 */
	public OmaMoottori(IKontrolleri kontrolleri) {

		super(kontrolleri);
		a.nollaa();
		palvelupisteet = new Palvelupiste[4];

		// Luodaan omat palvelupisteet
		palvelupisteet[0] = new Palvelupiste(2.0, tapahtumalista, TapahtumanTyyppi.VUORO); // kesto muutettu 1.0 -> 2.0
		palvelupisteet[1] = new Palvelupiste(new Normal(7.5, 3), tapahtumalista, TapahtumanTyyppi.RES1);
		palvelupisteet[2] = new Palvelupiste(new Normal(7.5, 3), tapahtumalista, TapahtumanTyyppi.RES2);
		palvelupisteet[3] = new Palvelupiste(new Normal(4.5, 3), tapahtumalista, TapahtumanTyyppi.KASSA);

		saapumisprosessi = new Saapumisprosessi(luoJakauma(kontrolleri.aktiivisenJakaumanIndex()), tapahtumalista,
				TapahtumanTyyppi.ARR1);

	}

	/**
	 * Muuttuja <code>saapumisprosessi</code> luo ensimmäisen tapahtuman
	 * järjestelmään
	 */
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	/**
	 * Metodi <code>suoritaTapahtuma</code> suorittaa parametrina annetun tapahtuman
	 * 
	 * @param t tapahtuma
	 */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		switch (t.getTyyppi()) {

		case ARR1:
			palvelupisteet[0].lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
			kontrolleri.visualisoiAsiakas();
			break;

		case VUORO:
			a = palvelupisteet[0].otaJonosta();

			if (palvelupisteet[1].getJono() > palvelupisteet[2].getJono()) {
				palvelupisteet[2].lisaaJonoon(a);
				kontrolleri.visualisoiPalvelupisteen2Asiakas();

			} else {
				palvelupisteet[1].lisaaJonoon(a);
				kontrolleri.visualisoiPalvelupisteenAsiakas();
			}
			break;

		case RES1:
			a = palvelupisteet[1].otaJonosta();
			palvelupisteet[3].lisaaJonoon(a);
			kontrolleri.visualisoiKassanAsiakas();

			break;
		case RES2:
			a = palvelupisteet[2].otaJonosta();
			palvelupisteet[3].lisaaJonoon(a);
			kontrolleri.visualisoiKassanAsiakas();

			break;
		case KASSA:
			a = palvelupisteet[3].otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			System.out.println("Kassajonon pituus: " + palvelupisteet[3].getJono() + " asiakasta");
			a.raportti();
			asiakasdao.createAsiakas(a);
			keski = a.getKeskiarvo();
			kontrolleri.visualisoiPalveltuAsiakas();

		}
	}

	// suorituskykysuureet
	/**
	 * palauttaa asiakkaiden viipymisaikojen keskiarvon
	 * 
	 * @return viipymisaikojen keskiarvo
	 */
	public double keskiarvo() {

		double roundOff = Math.round(keski * 100.0) / 100.0;
		return roundOff;
	}

	/**
	 * palauttaa apteekkisimulaattorin suoritustehon
	 * 
	 * @return suoritusteho
	 */
	public double suoritusteho() {

		double roundOff = Math.round(a.suoritusteho() * 100.0) / 100.0;
		return roundOff;

	}

	/**
	 * Syöttää tulokset konsoliin sekä kontrollerille
	 */
	@Override
	protected void tulokset() {

		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());

		// SUORITUSKYKYSUUREET
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo:" + keski);
		System.out.println(a.suoritusteho());

		kontrolleri.naytaTulokset();

	}

	/**
	 * metodi <code>luoJakauma</code> luo jakamia jakaumat.txt tiedoston sisällön
	 * perusteella ja palauttaa valitun jakauman
	 * 
	 * @param index jolla valitaan palautettava jakauma
	 * @return palauttaa eksponenttiaaliseen jakaumaan perustuvan satunnnaisluku
	 *         generaattorin
	 */

	public Negexp luoJakauma(int index) { // luo jakaumat ja palauttaa oikean jakauman indexin perusteella
		Negexp jakauma1 = null;
		Negexp jakauma2 = null;
		Negexp jakauma3 = null;
		ClassLoader classLoader = this.getClass().getClassLoader();
		File configFile = new File(classLoader.getResource("files/jakaumat.txt").getFile()); // hakee jakaumat tiedoston

		try {
			Scanner scanner = new Scanner(configFile);

			String nimi = null;
			double arvo = 0;
			double arvo2 = 0;
			boolean hasArvo;

			while (scanner.hasNextLine()) {

				String rivi = scanner.nextLine(); // luetaan rivi tiedostosta
				@SuppressWarnings("resource")
				Scanner scanner2 = new Scanner(rivi);

				hasArvo = false;

				while (scanner2.hasNext()) {

					String sana = scanner2.next(); // luetaan rivin sanat yksi kerrallaan.

					if (isDouble(sana) && !hasArvo) { // jos sana on luku eikä luku1 ole vielä asetettu.

						arvo = Double.parseDouble(sana);
						hasArvo = true;
					} else if (isDouble(sana)) { // jos sana on luku ja luku1 on asetettu

						arvo2 = Double.parseDouble(sana);
					} else if (!isDouble(sana)) { // jos sana ei ole luku niin se on silloin nimi

						nimi = sana;
					}
				}

				if (nimi.equals("rauhallinen")) // muodostetaan jakaumat nimen perusteella.
				{
					jakauma1 = new Negexp(arvo, (long) arvo2);
				} else if (nimi.equals("normaali")) {

					jakauma2 = new Negexp(arvo, (long) arvo2);
				} else if (nimi.equals("ruuhka")) {

					jakauma3 = new Negexp(arvo, (long) arvo2);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		if (index == 0) { // palautetaan oikea jakauma indexin perusteella
			return jakauma1;
		} else if (index == 1) {
			return jakauma2;
		} else if (index == 2) {
			return jakauma3;
		} else {
			return null;
		}
	}

	/**
	 * Selvittää onko parametrinä annettu String double muodossa.
	 * 
	 * @param input selvitettävä teksti
	 * 
	 * @return palauttaa true jos teksti on double muodossa muuten false.
	 */
	public boolean isDouble(String input) { // selvittää onko String double
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
