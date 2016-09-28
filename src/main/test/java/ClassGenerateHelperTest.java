import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by vadim on 28.09.16.
 */
public class ClassGenerateHelperTest {
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    @Test
    public void getClassNameModification_isCorrect() throws Exception {
        testModification("item", "ItemDto");
        testModification("-it.em_", "ItemDto");
    }

    private void testModification(String name, String targetName) {
        Assert.assertEquals(targetName, classGenerateHelper.getClassName(name));
    }
}
