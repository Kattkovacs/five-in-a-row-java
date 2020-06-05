# How To Use This File For Compile And Run:
# [1] Open a Terminal and step into the src folder of your project (your route should end with: .../src)
# [2] Run the command: chmod 755 make.sh (This command setup execute permission to your make.sh file) - YOU SHOULD MAKE THIS STEP ONLY ONCE! 
# [3] Run the command: sh make.sh (This ask Terminal to read and execute the content of your make.sh file)

# Following commands will be executed by the Terminal
find -name '*.java' | xargs javac -Xlint:all
cd main/java
java com.codecool.fiveinarow.FiveInARow
