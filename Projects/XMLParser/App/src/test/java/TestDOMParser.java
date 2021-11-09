import com.krymlov.xmlparser.object.Inhabitant;
import com.krymlov.xmlparser.parsers.DOMParserXML;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestDOMParser {

    private String filePath = "src\\main\\resources\\db.xml";

    @Test
    public void test1(){
        List<Inhabitant> list = DOMParserXML.parseXML(filePath, "19");
        int expected = 2;
        int actual = list.size();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).toString();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test2(){
        List<Inhabitant> list = DOMParserXML.parseXML(filePath, "");
        int expected = 6;
        int actual = list.size();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).toString();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test3(){
        List<Inhabitant> list = DOMParserXML.parseXML(filePath, "4(четвертий)");
        int expected = 1;
        int actual = list.size();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).toString();
        }
        Assert.assertEquals(expected, actual);
    }

}
