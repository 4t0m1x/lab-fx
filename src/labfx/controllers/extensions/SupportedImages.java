package labfx.controllers.extensions;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 08.12.13
 * Time: 19:05
 */
public class SupportedImages extends ExtensionsFilter {
    private static final String[] EXTENSIONS = new String[] {
        "jpg", "png", "gif"
    };

    @Override
    protected String[] getExtensions() {
        return EXTENSIONS;
    }
}
