package dev.lkdcode.adapter.output.guard

import dev.lkdcode.application.ports.output.guard.DirectMessageGuard
import org.springframework.stereotype.Service


@Service
class DirectMessageGuardAdapter(

) : DirectMessageGuard {

    override fun requireMemberOfMessageRoom() {
        TODO("Not yet implemented")
    }
}