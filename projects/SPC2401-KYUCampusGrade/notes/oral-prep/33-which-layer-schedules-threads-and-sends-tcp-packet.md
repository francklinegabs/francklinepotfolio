# 33. Which layer schedules threads and sends TCP packets?

*Topic: Section 2 – Notification and middleware*

The OS. The kernel scheduler runs our RMI, worker and notifier threads (Java threads map 1:1 to kernel threads), and the kernel TCP/IP stack sends the bytes written to our sockets.
