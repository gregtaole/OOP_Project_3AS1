/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author dinervoid
 */
public final class LoadImageResource
{
    private static NodeList textureAtlas;
    private static NodeList GUIAtlas ;
    private static BufferedImage textureSheet;
    private static BufferedImage GUISheet;
    
    
    private LoadImageResource()
    {
        textureAtlas =loadImageAtlas(ResourceReference.TEXTURE_ATLAS);
        GUIAtlas = loadImageAtlas(ResourceReference.GUI_ATLAS);
        textureSheet = loadTextureFile(ResourceReference.TEXTURE_SHEET);
        GUISheet = loadTextureFile(ResourceReference.GUI_SHEET);
    }

    public static BufferedImage getTexture(String textureName)
    {
        textureAtlas = loadImageAtlas(ResourceReference.TEXTURE_ATLAS);
        textureSheet = loadTextureFile(ResourceReference.TEXTURE_SHEET);
        Element textureElement;
        for(int i = 0 ; i < textureAtlas.getLength() ; ++i)
        {
            textureElement = (Element)textureAtlas.item(i);
            if(textureElement.getAttribute("name").equals(textureName))
            {
                int x = Integer.parseInt(textureElement.getAttribute("x"));
                int y = Integer.parseInt(textureElement.getAttribute("y"));
                int width = Integer.parseInt(textureElement.getAttribute("width"));
                int height = Integer.parseInt(textureElement.getAttribute("height"));
                
                return textureSheet.getSubimage(x, y, width, height);
            }
        }
        return null;
    }
    
    public static BufferedImage getGUIElement(String elementName)
    {
        GUIAtlas = loadImageAtlas(ResourceReference.GUI_ATLAS);
        GUISheet = loadTextureFile(ResourceReference.GUI_SHEET);
        Element textureElement;
        for(int i = 0 ; i < GUIAtlas.getLength() ; ++i)
        {
            textureElement = (Element)GUIAtlas.item(i);
            if(textureElement.getAttribute("name").equals(elementName))
            {
                int x = Integer.parseInt(textureElement.getAttribute("x"));
                int y = Integer.parseInt(textureElement.getAttribute("y"));
                int width = Integer.parseInt(textureElement.getAttribute("width"));
                int height = Integer.parseInt(textureElement.getAttribute("height"));
                
                return GUISheet.getSubimage(x, y, width, height);
            }
        }
        return null;
    }
    
    private static NodeList loadImageAtlas(String resourceFile)
    {
        InputStream file = LoadImageResource.class.getResourceAsStream(resourceFile);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try
        {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace(System.err);
        }
        
        try
        {
            if(documentBuilder != null)
            {
                document = documentBuilder.parse(new InputSource(file));
            }
        }
        catch(SAXException | IOException e)
        {
            e.printStackTrace(System.err);
        }
        if(document != null)
        {
            return document.getElementsByTagName("SubTexture");
        }
        return null;
    }
    
    private static BufferedImage loadTextureFile(String textureFile)
    {
        BufferedImage file = null;
        
        try
        {
            file = ImageIO.read(LoadImageResource.class.getResourceAsStream(textureFile));
        }
        catch(IOException e)
        {
            e.printStackTrace(System.err);
        }
        return file;
    }
}
