package chain

class ExecutiveHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    private var my_next1: Handler? = next1
    private var my_next2:Handler? = next2

    override fun handleRequest(priority: String, messageToBeProcessed: String) {
        var p=priority.toInt();
        when(p)
        {
            2->{println("Executiv: Prelucrez mesajul: "+messageToBeProcessed); next1!!.handleRequest("1", "Done by Executive: "+messageToBeProcessed)}
            else->{
                if(p>2)
                {
                    println("Executiv paseaza munca la Manager: "+messageToBeProcessed)
                    next2!!.handleRequest(priority, messageToBeProcessed)
                }
                else
                {
                    println("Executiv paseaza munca la CEO: "+messageToBeProcessed)
                    next1!!.handleRequest(priority, messageToBeProcessed)
                }
            }
        }
    }
    override fun set_next(next1: Handler?, next2: Handler?) {
        this.next1=next1
        this.next2=next2
    }
}