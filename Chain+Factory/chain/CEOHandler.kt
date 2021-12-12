package chain

class CEOHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    private var my_next1: Handler? = next1
    private var my_next2:Handler? = next2
    override fun handleRequest(priority: String, messageToBeProcessed: String) {
        var p=priority.toInt();
        when(p)
        {
            1->println("CEO: Prelucrez mesajul: "+messageToBeProcessed)
            else->{
                    println("CEO paseaza munca la Executiv: "+messageToBeProcessed)
                    next2!!.handleRequest(priority, messageToBeProcessed)
            }
        }
    }
    override fun set_next(next1: Handler?, next2: Handler?) {
        this.next1=next1
        this.next2=next2
    }
}