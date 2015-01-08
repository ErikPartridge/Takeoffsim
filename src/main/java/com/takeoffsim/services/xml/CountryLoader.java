/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
        Document doc = null;
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
        children.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                String regex = element.getAttributeValue("tailformat");
                String alpha3 = element.getAttributeValue("shortname");
                String tapCode = element.getAttributeValue("uid");
                String iso = alpha.get(alpha3);
                if (alpha3.equals("ANT")) {
                    iso = "BQ";
                }
                else if (alpha3.equals("RKS")) {
                    iso = "XK";
                }else if(alpha3.equals("VNT")) {
                    iso = "VU";
                }else if(alpha3.equals("DPK")) {
                    iso = "KP";
                }
                if(!alpha3.equals("---")&& iso!=null) {
                    Country country = new Country(iso, tapCode, regex);
                    Countries.putTapCountry(tapCode, country);
                    Countries.putCountry(alpha.get(alpha3), country);
                }
                else {
                    log.debug("Null for alpha3 of " + element.getAttributeValue("shortname"));
                }
        }});

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

