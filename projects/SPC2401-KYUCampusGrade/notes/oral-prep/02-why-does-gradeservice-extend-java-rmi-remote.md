# 02. Why does GradeService extend java.rmi.Remote?

*Topic: RMI in our design*

`Remote` is a marker interface. It tells RMI that objects of this type are passed by remote reference (as a stub) and that its methods may be called from another JVM. Without it, `exportObject` and the registry would reject the type.
