import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class App {
    public static void main(String[] args) throws Exception {
        // Fabrica o documento
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Inicialização da construção do doc
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Manipulação (parse) do doc
        Document doc = builder.parse(new File("alunos.xml"));

        // Pega todos os elementos <aluno>
        NodeList alunos = doc.getElementsByTagName("aluno");

        // For enxuto para exibir nome e nota
        for (int i = 0; i < alunos.getLength(); i++) {
            Element aluno = (Element) alunos.item(i);
            System.out.println("Aluno: " 
                + aluno.getElementsByTagName("nome").item(0).getTextContent() 
                + " - Nota: " 
                + aluno.getElementsByTagName("nota").item(0).getTextContent());
            System.out.println("----------------");
        }
    }
}