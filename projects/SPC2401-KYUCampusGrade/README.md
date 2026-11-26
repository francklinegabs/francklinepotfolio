# KYUCampusGrade – RMI core (SPC2401 group project)

My part of **KYUCampusGrade**, a distributed grading service in **Java RMI** built by SPC2401 Group 12
at Kirinyaga University. I was team lead and owned work package 1, the RMI core.

## What is in this folder

| Path | What it is |
|---|---|
| `src/ke/ac/kyu/campusgrade/api/` | Remote interfaces `GradeService` and `StudentListener`, serialisable `Lease` and `MarkRecord`, and the checked exception hierarchy |
| `src/ke/ac/kyu/campusgrade/server/GradeServiceImpl.java` | The remote object: no `synchronized` methods, no lock held during callbacks, at-most-once `submitMark` with request IDs |
| `src/ke/ac/kyu/campusgrade/server/GradeServer.java` | Exports the service and binds it in the RMI registry |
| `src/ke/ac/kyu/campusgrade/server/ServerSettings.java`, `Validation.java` | Server settings and input checks (mark 0–100, ID formats) |
| `docs/class-diagram.svg` | Class diagram of the whole system |
| `scripts/`, `.gitignore`, `TEAM.md`, `docs/WORK_PACKAGES.md`, `docs/GITHUB_WORKFLOW.md` | Repository set-up and the team's workflow |

## Design highlights

* **Remote invocation:** `StudentListener extends Remote`, so the student's callback object travels by
  remote reference while marks and IDs travel by value.
* **Deadlock-free publishing:** `publishMarks` takes a snapshot of the subscribers, releases its lock,
  and hands the callbacks to a notifier thread pool, so a student calling `getMark` from inside the
  callback cannot deadlock the server.
* **At-most-once `submitMark`:** a client-generated request ID is remembered and journaled, so a retry
  after a lost reply is ignored, even after a server crash and restart.

This folder holds only my work package. The other packages (marks store, notifier, fault handling, tests)
were written by my teammates, so this code does not compile on its own.

## Fault handling – Ashley Odiwuor (work package 4)

Added by Ashley Odiwuor through pull request `feature/fault-handling`.

| Path | What it is |
|---|---|
| `src/ke/ac/kyu/campusgrade/common/Config.java`, `config/kyu.properties` | Configuration lookup: `-Dkyu.*`, then `KYU_*`, then the properties file. No host or port is hard-coded. |
| `src/ke/ac/kyu/campusgrade/common/EventLog.java` | Timestamped log; FAILURE lines form the failure log |
| `src/ke/ac/kyu/campusgrade/common/KyuClientSocketFactory.java` | RMI socket factory with connect/read timeouts and an optional simulated network delay |
| `src/ke/ac/kyu/campusgrade/client/RetryingCaller.java` | Client timeout, retry with exponential back-off, re-lookup; never retries application errors |
| `src/ke/ac/kyu/campusgrade/server/RequestDeduplicator.java` | Request-ID table that makes a retried `submitMark` apply at most once |
| `src/ke/ac/kyu/campusgrade/server/Journal.java` | Write-ahead journal, so marks and request IDs survive a server crash |
| `src/ke/ac/kyu/campusgrade/client/LecturerClient.java` | Lecturer client: one request ID per submit, reused on every retry |

## Concurrency – Eunice Gikaru (work package 3)

Added by Eunice Gikaru through pull request `feature/concurrency`.

| Path | What it is |
|---|---|
| `src/ke/ac/kyu/campusgrade/server/MarkStore.java` | Thread-safe marks store: read/write lock, (student, unit) key, publish versions |
| `src/ke/ac/kyu/campusgrade/server/WorkerPool.java` | Worker-pool thread architecture (3.3), with the simulated 4 ms CPU + 6 ms disk cost used in 7.4 |
| `tests/ke/ac/kyu/campusgrade/server/MarkStoreTest.java` | Unit tests for the store, including 50 threads x 1,000 puts with no lost update |
| `tests/ke/ac/kyu/campusgrade/server/Section4ReproductionTest.java` | Reproduces the Section 4 deadlock and lost update with the original code, and shows the fix |
| `tests/ke/ac/kyu/campusgrade/server/TestSupport.java` | Shared test helpers: manual clock, recording and dead listeners |
