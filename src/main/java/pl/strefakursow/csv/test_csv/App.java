package pl.strefakursow.csv.test_csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * Zadanie rekrutacyjne:
 * 
 * Oczytaj plik happiness.csv w Javie:
 * 
 * https://drive.google.com/file/d/1zfeWa-Wgt2Uq9tKnK...
 * 
 * Do odczytu możesz użyć linka:
 * 
 * https://www.baeldung.com/apache-commons-csv
 * 
 * Używając programu w Javie:
 * 
 * Wypisz wszystkie kraje:
 * 
 * - w których ludzie są szczęśliwsi niż ludzie mieszkający w Polsce
 * 
 * - 5 państw, w których jest największa wolność
 * 
 * -5 państw, w których jest najmniejsza korupcja
 * 
 * Zapisz wyniki programu do pliku results.txt
 * 
 * .
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException {

		String[] HEADERS = { "Country (region)", "Ladder", "SD of Ladder", "Positive affect", "Negative affect",
				"Social support", "Freedom", "Corruption", "Generosity", "Log of GDP per capita",
				"Healthy life expectancy" };

		HashMap<String, Integer> mapBetterThanPoland = new HashMap<String, Integer>();
		HashMap<String, Integer> mapFreedom = new HashMap<String, Integer>();
		HashMap<String, Integer> mapCorruption = new HashMap<String, Integer>();

		try {
			Reader input = new FileReader("src/main/java/pl/strefakursow/csv/test_csv/happiness.csv");

			Iterable<CSVRecord> record = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(input);
			for (CSVRecord rec : record) {
				if (!rec.get("Freedom").equals("")) {
					mapFreedom.put(rec.get("Country (region)"), Integer.parseInt(rec.get("Freedom")));
				}
				if (!rec.get("Corruption").equals("")) {
					mapCorruption.put(rec.get("Country (region)"), Integer.parseInt(rec.get("Corruption")));
				}
				if (!rec.get("Ladder").equals("")) {
					mapBetterThanPoland.put(rec.get("Country (region)"), Integer.parseInt(rec.get("Ladder")));
				}
			}

//			System.out.println(mapCorruption);
//			System.out.println(mapFreedom);
//			System.out.println(mapBetterThanPoland);

		} catch (IOException e) {
			e.printStackTrace();

		}

		getBetterThanPoland(mapBetterThanPoland);
		// System.out.println("------------------------------------");
		getFiveTheBestInFreedomCountries(mapFreedom);
		// System.out.println("------------------------------------");
		getFiveTopCountriesWithBiggestCorruption(mapCorruption);
	}

	public static void getBetterThanPoland(HashMap<String, Integer> better) {
		try {
			File file = new File("src/main/java/pl/strefakursow/csv/test_csv/results.txt");
			PrintWriter writer = new PrintWriter(file);

			Map<String, Integer> map = sortByValues(better, "ASC");
			// System.out.println("More happy countries than Poland:");
			writer.println("More happy countries than Poland:");
			writer.println("----------------------------------");
			for (String country : map.keySet()) {
				if (country.equals("Poland")) {
					break;
				} else {
					writer.println(better.get(country) + "  " + country);
					// writer.println();
					// System.out.println(better.get(country) + " " + country);
				}
			}
			writer.println();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void getFiveTopCountriesWithBiggestCorruption(HashMap<String, Integer> mapBiggestCoruption)
			throws FileNotFoundException {
		try {
			FileWriter file = new FileWriter("src/main/java/pl/strefakursow/csv/test_csv/results.txt", true);
			PrintWriter writer = new PrintWriter(file);

			Map<String, Integer> map = sortByValues(mapBiggestCoruption, "ASC");
			writer.println();
			// System.out.println("Top 5 corupted countries:");
			writer.println("Top 5 countries, where is the lowest corruption:");
			writer.println("-------------------------");
			Integer i = 0;
			for (String countryCorruption : map.keySet()) {
				i += 1;
				if (i == 6) {
					break;
				} else {
					writer.print(mapBiggestCoruption.get(countryCorruption) + "  " + countryCorruption);
					writer.println();
					// writer.flush();
					// System.out.println(mapBiggestCoruption.get(countryCorruption) + " " +
					// countryCorruption);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void getFiveTheBestInFreedomCountries(HashMap<String, Integer> mapBestInFreedom) {
		try {
			FileWriter file = new FileWriter("src/main/java/pl/strefakursow/csv/test_csv/results.txt", true);
			PrintWriter writer = new PrintWriter(file);

			Map<String, Integer> map = sortByValues(mapBestInFreedom, "ASC");
			// System.out.println("Top 5 freedom countris:");
			writer.println("Top 5 freedom countries:");
			writer.println("-------------------------");
			Integer i = 0;
			for (String countryFreedom : map.keySet()) {
				i += 1;
				if (i == 6) {
					break;
				} else {
					writer.println(mapBestInFreedom.get(countryFreedom) + "  " + countryFreedom);
					// System.out.println(mapBestInFreedom.get(countryFreedom) + " " +
					// countryFreedom);
				}

			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static HashMap sortByValues(HashMap<String, Integer> map, String Direction) {
		List list = new LinkedList(map.entrySet());

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (Direction.equals("ASC")) {
					return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
				} else {// if (Direction.equals("DESC")) {
					return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());

				}
			}
		});

		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

}
