package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.GradeService;
import ke.ac.kyu.campusgrade.common.Config;
import ke.ac.kyu.campusgrade.common.EventLog;
import ke.ac.kyu.campusgrade.common.KyuClientSocketFactory;

import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

/**
 * Starts GradeService: exports the object and binds it in the registry.
 *
 * <pre>
 * java -cp out/classes ke.ac.kyu.campusgrade.server.GradeServer
 *      [-Dkyu.registry.host=...] [-Dkyu.registry.port=1099] [-Dkyu.registry.create=true]
 * </pre>
 */
public final class GradeServer implements AutoCloseable {

    private final GradeServiceImpl impl;
    private final Registry registry;
    private final EventLog log;
    private final String url;
    private final boolean ownsRegistry;

    private GradeServer(GradeServiceImpl impl, Registry registry, EventLog log, String url, boolean ownsRegistry) {
        this.impl = impl;
        this.registry = registry;
        this.log = log;
        this.url = url;
        this.ownsRegistry = ownsRegistry;
    }

    /** Exports {@code impl} and binds it. Used by main, tests and the benchmark. */
    public static GradeServer start(GradeServiceImpl impl, String registryHost, int registryPort,
                                    boolean createRegistry, int exportPort, EventLog log) throws Exception {
        GradeService stub = (GradeService) UnicastRemoteObject.exportObject(
                impl, exportPort, new KyuClientSocketFactory(), null);
        Registry reg;
        if (createRegistry) {
            reg = LocateRegistry.createRegistry(registryPort);
            log.info("created RMI registry on port " + registryPort);
        } else {
            reg = LocateRegistry.getRegistry(registryHost, registryPort);
        }
        reg.rebind(GradeService.SERVICE_NAME, stub);
        String url = "rmi://" + registryHost + ":" + registryPort + "/" + GradeService.SERVICE_NAME;
        log.info("GradeService exported and bound at " + url);
        return new GradeServer(impl, reg, log, url, createRegistry);
    }

    public GradeServiceImpl impl() {
        return impl;
    }

    public String url() {
        return url;
    }

    @Override
    public void close() {
        try {
            registry.unbind(GradeService.SERVICE_NAME);
        } catch (Exception ignored) {
            // registry may already be gone
        }
        try {
            UnicastRemoteObject.unexportObject(impl, true);
        } catch (NoSuchObjectException ignored) {
            // already unexported
        }
        if (ownsRegistry) {
            try {
                UnicastRemoteObject.unexportObject(registry, true);   // frees the port
            } catch (NoSuchObjectException ignored) {
                // already unexported
            }
        }
        impl.close();
        log.info("GradeService stopped");
    }

    public static void main(String[] args) throws Exception {
        String hostname = Config.get(Config.SERVER_HOSTNAME, null);
        if (hostname != null) {
            // the address written into stubs; must be reachable by clients
            System.setProperty("java.rmi.server.hostname", hostname);
        }
        EventLog log = new EventLog("server");
        ServerSettings settings = ServerSettings.fromConfig();
        log.info("starting GradeService with " + settings);

        GradeServiceImpl impl = new GradeServiceImpl(settings, log);
        GradeServer server = start(impl,
                Config.registryHost(),
                Config.registryPort(),
                Config.getBool(Config.REGISTRY_CREATE, false),
                Config.getInt(Config.SERVER_PORT, 0),
                log);

        CountDownLatch stop = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.close();
            stop.countDown();
        }, "shutdown"));
        log.info("server ready; press Ctrl+C to stop");
        stop.await();
    }
}
