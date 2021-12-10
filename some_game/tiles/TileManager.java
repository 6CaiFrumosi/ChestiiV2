package com.some_game.tiles;

import com.some_game.Graphics.Sprite;
import com.some_game.util.Vector2f;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class TileManager {
    public static ArrayList<TileMap> tm;
    private TileManager()
    {
        tm=new ArrayList<TileMap>();
    }

    public TileManager(String path)
    {
        tm=new ArrayList<TileMap>();
        addTileMap(path, 32,32);
    }
    private void addTileMap(String path, int blockWidth, int blockHeight) {
        String imagePath;
        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;

        String[] data = new String[10];
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("map");
            Node node = list.item(0);
            Element eElement = (Element) node;
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount = 198;//Integer.parseInt(eElement.getAttribute("height"));
            list = doc.getElementsByTagName("layer");
            layers = list.getLength();
            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                imagePath=eElement.getAttribute("name");
                sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                tileColumns= sprite.getwSprite();
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i<=0)
                {
                    tm.add(new TileMapNorm(data[i], sprite, width, height, tileWidth, tileHeight, tileColumns));

                }
                else
                {
                    tm.add(new TileMapObj(data[i], sprite, width, height, tileWidth, tileHeight, tileColumns));
                }

            //tm.add(new TileMapObj(data[i], sprite, width, height, tileWidth, tileHeight, tileColumns));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g)
    {
        for(int i=0; i<tm.size(); i++)
        {
            tm.get(i).render(g);
        }
    }

}
