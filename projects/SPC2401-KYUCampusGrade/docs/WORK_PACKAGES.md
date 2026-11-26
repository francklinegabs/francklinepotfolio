# Work packages – who owns what (Group 12)

Every file in the repository belongs to exactly one pull request. The pull requests are merged in the
order below, and `main` compiles after every merge (checked by compiling each stage).

Repository: https://github.com/francklinegabs/SPC2401-Group12

| Merge # | Owner (GitHub) | Branch | Pull request | Issue | GitHub PR | Reviewer |
|---|---|---|---|---|---|---|
| 1 | Franckline Were (`francklinegabs`) | `feature/rmi-core` | Repository skeleton and RMI remote interfaces | #1 | #8 | Ashley Odiwuor |
| 2 | Ashley Odiwuor (`odiwuor-ashley`) | `feature/fault-handling` | Configuration, failure log, retry and at-most-once support | #2 | #9 | Eunice Gikaru |
| 3 | Eunice Gikaru (`gikarueunice`) | `feature/concurrency` | Thread-safe marks store and worker pool | #3 | #10 | Ashley Odiwuor |
| 4 | Kelvin Lemaitoi (`Kyrie-ML`) | `feature/notification` | Leased subscriptions, notifier pool and student client | #4 | #11 | Franckline Were |
| 5 | Franckline Were (`francklinegabs`) | `feature/grade-service` | GradeService implementation and server start-up | #5 | #12 | Jeff Njuguna |
| 6 | Eunice Gikaru (`gikarueunice`) | `feature/section4-tests` | Reproduce the Section 4 deadlock and lost update | #6 | #13 | Ashley Odiwuor |
| 7 | Jeff Njuguna (`JustJeff-hub`) | `feature/testing` | Tests, experiments, benchmark, README and evidence | #7 | opened last | Eunice Gikaru |

GitHub numbers issues and pull requests together, so issues are #1–#7 and the pull requests start at #8.
Each member also opened one pull request of oral-check study notes (#14–#18), reviewed the same way.

Then the team lead tags `v1.0`.

## WP1 – Team lead, RMI core · Franckline Were (CT101/G/19845/23)

**Report:** Section 1 (Remote invocation) and the class diagram.

### Pull request #1 – `feature/rmi-core` – Repository skeleton and RMI remote interfaces
Reviewer: Ashley Odiwuor.

| # | Commit message | Files |
|---|---|---|
| 1 | Create folder layout, .gitignore, TEAM.md and team docs | `.gitignore`<br>`TEAM.md`<br>`docs/WORK_PACKAGES.md`<br>`docs/GITHUB_WORKFLOW.md`<br>`docs/split_plan.py` |
| 2 | Add build and test scripts | `scripts/build.ps1`<br>`scripts/build.sh`<br>`scripts/test.ps1`<br>`scripts/test.sh` |
| 3 | Add GradeService and StudentListener remote interfaces | `src/ke/ac/kyu/campusgrade/api/GradeService.java`<br>`src/ke/ac/kyu/campusgrade/api/StudentListener.java` |
| 4 | Add serialisable Lease and MarkRecord classes | `src/ke/ac/kyu/campusgrade/api/Lease.java`<br>`src/ke/ac/kyu/campusgrade/api/MarkRecord.java` |
| 5 | Add GradeException hierarchy for application errors | `src/ke/ac/kyu/campusgrade/api/GradeException.java`<br>`src/ke/ac/kyu/campusgrade/api/InvalidMarkException.java`<br>`src/ke/ac/kyu/campusgrade/api/UnknownStudentException.java`<br>`src/ke/ac/kyu/campusgrade/api/MarkNotPublishedException.java`<br>`src/ke/ac/kyu/campusgrade/api/UnknownUnitException.java`<br>`src/ke/ac/kyu/campusgrade/api/NotSubscribedException.java` |
| 6 | Add input validation for marks and IDs | `src/ke/ac/kyu/campusgrade/server/Validation.java` |
| 7 | Add class diagram for the RMI core | `docs/class-diagram.svg`<br>`docs/class-diagram.png` |

### Pull request #5 – `feature/grade-service` – GradeService implementation and server start-up
Reviewer: Jeff Njuguna.

| # | Commit message | Files |
|---|---|---|
| 1 | Add server settings read from configuration | `src/ke/ac/kyu/campusgrade/server/ServerSettings.java` |
| 2 | Implement GradeService without holding locks during callbacks | `src/ke/ac/kyu/campusgrade/server/GradeServiceImpl.java` |
| 3 | Export GradeService and bind it in the RMI registry | `src/ke/ac/kyu/campusgrade/server/GradeServer.java` |

**Be ready to explain (oral check):**

* What the stub, communication module and dispatcher each do in the 1.1 trace.
* Why StudentListener extends Remote, so it travels by remote reference (1.3).
* Why GradeException is never retried but RemoteException is (1.4).
* Why publishMarks releases its lock before callbacks (4.2 fix in GradeServiceImpl).

## WP2 – Notification · Kelvin Lemaitoi (CT101/G/18802/23)

**Report:** Section 2 (Notification, middleware and the OS).

### Pull request #4 – `feature/notification` – Leased subscriptions, notifier pool and student client
Reviewer: Franckline Were.

| # | Commit message | Files |
|---|---|---|
| 1 | Add SubscriptionManager with leases and expiry | `src/ke/ac/kyu/campusgrade/server/SubscriptionManager.java` |
| 2 | Send markPublished callbacks from a notifier thread pool | `src/ke/ac/kyu/campusgrade/server/Notifier.java` |
| 3 | Add student client that renews its lease and pulls missed marks | `src/ke/ac/kyu/campusgrade/client/StudentClient.java` |

**Be ready to explain (oral check):**

* The 2.2 calculation: 2,000 x 50 ms = 100 s one by one; 2,000/20 x 50 ms = 5 s with 20 threads (measured 106.96 s and 5.64 s).
* What happens when a laptop sleeps for 2 days (lease expires, re-subscribe, pull missed marks).
* Why notification is push but the mark itself is pulled with getMark.
* Experiment B: an offline student is removed and does not stop the others.

## WP3 – Concurrency · Eunice Gikaru (CT101/G/18425/23)

**Report:** Sections 3 and 4 (processes, threads, synchronisation, invocation cost).

### Pull request #3 – `feature/concurrency` – Thread-safe marks store and worker pool
Reviewer: Ashley Odiwuor.

| # | Commit message | Files |
|---|---|---|
| 1 | Add MarkStore with a read-write lock and (student, unit) key | `src/ke/ac/kyu/campusgrade/server/MarkStore.java` |
| 2 | Add worker pool for incoming requests | `src/ke/ac/kyu/campusgrade/server/WorkerPool.java` |
| 3 | Add unit tests for the marks store | `tests/ke/ac/kyu/campusgrade/server/MarkStoreTest.java` |

### Pull request #6 – `feature/section4-tests` – Reproduce the Section 4 deadlock and lost update
Reviewer: Ashley Odiwuor.

| # | Commit message | Files |
|---|---|---|
| 1 | Add shared test helpers for the service | `tests/ke/ac/kyu/campusgrade/server/TestSupport.java` |
| 2 | Add tests that reproduce the Section 4 deadlock and lost update | `tests/ke/ac/kyu/campusgrade/server/Section4ReproductionTest.java` |

**Be ready to explain (oral check):**

* The interleaving of lines (1)-(3) that loses an update, and the fix with a write lock.
* The four deadlock conditions in 4.2 and which one the fix breaks (hold-and-wait during a callback).
* 4.3: 100 req/s, 3 threads -> 250 req/s, 100 req/s; why more threads do not help.
* Why a worker pool suits 2,000 students in 10 minutes (3.3).

## WP4 – Fault handling · Ashley Odiwuor (CT101/G/18148/23)

**Report:** Section 5 (Amoeba case study).

### Pull request #2 – `feature/fault-handling` – Configuration, failure log, retry and at-most-once support
Reviewer: Eunice Gikaru.

| # | Commit message | Files |
|---|---|---|
| 1 | Read host and port from configuration | `src/ke/ac/kyu/campusgrade/common/Config.java`<br>`config/kyu.properties` |
| 2 | Add timestamped event and failure log | `src/ke/ac/kyu/campusgrade/common/EventLog.java` |
| 3 | Add socket factory with timeouts and simulated delay | `src/ke/ac/kyu/campusgrade/common/KyuClientSocketFactory.java` |
| 4 | Add client timeout and retry with back-off | `src/ke/ac/kyu/campusgrade/client/RetryingCaller.java` |
| 5 | Add request ID table so a retried submitMark applies once | `src/ke/ac/kyu/campusgrade/server/RequestDeduplicator.java` |
| 6 | Journal marks and request IDs for crash recovery | `src/ke/ac/kyu/campusgrade/server/Journal.java` |
| 7 | Add lecturer client with one request ID per submit | `src/ke/ac/kyu/campusgrade/client/LecturerClient.java` |

**Be ready to explain (oral check):**

* The three causes of RemoteException in 1.4 and which may have stored the mark.
* How the request ID and the journal keep submitMark at-most-once even after a crash (experiment A2).
* How Config avoids hard-coded host and port.
* Amoeba capabilities: port, object, rights, check, and why a student cannot add WRITE (5.2).

## WP5 – Testing and evaluation · Jeff Njuguna (CT101/G/18097/23)

**Report:** Section 7 (test report) and putting the final PDF together.

### Pull request #7 – `feature/testing` – Tests, experiments, benchmark, README and evidence
Reviewer: Eunice Gikaru.

| # | Commit message | Files |
|---|---|---|
| 1 | Add service tests for normal use, invalid mark and unknown student | `tests/ke/ac/kyu/campusgrade/server/GradeServiceImplTest.java` |
| 2 | Add RMI and four-process integration tests | `tests/ke/ac/kyu/campusgrade/integration/Proc.java`<br>`tests/ke/ac/kyu/campusgrade/integration/RmiIntegrationTest.java`<br>`tests/ke/ac/kyu/campusgrade/integration/MultiProcessIntegrationTest.java` |
| 3 | Add failure experiments for crash, offline client and delay | `tests/ke/ac/kyu/campusgrade/experiments/FailureExperiments.java` |
| 4 | Add benchmark and sizing measurements | `tests/ke/ac/kyu/campusgrade/experiments/Benchmark.java`<br>`tests/ke/ac/kyu/campusgrade/experiments/SizingExperiments.java` |
| 5 | Add scripts that plot results and render captures | `docs/plot_perf.py`<br>`docs/make_captures.py` |
| 6 | Write README build and run steps | `README.md` |
| 7 | Add evidence from the final test run | `evidence/` |
| 8 | Add graphs and the final report | `docs/threads.png`<br>`docs/local_vs_remote.png`<br>`docs/dashboard.png`<br>`docs/report/report.html`<br>`docs/report/SPC2401_Group12_Report.pdf` |

**Be ready to explain (oral check):**

* How the 31 tests are organised and what the 4-process integration test proves.
* What A1, A2, B, C and D showed about invocation semantics (7.3).
* Why 1 worker gave 88 req/s, not 100 (Thread.sleep(6) really takes 6.72 ms on Windows).
* The two limitations of the testing (7.5).

## Shared at the end

* Each member rewrites their own five lines of the contribution statement in the report to match what they did.
* Jeff (WP5) re-prints the PDF; all members sign the contribution page.
* The team lead tags the release: `git tag -a v1.0 -m "SPC2401 submission"` then `git push origin v1.0`.
