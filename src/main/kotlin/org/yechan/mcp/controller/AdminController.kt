package org.yechan.mcp.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.yechan.mcp.service.UrlShortenerService

@Controller
@RequestMapping("/admin")
class AdminController(private val urlShortenerService: UrlShortenerService) {
    
    private val logger = KotlinLogging.logger {}
    
    @GetMapping
    fun adminDashboard(model: Model): String {
        logger.info { "관리자 대시보드 접근" }
        
        val urlMappings = urlShortenerService.getAllUrls()
        model.addAttribute("urlMappings", urlMappings)
        
        return "admin/dashboard"
    }
}
