# 63. How would 4 worker threads look in an Amoeba GradeService?

*Topic: Section 5 – Amoeba*

The main thread registers the service, then creates 4 threads. Each one loops forever: getreq, check the rights in the capability, do SUBMIT or GET, then putrep.
