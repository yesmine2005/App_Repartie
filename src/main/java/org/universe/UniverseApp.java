package org.universe;

import org.w3c.dom.Document;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.SAXException;
import java.io.File;

public class UniverseApp {

    public static void main(String[] args) {
        String xmlPath = "src/main/resources/universe.xml";
        String xsdPath = "src/main/resources/universe.xsd";

        //  Validation XML/XSD
        if (validateXMLSchema(xsdPath, xmlPath)) {
            System.out.println(" XML valide selon le schema XSD !");
        } else {
            System.out.println(" XML invalide !");
        }

        //  Requetes XPath
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlPath));
            XPath xPath = XPathFactory.newInstance().newXPath();

            // 1️⃣ Liste des galaxies
            System.out.println("\n🌌🎇 Liste des galaxies :");
            var galaxies = (org.w3c.dom.NodeList) xPath.compile("//galaxy/nom/text()")
                    .evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < galaxies.getLength(); i++) {
                System.out.println("- " + galaxies.item(i).getNodeValue());
            }

            // 2️⃣ Liste des planetes habitees
            System.out.println("\n🪐 Planetes habitees :");
            var inhabited = (org.w3c.dom.NodeList) xPath.compile("//planete[habitants]/nom/text()")
                    .evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < inhabited.getLength(); i++) {
                System.out.println("- " + inhabited.item(i).getNodeValue());
            }

            // 3️⃣ Liste des createurs
            System.out.println("\n👽 Createurs :");
            var creators = (org.w3c.dom.NodeList) xPath.compile("//createur/nom/text()")
                    .evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < creators.getLength(); i++) {
                System.out.println("- " + creators.item(i).getNodeValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Validator validator = factory.newSchema(new File(xsdPath)).newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (SAXException | java.io.IOException e) {
            System.out.println("Erreur de validation : " + e.getMessage());
            return false;
        }
    }
}
