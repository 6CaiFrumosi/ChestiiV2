import factory.FactoryProducer
fun main(args: Array<String>) {
    // se creeaza 1xFactoryProducer, 1xEliteFactory, 1xHappyWorkerFactory
    //...
    var my_factories=FactoryProducer();
    var f1=my_factories.getFactory("EliteFactory")
    var f2=my_factories.getFactory("HappyWorkerFactory")
    // crearea instantelor (prin intermediul celor 2 fabrici):
    // 2xCEOHandler, 2xExecutiveHandler, 2xManagerHandler, 2xHappyWorkerHandler
    //...
    var ceo=f1.getHandler("CEOHandler")
    var exe=f1.getHandler("ExecutiveHandler")
    var man=f1.getHandler("ManagerHandler")
    var wk=f2.getHandler("HappyWorkerHandler")
    ceo.set_next(null, exe)
    exe.set_next(ceo, man)
    man.set_next(exe,wk)
    wk.set_next(man, null)
    // se construieste lantul (se verifica intai diagrama de obiecte si se realizeaza legaturile)
    //...
    exe.handleRequest("3", "Say Hello to Manager")
    print("\n\n")
    ceo.handleRequest("4", "Drive from A to B")
    // se executa lantul utilizand atat mesaje de prioritate diferita, cat si directii diferite in lant
    //...
}