"""Single source of truth for who owns which file and in what order pull requests merge.

Used by docs/WORK_PACKAGES.md (by hand) and by the tooling that prepares each member's
folder. Every file in the repository belongs to exactly one pull request.
"""

MEMBERS = {
    "WP1": ("Franckline Were", "CT101/G/19845/23", "Team lead, RMI core"),
    "WP2": ("Kelvin Lemaitoi", "CT101/G/18802/23", "Notification"),
    "WP3": ("Eunice Gikaru", "CT101/G/18425/23", "Concurrency"),
    "WP4": ("Ashley Odiwuor", "CT101/G/18148/23", "Fault handling"),
    "WP5": ("Jeff Njuguna", "CT101/G/18097/23", "Testing and evaluation"),
}

# Real GitHub accounts (no-reply e-mails link commits to the account without exposing private mail).
GITHUB = {
    "WP1": ("francklinegabs", "151562242+francklinegabs@users.noreply.github.com"),
    "WP2": ("Kyrie-ML", "299208418+Kyrie-ML@users.noreply.github.com"),
    "WP3": ("gikarueunice", "271707942+gikarueunice@users.noreply.github.com"),
    "WP4": ("odiwuor-ashley", "181076415+odiwuor-ashley@users.noreply.github.com"),
    "WP5": ("JustJeff-hub", "180539728+JustJeff-hub@users.noreply.github.com"),
}
REPO_URL = "https://github.com/francklinegabs/SPC2401-Group12"

S = "src/ke/ac/kyu/campusgrade/"
T = "tests/ke/ac/kyu/campusgrade/"

# Merge order. Each entry is one pull request; main compiles after every merge.
PULL_REQUESTS = [
    {
        "wp": "WP1", "n": 1, "branch": "feature/rmi-core", "reviewer": "WP4", "issue": 1, "github_pr": 8,
        "title": "Repository skeleton and RMI remote interfaces",
        "commits": [
            ("Create folder layout, .gitignore, TEAM.md and team docs",
             [".gitignore", "TEAM.md", "docs/WORK_PACKAGES.md", "docs/GITHUB_WORKFLOW.md", "docs/split_plan.py"]),
            ("Add build and test scripts",
             ["scripts/build.ps1", "scripts/build.sh", "scripts/test.ps1", "scripts/test.sh"]),
            ("Add GradeService and StudentListener remote interfaces",
             [S + "api/GradeService.java", S + "api/StudentListener.java"]),
            ("Add serialisable Lease and MarkRecord classes",
             [S + "api/Lease.java", S + "api/MarkRecord.java"]),
            ("Add GradeException hierarchy for application errors",
             [S + "api/GradeException.java", S + "api/InvalidMarkException.java",
              S + "api/UnknownStudentException.java", S + "api/MarkNotPublishedException.java",
              S + "api/UnknownUnitException.java", S + "api/NotSubscribedException.java"]),
            ("Add input validation for marks and IDs", [S + "server/Validation.java"]),
            ("Add class diagram for the RMI core", ["docs/class-diagram.svg", "docs/class-diagram.png"]),
        ],
    },
    {
        "wp": "WP4", "n": 1, "branch": "feature/fault-handling", "reviewer": "WP3", "issue": 2, "github_pr": 9,
        "title": "Configuration, failure log, retry and at-most-once support",
        "commits": [
            ("Read host and port from configuration",
             [S + "common/Config.java", "config/kyu.properties"]),
            ("Add timestamped event and failure log", [S + "common/EventLog.java"]),
            ("Add socket factory with timeouts and simulated delay",
             [S + "common/KyuClientSocketFactory.java"]),
            ("Add client timeout and retry with back-off", [S + "client/RetryingCaller.java"]),
            ("Add request ID table so a retried submitMark applies once",
             [S + "server/RequestDeduplicator.java"]),
            ("Journal marks and request IDs for crash recovery", [S + "server/Journal.java"]),
            ("Add lecturer client with one request ID per submit", [S + "client/LecturerClient.java"]),
        ],
    },
    {
        "wp": "WP3", "n": 1, "branch": "feature/concurrency", "reviewer": "WP4", "issue": 3, "github_pr": 10,
        "title": "Thread-safe marks store and worker pool",
        "commits": [
            ("Add MarkStore with a read-write lock and (student, unit) key", [S + "server/MarkStore.java"]),
            ("Add worker pool for incoming requests", [S + "server/WorkerPool.java"]),
            ("Add unit tests for the marks store", [T + "server/MarkStoreTest.java"]),
        ],
    },
    {
        "wp": "WP2", "n": 1, "branch": "feature/notification", "reviewer": "WP1", "issue": 4, "github_pr": 11,
        "title": "Leased subscriptions, notifier pool and student client",
        "commits": [
            ("Add SubscriptionManager with leases and expiry", [S + "server/SubscriptionManager.java"]),
            ("Send markPublished callbacks from a notifier thread pool", [S + "server/Notifier.java"]),
            ("Add student client that renews its lease and pulls missed marks",
             [S + "client/StudentClient.java"]),
        ],
    },
    {
        "wp": "WP1", "n": 2, "branch": "feature/grade-service", "reviewer": "WP5", "issue": 5, "github_pr": 12,
        "title": "GradeService implementation and server start-up",
        "commits": [
            ("Add server settings read from configuration", [S + "server/ServerSettings.java"]),
            ("Implement GradeService without holding locks during callbacks",
             [S + "server/GradeServiceImpl.java"]),
            ("Export GradeService and bind it in the RMI registry", [S + "server/GradeServer.java"]),
        ],
    },
    {
        "wp": "WP3", "n": 2, "branch": "feature/section4-tests", "reviewer": "WP4", "issue": 6, "github_pr": 13,
        "title": "Reproduce the Section 4 deadlock and lost update",
        "commits": [
            ("Add shared test helpers for the service", [T + "server/TestSupport.java"]),
            ("Add tests that reproduce the Section 4 deadlock and lost update",
             [T + "server/Section4ReproductionTest.java"]),
        ],
    },
    {
        "wp": "WP5", "n": 1, "branch": "feature/testing", "reviewer": "WP3", "issue": 7, "github_pr": None,
        "title": "Tests, experiments, benchmark, README and evidence",
        "commits": [
            ("Add service tests for normal use, invalid mark and unknown student",
             [T + "server/GradeServiceImplTest.java"]),
            ("Add RMI and four-process integration tests",
             [T + "integration/Proc.java", T + "integration/RmiIntegrationTest.java",
              T + "integration/MultiProcessIntegrationTest.java"]),
            ("Add failure experiments for crash, offline client and delay",
             [T + "experiments/FailureExperiments.java"]),
            ("Add benchmark and sizing measurements",
             [T + "experiments/Benchmark.java", T + "experiments/SizingExperiments.java"]),
            ("Add scripts that plot results and render captures",
             ["docs/plot_perf.py", "docs/make_captures.py"]),
            ("Write README build and run steps", ["README.md"]),
            ("Add evidence from the final test run", ["evidence/"]),
            ("Add graphs and the final report",
             ["docs/threads.png", "docs/local_vs_remote.png", "docs/dashboard.png",
              "docs/report/report.html", "docs/report/SPC2401_Group12_Report.pdf"]),
        ],
    },
]
