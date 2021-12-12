package factory

import chain.Handler
import chain.HappyWorkerHandler

class HappyWorkerFactory: AbstractFactory() {
    override fun getHandler(handler: String): Handler {
        var aux=when(handler)
        {
            "HappyWorkerHandler"->HappyWorkerHandler()
            else->{print("Poti sa-ti dau numai muncitori (:");HappyWorkerHandler()}
        }
        return aux
    }
}