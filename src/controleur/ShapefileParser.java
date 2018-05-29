package controleur;

/*
 * Copyright (c) delight.im <info@delight.im>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

import org.geotools.data.shapefile.ShapefileDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.Property;
import org.geotools.feature.FeatureIterator;
import org.geotools.data.DataStore;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;

/**
 * Classe utilisée par le parser de fichiers shp. Cette classe est disponible sur https://github.com/delight-im/Java-Shapefile-Parser 
 * en tant que logiciel libre sous les termes de la <a href="https://fr.wikipedia.org/wiki/Licence_publique_g%C3%A9n%C3%A9rale_limit%C3%A9e_GNU">LPGL</a>.
 * <p>
 * Parses ESRI shapefiles and extracts all spatial/geometric data with attributes
 * </p>
 * @author delight-im
 *
 */
public class ShapefileParser {

	public static String parse(final String filePath) {
		final LinkedList<LinkedList<String>> entries = extractRecords(filePath);

		StringBuilder out = new StringBuilder();

		for (LinkedList<String> entry : entries) {
			out.append(Csv.encode(entry));
			out.append("\n");
		}

		return out.toString();
	}

	protected static LinkedList<LinkedList<String>> extractRecords(final String filePath) {
		final LinkedList<LinkedList<String>> out = new LinkedList<LinkedList<String>>();

		try {
			final DataStore dataStore = openDataStore(filePath);
			final String[] typeNames = dataStore.getTypeNames();

			final FeatureIterator<SimpleFeature> iterator = dataStore.getFeatureSource(typeNames[0]).getFeatures().features();

			try {
				SimpleFeature feature = null;
				LinkedList<String> headers = null;
				LinkedList<String> keys;
				LinkedList<String> values;

				while (iterator.hasNext()) {
					feature = iterator.next();

					keys = new LinkedList<String>();
					values = new LinkedList<String>();

					// feature.getDefaultGeometry().toString()

					for (Property p : feature.getProperties()) {
						keys.add(p.getName().toString());
						values.add(p.getValue().toString());
					}

					out.add(values);

					if (headers == null) {
						headers = keys;
					}
					else {
						if (!keys.equals(headers)) {
							throw new RuntimeException("Keys vary in number, order or content");
						}
					}
				}

				if (headers != null) {
					out.addFirst(headers);
				}

				return out;
			}
			finally {
				iterator.close();
				dataStore.dispose();
			}

		}
		catch (Throwable e) {
			e.printStackTrace();
		}

		return out;
	}

	protected static DataStore openDataStore(final String filePath) throws IOException {
		final File file = new File(filePath);
		return new ShapefileDataStore(file.toURI().toURL());

		//final Map<String, Object> params = new HashMap<String, Object>();
		//params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
		//params.put(ShapefileDataStoreFactory.CREATE_SPATIAL_INDEX.key, Boolean.TRUE);
		//return DataStoreFinder.getDataStore(params);
	}

}
