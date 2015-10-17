# Set Base Adress
Flags listen to a base address which can range from 0-255

This way multiple games can be played in a field with the same system.

# Setting the address
Run the following Java code:

```javac -cp .:lib/RXTXcomm.jar SetBaseAddress.java```

Afterwards run this command:

```java -cp .:lib/RXTXcomm.jar -Djava.library.path=/usr/lib/jni SetBaseAddress <baseaddr>```

If you omit baseaddr it will be set to 0.
