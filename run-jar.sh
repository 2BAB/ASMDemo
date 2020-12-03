# Because this is just a demo, so I don't want to build a fat-jar for weaver module (including all dependencies),
# thus you have to manually execute below actions before running this script:
#
# (To generate `app-1.0-SNAPSHOT.jar`, which is the original jar for input)
# 1. ./gradlew clean :app:assemble
# (To process bytecode modification, which generates `classes_modified_app-1.0-SNAPSHOT.jar`)
# 2. AggregatedWeaver#main(...) by IDEA
# (Run our modified jar by below command)

java -cp ./app/build/libs/classes_modified_app-1.0-SNAPSHOT.jar me.xx2bab.asmdemo.JavaTestMain