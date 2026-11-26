# 07. What is the difference between rmiregistry and createRegistry?

*Topic: RMI in our design*

`rmiregistry` is a separate process and needs the interface classes on its class path. `LocateRegistry.createRegistry(port)` starts a registry inside the server JVM. Our server supports both through `registry.create` in `kyu.properties`.
