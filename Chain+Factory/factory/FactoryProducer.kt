package factory

class FactoryProducer {
    fun getFactory(choice: String): AbstractFactory {

        var aux=when(choice)
        {
            "EliteFactory"->EliteFactory()
            "HappyWorkerFactory"->HappyWorkerFactory()
            else->HappyWorkerFactory()
        }
        return aux
    }
}