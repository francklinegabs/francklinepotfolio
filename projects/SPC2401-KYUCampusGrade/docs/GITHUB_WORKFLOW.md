# How the team collaborates on GitHub

This is the step-by-step workflow the assignment's GitHub rules ask for, as we used it.
Repository: https://github.com/francklinegabs/SPC2401-Group12 (owner: Franckline Were, `francklinegabs`).
Commands are for Git Bash or PowerShell.

## 1. Team lead creates the repository (once)

1. GitHub → **New repository** → name `SPC2401-Group12` → **Public**. (On a free account GitHub only
   enforces branch protection on public repositories, so the repository is public.)
2. **Settings → Collaborators → Add people**: `ewairi` (the lecturer), `Kyrie-ML`, `gikarueunice`,
   `odiwuor-ashley` and `JustJeff-hub`.
3. **Settings → Branches → Add branch protection rule** for `main`:
   * Require a pull request before merging
   * Require approvals: **1**
   * Do not allow bypassing the above settings (applies to the owner too)
   This makes "nobody pushes straight to main" a rule GitHub enforces.
4. Make the first commit on `main` so branches have something to start from.
5. **Issues → New issue** for every pull request in [WORK_PACKAGES.md](WORK_PACKAGES.md) (issues #1–#7),
   each assigned to its owner.

## 2. Each member sets up (once)

```powershell
git clone https://github.com/francklinegabs/SPC2401-Group12.git
cd SPC2401-Group12
git config user.name  "<your name from the table>"
git config user.email "<your e-mail from the table>"
```

| Member | GitHub | `user.name` | `user.email` |
|---|---|---|---|
| Franckline Were | francklinegabs | francklinegabs | 151562242+francklinegabs@users.noreply.github.com |
| Kelvin Lemaitoi | Kyrie-ML | Kelvin Lemaitoi | 299208418+Kyrie-ML@users.noreply.github.com |
| Eunice Gikaru | gikarueunice | Eunice Gikaru | 271707942+gikarueunice@users.noreply.github.com |
| Ashley Odiwuor | odiwuor-ashley | Ashley Odiwuor | 181076415+odiwuor-ashley@users.noreply.github.com |
| Jeff Njuguna | JustJeff-hub | Jeff Njuguna | 180539728+JustJeff-hub@users.noreply.github.com |

These are each account's GitHub no-reply addresses, so commits are linked to the right account
without publishing anyone's private e-mail.

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
