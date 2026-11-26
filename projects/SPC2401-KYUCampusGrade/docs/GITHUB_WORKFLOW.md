# How the team collaborates on GitHub

This is the step-by-step workflow the assignment's GitHub rules ask for. Commands are for Git Bash
or PowerShell. Replace `OWNER` with the GitHub username of the team lead (Franckline Were), who owns the repository.

## 1. Team lead creates the repository (once)

1. GitHub → **New repository** → name `SPC2401-Group12` → Private → no template.
2. **Settings → Collaborators → Add people**: add `ewairi` (the lecturer) and the four other members.
3. **Settings → Branches → Add branch protection rule** for `main`:
   * Require a pull request before merging
   * Require approvals: **1**
   * Do not allow bypassing the above settings
   This makes "nobody pushes straight to main" a rule GitHub enforces.
4. Push the skeleton (folders, `.gitignore`, `TEAM.md`) as the first commit on `main`.
5. **Issues → New issue** for every task in [WORK_PACKAGES.md](WORK_PACKAGES.md). Assign each issue
   to its owner and add a label (`rmi`, `notification`, `concurrency`, `fault`, `testing`, `report`).

## 2. Each member sets up (once)

```powershell
git clone https://github.com/OWNER/SPC2401-Group12.git
cd SPC2401-Group12
git config user.name  "Your Real Name"
git config user.email "the-email-on-your-github-account@example.com"
```

The e-mail must be one that is verified on **your own** GitHub account, otherwise your commits
will not be counted as yours in the contribution graph.

## 3. Daily loop for a work package

```powershell
git switch main
git pull                                   # start from the latest main
git switch -c feature/notification         # your package branch
# ... edit files, build, run the tests ...
git add src/ke/ac/kyu/campusgrade/server/SubscriptionManager.java
git commit -m "Add SubscriptionManager with leases" -m "Refs #6"
git push -u origin feature/notification
```

Rules for commits:
* one logical change per commit, message in the imperative ("Add …", "Fix …", "Remove …");
* never commit `out/`, `lib/`, `.class` files, logs or passwords (`.gitignore` already blocks them);
* run `.\scripts\build.ps1` and `.\scripts\test.ps1` before pushing.

## 4. Pull request and review

1. On GitHub: **Compare & pull request** from your branch into `main`.
2. Title: what it does. Description: what changed, how it was tested, `Closes #6`.
3. **Reviewers**: request your review partner (see TEAM.md).
4. The reviewer opens **Files changed**, leaves at least one real comment (a question or a
   suggestion on a line), then **Review changes → Approve** or **Request changes**.
5. The author answers each comment, pushes fixes to the same branch, and re-requests review.
6. After approval, the author clicks **Squash and merge** or **Merge pull request** and deletes the branch.

Every member must have **commits** and **at least one reviewed pull request**, and should also
**review** someone else's pull request. The lecturer judges contribution from commits, issues and
reviews, so do your own work under your own account.

## 5. Keeping your branch up to date

If `main` moved while you were working:

```powershell
git switch main
git pull
git switch feature/notification
git merge main          # fix conflicts if any, then commit
git push
```

## 6. Release

When all pull requests are merged and the tests pass on `main`:

```powershell
git switch main
git pull
git tag -a v1.0 -m "SPC2401 submission"
git push origin v1.0
```

Then download the zip from **Releases / Tags → v1.0** for the upload, together with the report PDF.
