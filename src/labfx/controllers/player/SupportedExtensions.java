package labfx.controllers.player;

import java.io.File;
import java.io.FileFilter;

public class SupportedExtensions implements FileFilter {
    private static final String[] EXTENSIONS = new String[] { "flv", "fxm", "aif", "aiff", "wav", "mp3", "mp4" };
    @Override
    public boolean accept(File path) {
        for (String extension : EXTENSIONS) {
            if (path.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
