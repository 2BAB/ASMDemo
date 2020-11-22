package me.xx2bab.asmdemo

fun main() {
    AggregatedWeaver().process()
}

class AggregatedWeaver {

    fun process() {
        DebugFlags.processSingleClass = false
        DebugFlags.tempClassDir = "./app/build/libs/app-decompiled/me/xx2bab/asmdemo/"
    }

}