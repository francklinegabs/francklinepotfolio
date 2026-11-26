# 40. Why derive the class path from the code source?

*Topic: How the tests are built*

The JUnit console launcher loads our classes through its own loader, so `java.class.path` held only the JUnit jar. Child JVMs could not find our classes until we fixed this.
