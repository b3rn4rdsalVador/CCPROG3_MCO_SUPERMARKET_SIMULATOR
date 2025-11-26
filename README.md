Hello there my dudes, the compilation process for this program is kind of complicated but nothing too impossible.
We used Intellij to make the program, the language used was Java, the build system was Maven. 

So you have to jump through some hoops to be able to compile and run the simulator.
First of all make sure you got a JDK installed. After that follow the steps below:

**STEP 1:** Go to your C:/Program Files/Java and within there should be your JDK version, open that folder and copy
that path. It should look something like this:

*C:\Program Files\Java\*jdk-version* or like *C:\Program Files\Java\jdk-25*

Copy that path.

**STEP 2:** Make sure you have admin permissions. Press your Windows key and type in "System environment variables", open that up. 
Under the **Advanced** tab click **Environment Variables**. Once you open that up, click the **New...** button under
**System variables**. A window should pop up asking for the new variable's name and value. For the variable name, put **JAVA_HOME**
and for the value, paste the copied path earlier (which in this case is *C:\Program Files\Java\jdk-25*). Once that's done you can 
click **Ok** on the subsequent windows.

**STEP 3:** I'm pretty sure at this point you've already downloaded the **CCPROG3_MCO_SUPERMARKET_SIMULATOR** zip file.
Make your way through that and find the **MCO2** folder and extract it. There should be two folders there named **idea** and **mvn**.
Rename them to **.idea** and **.mvn** respectively.

**STEP 4** Once you've done those steps we can now compile it. Go to the address bar and type in **cmd** so you can open a command
prompt in that directory. Type the following command:

**.\mvnw.cmd clean install**

Wait until the process finishes and there should be a new folder named **target** in MCO2. Open that up and go to the 
**SupermarketSimulator** folder, then **bin** folder, and in there should be a windows batch file named **SupermarketSimulator.bat**.
Double click that to run and you're done! The program asks you for your name, then age, and it opens up the Supermarket Simulator.
then go 
