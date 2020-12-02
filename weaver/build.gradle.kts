plugins {
//    java
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("commons-io:commons-io:2.4")

    implementation("org.ow2.asm:asm:9.0")
    implementation("org.ow2.asm:asm-commons:9.0")
    implementation("org.ow2.asm:asm-util:9.0")

    testImplementation("junit", "junit", "4.12")
}
