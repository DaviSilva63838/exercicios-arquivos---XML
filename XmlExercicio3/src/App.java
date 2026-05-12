// App.java
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class App {

    public static void main(String[] args) {
        try {

            File xmlFile = new File("catalogo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList livros = doc.getElementsByTagName("livro");

            double somaPrecos = 0;
            double maiorPreco = Double.MIN_VALUE;
            String tituloMaisCaro = "";

            for (int i = 0; i < livros.getLength(); i++) {
                Element livro = (Element) livros.item(i);

                String titulo = livro.getElementsByTagName("titulo").item(0).getTextContent();
                String precoStr = livro.getElementsByTagName("preco").item(0).getTextContent();
                double preco = Double.parseDouble(precoStr);

                somaPrecos += preco;

                if (preco > maiorPreco) {
                    maiorPreco = preco;
                    tituloMaisCaro = titulo;
                }
            }

            System.out.printf("Soma total dos preços: %.2f\n", somaPrecos);
            System.out.println("Livro mais caro: " + tituloMaisCaro + " (R$ " + maiorPreco + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}