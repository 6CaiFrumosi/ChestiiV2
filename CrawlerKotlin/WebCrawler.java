
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
class Crawler {
    private String url;

    Crawler(String url) {
        this.url = new String(url);
    }

    public Document getResource() throws IOException {
        Document HttpResponse = Jsoup.connect(url).get();
        return HttpResponse;
    }

    public void processContent(String contentType) {
        Document aux;
        Map<Integer, String> my_map;
        try {
            aux = this.getResource();
            if (contentType.equals("XML")) {
                XmlParser p = new XmlParser();
                if (aux.toString().contains("https://")) {
                    my_map = p.parse(aux.toString());

                if (!my_map.isEmpty()) {
                        for (Map.Entry<Integer, String> entry : my_map.entrySet()) {

                            System.out.println("Inceperea altui fir de executie");
                            System.out.println("Noua radacina este: " + entry.getValue());
                            if (!entry.getValue().equals(this.url)) {
                                Crawler c = new Crawler(entry.getValue());
                                c.processContent("XML");
                            }
                            else
                            {
                                System.out.println("Terminare fir de executie");
                            }
                        }
                    } else {
                        System.out.println("Terminare fir de executie");
                    }
                }
            }
            else
            {
                System.out.println("Terminare fir de executie");
            }


        } catch (IOException e) {
            System.out.println("Eroare detectata");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Crawler my_crw = new Crawler("https://ro.wiktionary.org/wiki/arbore");
        my_crw.processContent("XML");

    }
}

interface Parser
{
    public Map<Integer, String> parse(String text);
}

class JsonParser implements Parser
{
    public Map<Integer, String> parse(String text)
    {
        return new HashMap<Integer, String>();
    }
}

class XmlParser implements Parser
{
    public Map<Integer, String> parse(String text)
    {
        int i=0;
        Map <Integer, String> my_map= new HashMap<Integer, String>();
        Pattern p1 = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
        Pattern p2 = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        Matcher m1 = p1.matcher(text);
        Matcher m2;
        while(m1.find()) {
            m2 = p2.matcher(m1.group(1));
            if(m2.find()) {
                String my_string = m2.group(1);
                if (my_string.contains("https")) {
                    if (my_string.contains("(")) {
                        my_string = my_string.substring(0, my_string.indexOf("(") - 1);
                    }
                    my_string = my_string.substring(1, my_string.length() - 1);
                    my_map.put(i, my_string);
                    i++;
                }
            }
            else
            {
                System.out.println("Niciun link gasit");
            }
            }
        return my_map;
    }
}


class YamlParser implements Parser
{
    public Map<Integer, String> parse(String text)
    {
        return new HashMap<Integer, String>();
    }
}



