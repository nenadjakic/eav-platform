package com.github.nenadjakic.eav.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
open class EMailService(private val javaMailSender: JavaMailSender) {

    @Async
    open fun send(to: String, subject: String, text: String) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text, false)

        javaMailSender.send(message)
    }
}