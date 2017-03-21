package ua.softgroup.matrix.desktop.spykit.listeners.activewindowistener;

import com.sun.jna.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vadim Boitsov <sg.vadimbojcov@gmail.com>
 */
public class ActiveWindowListenerFactory {
    private static final Logger logger = LoggerFactory.getLogger(ActiveWindowListenerFactory.class);

    /**
     * Returns window listener for detected platform or null if platform is unknown.
     * @return window listener
     */
    public static ActiveWindowListener getListener() {
        if (Platform.isWindows()) {
            logger.debug("Platform is Windows");
            return new WindowsActiveWindowListener();
        }
        if (Platform.isLinux()) {
            logger.debug("Platform is Linux");
            return new LinuxActiveWindowListener();
        }
        if (Platform.isMac()) {
            logger.debug("Platform is Mac");
            return new MacOsActiveWindowListener();
        }
        logger.debug("Platform is not detected");
        return null;
    }
}
