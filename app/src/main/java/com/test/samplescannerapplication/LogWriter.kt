package com.test.samplescannerapplication

import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.Level


/**
 * Created by kovacsdavid on 10,március,2025
 */
object LogWriter {
    private const val DATE_FORMAT_TO_SAVE: String = "yyyy.MM.dd:hh:mm:ss"
    private const val FolderName: String = "folderName"
    private const val logFileName: String = "filename.txt"

    fun log(level: Level = Level.INFO, tag: String?, message: String?) {
        try {
            val root =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val logFolder = File(root, FolderName)
            if (!logFolder.exists()) {
                logFolder.mkdirs()
            }
            val logFile = File(logFolder, logFileName)
            if (!logFile.exists()) {
                logFile.createNewFile()
            }
            val buf = BufferedWriter(FileWriter(logFile, true))
            val calendar: Calendar = Calendar.getInstance()
            buf.append(
                SimpleDateFormat(DATE_FORMAT_TO_SAVE, Locale.getDefault()).format(
                    calendar.time
                )
            )
            buf.append(" - ")
            buf.append(level.name)
            buf.append(" - ")
            buf.append(tag)
            buf.append(" - ")
            buf.append(message)
            buf.newLine()
            buf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}