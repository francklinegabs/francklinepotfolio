package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.common.Config;

/** Tunable server values, read from {@link Config}. Tests build their own. */
public final class ServerSettings {
    public int workers = 8;
    public int notifierThreads = 20;
    public long defaultLeaseMillis = 10 * 60_000L;     // 10 minutes
    public long maxLeaseMillis = 60 * 60_000L;         // 1 hour
    public long dedupWindowMillis = 24 * 60 * 60_000L; // 1 day
    public String journalFile = null;
    public long simCpuMillis = 0;
    public long simDiskMillis = 0;
    public int simCores = 0;
    public long simPostApplyMillis = 0;
    public long duplicateWaitMillis = 30_000;

    public static ServerSettings fromConfig() {
        ServerSettings s = new ServerSettings();
        s.workers = Config.getInt(Config.SERVER_WORKERS, s.workers);
        s.notifierThreads = Config.getInt(Config.NOTIFIER_THREADS, s.notifierThreads);
        s.defaultLeaseMillis = Config.getLong(Config.LEASE_DEFAULT_MS, s.defaultLeaseMillis);
        s.maxLeaseMillis = Config.getLong(Config.LEASE_MAX_MS, s.maxLeaseMillis);
        s.dedupWindowMillis = Config.getLong(Config.DEDUP_WINDOW_MS, s.dedupWindowMillis);
        s.journalFile = Config.get(Config.JOURNAL_FILE, "data/marks.journal");
        s.simCpuMillis = Config.getLong(Config.SIM_CPU_MS, 0);
        s.simDiskMillis = Config.getLong(Config.SIM_DISK_MS, 0);
        s.simCores = Config.getInt(Config.SIM_CORES, 0);
        s.simPostApplyMillis = Config.getLong(Config.SIM_POST_APPLY_MS, 0);
        return s;
    }

    @Override
    public String toString() {
        return "workers=" + workers + ", notifierThreads=" + notifierThreads
                + ", lease=" + defaultLeaseMillis + "ms (max " + maxLeaseMillis + "ms)"
                + ", journal=" + journalFile
                + (simCpuMillis + simDiskMillis + simPostApplyMillis > 0
                ? ", sim cpu=" + simCpuMillis + "ms disk=" + simDiskMillis + "ms cores=" + simCores
                  + " postApply=" + simPostApplyMillis + "ms" : "");
    }
}
