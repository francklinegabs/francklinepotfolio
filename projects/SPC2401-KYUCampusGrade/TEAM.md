# Team charter – SPC2401 Group 12

Repository: `SPC2401-Group12` · Lecturer collaborator: `ewairi`

## Members

| # | Name | Registration number | GitHub username | Role | Work package |
|---|------|--------------------|-----------------|------|--------------|
| 1 | Franckline Were | CT101/G/19845/23 | francklinegabs | Team lead, RMI core | WP1 – Remote interfaces, server, registry · Report Section 1 |
| 2 | Kelvin Lemaitoi | CT101/G/18802/23 | Kyrie-ML | Notification engineer | WP2 – Leases, notifier, student client · Report Section 2 |
| 3 | Eunice Gikaru | CT101/G/18425/23 | gikarueunice | Concurrency engineer | WP3 – Marks store, worker pool, Section 4 fixes · Report Sections 3 and 4 |
| 4 | Ashley Odiwuor | CT101/G/18148/23 | odiwuor-ashley | Fault-tolerance engineer | WP4 – Retry, request IDs, journal, config, lecturer client · Report Section 5 |
| 5 | Jeff Njuguna | CT101/G/18097/23 | JustJeff-hub | Test and evaluation lead | WP5 – Tests, experiments, benchmark, README · Report Section 7 |

Full task lists, files and branches for each package: [docs/WORK_PACKAGES.md](docs/WORK_PACKAGES.md).

## Working agreement

* Nobody pushes to `main`. Each package is developed on its own `feature/...` branch and merged by a
  pull request that **another member** reviews and approves.
* Reviews as they happened, written as pull request author → reviewer who approved it:
  #8 Franckline → Ashley · #9 Ashley → Eunice · #10 Eunice → Ashley · #11 Kelvin → Franckline ·
  #12 Franckline → Jeff · #13 Eunice → Ashley · #14 Franckline → Kelvin · #15 Kelvin → Franckline ·
  #16 Eunice → Ashley · #17 Ashley → Eunice · #18 Jeff → Eunice
* Every task is a GitHub issue; commits and pull requests reference it (`Closes #7`).
* Small commits with messages in the imperative ("Add lease expiry to notifier").
* No compiled files (`out/`), downloaded jars (`lib/`), logs or passwords are committed.
* Decisions are recorded in the pull-request discussion.
