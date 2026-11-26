# Team charter – SPC2401 Group 12

Repository: `SPC2401-Group12` · Lecturer collaborator: `ewairi`

## Members

| # | Name | Registration number | GitHub username | Role | Work package |
|---|------|--------------------|-----------------|------|--------------|
| 1 | Franckline Were | CT101/G/19845/23 | [github username] | Team lead, RMI core | WP1 – Remote interfaces, server, registry · Report Section 1 |
| 2 | Kelvin Lemaitoi | CT101/G/18802/23 | [github username] | Notification engineer | WP2 – Leases, notifier, student client · Report Section 2 |
| 3 | Eunice Gikaru | CT101/G/18425/23 | [github username] | Concurrency engineer | WP3 – Marks store, worker pool, Section 4 fixes · Report Sections 3 and 4 |
| 4 | Ashley Odiwuor | CT101/G/18148/23 | [github username] | Fault-tolerance engineer | WP4 – Retry, request IDs, journal, config, lecturer client · Report Section 5 |
| 5 | Jeff Njuguna | CT101/G/18097/23 | [github username] | Test and evaluation lead | WP5 – Tests, experiments, benchmark, README · Report Section 7 |

Full task lists, files and branches for each package: [docs/WORK_PACKAGES.md](docs/WORK_PACKAGES.md).

## Working agreement

* Nobody pushes to `main`. Each package is developed on its own `feature/...` branch and merged by a
  pull request that **another member** reviews and approves.
* Review pairs: Franckline ↔ Kelvin (WP1 ↔ WP2), Eunice ↔ Ashley (WP3 ↔ WP4);
  Jeff reviews Franckline's second pull request and is reviewed by Eunice.
* Every task is a GitHub issue; commits and pull requests reference it (`Closes #7`).
* Small commits with messages in the imperative ("Add lease expiry to notifier").
* No compiled files (`out/`), downloaded jars (`lib/`), logs or passwords are committed.
* Decisions are recorded in the pull-request discussion.
