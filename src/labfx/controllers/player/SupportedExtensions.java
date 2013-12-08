package labfx.controllers.player;

import labfx.controllers.extensions.ExtensionsFilter;

public class SupportedExtensions extends ExtensionsFilter {
    private static final String[] EXTENSIONS = new String[] {
        "flv", "fxm", "aif", "aiff", "wav", "mp3", "mp4"
    };

    @Override
    protected String[] getExtensions() {
        return EXTENSIONS;
    }
}
