# 08. What is java.rmi.server.hostname for?

*Topic: RMI in our design*

It is the address RMI writes into stubs. On a machine with several network cards, RMI may pick a wrong one, and clients on other machines then cannot connect. We set it from the `server.hostname` config key.
