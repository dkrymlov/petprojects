import com.krymlov.xmlparser.object.Inhabitant;
import com.krymlov.xmlparser.parsers.DOMParserXML;
import com.krymlov.xmlparser.transformer.XMLFromCode;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class TestXMLFromCode {

    @Test
    public void test1(){
        String tempdbPath = "src\\main\\resources\\tempdb.xml";
        String dbPath = "src\\main\\resources\\db.xml";

        List<Inhabitant> list = DOMParserXML.parseXML(dbPath, "");
        XMLFromCode.create(tempdbPath, list);

        List<Inhabitant> db = DOMParserXML.parseXML(dbPath, "");
        List<Inhabitant> tempdb = DOMParserXML.parseXML(tempdbPath, "");

        String actual;
        String expected;
        for (int i = 0; i < list.size(); i++) {
            actual = tempdb.get(i).toString();
            expected = db.get(i).toString();
            Assert.assertEquals("Expected == actual" ,expected, actual);
        }
    }

}
