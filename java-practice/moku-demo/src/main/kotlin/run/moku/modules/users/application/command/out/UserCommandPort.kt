package run.moku.modules.users.application.command.out

import run.moku.modules.users.domain.service.command.UserCommandRepository

interface UserCommandPort : UserCommandRepository {
}