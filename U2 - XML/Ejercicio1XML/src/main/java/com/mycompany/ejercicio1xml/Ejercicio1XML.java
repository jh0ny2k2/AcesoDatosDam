/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio1xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jhony
 */
public class Ejercicio1XML {

    private static final String INDENT_CHAR = " ";
    
    public static void muestraNodo ( Node nodo, int level, PrintStream ps )
    {
        for (int i = 0; i < level; i++) {
            System.out.println(INDENT_CHAR);
        }
        
        System.out.print("Nombre del Nodo: " + nodo.getNodeName());
        System.out.print(" | Tipo de Nodo: " + nodo.getNodeType());
        
        if (nodo.getNodeValue() != null) {
            System.out.print(" | Valor del Nodo: " + nodo.getNodeValue());
        } 
        
        System.out.println(" ");
        
        NamedNodeMap atributos = nodo.getAttributes();
        if (atributos != null) {
            for (int i = 0; i < atributos.getLength(); i++) {
                Node atributo = atributos.item(i);
                for (int j = 0; j < level; j++) {
                    System.out.print(INDENT_CHAR);
                }
                
                System.out.println("Atributo: " + atributo.getNodeName() + " = " + atributo.getNodeValue() + " = " + atributo.getNodeType());
            }
        }
        
        NodeList hijos = nodo.getChildNodes();
        if (hijos != null){
            for (int i = 0; i < hijos.getLength(); i++) {
                muestraNodo(hijos.item(i), level + 1, ps);
            }
        } 
    }
    
    public static void main(String[] args) {
        
        String nomFich = args[0];
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse( new File(nomFich) );
            muestraNodo( doc, 0, System.out );
        }
        catch( FileNotFoundException | ParserConfigurationException | SAXException ex )
        {
            System.out.println(ex.getMessage() );
        }
        catch( Exception ex )
        {
            
            ex.printStackTrace();
        }
    }
}
