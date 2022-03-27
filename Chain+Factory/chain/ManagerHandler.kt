package chain

class ManagerHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    private var my_next1: Handler? = next1
    private var my_next2:Handler? = next2
    override fun handleRequest(priority: String, messageToBeProcessed: String) {
        var p=priority.toInt();
        when(p)
        {
            3->{println("Manager: Prelucrez mesajul: "+messageToBeProcessed);next1!!.handleRequest("1", "Done by Manager: "+messageToBeProcessed)}
            else->{
                if(p>3)
                {
                    println("Manager paseaza munca la Worker")
                    next2!!.handleRequest(priority, messageToBeProcessed)
                }
                else
                {
                    println("Manager paseaza munca la Executiv")
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