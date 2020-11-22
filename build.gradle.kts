buildscript {

    project.extra["kotlinVersion"] = "1.4.10"

    repositories {
        google()
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = project.extra["kotlinVersion"].toString()))
    }

}

allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
}