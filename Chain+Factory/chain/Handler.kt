package chain

interface Handler {
    fun handleRequest(priority: String, messageToBeProcessed: String)
    fun set_next(next1:Handler?=null, next2:Handler?=null)
}