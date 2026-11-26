# 16. Step 4: what does the dispatcher (skeleton) do?

*Topic: Section 1 – Remote invocation*

`UnicastServerRef.dispatch` finds the object by its ID, maps the method hash to a `Method`, unmarshals the arguments into fresh server-side copies, and invokes the method by reflection.
