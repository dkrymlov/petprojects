import com.krymlov.xmlparser.transformer.XSLTransformer;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestXSLTransformer {

    @Test
    public void test1(){
        String pathToXSL = "src\\main\\resources\\db.xsl";
        String pathToXML = "src\\main\\resources\\db.xml";
        String pathToHTML = "src\\main\\resources\\output.html";
        XSLTransformer.transform(pathToXSL, pathToXML, pathToHTML);
        File actual = new File(pathToHTML);
        File expected = new File("E:\\Users\\Danil\\Desktop\\output.html");
        try {
            assertEquals("The files differ!",
                    FileUtils.readFileToString(expected, "utf-8"),
                    FileUtils.readFileToString(actual, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
