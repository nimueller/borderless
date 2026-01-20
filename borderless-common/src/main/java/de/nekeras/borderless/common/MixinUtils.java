package de.nekeras.borderless.common;

public final class MixinUtils {

    private MixinUtils() {
    }

    public static <T> T thisRef(Object thisRef) {
        return (T) thisRef;
    }

}
