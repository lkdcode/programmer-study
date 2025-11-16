package dev.lkdcode.report1

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class ReportController {

    @GetMapping("/report1")
    fun gerReport1(): String = "report1"

    @GetMapping("/report2")
    fun gerReport2(): String = "report2"

    @GetMapping("/report3")
    fun gerReport3(): String = "report3"
}