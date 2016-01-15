/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
 * Responsible for loading all the textures and GUI elements.
 * <p>
 *     Loads all the required textures and GUI elements from the texture atlases.
 * </p>
 */
public final class LoadImageResource
{
    /**
     * List of nodes containing all the information needed to retrieve the textures from textureSheet.
     */
    private static NodeList textureAtlas;

    /**
     * List of nodes containing all the information needed to retrieve the textures from GUISheet.
     */
    private static NodeList GUIAtlas ;

    /**
     * Image containing all the textures.
     */
    private static BufferedImage textureSheet;

    /**
     * Image containing all the GUI elements.
     */
    private static BufferedImage GUISheet;

    /**
     * Class constructor.
     */
    private LoadImageResource()
    {
        textureAtlas =loadImageAtlas(ResourceReference.TEXTURE_ATLAS);
        GUIAtlas = loadImageAtlas(ResourceReference.GUI_ATLAS);
        textureSheet = loadTextureFile(ResourceReference.TEXTURE_SHEET);
        GUISheet = loadTextureFile(ResourceReference.GUI_SHEET);
    }

    /**
     * Returns the texture associated with textureName.
     * <p>
     *     Searches the textureAtlas node list for a node whose "name" attribute is the same as textureName.
     * </p>
     * @param textureName Texture name as it is written in the texture atlas.
     * @return BufferedImage corresponding to textureName or null if none has been found.
     */
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

    /**
     * Returns the texture associated with elementName.
     * <p>
     *     Searches the GUIAtlas node list for a node whose "name" attribute is the same as elementName.
     * </p>
     * @param elementName Texture name as it is written in the texture atlas.
     * @return BufferedImage corresponding to elementName or null if none has been found.
     */
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

    /**
     * Parses an XML texture atlas file to extract information about the textures from it.
     * @param resourceFile Path to XML file containing the texture atlas.
     * @return NodeList containing image information or null if an error has occurred while opening resourceFile or parsing it.
     */
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

    /**
     * Loads the texture sheet in memory.
     * @param textureFile Path to the texture file.
     * @return BufferedImage containing the entire texture sheet.
     */
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
