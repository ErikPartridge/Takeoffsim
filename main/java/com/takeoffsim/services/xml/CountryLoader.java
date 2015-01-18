/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.services.xml;

import com.neovisionaries.i18n.CountryCode;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Country;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Erik
 */
@CommonsLog
public class CountryLoader {

    public void createCountries() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("tap_countries/countries.xml");
        Document doc;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException | IOException e) {
            log.error(e);
            return;
        }
        Map<String, String> alpha = new HashMap<>();
        Element root = doc.getRootElement();
        for (CountryCode countryCode : CountryCode.values()) {
            alpha.put(countryCode.getAlpha3(), countryCode.getAlpha2());
        }
        Stream<Element> children = root.getChildren().stream();
        children.forEach(new ElementConsumer(alpha));

        try {
            is.close();
        } catch (IOException e) {
            log.debug(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.debug(e);
            }
        }
    }

    private static class ElementConsumer implements Consumer<Element> {
        private final Map<String, String> alpha;

        public ElementConsumer(Map<String, String> alpha) {
            this.alpha = alpha;
        }

        @Override
        public void accept(Element t) {
            String regex = t.getAttributeValue("tailformat");
            String alpha3 = t.getAttributeValue("shortname");
            String tapCode = t.getAttributeValue("uid");
            String iso = alpha.get(alpha3);
            switch (alpha3) {
                case "ANT":
                    iso = "BQ";
                    break;
                case "RKS":
                    iso = "XK";
                    break;
                case "VNT":
                    iso = "VU";
                    break;
                case "DPK":
                    iso = "KP";
                    break;
            }
            if(!alpha3.equals("---")&& iso!=null) {
                Country country = new Country(iso, tapCode, regex);
                Countries.putTapCountry(tapCode, country);
                Countries.putCountry(alpha.get(alpha3), country);
            }
            else {
                log.debug("Null for alpha3 of " + t.getAttributeValue("shortname"));
            }
    }
    }
}

