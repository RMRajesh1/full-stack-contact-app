package workflow

import java.util.UUID

class HelperWorkflow {
    def generateRandomId(): String = {
        val randomId = UUID.randomUUID()
        randomId.toString()
    }
}
