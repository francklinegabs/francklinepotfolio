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
