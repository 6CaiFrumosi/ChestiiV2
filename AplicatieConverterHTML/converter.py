import sysv_ipc
import sys
from multiprocessing import Queue
def send_message(message_queue, message):
    message_queue.send(message, True, type=1)
    
def convert(path):
    text="<!DOCTYPE html>\n<html>\n<body>\n<center>\n"
    f = open(path, 'r')
    lines = f.readlines()
    #lines.remove("\n")
    lines[0]=lines[0].replace("\n", "")
    text+="<h>"+lines[0]+"</h>\n"
    text+="<body>\n"
    for i in range(1,len(lines)):
        lines[i]=lines[i].replace("\n", "")
        if (len(lines[i])!=0):
            text+="<pre>"+lines[i]+"</pre>\n"
    text+="</center>\n</body>\n</html>"
    f.close()
    #text="Ana are mere"
    return text

def main():
    try:
        text=convert(sys.argv[1])
        mq = sysv_ipc.MessageQueue(1234, sysv_ipc.IPC_CREAT)
        send_message(mq, text)
    except sysv_ipc.ExistentialError:
        print("ERROR: message queue creation failed")

if  __name__=='__main__':
    main()
