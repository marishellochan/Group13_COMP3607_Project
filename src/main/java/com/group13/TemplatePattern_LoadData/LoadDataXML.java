package com.group13.TemplatePattern_LoadData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

import com.group13.ExceptionHandling.NoDataException;
import com.group13.Questions.Question;
import com.group13.Singelton.GameData;

public class LoadDataXML extends TemplateLoadData {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    public LoadDataXML(String filePath) {
        super(filePath);
    }

    @Override
    protected void readData() {
        System.out.println("Reading data from XML file...");
        try{
            File file = new File(filePath);
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            document = builder.parse(file);
            document.getDocumentElement().normalize();

            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
            System.out.println("XML data loaded successfully.");
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error reading XML file: " + e.getMessage());
        }
        
    }

    @Override
    protected void parseAndStoreData() throws NoDataException {
        System.out.println("Storing XML data...");

        GameData gameData = GameData.getInstance();
        NodeList nodeList = document.getElementsByTagName("QuestionItem");
        
        if( nodeList.getLength() == 0 ){
            throw new NoDataException("No data to parse and store.");
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String category = element.getElementsByTagName("Category").item(0).getTextContent();
                int value = Integer.parseInt(element.getElementsByTagName("Value").item(0).getTextContent());
                String questionText = element.getElementsByTagName("QuestionText").item(0).getTextContent();
                String optionA = element.getElementsByTagName("OptionA").item(0).getTextContent();
                String optionB = element.getElementsByTagName("OptionB").item(0).getTextContent();
                String optionC = element.getElementsByTagName("OptionC").item(0).getTextContent();
                String optionD = element.getElementsByTagName("OptionD").item(0).getTextContent();
                String answer = element.getElementsByTagName("CorrectAnswer").item(0).getTextContent();

                // Create Question object and add to GameData
                Question question = new Question();
                question.setCategory(category);
                question.setValue(value);
                question.setQuestionText(questionText);
                question.setOptions(optionA, optionB, optionC, optionD);
                question.setAnswer(answer);
                gameData.addQuestion(question);
            }
        }
    }
    
}
