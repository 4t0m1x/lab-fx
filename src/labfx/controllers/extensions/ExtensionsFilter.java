package labfx.controllers.extensions;

import java.io.File;
import java.io.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 08.12.13
 * Time: 19:05
 */
public abstract class ExtensionsFilter implements FileFilter {
    protected abstract String[] getExtensions();

    @Override
    public boolean accept(File pathname) {
        for (String extension : getExtensions()) {
            if (pathname.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
